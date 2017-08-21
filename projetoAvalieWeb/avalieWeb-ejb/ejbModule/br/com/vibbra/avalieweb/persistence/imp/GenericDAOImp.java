package br.com.vibbra.avalieweb.persistence.imp;

import java.io.Serializable;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.math.BigInteger;
import java.util.Collection;
import java.util.List;
import java.util.Map;

import javax.interceptor.Interceptors;
import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.PersistenceException;
import javax.persistence.Query;
import javax.persistence.Transient;

import org.hibernate.Criteria;
import org.hibernate.Session;
import org.hibernate.criterion.Example;
import org.hibernate.criterion.MatchMode;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Projections;

import br.com.vibbra.avalieweb.entity.GenericEntity;
import br.com.vibbra.avalieweb.exception.DAOException;
import br.com.vibbra.avalieweb.exception.PersistenceExceptionHandler;
import br.com.vibbra.avalieweb.persistence.GenericDAO;

@SuppressWarnings("unchecked")
@Interceptors(PersistenceExceptionHandler.class)
public abstract class GenericDAOImp<T extends GenericEntity> implements	GenericDAO<T> {

	private static final long serialVersionUID = 1L;
	
	protected static final String MSG_ENTITY_MANAGER_INDISPONIVEL = "Entity Manager não encontrado";
	private static final String MSG_ERRO = "Erro ao acessar banco de dados: ";
	private static final String DESC = "desc";
	private static final String ASC = "asc";
	
	private Class<T> entityClass;
	
	private Class<T> getEntityClass() {
		if (entityClass == null) {
			Type type = getClass().getGenericSuperclass();
			if (type instanceof ParameterizedType) {
				ParameterizedType paramType = (ParameterizedType) type;
				entityClass = (Class<T>) paramType.getActualTypeArguments()[0];
				return entityClass;
			} else {
				throw new RuntimeException("Não foi possível descobrir a entidade por reflexão.");
			}
		} else {
			return entityClass;
		}
	}
	
	public void flushAndClear() {
		getEntityManager().flush();
		getEntityManager().clear();
	}
	
	public boolean isManaged(T instance) {
		return getEntityManager().contains(instance);
	}

	/**
	 * Método para salvar uma entidade do objeto genérico informado.
	 * 
	 * @param instance
	 *            Entidade a ser salva.
	 * @param flush
	 *            indica se o flush deverá ser feito.            
	 * @return instance Entidade com estado sincronizado ao banco de dados.
	 * @throws PersistenceException
	 *             Erro ao acessar o banco de dados.
	 */
	public T save(T instance, boolean flush) {
		getEntityManager().persist(instance);
		if(flush)
			getEntityManager().flush();
		return instance;
	}
	
	public T save(T instance) {
		return this.save(instance, true);
	}

	/**
	 * Método para atualizar uma entidade do objeto genérico informado.
	 * 
	 * @param instance
	 *            Entidade a ser atualizada.
	 * @param flush
	 *            indica se o flush deverá ser feito.     
	 * @return instance Entidade com estado sincronizado ao banco de dados.
	 * @throws PersistenceException
	 *             Erro ao acessar o banco de dados.
	 */
	public T merge(T instance, boolean flush) {
		instance = getEntityManager().merge(instance);
		if(flush)
			getEntityManager().flush();
		return instance;
	}
	
	public T merge(T instance) {
		return this.merge(instance, true);
	}

	/**
	 * Método para excluir uma entidade do objeto genérico informado.
	 * 
	 * @param instance
	 *            Entidade a ser excluída.
	 * @throws PersistenceException
	 *             Erro ao acessar o banco de dados.
	 */
	public void remove(T instance, boolean flush) {
		remove(instance, flush, true);
	}
	
	public void remove(T instance) {
		this.remove(instance, true);
	}
	
	public void remove(T instance, boolean flush, boolean mergeBefore) {
		if (mergeBefore) {
			T entidade2 = getEntityManager().merge(instance);
			getEntityManager().remove(entidade2);
		} else {
			//TORNAR ISSO PADRÃO ---
			getEntityManager().remove(getEntityManager().find(getEntityClass(), instance.getId()));
		}
		if(flush)
			getEntityManager().flush();
	}
	
