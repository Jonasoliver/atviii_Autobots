package com.autobots.automanager.modelos;

import java.util.List;

import lombok.Data;

@Data
public class VendaDto {

	private Long id;
	
	private String identificacao;
	
	private Long cliente;

	private Long funcionario;

	private List<Long> mercadorias;

	private List<Long> servicos;

	private Long veiculo;
}
