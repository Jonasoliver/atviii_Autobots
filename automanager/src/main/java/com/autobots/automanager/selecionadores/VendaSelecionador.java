package com.autobots.automanager.selecionadores;

import java.util.List;

import org.springframework.stereotype.Component;

import com.autobots.automanager.entidades.Venda;

@Component
public class VendaSelecionador {
	public Venda selecionar(List<Venda> vendas, long id) {
		Venda selecionado = null;
		for (Venda venda : vendas) {
			if (venda.getId() == id) {
				selecionado = venda;
			}
		}
		return selecionado;
	}

}
