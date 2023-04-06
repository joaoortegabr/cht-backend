package com.marpe.cht.services;

import java.util.List;
import java.util.Optional;

import javax.persistence.EntityNotFoundException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.stereotype.Service;

import com.marpe.cht.entities.Coordenador;
import com.marpe.cht.repositories.CoordenadorRepository;
import com.marpe.cht.services.exceptions.DatabaseException;
import com.marpe.cht.services.exceptions.ResourceNotFoundException;

@Service
public class CoordenadorService {

	@Autowired
	private CoordenadorRepository repository;
	
	public List<Coordenador> findAll() {
		return repository.findAll();
	}
	
	public Coordenador findById(Long id) {
		Optional<Coordenador> obj = repository.findById(id);
		return obj.orElseThrow(() -> new ResourceNotFoundException(id));
	}
	
	public Coordenador insert(Coordenador obj) {
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
	
	public Coordenador update(Long id, Coordenador obj) {
		try {
			Coordenador entity = repository.getReferenceById(id);
			updateData(entity, obj);
			return repository.save(entity);
		} catch (EntityNotFoundException e) {
			throw new ResourceNotFoundException(id);
		}
	}

	private void updateData(Coordenador entity, Coordenador obj) {
		entity.setUser(obj.getUser());
		entity.setRegional(obj.getRegional());	
		
//		  entity.getUser().setLogin(obj.getUser().getLogin());
//		  entity.getUser().setPassword(obj.getUser().getPassword());
//		  entity.getUser().setNome(obj.getUser().getNome());
//		  entity.getUser().setRg(obj.getUser().getRg());
//		  entity.getUser().setCpf(obj.getUser().getCpf());
//		  entity.getUser().setTelefone(obj.getUser().getTelefone());
//		  entity.getUser().setEmail(obj.getUser().getEmail());
//		  entity.getUser().setAtivo(obj.getUser().getAtivo());
//		  entity.getUser().setPerfil(obj.getUser().getPerfil());
//		  entity.getRegional().setNome(obj.getRegional().getNome());
		 
	}
	
}
