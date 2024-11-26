package com.autobots.automanager.excluidores;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.autobots.automanager.entidades.Servico;
import com.autobots.automanager.entidades.Venda;
import com.autobots.automanager.repositorios.ServicoRepositorio;
import com.autobots.automanager.repositorios.VendaRepositorio;
import com.autobots.automanager.entidades.Empresa;

@Service
public class ServicoExcluidor {

	@Autowired
	private VendaRepositorio vendaRepositorio;
	
	@Autowired
	private ServicoRepositorio servicoRepositorio;

	public void excluir(Empresa empresa, Servico servico) {
		if (servico != null) {
			empresa.getServicos().remove(servico);
		}
		List<Venda> vendas = vendaRepositorio.findAll();
		for (Venda venda : vendas) {
            venda.getServicos().removeIf(merc -> merc.getId().equals(servico.getId()));
		}
		
		servicoRepositorio.delete(servico);
		
	}

	public void excluir(Empresa empresa, List<Servico> servicos) {
		for (Servico servicoExcluido : servicos) {
			if (servicoExcluido.getId() != null) {
				excluir(empresa, servicoExcluido);
			}
		}
		
	}
}
