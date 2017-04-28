package br.example.model;


import static br.example.util.FeriadoUtil.*;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonFormat.Shape;

@Entity
@Table(name="feriado")
public class Feriado {

	@Id
	@GeneratedValue
	private Long id;
	
    @Column(name="data",nullable=true, unique =true)
	@Temporal(TemporalType.TIMESTAMP)
    @JsonFormat(shape = Shape.STRING, pattern = "yyyy-MM-dd'T'HH:mm:ss" )
	private Date data;

    @Column(name="tipo",nullable=true)
	private String tipo;

    @Column(name="descricao",nullable=true)
	private String descricao;

	public Feriado() {

	}

	public Feriado(String data, String tipo, String descricao) {
		super();
		this.data = stringToDateIso8601Format(data);
		this.tipo = tipo;
		this.descricao = descricao;
	}
	public Feriado(Long id, String data, String tipo, String descricao) {
		super();
		this.id = id;
		this.data = stringToDateIso8601Format(data);
		this.tipo = tipo;
		this.descricao = descricao;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Date getData() {
		return data;
	}

	public void setData(String data) {
		this.data = stringToDateIso8601Format(data);
	}

	public String getTipo() {
		return tipo;
	}

	public void setTipo(String tipo) {
		this.tipo = tipo;
	}

	public String getDescricao() {
		return descricao;
	}

	public void setDescricao(String descricao) {
		this.descricao = descricao;
	}
	
	@Override
	public String toString() {
		return "Feriado [id=" + id + ", data=" + dateToStringIso8601Format(data) + ", tipo=" + tipo + ", descricao=" + descricao + "]";
	}


}
