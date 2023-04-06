package com.marpe.cht.services;

import java.math.BigDecimal;
import java.sql.Date;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.Instant;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.marpe.cht.entities.Colaborador;
import com.marpe.cht.entities.OS;
import com.marpe.cht.entities.OSColab;
import com.marpe.cht.repositories.ColaboradorRepository;
import com.marpe.cht.repositories.OSColabRepository;
import com.marpe.cht.repositories.OSRepository;
import com.marpe.cht.repositories.ReportRepository;
import com.marpe.cht.services.exceptions.InvalidRequestException;

@Service
public class ReportService {

	DateTimeFormatter dtfmt = DateTimeFormatter.ofPattern("dd/MM/yyyy");
	
	@Autowired
	private OSColabRepository oscolabRepository;
	
	@Autowired
	private OSRepository osRepository;
	
	@Autowired
	private ColaboradorRepository colaboradorRepository;
	
	@Autowired
	private ReportRepository reportRepository;
	
	
	public List<OSColab> OSColabPorPeriodo(String startDate, String endDate) {
	    
		if (startDate == null || endDate == null) {
			throw new InvalidRequestException("Both startDate and endDate request parameters are mandatory.");
		}

		LocalDate DataInicio = LocalDate.parse(startDate, dtfmt);
		LocalDate DataFim = LocalDate.parse(endDate, dtfmt);
		
		List<OS> os = osRepository.findAll();
		List<OSColab> oscolabInThisPeriod = new ArrayList<OSColab>();

		for(OS x : os) {
			if (x.getDataInicio().isAfter(DataInicio) && x.getDataInicio().isBefore(DataFim)) {
				for(OSColab y : x.getOscolab()) {
					oscolabInThisPeriod.add(y);	
				}
			}
		}
		
		if(oscolabInThisPeriod.isEmpty()) {
			throw new InvalidRequestException("No record within the selected period.");
			} else {
				return oscolabInThisPeriod;
		}
	}
	
	
	public List<OSColab> OSColabPorPeriodoEPago(String startDate, String endDate, Boolean concluida) {
	    
		  java.sql.Date sqlDataInicio;
		  java.sql.Date sqlDataFim;
		  
		if(startDate == null) {
			java.util.Date dataInicio = new Date(0);
			dataInicio = Date.from(Instant.now().minus(15, ChronoUnit.DAYS));
			sqlDataInicio = new java.sql.Date(dataInicio.getTime());
		} else {
			java.util.Date dataInicio = new Date(0);
			try {
				dataInicio = new SimpleDateFormat("dd-MM-yyyy").parse(startDate);
			} catch (ParseException e) {
				e.printStackTrace();
			}
			sqlDataInicio = new java.sql.Date(dataInicio.getTime());
		}

		if(endDate == null) {
			java.util.Date dataFim = new Date(0);
			dataFim = Date.from(Instant.now());
			sqlDataFim = new java.sql.Date(dataFim.getTime());
		} else {
			java.util.Date dataFim = new Date(0);
			try {
				dataFim = new SimpleDateFormat("dd-MM-yyyy").parse(endDate);
			} catch (ParseException e) {
				e.printStackTrace();
			}
			sqlDataFim = new java.sql.Date(dataFim.getTime());
		}

		List<OSColab> oscolab = reportRepository.getOSColabPorPeriodo(sqlDataInicio, sqlDataFim);		
		List<OSColab> pagas = oscolab.stream()
			.filter(x -> x.getPago().equals(concluida))
			.collect(Collectors.toList());
		
		if(pagas.isEmpty()) {
			throw new InvalidRequestException("No OS within the selected period.");
		} else {
			return pagas;
		}
	  }
  
	
	
	public List<Object[]> OSColabSomadoPorPeriodo(String startDate, String endDate, Boolean todosPagos) {
	
		java.util.Date dataInicio = new Date(0);
		try {
			dataInicio = new SimpleDateFormat("dd-MM-yyyy").parse(startDate);
		} catch (ParseException e) {
			e.printStackTrace();
		}
		java.sql.Date sqlDataInicio = new java.sql.Date(dataInicio.getTime());
		
		java.util.Date dataFim = new Date(0);
		try {
			dataFim = new SimpleDateFormat("dd-MM-yyyy").parse(endDate);
		} catch (ParseException e) {
			e.printStackTrace();
		}
		java.sql.Date sqlDataFim = new java.sql.Date(dataFim.getTime());
		
		List<Object[]> result = reportRepository.getOSColabSomadoPorPeriodo(sqlDataInicio, sqlDataFim, todosPagos);
		
		return result;
		
	}
	
	public List<Object[]> topFiveOscolabSomadoPorPeriodo(String startDate, String endDate) {
		
		  java.sql.Date sqlDataInicio;
		  java.sql.Date sqlDataFim;
		  
		if(startDate == null) {
			java.util.Date dataInicio = new Date(0);
			dataInicio = Date.from(Instant.now().minus(1, ChronoUnit.MONTHS));
			sqlDataInicio = new java.sql.Date(dataInicio.getTime());
		} else {
			java.util.Date dataInicio = new Date(0);
			try {
				dataInicio = new SimpleDateFormat("dd-MM-yyyy").parse(startDate);
			} catch (ParseException e) {
				e.printStackTrace();
			}
			sqlDataInicio = new java.sql.Date(dataInicio.getTime());
		}

		if(endDate == null) {
			java.util.Date dataFim = new Date(0);
			dataFim = Date.from(Instant.now());
			sqlDataFim = new java.sql.Date(dataFim.getTime());
		} else {
			java.util.Date dataFim = new Date(0);
			try {
				dataFim = new SimpleDateFormat("dd-MM-yyyy").parse(endDate);
			} catch (ParseException e) {
				e.printStackTrace();
			}
			sqlDataFim = new java.sql.Date(dataFim.getTime());
		}
	
		List<Object[]> result = reportRepository.topFiveOscolabSomadoPorPeriodo(sqlDataInicio, sqlDataFim);
		
		List<Object[]> completeList = result.stream()
			.map(x -> {
				x[0] = getNomeColaborador(new BigDecimal(x[0].toString()).longValue());
				return new Object[]{x[0], x[1]};
			})
			.collect(Collectors.toList());
		
		return completeList;
		
	}
	
	public String getNomeColaborador(Long id) {
		List<Colaborador> list = colaboradorRepository.findAll();
		String nome = "";
		for(Colaborador c : list) {
			if(c.getId().equals(id)) {
				nome = c.getUser().getNome();
			}
		}
		return nome;
	}
	

	public List<OS> listOSDesc5() {
		List<OS> list = osRepository.findAll().stream()
				.sorted((f1, f2) -> Long.compare(f2.getId(), f1.getId()))
				.limit(5)
				.collect(Collectors.toList());
		return list;
	}
	
	public List<OSColab> listOSColabDesc5() {
		List<OSColab> list = oscolabRepository.findAll().stream()
				.sorted((f1, f2) -> Long.compare(f2.getId(), f1.getId()))
				.limit(5)
				.collect(Collectors.toList());
		return list;
	}
	
	public List<Colaborador> listColaboradorDesc5() {
		List<Colaborador> list = colaboradorRepository.findAll().stream()
				.sorted((f1, f2) -> Long.compare(f2.getId(), f1.getId()))
				.limit(5)
				.collect(Collectors.toList());
		return list;
	}
	
	
	
}
