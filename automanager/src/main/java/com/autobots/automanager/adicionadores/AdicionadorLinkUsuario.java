package com.autobots.automanager.adicionadores;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.Link;
import org.springframework.hateoas.server.mvc.WebMvcLinkBuilder;
import org.springframework.stereotype.Component;

import com.autobots.automanager.controles.UsuarioControle;
import com.autobots.automanager.entidades.CredencialCodigoBarra;
import com.autobots.automanager.entidades.CredencialUsuarioSenha;
import com.autobots.automanager.entidades.Documento;
import com.autobots.automanager.entidades.Email;
import com.autobots.automanager.entidades.Mercadoria;
import com.autobots.automanager.entidades.Telefone;
import com.autobots.automanager.entidades.Usuario;
import com.autobots.automanager.entidades.Veiculo;
import com.autobots.automanager.entidades.Venda;

@Component
public class AdicionadorLinkUsuario implements AdicionadorLink<Usuario> {
	
	@Autowired
	private AdicionadorLinkTelefone adicionadorLinkTelefone;
	
	@Autowired
	private AdicionadorLinkDocumento adicionadorLinkDocumento;
	
	@Autowired
	private AdicionadorLinkEmail adicionadorLinkEmail;
	
	@Autowired
	private AdicionadorLinkMercadoria adicionadorLinkMercadoria;
	
	@Autowired
	private AdicionadorLinkCredencialCodigoBarra adicionadorLinkCredencialCodigoBarra;

	@Autowired
	private AdicionadorLinkCredencialUsuarioSenha adicionadorLinkCredencialUsuarioSenha;
	
	@Autowired
	private AdicionadorLinkVenda adicionadorLinkVenda;
	
	@Autowired
	private AdicionadorLinkVeiculo adicionadorLinkVeiculo;

	@Override
	public void adicionarLink(List<Usuario> lista) {
		for (Usuario usuario : lista) {
			if (!usuario.hasLink("self")) {
				long id = usuario.getId();
				Link linkProprio = WebMvcLinkBuilder
						.linkTo(WebMvcLinkBuilder
								.methodOn(UsuarioControle.class)
								.obterUsuario(id))
						.withSelfRel();
				usuario.add(linkProprio);
				List<Telefone> telefones = usuario.getTelefones().stream().collect(Collectors.toList());
				adicionadorLinkTelefone.adicionarLink(telefones);
				List<Documento> documentos = usuario.getDocumentos().stream().collect(Collectors.toList());
				adicionadorLinkDocumento.adicionarLink(documentos);
				List<Email> emails = usuario.getEmails().stream().collect(Collectors.toList());
				adicionadorLinkEmail.adicionarLink(emails);
				List<Mercadoria> mercadorias = usuario.getMercadorias().stream().collect(Collectors.toList());
				adicionadorLinkMercadoria.adicionarLink(mercadorias);
				List<CredencialCodigoBarra> credenciaisCodigoBarra = usuario.getCredenciaisCodigoBarra().stream().collect(Collectors.toList());
				adicionadorLinkCredencialCodigoBarra.adicionarLink(credenciaisCodigoBarra);
				List<CredencialUsuarioSenha> credenciaisUsuarioSenha = usuario.getCredenciaisUsuarioSenha().stream().collect(Collectors.toList());
				adicionadorLinkCredencialUsuarioSenha.adicionarLink(credenciaisUsuarioSenha);
				List<Venda> vendas = usuario.getVendas().stream().collect(Collectors.toList());
				adicionadorLinkVenda.adicionarLink(vendas);
				List<Veiculo> veiculo = usuario.getVeiculos().stream().collect(Collectors.toList());
				adicionadorLinkVeiculo.adicionarLink(veiculo);
			}
		}
	}

	@Override
	public void adicionarLink(Usuario usuario) {
		if(!usuario.hasLink("usuarios")) {
			Link linkProprio = WebMvcLinkBuilder
					.linkTo(WebMvcLinkBuilder
							.methodOn(UsuarioControle.class)
							.obterUsuarios())
					.withRel("usuarios");
			usuario.add(linkProprio);
			List<Telefone> telefones = usuario.getTelefones().stream().collect(Collectors.toList());
			adicionadorLinkTelefone.adicionarLink(telefones);
			List<Documento> documentos = usuario.getDocumentos().stream().collect(Collectors.toList());
			adicionadorLinkDocumento.adicionarLink(documentos);
			List<Email> emails = usuario.getEmails().stream().collect(Collectors.toList());
			adicionadorLinkEmail.adicionarLink(emails);
			List<Mercadoria> mercadorias = usuario.getMercadorias().stream().collect(Collectors.toList());
			adicionadorLinkMercadoria.adicionarLink(mercadorias);
			List<CredencialCodigoBarra> credenciaisCodigoBarra = usuario.getCredenciaisCodigoBarra().stream().collect(Collectors.toList());
			adicionadorLinkCredencialCodigoBarra.adicionarLink(credenciaisCodigoBarra);
			List<CredencialUsuarioSenha> credenciaisUsuarioSenha = usuario.getCredenciaisUsuarioSenha().stream().collect(Collectors.toList());
			adicionadorLinkCredencialUsuarioSenha.adicionarLink(credenciaisUsuarioSenha);
			List<Venda> vendas = usuario.getVendas().stream().collect(Collectors.toList());
			adicionadorLinkVenda.adicionarLink(vendas);
			List<Veiculo> veiculo = usuario.getVeiculos().stream().collect(Collectors.toList());
			adicionadorLinkVeiculo.adicionarLink(veiculo);
		}
	}
}