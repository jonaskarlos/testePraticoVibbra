package br.com.vibbra.avalieweb.business.imp;

import org.jboss.seam.annotations.AutoCreate;
import org.jboss.seam.annotations.In;
import org.jboss.seam.annotations.Name;

import br.com.vibbra.avalieweb.business.EstabelecimentoBusiness;
import br.com.vibbra.avalieweb.entity.Estabelecimento;
import br.com.vibbra.avalieweb.persistence.EstabelecimentoDAO;

@Name("estabelecimentoBusiness")
@AutoCreate
public class EstabelecimentoBusinessImp extends GenericBusinessImp<Estabelecimento, EstabelecimentoDAO> implements EstabelecimentoBusiness {

	private static final long serialVersionUID = 6437722234034135590L;
	
	@In
	EstabelecimentoDAO estabelecimentoDAO;

	@Override
	public EstabelecimentoDAO getDao() {
		return estabelecimentoDAO;
	}

}
