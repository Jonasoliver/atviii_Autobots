package com.autobots.automanager.cadastradores;

import java.util.Date;
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
public class VendaCadastrador {
	
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
	
	public void cadastrar(Set<Venda> vendas, VendaDto venda) {
		if (venda != null) {
			
			Venda novaVenda = new Venda();
			novaVenda.setCadastro(new Date());
			
			if (!verificador.verificar(venda.getIdentificacao())) {
				novaVenda.setIdentificacao(venda.getIdentificacao());
			}
			
			Usuario cliente = usuarioRepositorio.findById(venda.getCliente()).get();
			if (cliente != null) {
				novaVenda.setCliente(cliente);
				cliente.getVendas().add(novaVenda);
			}
						
			Usuario funcionario = usuarioRepositorio.findById(venda.getFuncionario()).get();
			if (funcionario != null) {
				novaVenda.setFuncionario(funcionario);
				funcionario.getVendas().add(novaVenda);
			}
			
			Veiculo veiculo = veiculoRepositorio.findById(venda.getVeiculo()).get();
			if (veiculo != null) {
				veiculo.getVendas().add(novaVenda);
				novaVenda.setVeiculo(veiculo);
			}

			for (long servicoId : venda.getServicos()) {
				Servico servico = servicoRepositorio.findById(servicoId).get();
				novaVenda.getServicos().add(servico);
			}
			
			for (long mercadoriaId : venda.getMercadorias()) {
				Mercadoria mercadoria = mercadoriaRepositorio.findById(mercadoriaId).get();
				novaVenda.getMercadorias().add(mercadoria);
			}
			
			vendaRepositorio.save(novaVenda);
			veiculoRepositorio.save(veiculo);
			usuarioRepositorio.save(cliente);
			usuarioRepositorio.save(funcionario);
			vendas.add(novaVenda); 
			
			}
	}

	public void cadastrar(Set<Venda> vendas, List<VendaDto> vendasNovas) {
		for (VendaDto venda : vendasNovas) {
			cadastrar(vendas, venda);
		}
	}
}
