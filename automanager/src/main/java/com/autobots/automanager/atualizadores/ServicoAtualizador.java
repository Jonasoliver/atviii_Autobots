package com.autobots.automanager.atualizadores;

import java.util.List;
import java.util.Set;

import com.autobots.automanager.entidades.Servico;
import com.autobots.automanager.modelos.StringVerificadorNulo;

public class ServicoAtualizador {
	private StringVerificadorNulo verificador = new StringVerificadorNulo();

	public void atualizar(Servico servico, Servico atualizacao) {
		if (atualizacao != null) {
			if (!verificador.verificar(atualizacao.getNome())) {
				servico.setNome(atualizacao.getNome());
			}
			if (!verificador.verificar(atualizacao.getDescricao())) {
				servico.setDescricao(atualizacao.getDescricao());
			}
			servico.setValor(atualizacao.getValor());
		}
	}

	public void atualizar(Set<Servico> servicos, List<Servico> atualizacoes) {
		for (Servico atualizacao : atualizacoes) {
			for (Servico servico : servicos) {
				if (atualizacao.getId() != null) {
					atualizar(servico, atualizacao);
				}
			}
		}
	}
}