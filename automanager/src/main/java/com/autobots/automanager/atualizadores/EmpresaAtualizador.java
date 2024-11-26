package com.autobots.automanager.atualizadores;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.autobots.automanager.entidades.Empresa;

import com.autobots.automanager.modelos.EmpresaDto;
import com.autobots.automanager.modelos.StringVerificadorNulo;

@Component
public class EmpresaAtualizador {
	private StringVerificadorNulo verificador = new StringVerificadorNulo();
	
	@Autowired
	private EnderecoAtualizador enderecoAtualizador = new EnderecoAtualizador();

	private void atualizarDados(Empresa empresa, EmpresaDto atualizacao) {
		if (!verificador.verificar(atualizacao.getNomeFantasia())) {
			empresa.setNomeFantasia(atualizacao.getNomeFantasia());
		}
		if (!verificador.verificar(atualizacao.getRazaoSocial())) {
			empresa.setRazaoSocial(atualizacao.getRazaoSocial());
		}
	}

	public void atualizar(Empresa empresa, EmpresaDto atualizacao) {
		atualizarDados(empresa, atualizacao);
		enderecoAtualizador.atualizar(empresa.getEndereco(), atualizacao.getEndereco());
	}
}