	public void refresh(T instance) {
		getEntityManager().refresh(instance);
	}

	public T lazyInitialize(T instance) {
		instance = getEntityManager().merge(instance);
		for (Method m : instance.getClass().getMethods()) {
			if (m.getName().startsWith("get")) {
				if (Collection.class.isAssignableFrom(m.getReturnType())) {
					try {
						Collection<?> c = ((Collection<?>) m.invoke(instance));
						if (c != null) {
							c.size();
						}
					} catch (Exception e) {
						throw new DAOException(e);
					}
				}
			}
		}
		return instance;
	}

	/**
	 * Método para buscar uma entidade a partir do ID.
	 * 
	 * @param id
	 *            Tipo do Id da entidade. Exemplo: "Integer".
	 * @return T Entidade.
	 * @throws PersistenceException
	 *             Erro ao acessar o banco de dados.
	 */
	public T findById(Serializable id) {
		return getEntityManager().find(getEntityClass(), id);
	}

	/**
	 * Método para buscar uma entidade do objeto genérico informado a partir de
	 * uma query com a utilização de filtros de parâmetros.
	 * 
	 * @param queryString
	 *            Query (em HQL) utilizada na busca.
	 * @param parameters
	 *            Mapa de paramêtros utilizados na busca.
	 * @return T Entidade.
	 * @throws PersistenceException
	 *             Erro ao acessar o banco de dados.
	 */
	protected T findByParameters(String queryString, Map<String, Object> parameters) {
		try {
			Query query = getEntityManager().createQuery(queryString);
			query = this.setQueryParameters(query, parameters);
			return (T) query.getSingleResult();
		} catch (NoResultException e) {
			return null;
		}
	}

	/**
	 * Método para buscar todas as entidades do objeto genérico informado.
	 * 
	 * @return Lista de objetos resultante da busca.
	 * @throws PersistenceException
	 *             Erro ao acessar o banco de dados.
	 */
	public List<T> findAll() {
		return getFindAllQuery().getResultList();
	}

	/**
	 * Método para buscar todas as entidades do objeto genérico informado
	 * utilizando delimitadores de registros.
	 * 
	 * @param begin
	 *            Limite inicial dos registros retornados.
	 * @param end
	 *            Limite final dos registros retornados.
	 * @return Lista de objetos resultante da busca.
	 * @throws PersistenceException
	 *             Erro ao acessar o banco de dados. *
	 */
	public List<T> findAll(Integer begin, Integer end) {
		Query query = getFindAllQuery();
		query.setFirstResult(begin);
		query.setMaxResults(end);
		return query.getResultList();
	}

	public List<T> findAll(String orderField) {
		return this.findAll(orderField, false);
	}
	
	protected Query getFindAllQuery() {
		return getEntityManager().createQuery("FROM " + getEntityClass().getSimpleName());
	}
	
	protected Query getFindAllOrderedQuery(String orderField, boolean descending) {
		return getEntityManager().createQuery(" FROM " + getEntityClass().getSimpleName() + "  order by  " + orderField + " " + (descending ?  DESC :  ASC ));
	}

	/**
	 * Método para buscar todas as entidades do objeto genérico informado
	 * ordenando-os pelo atributo passado como parâmetro.
	 * 
	 * @param orderInfo
	 *            Objeto utilizado para representar informações da ordenação da
	 *            consulta. O atributo de ordenação deverá pertencer
	 *            obrigatoriamente ao objeto pai (não funcionará com atributos
	 *            pertencentes a relacionamentos).
	 * 
	 * @return Lista de objetos resultante da busca.
	 * @throws PersistenceException
	 *             Erro ao acessar o banco de dados. Erro ao acessar banco de
	 *             dados.
	 */
	public List<T> findAll(String orderField, boolean descending) {
		return getFindAllOrderedQuery(orderField, descending).getResultList();
	}

	public List<T> findAll(Integer begin, Integer end, String orderField) {
		return this.findAll(begin, end, orderField, false);
	}

