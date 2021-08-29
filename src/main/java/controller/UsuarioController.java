package controller;

import java.io.Serializable;

import javax.annotation.PostConstruct;
import javax.faces.view.ViewScoped;
import javax.inject.Inject;
import javax.inject.Named;

import model.entities.Usuario;
import repository.UsuarioRepository;

@Named
@ViewScoped
public class UsuarioController implements Serializable{

	private static final long serialVersionUID = 1L;
	
	private String mensagem = "Funcionando";

	@Inject
	private UsuarioRepository usuarioRepository;
	
	private String nome = "Ola mundo";
	
	private String sobrenome;

	private Usuario usuario;

	@PostConstruct
	public void init() {
		this.usuario = new Usuario();
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
	
	
	
	public String getMensagem() {
		return mensagem;
	}

	public void setMensagem(String mensagem) {
		this.mensagem = mensagem;
	}

	public void listener() {
		System.out.println("Nome" + nome);
		System.out.println("Sobrenome" + sobrenome);
	}

	public void cadastrar() {
		
		usuario.setNome(nome);
		usuario.setSobrenome(sobrenome);
		usuarioRepository.save(usuario);
	}

}
