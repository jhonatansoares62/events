package repository;

import javax.inject.Named;

import dao.DAOForImplementation;
import model.entities.Usuario;

@Named
public class UsuarioRepository extends DAOForImplementation<Usuario> {

	private static final long serialVersionUID = 1L;

	public UsuarioRepository() {
		super(Usuario.class);
	}

	@Override
	protected void doUpdate(Usuario entityManaged, Usuario entity) {

	}

	@Override
	protected void veriFyIfValid(Usuario entity) {
		
	}

	@Override
	protected boolean veriFyIfAlreadyExists(Usuario entity) {

		return false;
	}

}
