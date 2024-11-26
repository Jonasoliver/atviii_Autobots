package com.autobots.automanager.atualizadores;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.autobots.automanager.entidades.Mercadoria;
import com.autobots.automanager.entidades.Servico;
import com.autobots.automanager.entidades.Usuario;
import com.autobots.automanager.entidades.Veiculo;
import com.autobots.automanager.entidades.Venda;
import com.autobots.automanager.modelos.StringVerificadorNulo;
import com.autobots.automanager.modelos.VendaDto;
import com.autobots.automanager.repositorios.MercadoriaRepositorio;
import com.autobots.automanager.repositorios.ServicoRepositorio;
import com.autobots.automanager.repositorios.UsuarioRepositorio;
import com.autobots.automanager.repositorios.VeiculoRepositorio;
import com.autobots.automanager.repositorios.VendaRepositorio;

@Service
public class VendaAtualizador {
	
	@Autowired
	private VendaRepositorio vendaRepositorio;
	
	@Autowired
	private UsuarioRepositorio usuarioRepositorio;
	
	@Autowired
	private VeiculoRepositorio veiculoRepositorio;
	
	@Autowired
	private ServicoRepositorio servicoRepositorio;
	
	@Autowired
	private MercadoriaRepositorio mercadoriaRepositorio;
	
	StringVerificadorNulo verificador = new StringVerificadorNulo();
	
	public void atualizar(Venda venda, VendaDto atualizacao) {
		if (atualizacao != null) {
						
			if (!verificador.verificar(atualizacao.getIdentificacao())) {
				venda.setIdentificacao(atualizacao.getIdentificacao());
			}
			
			Usuario cliente = usuarioRepositorio.findById(atualizacao.getCliente()).get();
			if (cliente != null) {
				venda.setCliente(cliente);
			}
						
			Usuario funcionario = usuarioRepositorio.findById(atualizacao.getFuncionario()).get();
			if (funcionario != null) {
				venda.setFuncionario(funcionario);
			}
			
			Veiculo veiculo = veiculoRepositorio.findById(atualizacao.getVeiculo()).get();
			if (veiculo != null) {
				veiculo.getVendas().add(venda);
				venda.setVeiculo(veiculo);
			}

			List<Servico> servicos = new ArrayList<Servico>();
			for (long servicoId : atualizacao.getServicos()) {
				Servico servico = servicoRepositorio.findById(servicoId).get();
				servicos.add(servico);
			}
			venda.setServicos(servicos);
			
			List<Mercadoria> mercadorias = new ArrayList<Mercadoria>();
			for (long mercadoriaId : atualizacao.getMercadorias()) {
				Mercadoria mercadoria = mercadoriaRepositorio.findById(mercadoriaId).get();
				mercadorias.add(mercadoria);
			}
			venda.setMercadorias(mercadorias);
			
			vendaRepositorio.save(venda);
			}
	}

	public void atualizar(Set<Venda> vendas, List<VendaDto> atualizacoes) {
		for (VendaDto atualizacao : atualizacoes) {
			for (Venda venda : vendas) {
				if (!(atualizacao.getId() == null)) {
					if (atualizacao.getId() == venda.getId()) {						
						atualizar(venda, atualizacao);
					}
				}
			}
		}
	}
}
