package com.marpe.cht.entities;

import java.io.Serializable;
import java.util.Objects;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToOne;
import javax.persistence.Table;

import org.hibernate.annotations.SQLDelete;
import org.hibernate.annotations.Where;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.marpe.cht.entities.enums.DataState;
import com.marpe.cht.entities.enums.Perfil;

@Entity
@Table(name = "tb_user")
@SQLDelete(sql = "UPDATE tb_user SET state = '1' WHERE id = ?")
@Where(clause = "state = '0'")
public class User implements Serializable {
	private static final long serialVersionUID = 1L;
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	protected Long id;
	@JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
	private String password;
	private String nome;
	private String rg;
	private String cpf;
	private String telefone;
	@Column(unique = true)
	private String email;
	@Column(name = "ativo", nullable = false)
	private Boolean ativo;
	
    @Enumerated(EnumType.ORDINAL)
	private Perfil perfil;
	
	@JsonIgnore
	@OneToOne(mappedBy = "user")
	private Coordenador coordenador;
	
	@JsonIgnore
	@OneToOne(mappedBy = "user")
	private Colaborador colaborador;
	
    @Enumerated(EnumType.ORDINAL)
    private DataState state;
	
	public User() {
	}

	public User(Long id, String password, String nome, String rg, String cpf, String telefone, String email, Boolean ativo, 
			Perfil perfil) {
		this.id = id;
		//this.login = login;
		this.password = password;
		this.nome = nome;
		this.rg = rg;
		this.cpf = cpf;
		this.telefone = telefone;
		this.email = email;
		this.ativo = ativo;
		this.perfil = perfil;
		this.state = DataState.ACTIVE;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public String getNome() {
		return nome;
	}

	public void setNome(String nome) {
		this.nome = nome;
	}

	public String getRg() {
		return rg;
	}

	public void setRg(String rg) {
		this.rg = rg;
	}

	public String getCpf() {
		return cpf;
	}

	public void setCpf(String cpf) {
		this.cpf = cpf;
	}

	public String getTelefone() {
		return telefone;
	}

	public void setTelefone(String telefone) {
		this.telefone = telefone;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}
	
	public Boolean getAtivo() {
		return ativo;
	}

	public void setAtivo(Boolean ativo) {
		this.ativo = ativo;
	}

	public Perfil getPerfil() {
		return perfil;
	}

	public void setPerfil(Perfil perfil) {
		this.perfil = perfil;
	}
		
	public DataState getState() {
		return state;
	}

	public void setState(DataState state) {
		this.state = state;
	}


	@Override
	public int hashCode() {
		return Objects.hash(email);
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		User other = (User) obj;
		return Objects.equals(email, other.email);
	}


}
