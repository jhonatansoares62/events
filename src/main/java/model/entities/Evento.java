package model.entities;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.ManyToMany;

@Entity
public class Evento extends AbstractEntity {

	private static final long serialVersionUID = 1L;

	private String descricao;

	@ManyToMany(mappedBy = "eventos", cascade = CascadeType.ALL)
	private List<Usuario> usuarios = new ArrayList<>();

	public Evento() {

	}

	public Evento(String descricao) {
		this.descricao = descricao;
	}

	public String getDescricao() {
		return descricao;
	}

	public void setDescricao(String descricao) {
		this.descricao = descricao;
	}

	public List<Usuario> getUsuarios() {
		return usuarios;
	}

	public void setUsuarios(List<Usuario> usuarios) {
		this.usuarios = usuarios;
	}

}