	/**
	 * Método para buscar todas as entidades do objeto genérico informado
	 * utilizando delimitadores de registros e ordenando-os pelo atributo
	 * passado como parâmetro.
	 * 
	 * @param begin
	 *            Limite inicial dos registros retornados.
	 * @param end
	 *            Limite final dos registros retornados.
	 * @return Lista de objetos resultante da busca.
	 * @throws PersistenceException
	 *             Erro ao acessar o banco de dados.
	 */
	public List<T> findAll(Integer begin, Integer end, String orderField, boolean descending) {
		Query query = getFindAllOrderedQuery(orderField, descending);
		query.setFirstResult(begin);
		query.setMaxResults(end);
		return query.getResultList();
	}

	/**
	 * Método para buscar entidades do objeto genérico informado a partir de uma
	 * query.
	 * 
	 * @param queryString
	 * @return Lista de objetos resultante da busca.
	 * @throws PersistenceException
	 *             Erro ao acessar o banco de dados.
	 */
	protected List<T> find(String queryString) {
		return getEntityManager().createQuery(queryString).getResultList();
	}
	
	/**
	 * Método para buscar entidades do objeto genérico informado a partir de uma
	 * query com a utilização de delimitadores de registros.
	 * 
	 * @param queryString
	 *            Query (em HQL) utilizada na busca.
	 * @param begin
	 *            Limite inicial dos registros retornados.
	 * @param end
	 *            Limite final dos registros retornados.
	 * @return Lista de objetos resultante da busca.
	 * @throws PersistenceException
	 *             Erro ao acessar o banco de dados.
	 */
	protected List<T> find(String queryString, Integer begin, Integer end) {
		Query query = getEntityManager().createQuery(queryString);
		query.setFirstResult(begin);
		query.setMaxResults(end);
		return query.getResultList();
	}

	/**
	 * Método para buscar entidades do objeto genérico informado a partir de uma
	 * query com a utilização de filtros de parâmetros.
	 * 
	 * @param queryString
	 *            Query (em HQL) utilizada na busca.
	 * @param parameters
	 *            Mapa de paramêtros (filtros) utilizados na busca.
	 * @return Lista de objetos resultante da busca.
	 * @throws PersistenceException
	 *             Erro ao acessar o banco de dados.
	 */

	protected List<T> find(String queryString, Map<String, Object> parameters) {
		Query query = getEntityManager().createQuery(queryString);
		query = this.setQueryParameters(query, parameters);
		return query.getResultList();
	}

	/**
	 * Método para buscar entidades do objeto genérico informado a partir de uma
	 * query com a utilização de filtros de parâmetros e demilitadores de
	 * registros retornados.
	 * 
	 * @param queryString
	 *            Query (em HQL) utilizada na busca.
	 * @param parameters
	 *            Mapa de paramêtros (filtros) utilizados na busca.
	 * @return Lista de objetos resultante da busca.
	 * @throws PersistenceException
	 *             Erro ao acessar o banco de dados.
	 * 
	 */

	protected List<T> find(String queryString, Map<String, Object> parameters, Integer begin, Integer end) {
		Query query = getEntityManager().createQuery(queryString);
		query.setFirstResult(begin);
		query.setMaxResults(end);
		query = this.setQueryParameters(query, parameters);
		return query.getResultList();
	}

	/**
	 * Método para retornar a quantidade de resultados de uma busca a ser
	 * efetuada utilizando-se o método findByExample
	 * 
	 * @param instance
	 *            Objeto de "exemplo" com atributos a serem utilizados na busca
	 *            populados.
	 * @return Quantidade de resultados da busca.
	 * @throws PersistenceException
	 *             Erro ao acessar o banco de dados.
	 */
	public Integer countFindByExample(T instance) {
		try {
			Criteria criteria = this.createCriteria(instance);
			criteria.setProjection(Projections.rowCount());
			return (Integer) criteria.uniqueResult();
		} catch (Exception e) {
			throw new PersistenceException(MSG_ERRO + e);
		}
	}

	/**
	 * Método para buscar entidades do objeto genérico informado a partir dos
	 * atributos informados neste objeto. Funciona somente para busca em
	 * atributos de tipos simples, não funciona para busca em relacionamentos
	 * com outros objetos.
	 * 
	 * @param instance
	 *            Objeto de "exemplo" com atributos a serem utilizados na busca
	 *            populados.
	 * @return Lista de objetos resultante da busca.
	 * @throws PersistenceException
	 *             Erro ao acessar o banco de dados.
	 */
	public List<T> findByExample(T instance) {
		try {
			return this.createCriteria(instance).list();
		} catch (Exception e) {
			throw new PersistenceException(MSG_ERRO + e);
		}
	}

