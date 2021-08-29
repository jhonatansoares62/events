package dao;

import java.io.Serializable;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.TypedQuery;
import javax.transaction.TransactionScoped;

import exceptions.DAOExeption;
import exceptions.ErrorCode;
import exceptions.ResourceNotFoundException;
import model.entities.AbstractEntity;

public abstract class DAOForImplementation<E extends AbstractEntity> implements IDAO<E>, Serializable {

	protected final String ENTITY_SEARCH_ERROR_MESSAGE = "Erro ao recuperar registro no banco de dados.";
	protected final String ENTITIES_SEARCH_ERROR_MESSAGE = "Erro ao recuperar os registros no banco de dados.";
	protected final String ENTITY_NOT_FOUND_MESSAGE = "Registro nao encontrado no banco de dados.";
	protected final String ENTITIES_NOT_FOUND_MESSAGE = "Nenhum egistro encontrado no banco de dados.";
	protected final String ID_INVALID_ERROR_MESSAGE = "Erro id invalido.";
	protected final String SEARCH_BY_ID_ERROR_MESSAGE = "Erro ao buscar por id.";
	protected final String PARAMETER_INVALID_ERROR_MESSAGE = "Erro parametro invalido.";
	protected final String NULL_ENTITY_PARAMETER_ERROR_MESSAGE = "Erro entidade passada por parametro é null.";
	protected final String DELETE_ERROR_MESSAGE = "Erro ao remover registro do banco de dados.";
	protected final String UPDATE_ERROR_MESSAGE = "Erro ao atualizar registro no banco de dados.";

	private static final long serialVersionUID = 1605030503530528543L;
	protected Class<E> classe;
	private EntityManager em;

	public DAOForImplementation(Class<E> classe) {
		em = JPAUtil.getEntityManager();
		this.classe = classe;
	}

	@Override
	public E findById(Long id) {

		verifyIfIdIsValid(id);

		em = JPAUtil.getEntityManager();
		E entity = null;

		try {
			entity = em.find(classe, id);
		} catch (RuntimeException ex) {
			throw new DAOExeption(SEARCH_BY_ID_ERROR_MESSAGE + ex.getMessage(), ErrorCode.SERVER_ERROR.getCode());
		} finally {
			em.close();
		}

		verifyFindReturnExists(entity);

		return entity;

	}

	@Override
	public E findById(E e) {
		verifyIfNull(e);
		return findById(e.getId());
	}

	public List<E> find(int qtd, int deslocamento) {

		if (qtd > 20) {
			qtd = 20;
		}
		if (deslocamento > 20) {
			deslocamento = 20;
		}

		em = JPAUtil.getEntityManager();

		List<E> entities = null;

		try {
			String jpql = "select e from " + classe.getName() + " e";
			TypedQuery<E> query = em.createQuery(jpql, classe);
			query.setMaxResults(qtd);
			query.setFirstResult(deslocamento);
			entities = query.getResultList();

		} catch (RuntimeException ex) {
			throw new DAOExeption(ENTITIES_SEARCH_ERROR_MESSAGE + ex.getMessage(), ErrorCode.SERVER_ERROR.getCode());
		} finally {
			em.close();
		}

		verifyIfReturnIsEmpty(entities);

		return entities;

	}

	public List<E> findAll() {

		em = JPAUtil.getEntityManager();

		List<E> entities = null;

		try {
			String jpql = "select e from " + classe.getName() + " e";
			TypedQuery<E> query = em.createQuery(jpql, classe);
			entities = query.getResultList();

		} catch (RuntimeException ex) {
			throw new DAOExeption("Erro ao recuperar registros do banco: " + ex.getMessage(),
					ErrorCode.SERVER_ERROR.getCode());
		} finally {
			em.close();
		}

		verifyIfReturnIsEmpty(entities);

		return entities;

	}

	public List<E> find() {
		return this.find(10, 0);
	}

	public List<E> find(String nomeConsulta, Object... params) {

		em = JPAUtil.getEntityManager();

		List<E> entities = null;

		try {

			TypedQuery<E> query = em.createNamedQuery(nomeConsulta, classe);

			for (int i = 0; i < params.length; i += 2) {
				query.setParameter(params[i].toString(), params[i + 1]);
			}

			entities = query.getResultList();

		} catch (RuntimeException ex) {
			throw new DAOExeption("Erro ao recuperar registros do banco: " + ex.getMessage(),
					ErrorCode.SERVER_ERROR.getCode());
		} finally {
			em.close();
		}

		verifyIfReturnIsEmpty(entities);

		return entities;

	}

	public List<E> findLike(String nomeConsulta, Object... params) {

		em = JPAUtil.getEntityManager();

		List<E> entities = null;

		try {

			TypedQuery<E> query = em.createNamedQuery(nomeConsulta, classe);

			for (int i = 0; i < params.length; i += 2) {
				query.setParameter(params[i].toString(), "%" + params[i + 1] + "%");
			}

			entities = query.getResultList();

		} catch (RuntimeException ex) {
			throw new DAOExeption("Erro ao recuperar registros do banco: " + ex.getMessage(),
					ErrorCode.SERVER_ERROR.getCode());
		} finally {
			em.close();
		}

		verifyIfReturnIsEmpty(entities);

		return entities;

	}

