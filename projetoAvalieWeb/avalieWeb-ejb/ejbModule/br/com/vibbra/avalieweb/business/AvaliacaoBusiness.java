package br.com.vibbra.avalieweb.business;

import java.util.List;

import br.com.vibbra.avalieweb.entity.Avaliacao;
import br.com.vibbra.avalieweb.entity.Usuario;
import br.com.vibbra.avalieweb.enumeration.TipoEstabelecimentoEnum;

public interface AvaliacaoBusiness extends GenericBusiness<Avaliacao> {
	
	public List<Avaliacao> pesquisarAvalicaoPorUsuario(Usuario usuario);
	
	public List<Avaliacao> pesquisarAvaliacaoPorUsuarioTipoEndereco(Usuario usuario, 
			TipoEstabelecimentoEnum tipo, String endereco);

}