	public List<T> findByExample(T instance, String orderField) {
		return this.findByExample(instance, orderField, false);
	}

	/**
	 * Método para buscar entidades do objeto genérico informado a partir dos
	 * atributos informados neste objeto ordenando-os pelo atributo informado em
	 * ordem ascendente. Para atributos que forem relacionamentos, o método
	 * efetuará a busca somente em relacionamentos one-to-one e many-to-one.
	 * 
	 * @param instance
	 *            Objeto de "exemplo" com atributos a serem utilizados na busca
	 *            populados.
	 * @param orderInfo
	 *            Objeto utilizado para representar informações da ordenação da
	 *            consulta. O atributo de ordenação deverá pertencer
	 *            obrigatoriamente ao objeto pai (não funcionará com atributos
	 *            pertencentes a relacionamentos).
	 * 
	 * 
	 * 
	 * @return Lista de objetos resultante da busca.
	 * @throws PersistenceException
	 *             Erro ao acessar o banco de dados.
	 */

	public List<T> findByExample(T instance, String orderField, boolean descending) {
		try {
			Criteria criteria = createCriteria(instance, orderField, descending);
			return criteria.list();
		} catch (Exception e) {
			throw new PersistenceException(MSG_ERRO + e);
		}
	}

	/**
	 * Método para buscar entidades do objeto genérico informado a partir dos
	 * atributos informados neste objeto utilizando delimitadores de registros.
	 * Para atributos que forem relacionamentos, o método efetuará a busca
	 * somente em relacionamentos one-to-one e many-to-one.
	 * 
	 * @param instance
	 *            Objeto de "exemplo" com atributos a serem utilizados na busca
	 *            populados.
	 * @param begin
	 *            Limite inicial dos registros retornados.
	 * @param end
	 *            Limite final dos registros retornados.
	 * 
	 * @return Lista de objetos resultante da busca.
	 * @throws PersistenceException
	 *             Erro ao acessar o banco de dados.
	 */
	public List<T> findByExample(T instance, Integer begin, Integer end) {
		try {
			Criteria criteria = createCriteria(instance);
			criteria.setFirstResult(begin);
			criteria.setMaxResults(end);
			return criteria.list();
		} catch (Exception e) {
			throw new PersistenceException(MSG_ERRO + e);
		}
	}

	public List<T> findByExample(T instance, Integer begin, Integer end, String orderField) {
		return this.findByExample(instance, begin, end, orderField, false);
	}

	/**
	 * Método para buscar entidades do objeto genérico informado a partir dos
	 * atributos informados neste objeto utilizando delimitadores de registros
	 * ordernando-os pelo atributo informado em ordem ascendente. Para atributos
	 * que forem relacionamentos, o método efetuará a busca somente em
	 * relacionamentos one-to-one e many-to-one.
	 * 
	 * @param instance
	 *            Objeto de "exemplo" com atributos a serem utilizados na busca
	 *            populados.
	 * @param begin
	 *            Limite inicial dos registros retornados.
	 * @param end
	 *            Limite final dos registros retornados.
	 * @param orderInfo
	 *            Objeto utilizado para representar informações da ordenação da
	 *            consulta. O atributo de ordenação deverá pertencer
	 *            obrigatoriamente ao objeto pai (não funcionará com atributos
	 *            pertencentes a relacionamentos).
	 * @return Lista de objetos resultante da busca.
	 * @throws PersistenceException
	 *             Erro ao acessar o banco de dados.
	 */
	public List<T> findByExample(T instance, Integer begin, Integer end, String orderField, boolean descending) {
		try {
			Criteria criteria = createCriteria(instance, orderField, descending);
			criteria.setFirstResult(begin);
			criteria.setMaxResults(end);
			return criteria.list();
		} catch (Exception e) {
			throw new PersistenceException(MSG_ERRO + e);
		}
	}

