package com.marpe.cht.services.export;

import java.io.ByteArrayInputStream;
import java.sql.Date;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.Instant;
import java.time.format.DateTimeFormatter;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.marpe.cht.entities.Colaborador;
import com.marpe.cht.entities.Coordenador;
import com.marpe.cht.entities.OS;
import com.marpe.cht.entities.OSColab;
import com.marpe.cht.export.helper.ExcelHelper;
import com.marpe.cht.repositories.ColaboradorRepository;
import com.marpe.cht.repositories.CoordenadorRepository;
import com.marpe.cht.repositories.OSColabRepository;
import com.marpe.cht.repositories.OSRepository;
import com.marpe.cht.repositories.ReportRepository;
import com.marpe.cht.repositories.UserRepository;
import com.marpe.cht.services.exceptions.InvalidRequestException;

@Service
public class ExcelService {
	
  @Autowired
  UserRepository repository;
  
  @Autowired
  ColaboradorRepository colaboradoresRepository;
  
  @Autowired
  CoordenadorRepository coordenadorRepository;
  
  @Autowired
  OSRepository osRepository;
  
  @Autowired
  OSColabRepository oscolabRepository;
  
  @Autowired
  ReportRepository reportRepository;
  
  DateTimeFormatter dtfmt = DateTimeFormatter.ofPattern("dd/MM/yyyy");

  public ByteArrayInputStream listCoordenadores() {
	    List<Coordenador> coordenadores = coordenadorRepository.findAll();

	    ByteArrayInputStream in = ExcelHelper.coordenadoresToExcel(coordenadores);
	    return in;
	  }
  
  public ByteArrayInputStream listColaboradores() {
	    List<Colaborador> colaboradores = colaboradoresRepository.findAll();
	
	    ByteArrayInputStream in = ExcelHelper.colaboradoresToExcel(colaboradores);
	    return in;
	  }
  
  public ByteArrayInputStream listOS() {
	    List<OS> os = osRepository.findAll();

	    ByteArrayInputStream in = ExcelHelper.osToExcel(os);
	    return in;
	  }
  
  public ByteArrayInputStream listOSColab() {
	    List<OSColab> oscolab = oscolabRepository.findAll();

	    ByteArrayInputStream in = ExcelHelper.oscolabToExcel(oscolab);
	    return in;
	  }
  
  public ByteArrayInputStream listColaboradoresPorCidade(String cidade) {
	    List<Colaborador> colaboradores = colaboradoresRepository.findAll();
	
	    ByteArrayInputStream in = ExcelHelper.colaboradoresPorCidadeToExcel(colaboradores, cidade);
	    return in;
	  }
  
  public ByteArrayInputStream OSColabPorPeriodo(String startDate, String endDate, Boolean todosPagos) {
	    
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

		
		List<OSColab> oscolabs = reportRepository.getOSColabPorPeriodo(sqlDataInicio, sqlDataFim);

		List<OSColab> filtradas = new ArrayList<OSColab>();
		
		if(todosPagos.equals(true)) {
			filtradas = oscolabs.stream()
					.filter(x -> x.getOs().getConcluida().equals(true))
					.filter(x -> x.getOs().getTodosPagos().equals(false))
					.collect(Collectors.toList());
		} else {
			filtradas = oscolabs.stream()
					.filter(x -> x.getOs().getConcluida().equals(true))
					.collect(Collectors.toList());
		}

		
		if(filtradas.isEmpty()) {
			throw new InvalidRequestException("No OSColab within the selected period.");
		} else {
			ByteArrayInputStream in = ExcelHelper.oscolabPorPeriodo(filtradas);
			return in; 
		}
	  }

  public ByteArrayInputStream OSColabSomadoPorPeriodo(String startDate, String endDate, Boolean todosPagos) {
	    
	  java.sql.Date sqlDataInicio;
	  java.sql.Date sqlDataFim;
	  
		if(startDate == null) {
			java.util.Date dataInicio = new Date(0);
			dataInicio = Date.from(Instant.EPOCH);
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
		
	    List<Object[]> result = reportRepository.getOSColabSomadoPorPeriodo(sqlDataInicio, sqlDataFim, todosPagos);

		List<Colaborador> colaboradores = colaboradoresRepository.findAll();

		if(result.isEmpty()) {
			throw new InvalidRequestException("No OS within the selected period.");
		} else {
			ByteArrayInputStream in = ExcelHelper.oscolabSomadoPorPeriodo(colaboradores, result);
			return in; 
		}
	  }
  

//  public ByteArrayInputStream OSColabPorPeriodoEStatus(String startDate, String endDate, Boolean status) {
//	    
//		if (startDate == null || endDate == null) {
//			throw new InvalidRequestException("Both startDate and endDate request parameters are mandatory.");
//		}
//		
//	    LocalDate DataInicio = LocalDate.parse(startDate, dtfmt);
//		LocalDate DataFim = LocalDate.parse(endDate, dtfmt);
//		
//		List<OSColab> oscolab = oscolabRepository.findAll();
//		List<OSColab> oscolabInThisPeriod = new ArrayList<OSColab>();
//		
//		for(OSColab x : oscolab) {
//			if (x.getOs().getDataInicio().isAfter(DataInicio) && x.getOs().getDataInicio().isBefore(DataFim)) {
//		    	  if(x.getOs().getConcluida().equals(status)) {
//		    		  oscolabInThisPeriod.add(x);
//		    	  }
//			}
//		}
//
//		if(oscolabInThisPeriod.isEmpty()) {
//			throw new InvalidRequestException("No OS within the selected period.");
//		} else {
//			ByteArrayInputStream in = ExcelHelper.oscolabPorPeriodoEStatus(oscolabInThisPeriod, status);
//			return in; 
//		}
//	  }
  
//  public ByteArrayInputStream OSColabPorPeriodoEPago(String startDate, String endDate, Boolean pago) {
//	    
//	  java.sql.Date sqlDataInicio;
//	  java.sql.Date sqlDataFim;
//	  
//		if(startDate == null) {
//			java.util.Date dataInicio = new Date(0);
//			dataInicio = Date.from(Instant.EPOCH);
//			sqlDataInicio = new java.sql.Date(dataInicio.getTime());
//		} else {
//			java.util.Date dataInicio = new Date(0);
//			try {
//				dataInicio = new SimpleDateFormat("dd-MM-yyyy").parse(startDate);
//			} catch (ParseException e) {
//				e.printStackTrace();
//			}
//			sqlDataInicio = new java.sql.Date(dataInicio.getTime());
//		}
//
//		if(endDate == null) {
//			java.util.Date dataFim = new Date(0);
//			dataFim = Date.from(Instant.now());
//			sqlDataFim = new java.sql.Date(dataFim.getTime());
//		} else {
//			java.util.Date dataFim = new Date(0);
//			try {
//				dataFim = new SimpleDateFormat("dd-MM-yyyy").parse(endDate);
//			} catch (ParseException e) {
//				e.printStackTrace();
//			}
//			sqlDataFim = new java.sql.Date(dataFim.getTime());
//		}
//		
//	    List<OSColab> result = reportRepository.getOSColabPorPeriodoEPago(sqlDataInicio, sqlDataFim, pago);
//		List<Colaborador> colaboradores = colaboradoresRepository.findAll();
//
//		if(result.isEmpty()) {
//			throw new InvalidRequestException("No OS within the selected period.");
//		} else {
//			ByteArrayInputStream in = ExcelHelper.oscolabPorPeriodoEPago(colaboradores, result);
//			return in; 
//		}
//	  }

  
  
  


  
  

}
