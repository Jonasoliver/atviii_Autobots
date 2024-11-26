package com.autobots.automanager.modelos;

import java.util.Set;

import com.autobots.automanager.entidades.Endereco;
import com.autobots.automanager.enumeracoes.PerfilUsuario;

import lombok.Data;

@Data
public class UsuarioDto {
	
	private Long id;

	private String nome;
	
	private String nomeSocial;

	private Set<PerfilUsuario> perfis;

	private Endereco endereco;
}
