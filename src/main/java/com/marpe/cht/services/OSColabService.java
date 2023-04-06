package com.marpe.cht.services;

import java.time.Duration;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.Optional;

import javax.persistence.EntityNotFoundException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.stereotype.Service;

import com.marpe.cht.entities.OS;
import com.marpe.cht.entities.OSColab;
import com.marpe.cht.repositories.OSColabRepository;
import com.marpe.cht.repositories.OSRepository;
import com.marpe.cht.repositories.UserRepository;
import com.marpe.cht.services.exceptions.DatabaseException;
import com.marpe.cht.services.exceptions.ResourceNotFoundException;

@Service
public class OSColabService {

	@Autowired
	private OSColabRepository repository;
	
	@Autowired
	private OSRepository OSRepository;
	
	@Autowired
	UserRepository userRepository;
	
	public List<OSColab> findAll() {
		return repository.findAll();
	}
	
	public List<OSColab> findAllDescendingOrder() {
		List<OSColab> list = repository.findAll().stream()
				.sorted((f1, f2) -> Long.compare(f2.getId(), f1.getId()))
				.toList();
		return list;
	}
	
	DateTimeFormatter dtfmt = DateTimeFormatter.ofPattern("dd/MM/yyyy");
	
	public OSColab findById(Long id) {
		Optional<OSColab> obj = repository.findById(id);
		return obj.orElseThrow(() -> new ResourceNotFoundException(obj));
	}
	
	public OSColab insert(OSColab obj) {
		
		Optional<OS> os = OSRepository.findById(obj.getOs().getId());
		try {
			if(obj.getHoraFinal() != null) {
				calcularHorasAPagar(obj, os.get());
				calcularTotalAReceber(obj, os.get());
			}
			os.get().addOscolab(obj);
			return repository.save(obj);
		} catch (Exception e) {
			throw new ResourceNotFoundException(obj);
		}
	}

	public void delete(Long id) {
		try {
			repository.deleteById(id);	
		} catch(EmptyResultDataAccessException e) {
			throw new ResourceNotFoundException(id);
		} catch(DataIntegrityViolationException e) {
			throw new DatabaseException(e.getMessage());
		}
	}
	
	public OSColab update(Long id, OSColab obj) {
		try {
			OSColab entity = repository.getReferenceById(id);
			Optional<OS> os = OSRepository.findById(obj.getOs().getId());
					
			updateData(entity, obj, os.get());
			return repository.save(entity);
		} catch (EntityNotFoundException e) {
			throw new ResourceNotFoundException(id);
		}
	}

