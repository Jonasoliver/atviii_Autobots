package com.autobots.automanager.atualizadores;

import java.util.List;
import java.util.Set;

import com.autobots.automanager.entidades.Mercadoria;
import com.autobots.automanager.modelos.StringVerificadorNulo;

public class MercadoriaAtualizador {
	private StringVerificadorNulo verificador = new StringVerificadorNulo();

	public void atualizar(Mercadoria mercadoria, Mercadoria atualizacao) {
		if (atualizacao != null) {
			if (!verificador.verificar(atualizacao.getNome())) {
				mercadoria.setNome(atualizacao.getNome());
			}
			if (!verificador.verificar(atualizacao.getDescricao())) {
				mercadoria.setDescricao(atualizacao.getDescricao());
			}
			mercadoria.setCadastro(atualizacao.getCadastro());
			mercadoria.setValidade(atualizacao.getValidade());
			mercadoria.setFabricao(atualizacao.getFabricao());
			mercadoria.setQuantidade(atualizacao.getQuantidade());
			mercadoria.setValor(atualizacao.getValor());
		}
	}

	public void atualizar(Set<Mercadoria> mercadorias, List<Mercadoria> atualizacoes) {
		for (Mercadoria atualizacao : atualizacoes) {
			for (Mercadoria mercadoria : mercadorias) {
				if (atualizacao.getId() != null) {
					atualizar(mercadoria, atualizacao);
				}
			}
		}
	}
}