package br.com.vibbra.avalieweb.business.imp;

import java.io.Serializable;
import java.util.Collection;
import java.util.List;

import javax.interceptor.Interceptors;

import br.com.vibbra.avalieweb.business.GenericBusiness;
import br.com.vibbra.avalieweb.entity.GenericEntity;
import br.com.vibbra.avalieweb.exception.BusinessException;
import br.com.vibbra.avalieweb.exception.EJBExceptionHandler;
import br.com.vibbra.avalieweb.persistence.GenericDAO;

@Interceptors({EJBExceptionHandler.class})
public abstract class GenericBusinessImp<T extends GenericEntity, Dao extends GenericDAO<T>> implements GenericBusiness<T> {
	private static final long serialVersionUID = -3741928506208078810L;

	public T save(T instance, boolean flush) throws BusinessException {
		if (instance.getId() == null) {
			this.validateCreate(instance);
			return getDao().save(instance, flush);
		} else {
			this.validateUpdate(instance);
			return getDao().merge(instance, flush);
		}
	}
	
	public T save(T instance) throws BusinessException {
		return save(instance, true);
	}
	
	public Collection<T> saveAll(Collection<T> instances, boolean flush) throws BusinessException {
		for (T instance : instances) {
			save(instance, flush);
		}
		return instances;
	}
	
	public Collection<T> saveAll(Collection<T> instances) throws BusinessException {
		return saveAll(instances, true);
	}
	
	public void remove(T instance, boolean flush, boolean mergeBefore) throws BusinessException {
		this.validateRemove(instance);
		getDao().remove(instance, flush, mergeBefore);
	}

	public void remove(T instance, boolean flush) throws BusinessException {
		remove(instance, flush, true);
	}
	
	public void remove(T instance) throws BusinessException {
		remove(instance, true);
	}
	
	public void removeAll(Collection<T> instances) throws BusinessException {
		for (T instance : instances) {
			remove(instance);
		}
	}
	
	public void removeAll(Collection<T> instances, boolean flush) throws BusinessException {
		for (T instance : instances) {
			remove(instance, flush);
		}
	}
	
	public void refresh(T instance) {
		getDao().refresh(instance);
	}
	
	/**
	 * Valida a criação de um objeto.
	 * @param instance
	 * @throws BusinessException
	 */
	protected void validateCreate(T instance) throws BusinessException {
		this.validate(instance, false);
	}

	/**
	 * Valida a atualização de um objeto.
	 * @param instance
	 * @throws BusinessException
	 */
	protected void validateUpdate(T instance) throws BusinessException {
		this.validate(instance, true);
	}

	/**
	 * Valida a remoção de objetos.
	 * @param instance
	 * @throws BusinessException
	 */
	protected void validateRemove(T instance) throws BusinessException {
		// empty
	}
	
	/**
	 * Valida todas as regras de negócio
     * Ordem de chamada: required, invalid and business rules.
	 * @param instance
	 * @param isUpdating indica se é uma operação de update(merge).
	 * @throws BusinessException
	 */
	protected void validate(T instance, boolean isUpdating) throws BusinessException {
		this.validateRequiredFields(instance, isUpdating);
		this.validateInvalidFields(instance, isUpdating);
		this.validateBusinessRules(instance, isUpdating);
	}
	
	/**
	 * Valida os campos obrigatórios.
	 * 
	 * @param entity
	 * @param isUpdating
	 * @throws BusinessException
	 */
	protected void validateRequiredFields(T instance, boolean isUpdating) throws BusinessException {
		// empty
	}

	/**
	 * Valida os campos de entrada inválidos ou incorretos. 
	 * 
	 * @param instance
	 * @param isUpdating
	 * @throws BusinessException
	 */
	protected void validateInvalidFields(T instance, boolean isUpdating) throws BusinessException {
		// empty
	}

	/**
	 * Valida as regras de negócio.
	 * 
	 * @param instance
	 * @param isUpdating
	 * @throws BusinessException
	 */
	protected void validateBusinessRules(T instance, boolean isUpdating) throws BusinessException {
		// empty
	}
	
	public List<T> pesquisar(T instance) {
		return getDao().findByExample(instance);
	}
	
	public T findById(Serializable id) {
		return getDao().findById(id);
	}

	public List<T> findAll() {
		return getDao().findAll();
	}

	public List<T> findAll(String orderField) {
		return getDao().findAll(orderField);
	}

	public List<T> findAll(String orderField, boolean descending) {
		return getDao().findAll(orderField, descending);
	}

	public List<T> findAll(Integer begin, Integer end) {
		return getDao().findAll(begin, end);
	}

	public List<T> findAll(Integer begin, Integer end, String orderField) {
		return getDao().findAll(begin, end, orderField);
	}

	public List<T> findByExample(T instance) {
		return getDao().findByExample(instance);
	}

	public List<T> findByExample(T instance, String orderField) {
		return getDao().findByExample(instance, orderField);
	}

	public List<T> findByExample(T instance, String orderField, boolean descending) {
		return getDao().findByExample(instance, orderField, descending);
	}

	public List<T> findByExample(T instance, Integer begin, Integer end) {
		return getDao().findByExample(instance, begin, end);
	}

	public List<T> findByExample(T instance, Integer begin, Integer end, String orderField) {
		return getDao().findByExample(instance, begin, end, orderField);
	}

	public List<T> findByExample(T instance, Integer begin, Integer end, String orderField, boolean descending) {
		return getDao().findByExample(instance, begin, end, orderField, descending);
	}
	
	
	@Override
	public Long  getSequenceNextValue(String sequenceName) {
		return getDao().getSequenceValue(sequenceName);
	}
	
	public abstract Dao getDao();
	
	public void flushAndClear() {
		getDao().flushAndClear();
	}
	
	public boolean isManaged(T instance) {
		return getDao().isManaged(instance);
	}
	
	public T lazyInitialize(T instance) {
		return getDao().lazyInitialize(instance);
	}
}