	private void updateData(OSColab entity, OSColab obj, OS os) {
		entity.setHoraInicial(obj.getHoraInicial());
		entity.setHoraFinal(obj.getHoraFinal());
		entity.setIntervalo(obj.getIntervalo());
		entity.setTransportes(obj.getTransportes());
		entity.setPago(obj.getPago());
		entity.setColaborador(obj.getColaborador());
		
		if(obj.getHoraFinal() == null) {
			entity.setTotalHorasDiurnas(0.0);
			entity.setTotalHorasNoturnas(0.0);
			entity.setTotalAReceber(0.0);
		} else {
			calcularHorasAPagar(obj, os);
			entity.setTotalHorasDiurnas(obj.getTotalHorasDiurnas());
			entity.setTotalHorasNoturnas(obj.getTotalHorasNoturnas());
			calcularTotalAReceber(obj, os);
			entity.setTotalAReceber(obj.getTotalAReceber());
		}
	
	}
	
	
	public void calcularTotalAReceber(OSColab obj, OS os) {
		
		Double valorHoraDiurna = os.getRegional().getValorHoraDiurna();
		Double valorHoraNoturna = os.getRegional().getValorHoraNoturna();
		Double valorTransporte = os.getRegional().getValorTransporte();
		Integer transportes = obj.getTransportes();	
		
		Double totalAReceber = 0.0;
		Double totalHorasDiurnas = obj.getTotalHorasDiurnas();
		Double totalHorasNoturnas = obj.getTotalHorasNoturnas();
		
		totalAReceber += (totalHorasDiurnas * valorHoraDiurna) + (totalHorasNoturnas * valorHoraNoturna);
		
		if(transportes > 0) {
			totalAReceber += (transportes * valorTransporte);
		}
		obj.setTotalAReceber(totalAReceber);
	}
	
	
	public void calcularHorasAPagar(OSColab obj, OS os) {
				
		Duration periodo = Duration.between(obj.getHoraInicial(), obj.getHoraFinal());
		Double totalHorasDiurnas = 0.0;
		Double totalHorasNoturnas = 0.0;
		Double totalDeHoras = 0.0;
		Double minutos = (double) periodo.toMinutes();
		
		if(minutos < 0) {
			minutos += 1440;
		}

		if((minutos % 60) == 0) {
			totalDeHoras = (minutos / 60);
		} else if((minutos % 60) <= 30) {
			totalDeHoras = (int) (minutos / 60) + 0.5;
		} else {
			totalDeHoras = (int) (minutos / 60) + 1.0;
		}
		
		if(obj.getIntervalo()) {
			totalDeHoras -= 1.0;
		}
		
		if(totalDeHoras < os.getRegional().getHorasPadrao()) {
			totalDeHoras = os.getRegional().getHorasPadrao();
		}
				
		switch(obj.getHoraInicial().getHour()) {
			case 0: 
				if(totalDeHoras > 5) {
					totalHorasNoturnas = 5.0;
					totalHorasDiurnas = totalDeHoras - totalHorasNoturnas;
					break;
				} else {
					totalHorasNoturnas = totalDeHoras;
					break;
				}

			case 1:
				if(totalDeHoras > 4) {
					totalHorasNoturnas = 4.0;
					totalHorasDiurnas = totalDeHoras - totalHorasNoturnas;
					break;
				} else {
					totalHorasNoturnas = totalDeHoras;
					break;
				}
				
			case 2:
				if(totalDeHoras > 3) {
					totalHorasNoturnas = 3.0;
					totalHorasDiurnas = totalDeHoras - totalHorasNoturnas;
					break;
				} else {
					totalHorasNoturnas = totalDeHoras;
					break;
				}
				
			case 3:
				if(totalDeHoras > 2) {
					totalHorasNoturnas = 2.0;
					totalHorasDiurnas = totalDeHoras - totalHorasNoturnas;
					break;
				} else {
					totalHorasNoturnas = totalDeHoras;
					break;
				}
				
			case 4:
				if(totalDeHoras > 1) {
					totalHorasNoturnas = 1.0;
					totalHorasDiurnas = totalDeHoras - totalHorasNoturnas;
					break;
				} else {
					totalHorasNoturnas = totalDeHoras;
					break;
				}
				
			case 5:
				if(totalDeHoras > 18) {
					totalHorasDiurnas = 17.0;
					totalHorasNoturnas = totalDeHoras - totalHorasDiurnas;
					break;
				} else {
					totalHorasDiurnas = totalDeHoras;
					break;
				}
				
			case 6:
				if(totalDeHoras > 17) {
					totalHorasDiurnas = 16.0;
					totalHorasNoturnas = totalDeHoras - totalHorasDiurnas;
					break;
				} else {
					totalHorasDiurnas = totalDeHoras;
					break;
				}
				
			case 7:
				if(totalDeHoras > 16) {
					totalHorasDiurnas = 15.0;
					totalHorasNoturnas = totalDeHoras - totalHorasDiurnas;
					break;
				} else {
					totalHorasDiurnas = totalDeHoras;
					break;
				}
				
			case 8:
				if(totalDeHoras > 15) {
					totalHorasDiurnas = 14.0;
					totalHorasNoturnas = totalDeHoras - totalHorasDiurnas;
					break;
				} else {
					totalHorasDiurnas = totalDeHoras;
					break;
				}
				
			case 9:
				if(totalDeHoras > 14) {
					totalHorasDiurnas = 13.0;
					totalHorasNoturnas = totalDeHoras - totalHorasDiurnas;
					break;
				} else {
					totalHorasDiurnas = totalDeHoras;
					break;
				}
				
			case 10:
				if(totalDeHoras > 13) {
					totalHorasDiurnas = 12.0;
					totalHorasNoturnas = totalDeHoras - totalHorasDiurnas;
					break;
				} else {
					totalHorasDiurnas = totalDeHoras;
					break;
				}
				
			case 11:
				if(totalDeHoras > 12) {
					totalHorasDiurnas = 11.0;
					totalHorasNoturnas = totalDeHoras - totalHorasDiurnas;
					break;
				} else {
					totalHorasDiurnas = totalDeHoras;
					break;
				}
				
			case 12:
				if(totalDeHoras > 11) {
					totalHorasDiurnas = 10.0;
					totalHorasNoturnas = totalDeHoras - totalHorasDiurnas;
					break;
				} else {
					totalHorasDiurnas = totalDeHoras;
					break;
				}
				
			case 13:
				if(totalDeHoras > 10) {
					totalHorasDiurnas = 9.0;
					totalHorasNoturnas = totalDeHoras - totalHorasDiurnas;
					break;
				} else {
					totalHorasDiurnas = totalDeHoras;
					break;
				}
				
			case 14:
				if(totalDeHoras > 9) {
					totalHorasDiurnas = 8.0;
					totalHorasNoturnas = totalDeHoras - totalHorasDiurnas;
					break;
				} else {
					totalHorasDiurnas = totalDeHoras;
					break;
				}

			case 15:
				if(totalDeHoras > 8) {
					totalHorasDiurnas = 7.0;
					totalHorasNoturnas = totalDeHoras - totalHorasDiurnas;
					break;
				} else {
					totalHorasDiurnas = totalDeHoras;
					break;
				}
				
			case 16:
				if(totalDeHoras > 7) {
					totalHorasDiurnas = 6.0;
					totalHorasNoturnas = totalDeHoras - totalHorasDiurnas;
					break;
				} else {
					totalHorasDiurnas = totalDeHoras;
					break;
				}
				
			case 17:
				if(totalDeHoras > 6) {
					totalHorasDiurnas = 5.0;
					totalHorasNoturnas = totalDeHoras - totalHorasDiurnas;
					break;
				} else {
					totalHorasDiurnas = totalDeHoras;
					break;
				}
				
			case 18:
				if(totalDeHoras > 5) {
					totalHorasDiurnas = 4.0;
					totalHorasNoturnas = totalDeHoras - totalHorasDiurnas;
					break;
				} else {
					totalHorasDiurnas = totalDeHoras;
					break;
				}
				
			case 19:
				if(totalDeHoras > 4) {
					totalHorasDiurnas = 3.0;
					totalHorasNoturnas = totalDeHoras - totalHorasDiurnas;
					break;
				} else {
					totalHorasDiurnas = totalDeHoras;
					break;
				}
				
			case 20:
				if(totalDeHoras > 3) {
					totalHorasDiurnas = 2.0;
					totalHorasNoturnas = totalDeHoras - totalHorasDiurnas;
					break;
				} else {
					totalHorasDiurnas = totalDeHoras;
					break;
				}
				
			case 21:
				if(totalDeHoras > 2) {
					totalHorasDiurnas = 1.0;
					totalHorasNoturnas = totalDeHoras - totalHorasDiurnas;
					break;
				} else {
					totalHorasDiurnas = totalDeHoras;
					break;
				}
				
			case 22:
				if(totalDeHoras > 7) {
					totalHorasNoturnas = 7.0;
					totalHorasDiurnas = totalDeHoras - totalHorasNoturnas;
					break;
				} else {
					totalHorasNoturnas = totalDeHoras;
					break;
				}
				
			case 23:
				if(totalDeHoras > 6) {
					totalHorasNoturnas = 6.0;
					totalHorasDiurnas = totalDeHoras - totalHorasNoturnas;
					break;
				} else {
					totalHorasNoturnas = totalDeHoras;
					break;
				}
				
			default: //do nothing
					break;
		}
		
		obj.setTotalHorasDiurnas(totalHorasDiurnas);
		obj.setTotalHorasNoturnas(totalHorasNoturnas);
		
	}
	
	
		
}