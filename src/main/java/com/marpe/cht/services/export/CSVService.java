package com.marpe.cht.services.export;

import java.io.ByteArrayInputStream;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.marpe.cht.entities.User;
import com.marpe.cht.export.helper.CSVHelper;
import com.marpe.cht.repositories.UserRepository;

@Service
public class CSVService {

  @Autowired
  UserRepository repository;
  
  public ByteArrayInputStream load() {
    List<User> users = repository.findAll();

    ByteArrayInputStream in = CSVHelper.usersToCSV(users);
    return in;
  }
}
