package com.marpe.cht.resources;

import java.net.URI;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import com.fasterxml.jackson.databind.node.ObjectNode;
import com.marpe.cht.entities.User;
import com.marpe.cht.repositories.UserRepository;
import com.marpe.cht.services.UserService;

@CrossOrigin
@RestController
@RequestMapping(value = "/api/users")
public class UserResource {

	@Autowired
	private UserService service;
	
	@Autowired
	private UserRepository repository;
	
	BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();
	
	@GetMapping
	public ResponseEntity<List<User>> findAll() {
		List<User> list = service.findAll();
		return ResponseEntity.ok().body(list);
	}
	
	@GetMapping(value = "/{id}")
	public ResponseEntity<User> findById(@PathVariable Long id) {
		User obj = service.findById(id);
		return ResponseEntity.ok().body(obj);
	}
	
	@PostMapping
	public ResponseEntity<User> insert(@RequestBody User obj) {
		obj.setPassword(encoder.encode(obj.getPassword()));
		obj = service.insert(obj);
		URI uri = ServletUriComponentsBuilder.fromCurrentRequest().path("/{id}")
				.buildAndExpand(obj.getId()).toUri();
		return ResponseEntity.created(uri).body(obj);
	}
	
	@DeleteMapping(value = "/{id}")
	public ResponseEntity<Void> delete(@PathVariable Long id) {
		service.delete(id);
		return ResponseEntity.noContent().build();
	}
	
	@PutMapping(value = "/{id}")
	public ResponseEntity<User> update(@PathVariable Long id, @RequestBody User obj) {
		obj.setPassword(encoder.encode(obj.getPassword()));
		obj = service.update(id, obj);
		return ResponseEntity.ok().body(obj);
	}
	
	
	@SuppressWarnings("unchecked")
	@PostMapping(value = "/login")
	public ResponseEntity<Optional<User>> validateUser(@RequestBody ObjectNode JSONObject) {
		String username = JSONObject.get("username").asText();
        String password = JSONObject.get("password").asText();
  
		Optional<User> optUser = Optional.of(repository.findByEmail(username));
		if(optUser.isEmpty()) {
			  return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(null);
		}
		  
		boolean valid = encoder.matches(password, optUser.get().getPassword());
		if(valid) {
			HttpStatus status = HttpStatus.OK;
			return ResponseEntity.status(status).body(optUser);
		} else {
			return (ResponseEntity<Optional<User>>) ResponseEntity.status(HttpStatus.UNAUTHORIZED);
		}
		
	}
	 
}
