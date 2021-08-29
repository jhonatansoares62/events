package dao;

import java.io.Serializable;
import java.util.List;

import exceptions.ResourceNotYetImplementedException;
import model.entities.AbstractEntity;

public interface IDAO<E extends AbstractEntity> extends Serializable {

	public abstract E findById(Long id);

	public abstract E findById(E e);

	public abstract List<E> findAll();

	public abstract List<E> find(int quantidade, int deslocamento);

	public default List<E> find(String consulta, Object... params) {
		throw new ResourceNotYetImplementedException();
	}

	public default List<E> findLike(String consulta, Object... params) {
		throw new ResourceNotYetImplementedException();
	}

	public default E findSingle(String consulta, Object... params) {
		throw new ResourceNotYetImplementedException();
	}

	public default List<E> find(String nomeConsulta) {

		throw new ResourceNotYetImplementedException();

	}

	public abstract E save(E e);

	public abstract E update(E e);

	public abstract E delete(Long id);

	public abstract E delete(E e);

}
