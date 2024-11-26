package com.autobots.automanager.atualizadores;

import java.util.List;
import java.util.Set;

import com.autobots.automanager.entidades.Email;
import com.autobots.automanager.modelos.StringVerificadorNulo;

public class EmailAtualizador {
	private StringVerificadorNulo verificador = new StringVerificadorNulo();

	public void atualizar(Email Email, Email atualizacao) {
		if (atualizacao != null) {
			if (!verificador.verificar(atualizacao.getEndereco())) {
				Email.setEndereco(atualizacao.getEndereco());
			}
		}
	}

	public void atualizar(Set<Email> Emails, List<Email> atualizacoes) {
		for (Email atualizacao : atualizacoes) {
			for (Email Email : Emails) {
				if (atualizacao.getId() != null) {
					if (atualizacao.getId() == Email.getId()) {
						atualizar(Email, atualizacao);
					}
				}
			}
		}
	}
}