	public E findLikeSingle(String nomeConsulta, Object... params) {

		em = JPAUtil.getEntityManager();

		E entity = null;

		try {
			TypedQuery<E> query = em.createNamedQuery(nomeConsulta, classe);
			for (int i = 0; i < params.length; i += 2) {
				query.setParameter(params[i].toString(), "%" + params[i + 1] + "%");
			}
			entity = query.getSingleResult();
		} catch (RuntimeException ex) {
			throw new DAOExeption("Erro ao recuperar registro do banco: " + ex.getMessage(),
					ErrorCode.SERVER_ERROR.getCode());
		} finally {
			em.close();
		}

		verifyFindReturnExists(entity);

		return entity;

	}

	public List<E> find(String nomeConsulta) {

		em = JPAUtil.getEntityManager();

		List<E> entities = null;

		try {
			TypedQuery<E> query = em.createNamedQuery(nomeConsulta, classe);
			entities = query.getResultList();
		} catch (RuntimeException ex) {
			throw new DAOExeption("Erro ao recuperar registros do banco: " + ex.getMessage(),
					ErrorCode.SERVER_ERROR.getCode());
		} finally {
			em.close();
		}

		verifyIfReturnIsEmpty(entities);

		return entities;

	}

	public E findSingle(String nomeConsulta, Object... params) {

		em = JPAUtil.getEntityManager();

		E entity = null;

		try {
			TypedQuery<E> query = em.createNamedQuery(nomeConsulta, classe);

			for (int i = 0; i < params.length; i += 2) {
				query.setParameter(params[i].toString(), params[i + 1]);
			}
			entity = query.getSingleResult();
		} catch (RuntimeException ex) {
			throw new DAOExeption("Erro ao recuperar registros do banco: " + ex.getMessage(),
					ErrorCode.SERVER_ERROR.getCode());
		} finally {
			em.close();
		}

		verifyFindReturnExists(entity);

		return entity;
	}

	@Override
	public E save(E entity) {

		verifyIfNull(entity);

		veriFyIfValid(entity);

		veriFyIfAlreadyExists(entity);

		em = JPAUtil.getEntityManager();

		try {
			em.getTransaction().begin();
			em.persist(entity);
			em.getTransaction().commit();
		} catch (RuntimeException ex) {
			em.getTransaction().rollback();
			throw new DAOExeption("Erro ao salvar registro no banco de dados. " + ex.getMessage(),
					ErrorCode.SERVER_ERROR.getCode());
		} finally {
			em.close();
		}
		return entity;
	}

	@Override
	@TransactionScoped
	public E update(E entity) {

		verifyIfNull(entity);

		veriFyIfValid(entity);

		em = JPAUtil.getEntityManager();

		E entityManaged = null;

		try {
			em.getTransaction().begin();
			entityManaged = em.find(classe, entity.getId());
			doUpdate(entityManaged, entity);
			em.merge(entityManaged);
			em.getTransaction().commit();
		} catch (NullPointerException ex) {
			em.getTransaction().rollback();
			throw new ResourceNotFoundException(ENTITY_NOT_FOUND_MESSAGE + ex.getMessage(),
					ErrorCode.NOT_FOUND.getCode());
		} catch (RuntimeException ex) {
			em.getTransaction().rollback();
			throw new DAOExeption(UPDATE_ERROR_MESSAGE + ex.getMessage(), ErrorCode.SERVER_ERROR.getCode());
		} finally {
			em.close();
		}
		return entityManaged;
	}

	@Override
	public E delete(Long id) {

		verifyIfIdIsValid(id);

		em = JPAUtil.getEntityManager();

		E entity = null;

		try {
			em.getTransaction().begin();
			entity = em.find(classe, id);
			em.remove(entity);
			em.getTransaction().commit();
		} catch (IllegalArgumentException ex) {
			em.getTransaction().rollback();
			throw new ResourceNotFoundException(ENTITY_NOT_FOUND_MESSAGE + ex.getMessage(),
					ErrorCode.NOT_FOUND.getCode());
		} catch (RuntimeException ex) {
			em.getTransaction().rollback();
			throw new DAOExeption(DELETE_ERROR_MESSAGE + ex.getMessage(), ErrorCode.SERVER_ERROR.getCode());
		} finally {
			em.close();
		}
		return entity;
	}

	@Override
	public E delete(E entity) {
		verifyIfNull(entity);
		return this.delete(entity.getId());
	}

	private void verifyIfNull(E entity) {
		if (entity == null) {
			throw new DAOExeption(NULL_ENTITY_PARAMETER_ERROR_MESSAGE, ErrorCode.BAD_REQUEST.getCode());
		}
	}

	private void verifyIfIdIsValid(Long id) {
		if (id <= 0) {
			throw new DAOExeption(ID_INVALID_ERROR_MESSAGE, ErrorCode.BAD_REQUEST.getCode());
		}
	}

	private void verifyFindReturnExists(E entity) {
		if (entity == null) {
			throw new ResourceNotFoundException(ENTITY_NOT_FOUND_MESSAGE, ErrorCode.NOT_FOUND.getCode());
		}
	}

	private void verifyIfReturnIsEmpty(List<E> entities) {
		if (entities.isEmpty()) {
			throw new ResourceNotFoundException(ENTITIES_NOT_FOUND_MESSAGE, ErrorCode.NOT_FOUND.getCode());
		}
	}

	protected abstract void doUpdate(E entityManaged, E entity);

	protected abstract void veriFyIfValid(E entity);

	protected abstract boolean veriFyIfAlreadyExists(E entity);
}
