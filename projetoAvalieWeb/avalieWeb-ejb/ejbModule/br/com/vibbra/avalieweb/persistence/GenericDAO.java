package br.com.vibbra.avalieweb.persistence;

import java.io.Serializable;
import java.util.List;

import br.com.vibbra.avalieweb.entity.GenericEntity;

public interface GenericDAO<T extends GenericEntity> extends Serializable {
	
	public void flushAndClear();
	
	public boolean isManaged(T instance);

	public T save(T instance);
	
	public T save(T instance, boolean flush);

	public T merge(T instance);
	
	public T merge(T instance, boolean flush);

	public void remove(T instance);
	
	public void remove(T instance, boolean flush);
	
	public void remove(T instance, boolean flush, boolean merge);

	public T findById(Serializable id);

	public List<T> findAll();

	public List<T> findAll(String orderField);

	public List<T> findAll(String orderField, boolean descending);

	public List<T> findAll(Integer begin, Integer end);

	public List<T> findAll(Integer begin, Integer end, String orderField);

	public List<T> findByExample(T instance);

	public List<T> findByExample(T instance, String orderField);

	public List<T> findByExample(T instance, String orderField, boolean descending);

	public List<T> findByExample(T instance, Integer begin, Integer end);

	public List<T> findByExample(T instance, Integer begin, Integer end, String orderField);

	public List<T> findByExample(T instance, Integer begin, Integer end, String orderField, boolean descending);

	public Integer countFindByExample(T instance);
 
	public long getSequenceValue(String sequenceName);
	
	public void refresh(T instance);
	
	public T lazyInitialize(T instance);

	public class OrderInfo {

		private final String orderField;
		private final boolean descending;
		private final Class<Object> classe;

		public OrderInfo(String orderField, boolean descending, Class<Object> classe) {
			super();
			this.orderField = orderField;
			this.descending = descending;
			this.classe = classe;
		}

		public OrderInfo(String orderField, Class<Object> classe) {
			super();
			this.orderField = orderField;
			this.descending = false;
			this.classe = classe;
		}

		public String getOrderField() {
			return orderField;
		}

		public boolean isDescending() {
			return descending;
		}

		public Object getClasse() {
			return classe;
		}

	}


}
