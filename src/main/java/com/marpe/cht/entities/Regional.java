package com.marpe.cht.entities;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import org.hibernate.annotations.SQLDelete;
import org.hibernate.annotations.Where;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.marpe.cht.entities.enums.DataState;

@Entity
@Table(name = "tb_regional")
@SQLDelete(sql = "UPDATE tb_regional SET state = '1' WHERE id = ?")
@Where(clause = "state = '0'")
public class Regional implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	private String nome;
	private Double horasPadrao;
	private Double valorHoraDiurna;
	private Double valorHoraNoturna;
	private Double valorTransporte;
	private String descricao;
	
	@JsonIgnore
	@OneToMany(mappedBy = "regional")
	private List<OS> orders = new ArrayList<>();
	
    @Enumerated(EnumType.ORDINAL)
    private DataState state;
	
	public Regional() {
	}

	public Regional(Long id, String nome, Double horasPadrao, Double valorHoraDiurna, Double valorHoraNoturna,
			Double valorTransporte, String descricao) {
		this.id = id;
		this.nome = nome;
		this.horasPadrao = horasPadrao;
		this.valorHoraDiurna = valorHoraDiurna;
		this.valorHoraNoturna = valorHoraNoturna;
		this.valorTransporte = valorTransporte;
		this.descricao = descricao;
		this.state = DataState.ACTIVE;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getNome() {
		return nome;
	}

	public void setNome(String nome) {
		this.nome = nome;
	}

	public Double getHorasPadrao() {
		return horasPadrao;
	}

	public void setHorasPadrao(Double horasPadrao) {
		this.horasPadrao = horasPadrao;
	}

	public Double getValorHoraDiurna() {
		return valorHoraDiurna;
	}

	public void setValorHoraDiurna(Double valorHoraDiurna) {
		this.valorHoraDiurna = valorHoraDiurna;
	}

	public Double getValorHoraNoturna() {
		return valorHoraNoturna;
	}

	public void setValorHoraNoturno(Double valorHoraNoturna) {
		this.valorHoraNoturna = valorHoraNoturna;
	}

	public Double getValorTransporte() {
		return valorTransporte;
	}

	public void setValorTransporte(Double valorTransporte) {
		this.valorTransporte = valorTransporte;
	}

	public String getDescricao() {
		return descricao;
	}

	public void setDescricao(String descricao) {
		this.descricao = descricao;
	}

	public List<OS> getOrders() {
		return orders;
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
		Regional other = (Regional) obj;
		return Objects.equals(id, other.id);
	}
	
}
