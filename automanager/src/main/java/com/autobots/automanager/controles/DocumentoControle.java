package com.autobots.automanager.controles;

import java.util.List;
import java.util.Set;
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

import com.autobots.automanager.adicionadores.AdicionadorLinkDocumento;
import com.autobots.automanager.entidades.Documento;
import com.autobots.automanager.entidades.Usuario;
import com.autobots.automanager.excluidores.DocumentoExcluidor;
import com.autobots.automanager.atualizadores.DocumentoAtualizador;
import com.autobots.automanager.cadastradores.DocumentoCadastrador;
import com.autobots.automanager.selecionadores.DocumentoSelecionador;
import com.autobots.automanager.selecionadores.UsuarioSelecionador;
import com.autobots.automanager.repositorios.DocumentoRepositorio;
import com.autobots.automanager.repositorios.UsuarioRepositorio;

@RestController
@RequestMapping("/documento")
public class DocumentoControle {

	@Autowired
	private AdicionadorLinkDocumento adicionadorLink;

	@Autowired
	private DocumentoRepositorio repositorio;

	@Autowired
	private UsuarioRepositorio usuarioRepositorio;

	@Autowired
	private DocumentoSelecionador selecionador;
	
	@Autowired
	private UsuarioSelecionador usuarioSelecionador;
	
	@Autowired
	private DocumentoCadastrador cadastrador;

	@GetMapping("/{id}")
	public ResponseEntity<Documento> obterDocumento(@PathVariable long id) {
		List<Documento> documentos = repositorio.findAll();
		Documento documento = selecionador.selecionar(documentos, id);
		if (documento == null) {
			ResponseEntity<Documento> resposta = new ResponseEntity<>(HttpStatus.NOT_FOUND);
			return resposta;
		} else {
			adicionadorLink.adicionarLink(documento);
			ResponseEntity<Documento> resposta = new ResponseEntity<Documento>(documento, HttpStatus.FOUND);
			return resposta;
		}
	}
	
	@GetMapping("/documentos")
	public ResponseEntity<List<Documento>> obterDocumentos() {
		List<Documento> documentos = repositorio.findAll();
		if (documentos.isEmpty()) {
			ResponseEntity<List<Documento>> resposta = new ResponseEntity<>(HttpStatus.NOT_FOUND);
			return resposta;
		} else {
			adicionadorLink.adicionarLink(documentos);
			ResponseEntity<List<Documento>> resposta = new ResponseEntity<>(documentos, HttpStatus.FOUND);
			return resposta;
		}
	}


	@GetMapping("/usuario/{id}")
	public ResponseEntity<List<Documento>> obterusuarioDocumento(@PathVariable long id) {
		List<Usuario> usuarios = usuarioRepositorio.findAll();
		Usuario usuario = usuarioSelecionador.selecionar(usuarios, id);
		List<Documento> documentos = usuario.getDocumentos().stream().collect(Collectors.toList());
		if (documentos.isEmpty()) {
			ResponseEntity<List<Documento>> resposta = new ResponseEntity<>(HttpStatus.NOT_FOUND);
			return resposta;
		} else {
			adicionadorLink.adicionarLink(documentos);
			ResponseEntity<List<Documento>> resposta = new ResponseEntity<>(documentos, HttpStatus.FOUND);
			return resposta;
		}
	}

	@PostMapping("/cadastro/{id}")
	public void cadastrarDocumento(@RequestBody List<Documento> documento, @PathVariable long id) {
		Usuario usuario = usuarioRepositorio.getById(id);
		cadastrador.cadastrar(usuario, documento);
		usuarioRepositorio.save(usuario);
	}

	@PutMapping("/atualizar/usuario/{id}")
	public void atualizarusuarioDocumento(@RequestBody List<Documento> atualizacao, @PathVariable long id) {
		Usuario usuario = usuarioRepositorio.getById(id);
		Set<Documento> documentos = usuario.getDocumentos();
		DocumentoAtualizador atualizador = new DocumentoAtualizador();
		atualizador.atualizar(documentos, atualizacao);
		usuarioRepositorio.save(usuario);
	}
	
	@PutMapping("/atualizar/{id}")
	public void atualizarDocumento(@RequestBody Documento atualizacao, @PathVariable long id) {
		List<Documento> documentos = repositorio.findAll();
		Documento documento = selecionador.selecionar(documentos, id);
		DocumentoAtualizador atualizador = new DocumentoAtualizador();
		atualizador.atualizar(documento, atualizacao);
		repositorio.save(documento);
	}

	@DeleteMapping("/excluir/usuario/{id}")
	public void excluirUsuarioDocumento(@RequestBody List<Documento> documentos, @PathVariable long id) {
		Usuario usuario = usuarioRepositorio.getById(id);
		DocumentoExcluidor excluidor = new DocumentoExcluidor();
		excluidor.excluir(usuario, documentos);
		usuarioRepositorio.save(usuario);
	}
}
