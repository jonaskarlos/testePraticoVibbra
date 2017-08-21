package br.com.vibbra.avalieweb.business;

import java.io.Serializable;
import java.util.Collection;
import java.util.List;

import br.com.vibbra.avalieweb.entity.GenericEntity;
import br.com.vibbra.avalieweb.exception.BusinessException;

public interface GenericBusiness<T extends GenericEntity> extends Serializable {
	
	void flushAndClear();
	
	boolean isManaged(T instance);

	T save(T instance) throws BusinessException;
	
	T save(T instance, boolean flush) throws BusinessException;
	
	Collection<T> saveAll(Collection<T> instances) throws BusinessException;
	
	Collection<T> saveAll(Collection<T> instances, boolean flush) throws BusinessException;
	
	T findById(Serializable id) throws BusinessException;

	void remove(T instance) throws BusinessException;
	
	void remove(T instance, boolean flush) throws BusinessException;
	
	void remove(T instance, boolean flush, boolean mergeBefore) throws BusinessException;
	
	void removeAll(Collection<T> instances) throws BusinessException;
	
	void removeAll(Collection<T> instances, boolean flush) throws BusinessException;
	
	void refresh(T instance);
	
	T lazyInitialize(T instance);
	
	List<T> pesquisar(T instance);

	List<T> findAll();

	List<T> findAll(String orderField);

	List<T> findAll(String orderField, boolean descending);

	List<T> findAll(Integer begin, Integer end);

	List<T> findAll(Integer begin, Integer end, String orderField);

	List<T> findByExample(T instance);

	List<T> findByExample(T instance, String orderField);

	List<T> findByExample(T instance, String orderField, boolean descending);

	List<T> findByExample(T instance, Integer begin, Integer end);

	List<T> findByExample(T instance, Integer begin, Integer end, String orderField);

	List<T> findByExample(T instance, Integer begin, Integer end, String orderField, boolean descending);
	
	Long getSequenceNextValue(String sequenceName);
	
}
