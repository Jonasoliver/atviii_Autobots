package com.autobots.automanager.selecionadores;

import java.util.List;

import org.springframework.stereotype.Component;

import com.autobots.automanager.entidades.Empresa;

@Component
public class EmpresaSelecionador {
	public Empresa selecionar(List<Empresa> empresas, long id) {
		Empresa selecionado = null;
		for (Empresa empresa : empresas) {
			if (empresa.getId() == id) {
				selecionado = empresa;
			}
		}
		return selecionado;
	}

}
