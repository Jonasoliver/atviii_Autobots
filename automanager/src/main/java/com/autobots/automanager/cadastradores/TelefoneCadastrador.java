package com.autobots.automanager.cadastradores;

import java.util.List;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.autobots.automanager.entidades.Telefone;
import com.autobots.automanager.modelos.StringVerificadorNulo;
import com.autobots.automanager.repositorios.TelefoneRepositorio;

@Service
public class TelefoneCadastrador {
	private StringVerificadorNulo verificador = new StringVerificadorNulo();
	
	@Autowired
	private TelefoneRepositorio telefoneRepositorio;

	public void cadastrar(Set<Telefone> telefones, Telefone telefone) {
		Telefone novoTelefone = new Telefone();
		if (telefone != null) {
			if (!verificador.verificar(telefone.getDdd())) {
				novoTelefone.setDdd(telefone.getDdd());
			}
			if (!verificador.verificar(telefone.getNumero())) {
				novoTelefone.setNumero(telefone.getNumero());
			}
			telefones.add(novoTelefone);
			telefoneRepositorio.save(novoTelefone);
		}
	}
	
	public void cadastrar(Set<Telefone> telefones, List<Telefone> telefoneNovos) {
		for (Telefone tel : telefoneNovos) {
			cadastrar(telefones, tel);
		}
	}
}
