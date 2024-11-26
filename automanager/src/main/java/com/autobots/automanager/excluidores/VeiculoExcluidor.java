package com.autobots.automanager.excluidores;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.autobots.automanager.entidades.Veiculo;
import com.autobots.automanager.entidades.Venda;
import com.autobots.automanager.repositorios.UsuarioRepositorio;
import com.autobots.automanager.entidades.Usuario;

@Service
public class VeiculoExcluidor {
	
	@Autowired
	private UsuarioRepositorio usuarioRepositorio;

	public void excluir(Usuario usuario, Veiculo veiculo) {
		if (veiculo != null) {
				usuario.getVeiculos().remove(veiculo);
				usuarioRepositorio.save(usuario);
				for( Venda venda : veiculo.getVendas()) {
					venda.setVeiculo(null);
				}
		}
	}

	public void excluir(Usuario usuario, List<Veiculo> Veiculos) {
		for (Veiculo VeiculoExcluido : Veiculos) {
			if (VeiculoExcluido.getId() != null) {
				excluir(usuario, VeiculoExcluido);
			}
		}
	}
}
