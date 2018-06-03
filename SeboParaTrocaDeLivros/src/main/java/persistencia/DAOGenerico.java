package persistencia;

import java.awt.Image;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceException;
import javax.persistence.Query;
import javax.persistence.TypedQuery;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;

public class DAOGenerico extends DAO {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;


	public void save(Object o) throws Exception {

		EntityManager em = getEntityManager();
		try {
			em.persist(o);
		} catch (Exception pe) {
			pe.printStackTrace();
			throw new Exception("Falha em persistir " + o.getClass().getSimpleName(), pe);
		}
	}

	
	public void deleteAll(String entityName) throws Exception {
		EntityManager em = getEntityManager();
		try {
			Query query = em.createQuery("delete from " + entityName);
			query.executeUpdate();
		} catch (PersistenceException pe) {
			pe.printStackTrace();
			throw new Exception("Erro na exclusão", pe);
		}

	}

	public Object getByID(Object objeto, Long id) throws Exception {
		EntityManager em = getEntityManager();
		Object resultado = null;
		try {
			resultado = em.find(objeto.getClass(), id);
		} catch (PersistenceException pe) {
			pe.printStackTrace();
			throw new Exception("Erro na recuperação do item.", pe);
		} 
		return resultado;
	}


	public void delete(Object o, Long pk) throws Exception {
		EntityManager em = getEntityManager();
		try {
			o = em.find(o.getClass(), pk);
			em.remove(o);
		} catch (PersistenceException pe) {
			pe.printStackTrace();
			throw new Exception("Erro na exclusão", pe);
		}
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
			throw new Exception("Erro na recuperação de todos os itens.", pe);
		} 
		return resultado;
	}

	public void update(Object o) throws Exception {
		EntityManager em = getEntityManager();
		try {
			em.merge(o);
		} catch (Exception e) {
			throw new Exception("Erro na atualização de " + o.getClass().getSimpleName(), e);
		}
	}

	public Image find(Long id) {
		EntityManager em = getEntityManager();
		return em.find(Image.class, id);
	}
}
