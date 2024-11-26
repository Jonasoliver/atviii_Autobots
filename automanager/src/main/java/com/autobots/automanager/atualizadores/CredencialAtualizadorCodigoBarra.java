package com.autobots.automanager.atualizadores;

import java.util.List;
import java.util.Set;

import com.autobots.automanager.entidades.CredencialCodigoBarra;

public class CredencialAtualizadorCodigoBarra {

	public void atualizar(CredencialCodigoBarra credencial, CredencialCodigoBarra atualizacao) {
		if (atualizacao != null) {
				credencial.setCodigo(atualizacao.getCodigo());
		}
	}

	public void atualizar(Set<CredencialCodigoBarra> credenciais, List<CredencialCodigoBarra> atualizacoes) {
		for (CredencialCodigoBarra atualizacao : atualizacoes) {
			for (CredencialCodigoBarra credencial : credenciais) {
				if (atualizacao.getId() != null) {
					if (atualizacao.getId() == credencial.getId()) {
						atualizar(credencial, atualizacao);
					}
				}
			}
		}
	}
}
