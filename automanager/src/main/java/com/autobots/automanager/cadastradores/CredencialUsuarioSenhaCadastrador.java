package com.autobots.automanager.cadastradores;

import java.util.Date;
import java.util.List;

import com.autobots.automanager.entidades.CredencialUsuarioSenha;
import com.autobots.automanager.entidades.Usuario;
import com.autobots.automanager.modelos.StringVerificadorNulo;

public class CredencialUsuarioSenhaCadastrador {
	private StringVerificadorNulo verificador = new StringVerificadorNulo();

	public void cadastrar(Usuario usuario, CredencialUsuarioSenha credencial) {
		if (credencial != null) {
			CredencialUsuarioSenha novoCredencial = new CredencialUsuarioSenha();
			novoCredencial.setCriacao(new Date());
			novoCredencial.setUltimoAcesso(new Date());
			novoCredencial.setInativo(false);
			if (!verificador.verificar(credencial.getNomeUsuario())) {
				novoCredencial.setNomeUsuario(credencial.getNomeUsuario());
			}
			if (!verificador.verificar(credencial.getSenha())) {
				novoCredencial.setSenha(credencial.getSenha());
			}
			usuario.getCredenciais().add(novoCredencial);
		}
	}

	public void cadastrar(Usuario usuario, List<CredencialUsuarioSenha> credenciais) {
		for (CredencialUsuarioSenha credencial : credenciais) {
			cadastrar(usuario, credencial);
		}
	}
}
