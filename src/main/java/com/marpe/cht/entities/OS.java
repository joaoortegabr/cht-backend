package com.marpe.cht.entities;

import java.io.Serializable;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.HashSet;
import java.util.Objects;
import java.util.Set;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import org.hibernate.annotations.SQLDelete;
import org.hibernate.annotations.Where;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;
import com.marpe.cht.entities.enums.DataState;

@Entity
@Table(name = "tb_os")
@SQLDelete(sql = "UPDATE tb_os SET state = '1' WHERE id = ?")
@Where(clause = "state = '0'")
@JsonIdentityInfo(generator = ObjectIdGenerators.PropertyGenerator.class, property = "id")
public class OS implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@Column(name="id")
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	@JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "dd-MM-yyyy")
	private LocalDate dataInicio;
	@JsonFormat(pattern = "HH:mm")
	private LocalTime horaInicio;
	private String observacao;
	@Column(name = "todos_pagos", nullable = false)
	private Boolean todosPagos;
	@Column(name = "concluida", nullable = false)
	private Boolean concluida;
	
	@ManyToOne
	@JoinColumn(name = "cliente_id")
	private Cliente cliente;
	@ManyToOne
	@JoinColumn(name = "regional_id")
	private Regional regional;
	@ManyToOne
	@JoinColumn(name = "coordenador_id")
	private Coordenador coordenador;

	@JsonManagedReference
	@OneToMany(mappedBy = "os")
	protected Set<OSColab> oscolab = new HashSet<>();
	
    @Enumerated(EnumType.ORDINAL)
    private DataState state;
	
	public OS() {
	}

	public OS(LocalDate dataInicio, LocalTime horaInicio, String observacao,
			Cliente cliente, Regional regional, Coordenador coordenador) {
		this.dataInicio = dataInicio;
		this.horaInicio = horaInicio;
		this.observacao = observacao;
		this.todosPagos = false;
		this.concluida = false;
		this.cliente = cliente;
		this.regional = regional;
		this.coordenador = coordenador;
		this.state = DataState.ACTIVE;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public LocalDate getDataInicio() {
		return dataInicio;
	}

	public void setDataInicio(LocalDate dataInicio) {
		this.dataInicio = dataInicio;
	}

	public LocalTime getHoraInicio() {
		return horaInicio;
	}

	public void setHoraInicio(LocalTime horaInicio) {
		this.horaInicio = horaInicio;
	}

	public String getObservacao() {
		return observacao;
	}

	public void setObservacao(String observacao) {
		this.observacao = observacao;
	}

	public Boolean getTodosPagos() {
		return todosPagos;
	}

	public void setTodosPagos(Boolean todosPagos) {
		this.todosPagos = todosPagos;
	}

	public Boolean getConcluida() {
		return concluida;
	}

	public void setConcluida(Boolean concluida) {
		this.concluida = concluida;
	}

	public Cliente getCliente() {
		return cliente;
	}

	public void setCliente(Cliente cliente) {
		this.cliente = cliente;
	}

	public Regional getRegional() {
		return regional;
	}

	public void setRegional(Regional regional) {
		this.regional = regional;
	}

	public Coordenador getCoordenador() {
		return coordenador;
	}

	public void setCoordenador(Coordenador coordenador) {
		this.coordenador = coordenador;
	}

	public Set<OSColab> getOscolab() {
		return oscolab;
	}

	public void addOscolab(OSColab oscolab) {
		this.oscolab.add(oscolab);
	}
	
	public DataState getState() {
		return state;
	}

	public void setState(DataState state) {
		this.state = state;
	}
	
	
	@Override
	public int hashCode() {
		return Objects.hash(id);
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		OS other = (OS) obj;
		return Objects.equals(id, other.id);
	}
		
}