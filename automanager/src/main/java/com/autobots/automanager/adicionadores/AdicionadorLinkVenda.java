package com.autobots.automanager.adicionadores;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.hateoas.Link;
import org.springframework.hateoas.server.mvc.WebMvcLinkBuilder;
import org.springframework.stereotype.Component;

import com.autobots.automanager.controles.VendaControle;
import com.autobots.automanager.entidades.Mercadoria;
import com.autobots.automanager.entidades.Servico;
import com.autobots.automanager.entidades.Venda;

@Component
public class AdicionadorLinkVenda implements AdicionadorLink<Venda> {
	
	@Autowired    
	@Lazy
	private AdicionadorLinkMercadoria adicionadorLinkMercadoria;
	
	@Autowired
    @Lazy
	private AdicionadorLinkServico adicionadorLinkServico;
	
	@Autowired
    @Lazy
	private AdicionadorLinkUsuario adicionadorLinkUsuario;

	@Override
	public void adicionarLink(List<Venda> lista) {
		for (Venda venda : lista) {
			if (!venda.hasLink("self")) {
			long id = venda.getId();
			Link linkProprio = WebMvcLinkBuilder
					.linkTo(WebMvcLinkBuilder
							.methodOn(VendaControle.class)
							.obterVenda(id))
					.withSelfRel();
			venda.add(linkProprio);
			List<Mercadoria> mercadorias = venda.getMercadorias().stream().collect(Collectors.toList());
			adicionadorLinkMercadoria.adicionarLink(mercadorias);
			List<Servico> servicos = venda.getServicos().stream().collect(Collectors.toList());
			adicionadorLinkServico.adicionarLink(servicos);
			}
		}
	}

	@Override
	public void adicionarLink(Venda venda) {
		Link linkProprio = WebMvcLinkBuilder
				.linkTo(WebMvcLinkBuilder
						.methodOn(VendaControle.class)
						.obterVendas())
				.withRel("vendas");
		venda.add(linkProprio);
		adicionadorLinkUsuario.adicionarLink(venda.getFuncionario());
		adicionadorLinkUsuario.adicionarLink(venda.getCliente());
		List<Mercadoria> mercadorias = venda.getMercadorias().stream().collect(Collectors.toList());
		adicionadorLinkMercadoria.adicionarLink(mercadorias);
		List<Servico> servicos = venda.getServicos().stream().collect(Collectors.toList());
		adicionadorLinkServico.adicionarLink(servicos);
	}
}