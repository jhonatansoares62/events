package model.entities;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;

@Entity
public class Usuario extends AbstractEntity {

	private static final long serialVersionUID = 1L;

	private String nome;
	private String sobrenome;

	@ManyToMany  @JoinTable(name="usuario_evento", joinColumns=  {@JoinColumn(name="usuario_id")},
			 inverseJoinColumns=  {@JoinColumn(name="evento_id")})
	private List<Evento> eventos = new ArrayList<>();

	public Usuario() {

	}

	public Usuario(String nome, String sobrenome) {
		this.nome = nome;
		this.sobrenome = sobrenome;
	}

	public String getNome() {
		return nome;
	}

	public void setNome(String nome) {
		this.nome = nome;
	}

	public String getSobrenome() {
		return sobrenome;
	}

	public void setSobrenome(String sobrenome) {
		this.sobrenome = sobrenome;
	}

	public List<Evento> getEventos() {
		return eventos;
	}

	public void setEventos(List<Evento> eventos) {
		this.eventos = eventos;
	}

}
