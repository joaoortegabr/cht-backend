package com.marpe.cht.entities;

import java.io.Serializable;
import java.util.HashSet;
import java.util.Objects;
import java.util.Set;

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
@Table(name = "tb_cidade")
@SQLDelete(sql = "UPDATE tb_cidade SET state = '1' WHERE id = ?")
@Where(clause = "state = '0'")
public class Cidade implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	protected Long id;
	private String nome;
	
	@JsonIgnore
	@OneToMany(mappedBy = "cidade")
	private Set<Colaborador> colaboradores = new HashSet<>();
    
    @Enumerated(EnumType.ORDINAL)
    private DataState state;
	
	public Cidade() {
	}
	
	public Cidade(Long id, String nome) {
		this.id = id;
		this.nome = nome;
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
	
	public Set<Colaborador> getColaboradores() {
		return colaboradores;
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
		Cidade other = (Cidade) obj;
		return Objects.equals(id, other.id);
	}
	
}
