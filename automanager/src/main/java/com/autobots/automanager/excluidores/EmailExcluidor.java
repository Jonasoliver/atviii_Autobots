package com.autobots.automanager.excluidores;

import java.util.List;

import com.autobots.automanager.entidades.Email;
import com.autobots.automanager.entidades.Usuario;
import com.autobots.automanager.modelos.StringVerificadorNulo;

public class EmailExcluidor {

	private StringVerificadorNulo verificador = new StringVerificadorNulo();

	public void excluir(Usuario usuario, Email Email) {
		if (Email != null) {
			if (!verificador.verificar(Email.getEndereco())) {
				usuario.getEmails().remove(Email);
			}
		}
	}

	public void excluir(Usuario usuario, List<Email> Emails) {
		for (Email EmailExcluido : Emails) {
			if (EmailExcluido.getId() != null) {
				excluir(usuario, EmailExcluido);
			}
		}
	}
}
