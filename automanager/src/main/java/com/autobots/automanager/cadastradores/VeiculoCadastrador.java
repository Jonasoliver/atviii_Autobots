package com.autobots.automanager.cadastradores;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.autobots.automanager.entidades.Usuario;
import com.autobots.automanager.entidades.Veiculo;
import com.autobots.automanager.modelos.StringVerificadorNulo;
import com.autobots.automanager.modelos.VeiculoDto;
import com.autobots.automanager.repositorios.UsuarioRepositorio;

@Service
public class VeiculoCadastrador {
	private StringVerificadorNulo verificador = new StringVerificadorNulo();
	
	@Autowired
	private UsuarioRepositorio usuarioRepositorio;

	public void cadastrar(Usuario usuario, VeiculoDto veiculo) {
		if (veiculo != null) {
			Veiculo novoVeiculo = new Veiculo();
			if (!verificador.verificar(veiculo.getModelo())) {
				novoVeiculo.setModelo(veiculo.getModelo());
			}
			if (!verificador.verificar(veiculo.getPlaca())) {
				novoVeiculo.setPlaca(veiculo.getPlaca());
			}
			novoVeiculo.setTipo(veiculo.getTipo());
			novoVeiculo.setProprietario(usuario);
			usuario.getVeiculos().add(novoVeiculo);
			usuarioRepositorio.save(usuario);
		}
	}

	public void cadastrar(Usuario usuario, List<VeiculoDto> veiculosNovos) {
		for (VeiculoDto veiculo : veiculosNovos) {
			cadastrar(usuario, veiculo);
		}
	}
}
