package com.autobots.automanager.adicionadores;

import java.util.List;

import org.springframework.hateoas.Link;
import org.springframework.hateoas.server.mvc.WebMvcLinkBuilder;
import org.springframework.stereotype.Component;

import com.autobots.automanager.controles.CredencialControle;
import com.autobots.automanager.entidades.CredencialCodigoBarra;

@Component
public class AdicionadorLinkCredencialCodigoBarra implements AdicionadorLink<CredencialCodigoBarra>{
	
	@Override
	public void adicionarLink(List<CredencialCodigoBarra> lista) {
		for (CredencialCodigoBarra credencial : lista) {
			long id = credencial.getId();
			Link linkProprio = WebMvcLinkBuilder
					.linkTo(WebMvcLinkBuilder
							.methodOn(CredencialControle.class)
							.obterCredencialCodigoBarra(id))
					.withSelfRel();
			credencial.add(linkProprio);
		}
	}

	@Override
	public void adicionarLink(CredencialCodigoBarra objeto) {
		Link linkProprio = WebMvcLinkBuilder
				.linkTo(WebMvcLinkBuilder
						.methodOn(CredencialControle.class)
						.obterCredenciaisCodigoBarra())
				.withRel("credenciais");
		objeto.add(linkProprio);
	}
}
