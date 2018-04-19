package persistencia;

import java.awt.Image;
import java.math.BigInteger;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.EntityTransaction;
import javax.persistence.PersistenceException;
import javax.persistence.Query;
import javax.persistence.TypedQuery;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;

public class DAOGenerico extends DAO {

	public void save(Object o) throws Exception {

		EntityManager em = getEntityManager();
		EntityTransaction transaction = em.getTransaction();
		transaction.begin();
		try {
			em.persist(o);
			transaction.commit();
		} catch (Exception pe) {
			pe.printStackTrace();
			transaction.rollback();
			throw new Exception("Falha em persistir " + o.getClass().getSimpleName(), pe);
		} finally {
			em.close();
		}
	}

	public void deleteAll(String entityName) throws Exception {
		EntityManager em = getEntityManager();
		EntityTransaction transaction = em.getTransaction();
		transaction.begin();
		try {
			Query query = em.createQuery("delete from " + entityName);
			query.executeUpdate();
			transaction.commit();
		} catch (PersistenceException pe) {
			transaction.rollback();
			pe.printStackTrace();
			throw new Exception("Erro na exclusÃ£o", pe);
		} finally {
			em.close();
		}

	}

	public Object getByID(Object objeto, Long id) throws Exception {
		EntityManager em = getEntityManager();
		Object resultado = null;
		try {
			resultado = em.find(objeto.getClass(), id);
		} catch (PersistenceException pe) {
			pe.printStackTrace();
			throw new Exception("Erro na recuperaÃ§Ã£o do item.", pe);
		} finally {
			em.close();
		}

		return resultado;
	}

	public Object getByID(Object objeto, String id) throws Exception {
		EntityManager em = getEntityManager();
		Object resultado = null;
		try {
			resultado = em.find(objeto.getClass(), id);
		} catch (PersistenceException pe) {
			pe.printStackTrace();
			throw new Exception("Erro na recuperaÃ§Ã£o do item.", pe);
		} finally {
			em.close();
		}

		return resultado;
	}

	public List<Object> getAll(Object o) throws Exception {
		EntityManager em = getEntityManager();
		List<Object> resultado = null;
		try {
			CriteriaBuilder cb = em.getCriteriaBuilder();

			CriteriaQuery<Object> query = (CriteriaQuery<Object>) cb.createQuery(o.getClass());
			Root<Object> root = (Root<Object>) query.from(o.getClass());
			query.select(root);

			TypedQuery<Object> typedQuery = em.createQuery(query);
			resultado = typedQuery.getResultList();
		} catch (PersistenceException pe) {
			pe.printStackTrace();
			throw new Exception("Erro na recuperaÃ§Ã£o de todos os itens.", pe);
		} finally {
			em.close();
		}
		return resultado;
	}

	public void update(Object o) throws Exception {
		EntityManager em = getEntityManager();
		EntityTransaction transaction = em.getTransaction();
		transaction.begin();
		try {
			em.merge(o);
			transaction.commit();
		} catch (Exception e) {
			throw new Exception("Erro na atualizaÃ§Ã£o de " + o.getClass().getSimpleName(), e);
		} finally {
			em.close();
		}

	}

	public void delete(Object o) throws Exception {
		EntityManager em = getEntityManager();
		EntityTransaction transaction = em.getTransaction();
		transaction.begin();
		try {
			em.detach(o);
			em.remove(o);
			transaction.commit();
		} catch (PersistenceException pe) {
			transaction.rollback();
			pe.printStackTrace();
			throw new Exception("Erro na exclusÃ£o", pe);
		} finally {
			em.close();
		}
	}

	public Image find(Long id) {
		EntityManager em = getEntityManager();
		return em.find(Image.class, id);
	}

	public Object getByID(Object objeto, BigInteger id) throws Exception {
		EntityManager em = getEntityManager();
		Object resultado = null;
		try {
			resultado = em.find(objeto.getClass(), id);
		} catch (PersistenceException pe) {
			pe.printStackTrace();
			throw new Exception("Erro na recuperaÃ§Ã£o do item.", pe);
		} finally {
			em.close();
		}

		return resultado;
	}

}
