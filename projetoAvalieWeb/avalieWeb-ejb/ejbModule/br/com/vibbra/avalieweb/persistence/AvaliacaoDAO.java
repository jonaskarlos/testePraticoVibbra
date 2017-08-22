package br.com.vibbra.avalieweb.persistence;

import java.util.List;

import br.com.vibbra.avalieweb.entity.Avaliacao;
import br.com.vibbra.avalieweb.entity.Usuario;
import br.com.vibbra.avalieweb.enumeration.TipoEstabelecimentoEnum;

public interface AvaliacaoDAO extends GenericDAO<Avaliacao> {
	
	public List<Avaliacao> pesquisarAvalicaoPorUsuario(Usuario usuario);
	
	public List<Avaliacao> pesquisarAvaliacaoPorUsuarioTipoEndereco(Usuario usuario, 
			TipoEstabelecimentoEnum tipo, String endereco);
	
}
