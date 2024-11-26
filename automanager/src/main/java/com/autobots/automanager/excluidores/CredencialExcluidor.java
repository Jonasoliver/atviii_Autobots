package com.autobots.automanager.excluidores;

import java.util.List;

import com.autobots.automanager.entidades.Credencial;
import com.autobots.automanager.entidades.Usuario;

public class CredencialExcluidor {

	public void excluir(Credencial credencial, Credencial credencialExcluida) {
		if (credencialExcluida != null && credencial.getId() == credencialExcluida.getId()) {
				credencial.setInativo(true);
		}
	}

	public void excluir(Usuario usuario, List<Credencial> credenciais) {
		for (Credencial credencial : usuario.getCredenciais())
		for (Credencial credencialExcluida : credenciais) {
			if (credencialExcluida.getId() != null) {
				excluir(credencial, credencialExcluida);
			}
		}
	}
}
