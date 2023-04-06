
package com.marpe.cht.services;

import java.util.List;
import java.util.Optional;

import javax.persistence.EntityNotFoundException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.stereotype.Service;

import com.marpe.cht.entities.User;
import com.marpe.cht.repositories.UserRepository;
import com.marpe.cht.services.exceptions.DatabaseException;
import com.marpe.cht.services.exceptions.ExistingUserException;
import com.marpe.cht.services.exceptions.ResourceNotFoundException;

@Service
public class UserService {

	@Autowired
	private UserRepository repository;
	
	public List<User> findAll() {
		return repository.findAll();
	}
	
	public User findById(Long id) {
		Optional<User> obj = repository.findById(id);
		return obj.orElseThrow(() -> new ResourceNotFoundException(id));
	}
	
	public User insert(User obj) {
		if(repository.findByEmail(obj.getEmail()) != null) {
			throw new Error("User already exists.");
		}
		if(verifyExistingEmail(obj)) {
			throw new ExistingUserException("Email already registered.");
		}
		if(verifyExistingCpf(obj)) {
			throw new ExistingUserException("CPF already registered.");
		}
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
	
	public User update(Long id, User obj) {
		try {
			User entity = repository.getReferenceById(id);
			updateData(entity, obj);
			return repository.save(entity);
		} catch (EntityNotFoundException e) {
			throw new ResourceNotFoundException(id);
		}
	}

	private void updateData(User entity, User obj) {
		entity.setPassword(obj.getPassword());
		entity.setNome(obj.getNome());
		entity.setRg(obj.getRg());
		entity.setCpf(obj.getCpf());
		entity.setTelefone(obj.getTelefone());
		entity.setEmail(obj.getEmail());
		entity.setAtivo(obj.getAtivo());
		entity.setPerfil(obj.getPerfil());
	}

	
	private Boolean verifyExistingEmail(User obj) {
		List<User> allUsers = findAll();
		boolean result = allUsers.stream().anyMatch(x -> x.getEmail().equals(obj.getEmail()));
		return result;
	}
	
	private Boolean verifyExistingCpf(User obj) {
		List<User> allUsers = findAll();
		boolean result = allUsers.stream().anyMatch(x -> x.getCpf().equals(obj.getCpf()));
		return result;
	}
	
}
