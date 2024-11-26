package com.autobots.automanager.cadastradores;

import com.autobots.automanager.entidades.Empresa;
import com.autobots.automanager.entidades.Endereco;
import com.autobots.automanager.entidades.Usuario;
import com.autobots.automanager.modelos.StringVerificadorNulo;

public class EnderecoCadastrador {
	private StringVerificadorNulo verificador = new StringVerificadorNulo();

	public void cadastrarUser(Usuario usuario, Endereco endereco) {
		if (endereco != null) {
			Endereco novoEndereco = new Endereco();
			if (!verificador.verificar(endereco.getRua())) {
				novoEndereco.setRua(endereco.getRua());
			}
			if (!verificador.verificar(endereco.getBairro())) {
				novoEndereco.setBairro(endereco.getBairro());
			}
			if (!verificador.verificar(endereco.getCidade())) {
				novoEndereco.setCidade(endereco.getCidade());
			}
			if (!verificador.verificar(endereco.getNumero())) {
				novoEndereco.setNumero(endereco.getNumero());
			}
			if (!verificador.verificar(endereco.getCodigoPostal())) {
				novoEndereco.setCodigoPostal(endereco.getCodigoPostal());
			}
			if (!verificador.verificar(endereco.getInformacoesAdicionais())) {
				novoEndereco.setInformacoesAdicionais(endereco.getInformacoesAdicionais());
			}
			if (!verificador.verificar(endereco.getEstado())) {
				novoEndereco.setEstado(endereco.getEstado());
			}
			usuario.setEndereco(novoEndereco);
		}
	}
	
	public void cadastrarEmp(Empresa empresa, Endereco endereco) {
		if (endereco != null) {
			Endereco novoEndereco = new Endereco();
			if (!verificador.verificar(endereco.getRua())) {
				novoEndereco.setRua(endereco.getRua());
			}
			if (!verificador.verificar(endereco.getBairro())) {
				novoEndereco.setBairro(endereco.getBairro());
			}
			if (!verificador.verificar(endereco.getCidade())) {
				novoEndereco.setCidade(endereco.getCidade());
			}
			if (!verificador.verificar(endereco.getNumero())) {
				novoEndereco.setNumero(endereco.getNumero());
			}
			if (!verificador.verificar(endereco.getCodigoPostal())) {
				novoEndereco.setCodigoPostal(endereco.getCodigoPostal());
			}
			if (!verificador.verificar(endereco.getInformacoesAdicionais())) {
				novoEndereco.setInformacoesAdicionais(endereco.getInformacoesAdicionais());
			}
			if (!verificador.verificar(endereco.getEstado())) {
				novoEndereco.setEstado(endereco.getEstado());
			}
			empresa.setEndereco(novoEndereco);
		}
	}
}
