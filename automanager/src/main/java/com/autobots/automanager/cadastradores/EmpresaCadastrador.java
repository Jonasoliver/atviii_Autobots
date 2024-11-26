package com.autobots.automanager.cadastradores;

import java.util.List;

import com.autobots.automanager.entidades.Empresa;
import com.autobots.automanager.modelos.EmpresaDto;
import com.autobots.automanager.modelos.StringVerificadorNulo;

public class EmpresaCadastrador {
	private StringVerificadorNulo verificador = new StringVerificadorNulo();

	public Empresa cadastrar(EmpresaDto empresa) {
		Empresa novaEmpresa = new Empresa();
		if (empresa != null) {
			if (!verificador.verificar(empresa.getRazaoSocial())) {
				novaEmpresa.setRazaoSocial(empresa.getRazaoSocial());
			}
			if (!verificador.verificar(empresa.getNomeFantasia())) {
				novaEmpresa.setNomeFantasia(empresa.getNomeFantasia());
			}
			
			EnderecoCadastrador enderecoCadastrador = new EnderecoCadastrador();
			enderecoCadastrador.cadastrarEmp(novaEmpresa, empresa.getEndereco());
		}
		return novaEmpresa;
	}

	public void cadastrar(List<EmpresaDto> empresasNovas) {
		for (EmpresaDto empresa : empresasNovas) {
			cadastrar(empresa);
		}
	}
}
