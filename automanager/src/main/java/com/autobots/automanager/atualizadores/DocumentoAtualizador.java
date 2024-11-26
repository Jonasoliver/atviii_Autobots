package com.autobots.automanager.atualizadores;

import java.util.List;
import java.util.Set;

import com.autobots.automanager.entidades.Documento;
import com.autobots.automanager.modelos.StringVerificadorNulo;

public class DocumentoAtualizador {
	private StringVerificadorNulo verificador = new StringVerificadorNulo();

	public void atualizar(Documento documento, Documento atualizacao) {
		if (atualizacao != null) {
			if (!verificador.verificar(atualizacao.getNumero())) {
				documento.setTipo(atualizacao.getTipo());
				documento.setNumero(atualizacao.getNumero());
			}
		}
	}

	public void atualizar(Set<Documento> documentos, List<Documento> atualizacoes) {
		for (Documento atualizacao : atualizacoes) {
			for (Documento documento : documentos) {
				if (atualizacao.getId() != null) {
					if (atualizacao.getId() == documento.getId()) {
						atualizar(documento, atualizacao);
					}
				}
			}
		}
	}
}
