package com.marpe.cht.repositories;

import java.sql.Date;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.web.bind.annotation.CrossOrigin;

import com.marpe.cht.entities.OSColab;

@CrossOrigin
@Repository
public interface ReportRepository extends JpaRepository<OSColab, Long> {

	@Query(value = "SELECT * FROM tb_oscolab INNER JOIN tb_os ON tb_oscolab.os_id = tb_os.id WHERE tb_os.data_inicio BETWEEN :startDate AND :endDate", nativeQuery = true)
	List<OSColab> getOSColabPorPeriodo(@Param("startDate") Date startDate, @Param("endDate") Date endDate);

	@Query(value = "SELECT colaborador_id, SUM(totalareceber) FROM tb_oscolab INNER JOIN tb_os ON tb_oscolab.os_id = tb_os.id WHERE (tb_os.concluida = 1) AND (tb_os.todos_pagos = :todosPagos) AND (tb_os.data_inicio BETWEEN :startDate AND :endDate) GROUP BY colaborador_id", nativeQuery = true)
	List<Object[]> getOSColabSomadoPorPeriodo(@Param("startDate") Date startDate, @Param("endDate") Date endDate, @Param("todosPagos") Boolean todosPagos);
	
	@Query(value = "SELECT colaborador_id, SUM(totalareceber) FROM tb_oscolab INNER JOIN tb_os ON tb_oscolab.os_id = tb_os.id WHERE tb_os.data_inicio BETWEEN :startDate AND :endDate GROUP BY colaborador_id ORDER BY SUM(totalareceber) DESC LIMIT 5", nativeQuery = true)
	List<Object[]> topFiveOscolabSomadoPorPeriodo(@Param("startDate") Date startDate, @Param("endDate") Date endDate);

//	@Query(value = "SELECT * FROM tb_oscolab INNER JOIN tb_os ON tb_oscolab.os_id = tb_os.id WHERE (tb_os.data_inicio BETWEEN :startDate AND :endDate) AND (tb_oscolab.pago = :pago)", nativeQuery = true)
//	List<OSColab> getOSColabPorPeriodoEPago(@Param("startDate") Date startDate, @Param("endDate") Date endDate, @Param("pago") Boolean pago);


}
