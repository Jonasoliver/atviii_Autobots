package com.autobots.automanager.adicionadores;

import java.util.List;

import org.springframework.hateoas.Link;
import org.springframework.hateoas.server.mvc.WebMvcLinkBuilder;
import org.springframework.stereotype.Component;

import com.autobots.automanager.controles.MercadoriaControle;
import com.autobots.automanager.entidades.Mercadoria;

@Component
public class AdicionadorLinkMercadoria implements AdicionadorLink<Mercadoria> {

	@Override
	public void adicionarLink(List<Mercadoria> lista) {
		for (Mercadoria mercadoria : lista) {
			long id = mercadoria.getId();
			if (!mercadoria.hasLink("self")) {			
			Link linkProprio = WebMvcLinkBuilder
					.linkTo(WebMvcLinkBuilder
							.methodOn(MercadoriaControle.class)
							.obterMercadoria(id))
					.withSelfRel();
			mercadoria.add(linkProprio);
			}
		}
	}

	@Override
	public void adicionarLink(Mercadoria mercadoria) {
		if (!mercadoria.hasLink("mercadorias")) {			
		Link linkProprio = WebMvcLinkBuilder
				.linkTo(WebMvcLinkBuilder
						.methodOn(MercadoriaControle.class)
						.obterMercadorias())
				.withRel("mercadorias");
		mercadoria.add(linkProprio);
		}
	}
}