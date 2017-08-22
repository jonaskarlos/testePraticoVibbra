package br.com.vibbra.avalieweb.business.imp;

import java.util.List;

import org.jboss.seam.annotations.AutoCreate;
import org.jboss.seam.annotations.In;
import org.jboss.seam.annotations.Name;

import br.com.vibbra.avalieweb.business.AvaliacaoBebidaBusiness;
import br.com.vibbra.avalieweb.business.AvaliacaoBusiness;
import br.com.vibbra.avalieweb.business.AvaliacaoComidaBusiness;
import br.com.vibbra.avalieweb.entity.Avaliacao;
import br.com.vibbra.avalieweb.entity.Usuario;
import br.com.vibbra.avalieweb.enumeration.TipoEstabelecimentoEnum;
import br.com.vibbra.avalieweb.exception.BusinessException;
import br.com.vibbra.avalieweb.persistence.AvaliacaoDAO;

@Name("avaliacaoBusiness")
@AutoCreate
public class AvaliacaoBusinessImp extends GenericBusinessImp<Avaliacao, AvaliacaoDAO> implements AvaliacaoBusiness {

	private static final long serialVersionUID = -8350164782109011769L;
	
	@In
	AvaliacaoDAO avaliacaoDAO;
	
	@In
	private AvaliacaoComidaBusiness avaliacaoComidaBusiness;
	
	@In
	private AvaliacaoBebidaBusiness avaliacaoBebidaBusiness;
	
	@Override
	public List<Avaliacao> pesquisarAvalicaoPorUsuario(Usuario usuario) {
		return getDao().pesquisarAvalicaoPorUsuario(usuario);
	}
	
	@Override
	public List<Avaliacao> pesquisarAvaliacaoPorUsuarioTipoEndereco(Usuario usuario, 
			TipoEstabelecimentoEnum tipo, String endereco) {
		return getDao().pesquisarAvaliacaoPorUsuarioTipoEndereco(usuario, tipo, endereco);
	}
	
	@Override
	protected void validateRemove(Avaliacao avaliacao) throws BusinessException {
		avaliacaoBebidaBusiness.excluirBebidaPorAvaliacao(avaliacao);
		avaliacaoComidaBusiness.excluirComidaPorAvaliacao(avaliacao);
	}

	@Override
	public AvaliacaoDAO getDao() {
		return avaliacaoDAO;
	}

}
