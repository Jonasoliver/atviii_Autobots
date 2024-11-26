package com.autobots.automanager.atualizadores;

import java.util.List;
import java.util.Set;

import com.autobots.automanager.entidades.CredencialUsuarioSenha;
import com.autobots.automanager.modelos.StringVerificadorNulo;

public class CredencialAtualizadorUsuarioSenha {
	private StringVerificadorNulo verificador = new StringVerificadorNulo();

	public void atualizar(CredencialUsuarioSenha credencial, CredencialUsuarioSenha atualizacao) {
		if (atualizacao != null) {
			if (!verificador.verificar(atualizacao.getNomeUsuario())) {
				credencial.setNomeUsuario(atualizacao.getNomeUsuario());
			}
			if (!verificador.verificar(atualizacao.getSenha())){				
				credencial.setSenha(atualizacao.getSenha());
			}
		}
	}

	public void atualizar(Set<CredencialUsuarioSenha> credenciais, List<CredencialUsuarioSenha> atualizacoes) {
		for (CredencialUsuarioSenha atualizacao : atualizacoes) {
			for (CredencialUsuarioSenha credencial : credenciais) {
				if (atualizacao.getId() != null) {
					if (atualizacao.getId() == credencial.getId()) {
						atualizar(credencial, atualizacao);
					}
				}
			}
		}
	}
}
