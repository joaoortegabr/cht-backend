package com.marpe.cht.services;

import java.util.List;
import java.util.Optional;

import javax.persistence.EntityNotFoundException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.stereotype.Service;

import com.marpe.cht.entities.Colaborador;
import com.marpe.cht.repositories.ColaboradorRepository;
import com.marpe.cht.services.exceptions.DatabaseException;
import com.marpe.cht.services.exceptions.ResourceNotFoundException;

@Service
public class ColaboradorService {

	@Autowired
	private ColaboradorRepository repository;
	
	public List<Colaborador> findAll() {
		return repository.findAll();
	}
	
	public List<Colaborador> findAllDescendingOrder() {
		List<Colaborador> list = repository.findAll().stream()
				.sorted((f1, f2) -> Long.compare(f2.getId(), f1.getId()))
				.toList();
		return list;
	}
	
	public Colaborador findById(Long id) {
		Optional<Colaborador> obj = repository.findById(id);
		return obj.orElseThrow(() -> new ResourceNotFoundException(id));
	}
	
	public Colaborador insert(Colaborador obj) {
		return repository.save(obj);
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
	
	public Colaborador update(Long id, Colaborador obj) {
		try {
			Colaborador entity = repository.getReferenceById(id);
			updateData(entity, obj);
			return repository.save(entity);
		} catch (EntityNotFoundException e) {
			throw new ResourceNotFoundException(id);
		}
	}

	private void updateData(Colaborador entity, Colaborador obj) {
		entity.setUser(obj.getUser());
		entity.setBanco(obj.getBanco());
		entity.setAgencia(obj.getAgencia());
		entity.setConta(obj.getConta());
		entity.setOperacao(obj.getOperacao());
		entity.setTitular(obj.getTitular());
		entity.setCidade(obj.getCidade());
		
	}
	

	
}
