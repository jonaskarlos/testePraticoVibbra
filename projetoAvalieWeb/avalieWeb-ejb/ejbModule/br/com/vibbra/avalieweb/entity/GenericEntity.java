package br.com.vibbra.avalieweb.entity;

import java.io.Serializable;

public abstract class GenericEntity  implements Serializable {
	
	
	private static final long serialVersionUID = 1L;

	public abstract Long getId() ;

	public abstract void setId(Long id);

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((getId() == null) ? 0 : getId().hashCode()) + getClass().hashCode();
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj) {
			return true;
		}
		if (obj == null) {
			return false;
		}
		if (!(obj instanceof GenericEntity)) {
			return false;
		}
		GenericEntity other = (GenericEntity) obj;
		if (getId() == null) {
			if (other.getId() != null) {
				return false;
			}
		} else if (!getId().equals(other.getId())) {
			return false;
		} else {
			if (!objetosSaoDaMesmaClasse(obj.getClass(), getClass())) {
				return false;
			} else {
				return true;
			}
		}
		
		if (getClass() != obj.getClass()) {
			return false;
		}
		
		return true;
	}
	
	@SuppressWarnings("rawtypes")
	public boolean objetosSaoDaMesmaClasse(Class outra, Class atual) {
		String nomeOutra = outra.getSimpleName();
		String nomeAtual = atual.getSimpleName();
		
		if (nomeOutra.contains("_")) {
			nomeOutra = nomeOutra.substring(0, nomeOutra.indexOf("_"));
		}
		
		if (nomeAtual.contains("_")) {
			nomeAtual = nomeAtual.substring(0, nomeAtual.indexOf("_"));
		}
		return nomeAtual.equals(nomeOutra);
	}
	
}
