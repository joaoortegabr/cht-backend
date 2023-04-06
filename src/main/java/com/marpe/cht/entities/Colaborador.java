package com.marpe.cht.entities;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Objects;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.persistence.Table;

import org.hibernate.annotations.SQLDelete;
import org.hibernate.annotations.Where;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.marpe.cht.entities.enums.DataState;

@Entity
@Table(name = "tb_colaborador")
@SQLDelete(sql = "UPDATE tb_colaborador SET state = '1' WHERE id = ?")
@Where(clause = "state = '0'")
public class Colaborador implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	
	@OneToOne(cascade=CascadeType.REFRESH)
	@JoinColumn(name = "user_id")
	private User user;
	
	private String banco;
	private String agencia;
	private String conta;
	private String operacao;
	private String titular;

	@ManyToOne
	@JoinColumn(name = "cidade_id")
	private Cidade cidade;
	
	@JsonIgnore
	@OneToMany(mappedBy = "colaborador")
	private Set<OSColab> ordersByColab = new HashSet<>();
	
    @Enumerated(EnumType.ORDINAL)
    private DataState state;
	
	public Colaborador() {
	}

	public Colaborador(Long id, User user, String banco, String agencia, String conta, String operacao, String titular, Cidade cidade) {
		this.id = id;
		this.user = user;
		this.banco = banco;
		this.agencia = agencia;
		this.conta = conta;
		this.operacao = operacao;
		this.titular = titular;
		this.cidade = cidade;
		this.state = DataState.ACTIVE;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}
	
	public User getUser() {
		return user;
	}

	public void setUser(User user) {
		this.user = user;
	}

	public String getBanco() {
		return banco;
	}

	public void setBanco(String banco) {
		this.banco = banco;
	}

	public String getAgencia() {
		return agencia;
	}

	public void setAgencia(String agencia) {
		this.agencia = agencia;
	}

	public String getConta() {
		return conta;
	}

	public void setConta(String conta) {
		this.conta = conta;
	}

	public String getOperacao() {
		return operacao;
	}

	public void setOperacao(String operacao) {
		this.operacao = operacao;
	}

	public String getTitular() {
		return titular;
	}

	public void setTitular(String titular) {
		this.titular = titular;
	}

	public Cidade getCidade() {
		return cidade;
	}

	public void setCidade(Cidade cidade) {
		this.cidade = cidade;
	}

	@JsonIgnore
	public List<OSColab> getOrdersByColab() {
		List<OSColab> list = new ArrayList<>();
		for (OSColab i : ordersByColab) {
			list.add(i);
		}
		return list;
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
		Colaborador other = (Colaborador) obj;
		return Objects.equals(id, other.id);
	}
	
	
}