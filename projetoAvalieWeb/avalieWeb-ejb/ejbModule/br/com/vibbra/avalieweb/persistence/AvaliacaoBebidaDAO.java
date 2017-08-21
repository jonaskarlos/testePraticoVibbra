package br.com.vibbra.avalieweb.persistence;

import java.util.List;

import br.com.vibbra.avalieweb.entity.Avaliacao;
import br.com.vibbra.avalieweb.entity.AvaliacaoBebida;

public interface AvaliacaoBebidaDAO extends GenericDAO<AvaliacaoBebida> {
	
	public List<AvaliacaoBebida> pesquisarPorAvaliacao(Avaliacao avaliacao);
}
