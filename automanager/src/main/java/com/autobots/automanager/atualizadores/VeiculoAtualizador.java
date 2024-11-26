package com.autobots.automanager.atualizadores;

import java.util.List;
import java.util.Set;

import com.autobots.automanager.entidades.Veiculo;
import com.autobots.automanager.modelos.StringVerificadorNulo;
import com.autobots.automanager.modelos.VeiculoDto;

public class VeiculoAtualizador {
	
	private StringVerificadorNulo verificador = new StringVerificadorNulo();

	public void atualizar(Veiculo veiculo, VeiculoDto atualizacao) {
		if (atualizacao != null) {
			if (!verificador.verificar(atualizacao.getModelo())) {
				veiculo.setModelo(atualizacao.getModelo());
			}
			if (!verificador.verificar(atualizacao.getPlaca())) {
				veiculo.setPlaca(atualizacao.getPlaca());
			}
			veiculo.setTipo(atualizacao.getTipo());
		}
	}

	public void atualizar(Set<Veiculo> veiculos, List<VeiculoDto> atualizacoes) {
		for (VeiculoDto atualizacao : atualizacoes) {
			for (Veiculo veiculo : veiculos) {
				if (atualizacao.getId() != null) {
					if (atualizacao.getId() == veiculo.getId()) {
						atualizar(veiculo, atualizacao);
					}
				}
			}
		}
	}
}
