package com.autobots.automanager.cadastradores;

import java.util.Date;
import java.util.List;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.autobots.automanager.entidades.Mercadoria;
import com.autobots.automanager.modelos.StringVerificadorNulo;
import com.autobots.automanager.repositorios.MercadoriaRepositorio;

@Service
public class MercadoriaCadastrador {
	private StringVerificadorNulo verificador = new StringVerificadorNulo();
	
	@Autowired
	private MercadoriaRepositorio mercadoriaRepositorio;

	public void cadastrar(Set<Mercadoria> mercadorias, Mercadoria mercadoria) {
		if (mercadoria != null) {
			Mercadoria novaMercadoria = new Mercadoria();
			if (!verificador.verificar(mercadoria.getNome())) {
				novaMercadoria.setNome(mercadoria.getNome());
			}
			if (!verificador.verificar(mercadoria.getDescricao())) {
				novaMercadoria.setDescricao(mercadoria.getDescricao());
			}
			
			novaMercadoria.setCadastro(new Date());
			novaMercadoria.setFabricao(new Date());
			novaMercadoria.setValidade(new Date());
			novaMercadoria.setQuantidade(mercadoria.getQuantidade());
			novaMercadoria.setValor(mercadoria.getValor());
			
			mercadorias.add(novaMercadoria);
			mercadoriaRepositorio.save(mercadoria);
		}
	}

	public void cadastrar(Set<Mercadoria> mercadorias, List<Mercadoria> mercadoriasNovas) {
		for (Mercadoria mercadoria : mercadoriasNovas) {
			cadastrar(mercadorias, mercadoria);
		}
	}
}
