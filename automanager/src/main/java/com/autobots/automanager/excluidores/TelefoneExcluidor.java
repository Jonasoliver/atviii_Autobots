package com.autobots.automanager.excluidores;

import java.util.List;

import com.autobots.automanager.entidades.Telefone;
import com.autobots.automanager.entidades.Usuario;
import com.autobots.automanager.modelos.StringVerificadorNulo;

public class TelefoneExcluidor {

	private StringVerificadorNulo verificador = new StringVerificadorNulo();

	public void excluir(Usuario usuario, Telefone Telefone) {
		if (Telefone != null) {
			if (!verificador.verificar(Telefone.getDdd()) & !verificador.verificar(Telefone.getNumero())) {
				usuario.getTelefones().remove(Telefone);
			}
		}
	}

	public void excluir(Usuario usuario, List<Telefone> Telefones) {
		for (Telefone TelefoneExcluido : Telefones) {
			if (TelefoneExcluido.getId() != null) {
				excluir(usuario, TelefoneExcluido);
			}
		}
	}
}
