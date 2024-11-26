package com.autobots.automanager.controles;

import java.util.List;

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

import com.autobots.automanager.adicionadores.AdicionadorLinkUsuario;
import com.autobots.automanager.atualizadores.EnderecoAtualizador;
import com.autobots.automanager.atualizadores.UsuarioAtualizador;
import com.autobots.automanager.cadastradores.UsuarioCadastrador;
import com.autobots.automanager.entidades.Empresa;
import com.autobots.automanager.entidades.Endereco;
import com.autobots.automanager.entidades.Usuario;
import com.autobots.automanager.excluidores.UsuarioExcluidor;
import com.autobots.automanager.modelos.UsuarioDto;
import com.autobots.automanager.selecionadores.EmpresaSelecionador;
import com.autobots.automanager.selecionadores.UsuarioSelecionador;
import com.autobots.automanager.repositorios.EmpresaRepositorio;
import com.autobots.automanager.repositorios.UsuarioRepositorio;

@RestController
@RequestMapping("/usuario")
public class UsuarioControle {
	
	@Autowired
	private UsuarioRepositorio repositorio;
	
	@Autowired
	private AdicionadorLinkUsuario adicionadorLink;
	
	@Autowired
	private UsuarioSelecionador selecionador;
	
	@Autowired
	private UsuarioExcluidor usuarioExcluidor;
	
	@Autowired
	private UsuarioAtualizador atualizador;
	
	@Autowired
	private EmpresaRepositorio empresaRepositorio;
	
	@Autowired 
	private EmpresaSelecionador empresaSelecionador;

	
	@GetMapping("/{id}")
	public ResponseEntity<Usuario> obterUsuario(@PathVariable long id) {
		List<Usuario> usuarios = repositorio.findAll();
		Usuario usuario = selecionador.selecionar(usuarios, id);
		if (usuario == null) {
			ResponseEntity<Usuario> resposta = new ResponseEntity<>(HttpStatus.NOT_FOUND);
			return resposta;
		} else {
			adicionadorLink.adicionarLink(usuario);
			ResponseEntity<Usuario> resposta = new ResponseEntity<Usuario>(usuario, HttpStatus.FOUND);
			return resposta;
		}
	}

	@GetMapping("/usuarios")
	public ResponseEntity<List<Usuario>> obterUsuarios() {
		List<Usuario> usuarios = repositorio.findAll();
		if (usuarios.isEmpty()) {
			ResponseEntity<List<Usuario>> resposta = new ResponseEntity<>(HttpStatus.NOT_FOUND);
			return resposta;
		} else {
			adicionadorLink.adicionarLink(usuarios);
			ResponseEntity<List<Usuario>> resposta = new ResponseEntity<>(usuarios, HttpStatus.FOUND);
			return resposta;
		}
	}
	
	@PostMapping("/cadastro")
	public void cadastrarUsuario(@RequestBody UsuarioDto usuario) {
		UsuarioCadastrador cadastrador = new UsuarioCadastrador();
		Usuario novoUsuario = cadastrador.cadastrar(usuario);
		repositorio.save(novoUsuario);
	}
	
	@PostMapping("/cadastro/empresa/{id}")
	public void cadastrarUsuarioEmpresa(@RequestBody UsuarioDto usuario, @PathVariable long id) {
		List<Empresa> empresas = empresaRepositorio.findAll();
		Empresa empresa = empresaSelecionador.selecionar(empresas, id);
		UsuarioCadastrador cadastrador = new UsuarioCadastrador();
		Usuario novoUsuario = cadastrador.cadastrar(usuario);
		empresa.getUsuarios().add(novoUsuario);
		empresaRepositorio.save(empresa);
	}

	@PutMapping("/atualizar/{id}")
	public void atualizarUsuario(@RequestBody UsuarioDto atualizacao, @PathVariable long id) {
		Usuario usuario = repositorio.getById(id);
		atualizador.atualizar(usuario, atualizacao);
		EnderecoAtualizador enderecoAtualizador = new EnderecoAtualizador();
		if (usuario.getEndereco() == null) {
			Endereco end = new Endereco();
			usuario.setEndereco(end);
		}
		enderecoAtualizador.atualizar(usuario.getEndereco(), atualizacao.getEndereco());
		repositorio.save(usuario);
	}
	
	@DeleteMapping("/excluir/{id}")
	public void excluirUsuario(@PathVariable long id) {
		Usuario usuario = repositorio.getById(id);
		usuarioExcluidor.excluir(usuario);
	}
}
