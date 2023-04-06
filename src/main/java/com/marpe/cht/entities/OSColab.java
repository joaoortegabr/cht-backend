package com.marpe.cht.entities;

import java.io.Serializable;
import java.time.LocalTime;
import java.util.Objects;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import org.hibernate.annotations.SQLDelete;
import org.hibernate.annotations.Where;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.marpe.cht.entities.enums.DataState;
	
@Entity
@Table(name = "tb_oscolab")
@SQLDelete(sql = "UPDATE tb_oscolab SET state = '1' WHERE id = ?")
@Where(clause = "state = '0'")
public class OSColab implements Serializable {
	private static final long serialVersionUID = 1L;
		
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	@JsonFormat(pattern = "HH:mm")
	protected LocalTime horaInicial;
	@JsonFormat(pattern = "HH:mm")
	protected LocalTime horaFinal;
	protected Double totalHorasDiurnas;
	protected Double totalHorasNoturnas;
	@Column(name = "intervalo", nullable = false)
	protected Boolean intervalo;
	protected Integer transportes;
	protected Double totalAReceber;
	@Column(name = "pago", nullable = false)
	protected Boolean pago;
	
	@ManyToOne
	@JoinColumn(name = "colaborador_id")
	private Colaborador colaborador;
	
	@JsonBackReference
	@ManyToOne
	@JoinColumn(name = "os_id")
	private OS os;
	
    @Enumerated(EnumType.ORDINAL)
    private DataState state;
	
	public OSColab() {
	}

	public OSColab(LocalTime horaInicial, LocalTime horaFinal, Boolean intervalo,
			Integer transportes, Boolean pago, Colaborador colaborador, OS os) {
		this.horaInicial = this.os.getHoraInicio();
		this.horaFinal = horaFinal;
		this.totalHorasDiurnas = 0.0;
		this.totalHorasNoturnas = 0.0;
		this.intervalo = false;
		this.transportes = transportes;
		this.totalAReceber = 0.0;
		this.pago = false;
		this.colaborador = colaborador;
		this.os = os;
		this.state = DataState.ACTIVE;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}
	
	public LocalTime getHoraInicial() {
		return horaInicial;
	}

	public void setHoraInicial(LocalTime horaInicial) {
		this.horaInicial = horaInicial;
	}
	
	public LocalTime getHoraFinal() {
		return horaFinal;
	}

	public void setHoraFinal(LocalTime horaFinal) {
		this.horaFinal = horaFinal;
	}
	
	public Double getTotalHorasDiurnas() {
		return totalHorasDiurnas;
	}

	public void setTotalHorasDiurnas(Double totalHorasDiurnas) {
		this.totalHorasDiurnas = totalHorasDiurnas;
	}
	
	public Double getTotalHorasNoturnas() {
		return totalHorasNoturnas;
	}

	public void setTotalHorasNoturnas(Double totalHorasNoturnas) {
		this.totalHorasNoturnas = totalHorasNoturnas;
	}

	public Boolean getIntervalo() {
		return intervalo;
	}

	public void setIntervalo(Boolean intervalo) {
		this.intervalo = intervalo;
	}

	public Integer getTransportes() {
		return transportes;
	}

	public void setTransportes(Integer transportes) {
		this.transportes = transportes;
	}

	public Double getTotalAReceber() {
		return totalAReceber;
	}

	public void setTotalAReceber(Double totalAReceber) {
		this.totalAReceber = totalAReceber;
	}

	public Boolean getPago() {
		return pago;
	}

	public void setPago(Boolean pago) {
		this.pago = pago;
	}
	
	public Colaborador getColaborador() {
		return colaborador;
	}
	
	public void setColaborador(Colaborador colaborador) {
		this.colaborador = colaborador;
	}	
	
	public OS getOs() {
		return os;
	}
	
	public void setOs(OS os) {
		this.os = os;
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
		OSColab other = (OSColab) obj;
		return Objects.equals(id, other.id);
	}
	
}