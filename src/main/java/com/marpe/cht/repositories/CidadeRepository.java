package com.marpe.cht.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import org.springframework.web.bind.annotation.CrossOrigin;

import com.marpe.cht.entities.Cidade;

@CrossOrigin
@Repository
public interface CidadeRepository extends JpaRepository<Cidade, Long> {

}