	/**
	 * Método para fazer uma atualizacao (ou deleção) a partir de uma query com
	 * a utilização de filtros de parâmetros.
	 * 
	 * @param queryString
	 *            Query (em HQL) utilizada na busca.
	 * 
	 * @param parameters
	 *            Mapa de paramêtros utilizados na busca.
	 * 
	 * @return numero de entidades deletadas ou atualizadas .
	 * 
	 * @throws PersistenceException
	 *             Erro ao acessar o banco de dados.
	 */
	protected int updateByParameters(String queryString, Map<String, Object> parameters) {
		try {
			Query query = getEntityManager().createQuery(queryString);
			query = this.setQueryParameters(query, parameters);
			return query.executeUpdate();
		} catch (NoResultException e) {
			return 0;
		} catch (RuntimeException e) {
			throw new PersistenceException(MSG_ERRO + e);

		}
	}

	private Query setQueryParameters(Query query, Map<String, Object> parameters) {
		if (query != null) {
			for (Map.Entry<String, Object> param : parameters.entrySet()) {
				query.setParameter(param.getKey(), param.getValue());
			}
		} else {
			return null;
		}
		return query;
	}

	protected abstract EntityManager getEntityManager();

	private Criteria createCriteria(T instance) throws SecurityException,
			IllegalArgumentException, NoSuchMethodException,
			IllegalAccessException, InvocationTargetException {
		return this.createCriteria(instance, null, false);
	}

	private Criteria createCriteria(T instance, String orderField,
			boolean descending) throws SecurityException,
			IllegalArgumentException, NoSuchMethodException,
			IllegalAccessException, InvocationTargetException {
		Session session = (Session) getEntityManager().getDelegate();
		Example example = Example.create(instance);
		example.enableLike(MatchMode.ANYWHERE);
		example.excludeZeroes();
		example.ignoreCase();

		// Criando o criteria principal
		Criteria criteriaPrincipal = session
				.createCriteria(instance.getClass());

		criteriaPrincipal.add(example);

		//inclui as associações
		gerarExemplosAninhados(criteriaPrincipal, instance);

		// Adicionando criteria de ordenação
		if (orderField != null) {
			if (descending) {
				criteriaPrincipal.addOrder(Order.desc(orderField));
			} else {
				criteriaPrincipal.addOrder(Order.asc(orderField));
			}
		}
		return criteriaPrincipal;
	}
	
	private void gerarExemplosAninhados(Criteria criteria, T entidade) throws DAOException {
		Method[] methods = entidade.getClass().getMethods();
		for(Method method:methods) {

			// Verificar se é um "Get".
			if ( method.getName().indexOf("get") > -1 && !method.isAnnotationPresent(Transient.class)) {
				try {
					Object o = null;
					try {
						 o = method.invoke(entidade);
					} catch (InvocationTargetException ioe) {}
					if (o != null) {
						if ( o instanceof GenericEntity ) {
							if (campoExiste(method.getName(), entidade)) {
								Example example = Example.create(o).enableLike(MatchMode.ANYWHERE).ignoreCase().excludeZeroes();
								String m = method.getName().substring(3);
								m = m.substring(0,1).toLowerCase() + m.substring(1);
								criteria.createCriteria(m).add(example);
							}
						}
					}
				} catch (IllegalArgumentException e) {
					throw new DAOException(e);
				} catch (IllegalAccessException e) {
					throw new DAOException(e);
				}
			}
		}
	}
	
	/**
	 * Verifica se existe uma variável para o método get.
	 * Ex. getOrgao -> private Orgao orgao;
	 * @param method
	 * @param entidade
	 * @return
	 */
	private boolean campoExiste(String method, T entidade) {
		String campo = getFirstLowerCase(method.replace("get", ""));
		try {
			entidade.getClass().getDeclaredField(campo);
			return true;
		} catch (NoSuchFieldException e) {
			return false;
		}
	}
	
	private String getFirstLowerCase(String v) {
    	return v.replaceFirst(v.substring(0, 1), v.substring(0, 1).toLowerCase());
    }

	/*
	 * Metodo para retornar o proximo numero de sequencia
	 * Obs: Se o banco for alterado poderá apresentar problemas com o sql nativo
	 */
	public long getSequenceValue(String sequenceName){  
		Query query = getEntityManager().createNativeQuery("select nextval('" + sequenceName + "')");
		BigInteger sequenceValue = (BigInteger) query.getSingleResult();
		return sequenceValue.longValue();
    } 

}
