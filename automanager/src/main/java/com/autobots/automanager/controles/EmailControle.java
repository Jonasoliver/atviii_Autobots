package com.autobots.automanager.controles;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.autobots.automanager.adicionadores.AdicionadorLinkEmail;
import com.autobots.automanager.atualizadores.EmailAtualizador;
import com.autobots.automanager.cadastradores.EmailCadastrador;
import com.autobots.automanager.entidades.Email;
import com.autobots.automanager.entidades.Usuario;
import com.autobots.automanager.excluidores.EmailExcluidor;
import com.autobots.automanager.selecionadores.UsuarioSelecionador;
import com.autobots.automanager.selecionadores.EmailSelecionador;
import com.autobots.automanager.repositorios.EmailRepositorio;
import com.autobots.automanager.repositorios.UsuarioRepositorio;

@RestController
@RequestMapping("/email")
public class EmailControle {

	@Autowired
	private AdicionadorLinkEmail adicionadorLink;

	@Autowired
	private EmailRepositorio repositorio;

	@Autowired
	private UsuarioRepositorio usuarioRepositorio;

	@Autowired
	private EmailSelecionador selecionador;
	
	@Autowired
	private UsuarioSelecionador usuarioSelecionador;
	
	@Autowired
	private EmailRepositorio emailRepositorio;
	
	@Autowired
	private EmailSelecionador emailSelecionador;

	@GetMapping("/{id}")
	public ResponseEntity<Email> obterEmail(@PathVariable long id) {
		List<Email> Emails = repositorio.findAll();
		Email Email = selecionador.selecionar(Emails, id);
		if (Email == null) {
			ResponseEntity<Email> resposta = new ResponseEntity<>(HttpStatus.NOT_FOUND);
			return resposta;
		} else {
			adicionadorLink.adicionarLink(Email);
			ResponseEntity<Email> resposta = new ResponseEntity<Email>(Email, HttpStatus.FOUND);
			return resposta;
		}
	}
	
	@GetMapping("/emails")
	public ResponseEntity<List<Email>> obterEmails() {
		List<Email> Emails = repositorio.findAll();
		if (Emails.isEmpty()) {
			ResponseEntity<List<Email>> resposta = new ResponseEntity<>(HttpStatus.NOT_FOUND);
			return resposta;
		} else {
			adicionadorLink.adicionarLink(Emails);
			ResponseEntity<List<Email>> resposta = new ResponseEntity<>(Emails, HttpStatus.FOUND);
			return resposta;
		}
	}


	@GetMapping("/email/{id}")
	public ResponseEntity<List<Email>> obterusuarioEmail(@PathVariable long id) {
		List<Usuario> usuarios = usuarioRepositorio.findAll();
		Usuario usuario = usuarioSelecionador.selecionar(usuarios, id);
		List<Email> Emails = usuario.getEmails().stream().collect(Collectors.toList());
		if (Emails.isEmpty()) {
			ResponseEntity<List<Email>> resposta = new ResponseEntity<>(HttpStatus.NOT_FOUND);
			return resposta;
		} else {
			adicionadorLink.adicionarLink(Emails);
			ResponseEntity<List<Email>> resposta = new ResponseEntity<>(Emails, HttpStatus.FOUND);
			return resposta;
		}
	}

	@PostMapping("/cadastro/{id}")
	public void cadastrarEmail(@RequestBody List<Email> Email, @PathVariable long id) {
		Usuario usuario = usuarioRepositorio.getById(id);
		EmailCadastrador cadastrador = new EmailCadastrador();
		cadastrador.cadastrar(usuario, Email);
		usuarioRepositorio.save(usuario);
	}

	@PutMapping("/atualizar/usuario/{id}")
	public void atualizarusuarioEmail(@RequestBody List<Email> atualizacao, @PathVariable long id) {
		List<Usuario> usuarios = usuarioRepositorio.findAll();
		Usuario usuario = usuarioSelecionador.selecionar(usuarios, id);
		EmailAtualizador atualizador = new EmailAtualizador();
		atualizador.atualizar(usuario.getEmails(), atualizacao);
		usuarioRepositorio.save(usuario);
	}
	
	@PutMapping("/atualizar/{id}")
	public void atualizarEmail(@RequestBody Email atualizacao, @PathVariable long id) {
		List<Email> emails = emailRepositorio.findAll();
		Email email = emailSelecionador.selecionar(emails, id);
		EmailAtualizador atualizador = new EmailAtualizador();
		atualizador.atualizar(email, atualizacao);
		emailRepositorio.save(email);
}

	@DeleteMapping("/excluir/usuario/{id}")
	public void excluirUsuarioEmail(@RequestBody List<Email> emails, @PathVariable long id) {
		Usuario usuario = usuarioRepositorio.getById(id);
		EmailExcluidor excluidor = new EmailExcluidor();
		excluidor.excluir(usuario, emails);
		usuarioRepositorio.save(usuario);
	}
}
