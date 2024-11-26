package com.autobots.automanager.atualizadores;

import org.springframework.stereotype.Service;

import com.autobots.automanager.entidades.Usuario;

import com.autobots.automanager.modelos.UsuarioDto;
import com.autobots.automanager.modelos.StringVerificadorNulo;

@Service
public class UsuarioAtualizador {
	private StringVerificadorNulo verificador = new StringVerificadorNulo();

	private void atualizarDados(Usuario usuario, UsuarioDto atualizacao) {
		if (!verificador.verificar(atualizacao.getNome())) {
			usuario.setNome(atualizacao.getNome());
		}
		if (!verificador.verificar(atualizacao.getNomeSocial())) {
			usuario.setNomeSocial(atualizacao.getNomeSocial());
		}
		if (atualizacao.getPerfis() != null) {
			usuario.setPerfis(atualizacao.getPerfis());
		}
		
	}

	public void atualizar(Usuario usuario, UsuarioDto atualizacao) {
		atualizarDados(usuario, atualizacao);
	}
}