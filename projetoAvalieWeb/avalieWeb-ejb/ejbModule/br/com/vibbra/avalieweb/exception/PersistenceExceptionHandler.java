package br.com.vibbra.avalieweb.exception;

import javax.interceptor.AroundInvoke;
import javax.interceptor.InvocationContext;
import javax.persistence.NoResultException;
import javax.persistence.NonUniqueResultException;
import javax.persistence.PersistenceException;

import org.hibernate.QueryException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class PersistenceExceptionHandler {
	
	private static Logger logger = LoggerFactory.getLogger(PersistenceExceptionHandler.class);

	@AroundInvoke
	public Object handle(InvocationContext context) throws Exception {
		Object result = null;
		try {
			result = context.proceed();	
		} catch (NoResultException nre) {
			logger.error(nre.getMessage(), nre);
			throw new DAOException("entidade.naoEncontrada", nre);
		} catch (NonUniqueResultException nure) {
			logger.error(nure.getMessage(), nure);
			throw new DAOException("entidade.naoUnica", nure);
		} catch (PersistenceException ex) {
			logger.error(ex.getMessage(), ex);
			throw new DAOException("dao.mensagem.exception", ex);
		} catch (QueryException qex) {
			logger.error(qex.getMessage(), qex);
			throw new DAOException("dao.mensagem.exception", qex);
		}
		return result;
	}
}
