package com.autobots.automanager.excluidores;

import java.util.List;

import com.autobots.automanager.entidades.Documento;
import com.autobots.automanager.entidades.Usuario;
import com.autobots.automanager.modelos.StringVerificadorNulo;

public class DocumentoExcluidor {

	private StringVerificadorNulo verificador = new StringVerificadorNulo();

	public void excluir(Usuario usuario, Documento documento) {
		if (documento != null) {
			if (!verificador.verificar(documento.getNumero())) {
				usuario.getDocumentos().remove(documento);
			}
		}
	}

	public void excluir(Usuario usuario, List<Documento> documentos) {
		for (Documento documentoExcluido : documentos) {
			if (documentoExcluido.getId() != null && documentoExcluido.getTipo() != null) {
				excluir(usuario, documentoExcluido);
			}
		}
	}
}
