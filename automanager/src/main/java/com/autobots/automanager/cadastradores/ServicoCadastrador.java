package com.autobots.automanager.cadastradores;

import java.util.List;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.autobots.automanager.entidades.Servico;
import com.autobots.automanager.modelos.StringVerificadorNulo;
import com.autobots.automanager.repositorios.ServicoRepositorio;

@Service
public class ServicoCadastrador {
	private StringVerificadorNulo verificador = new StringVerificadorNulo();
	
	@Autowired
	private ServicoRepositorio servicoRepositorio;

	public void cadastrar(Set<Servico> servicos, Servico servico) {
		if (servico != null) {
			Servico novoServico = new Servico();
			if (!verificador.verificar(servico.getNome())) {
				novoServico.setNome(servico.getNome());
			}
			if (!verificador.verificar(servico.getDescricao())) {
				novoServico.setDescricao(servico.getDescricao());
			}
			
			novoServico.setValor(servico.getValor());
			
			servicos.add(novoServico);
			servicoRepositorio.save(novoServico);
		}
	}

	public void cadastrar(Set<Servico> servicos, List<Servico> servicosNovas) {
		for (Servico servico : servicosNovas) {
			cadastrar(servicos, servico);
		}
	}
}
