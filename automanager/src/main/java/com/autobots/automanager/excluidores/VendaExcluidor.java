package com.autobots.automanager.excluidores;

import java.util.List;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.autobots.automanager.entidades.Venda;
import com.autobots.automanager.entidades.Usuario;
import com.autobots.automanager.modelos.VendaDto;
import com.autobots.automanager.repositorios.UsuarioRepositorio;
import com.autobots.automanager.repositorios.VendaRepositorio;

@Service
public class VendaExcluidor {
	
	@Autowired
	private UsuarioRepositorio usuarioRepositorio;
	
	@Autowired
	private VendaRepositorio vendaRepositorio;

	public void excluir(Set<Venda> vendas, VendaDto vendaDto) {
		if (vendaDto != null) {
			Venda venda = vendaRepositorio.findById(vendaDto.getId()).get();
			if (venda != null) {				
				if (venda.getCliente() != null) {
					Usuario cliente = venda.getCliente();
					venda.getCliente().getVendas().remove(venda);
					usuarioRepositorio.save(cliente);
				}
				if(venda.getFuncionario() != null) {
					Usuario funcionario = venda.getFuncionario();
					venda.getFuncionario().getVendas().remove(venda);
					usuarioRepositorio.save(funcionario);
					
				}
				if(venda.getVeiculo() != null) {
					venda.getVeiculo().getVendas().remove(venda);
				}
				vendas.remove(venda);
				vendaRepositorio.delete(venda);
			}
		}
	}

	public void excluir(Set<Venda> vendas, List<VendaDto> vendasExcluidas) {
		for (VendaDto vendaExcluida : vendasExcluidas) {
			if (vendaExcluida.getId() != null) {
				excluir(vendas, vendaExcluida);
			}
		}
	}
}
