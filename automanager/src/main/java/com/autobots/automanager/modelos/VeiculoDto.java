package com.autobots.automanager.modelos;

import com.autobots.automanager.enumeracoes.TipoVeiculo;

import lombok.Data;

@Data
public class VeiculoDto {
	private Long id;
	private TipoVeiculo tipo;
	private String modelo;
	private String placa;
}