package com.generation.blogpessoal.model;

import java.time.LocalDateTime;
import org.hibernate.annotations.UpdateTimestamp;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;


@Entity								//indica que a classe é uma tabela
@Table(name = "tb_postagens")		//indica o nome da tabela no bd
public class Postagem {

	@Id // Chave Primária(PRIMARY KEY)
	@GeneratedValue(strategy = GenerationType.IDENTITY) // AUTO INCREMENT
	private Long id; // identificação da postagem
	
	@NotBlank(message = "Este campo é obrigatório!") // Not Null(campo obrigatório!) e o blank tambám indica que não aceita espaços vavios
	@Size(min = 5, max = 100, message = "O mínimo é 5 caracteres")
	private String titulo; // Título da Postagem
	
	@NotBlank(message = "Este campo é obrigatório!") // Not Null(campo obrigatório!) e o blank tambám indica que não aceita espaços vavios
	@Size(min = 10, max = 1000, message = "O mínimo é 10 caracteres e o máximo é 1.000 caracteres")
	private String texto;
	
	@UpdateTimestamp
	private LocalDateTime data; // 10/10/25 10:33
	
	@ManyToOne
	@JsonIgnoreProperties("postagem")
	private Tema tema;
	
	
	public Tema getTema() {
		return tema;
	}
	public void setTema(Tema tema) {
		this.tema = tema;
	}
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	public String getTitulo() {
		return titulo;
	}
	public void setTitulo(String titulo) {
		this.titulo = titulo;
	}
	public String getTexto() {
		return texto;
	}
	public void setTexto(String texto) {
		this.texto = texto;
	}
	public LocalDateTime getData() {
		return data;
	}
	public void setData(LocalDateTime data) {
		this.data = data;
	}
	
}
