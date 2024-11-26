package com.autobots.automanager.excluidores;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.autobots.automanager.entidades.Mercadoria;
import com.autobots.automanager.entidades.Venda;
import com.autobots.automanager.repositorios.MercadoriaRepositorio;
import com.autobots.automanager.repositorios.VendaRepositorio;
import com.autobots.automanager.entidades.Empresa;

@Service
public class MercadoriaExcluidor {

	@Autowired
	private VendaRepositorio vendaRepositorio;
	
	@Autowired
	private MercadoriaRepositorio mercadoriaRepositorio;

	public void excluir(Empresa empresa, Mercadoria mercadoria) {
		if (mercadoria != null) {
			empresa.getMercadorias().remove(mercadoria);
		}
		List<Venda> vendas = vendaRepositorio.findAll();
		for (Venda venda : vendas) {
            venda.getMercadorias().removeIf(merc -> merc.getId().equals(mercadoria.getId()));
		}
		
		mercadoriaRepositorio.delete(mercadoria);
		
	}

	public void excluir(Empresa empresa, List<Mercadoria> mercadorias) {
		for (Mercadoria mercadoriaExcluido : mercadorias) {
			if (mercadoriaExcluido.getId() != null) {
				excluir(empresa, mercadoriaExcluido);
			}
		}
		
	}
}
