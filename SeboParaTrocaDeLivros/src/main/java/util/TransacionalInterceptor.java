package util;

import javax.inject.Inject;
import javax.interceptor.AroundInvoke;
import javax.interceptor.Interceptor;
import javax.interceptor.InvocationContext;
import javax.persistence.EntityManager;
import javax.persistence.EntityTransaction;

/**
 * Outra implementação XXX:
 * 
 * <pre>
 * http://github.com/matzew/hbase-jpa-jsf/blob/master/src/main/java/net/wessendorf/cdi/transactional/TransactionalInterceptor.java
 * </pre>
 * 
 * (taken from the Apache OpenWebBeans project's reservation example)
 * 
 * @author jaindsonvs
 */
@Interceptor
@TransacionalCdi
public class TransacionalInterceptor {
	
	//classe que será executada antes de cada método com a notação

	@Inject
	private EntityManager em;

	@AroundInvoke //define a logica que será implementada antes de cada metodo com a notação.
	public Object intercept(InvocationContext ctx) throws Exception {

		Object resultado = null;

		EntityTransaction transaction = em.getTransaction();
		transaction.begin();

		try {
			resultado = ctx.proceed(); //devolve a execução para o metodo com notação
			transaction.commit();
		} catch (Exception pe) {
			pe.printStackTrace();
			if (transaction.isActive()) {
				// Rollback if any error happens before reaching commit
				transaction.rollback();
			}
			throw pe;
		}

		return resultado;
	}

}
