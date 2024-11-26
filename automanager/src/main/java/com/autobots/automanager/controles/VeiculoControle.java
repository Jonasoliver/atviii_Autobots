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

import com.autobots.automanager.adicionadores.AdicionadorLinkVeiculo;
import com.autobots.automanager.atualizadores.VeiculoAtualizador;
import com.autobots.automanager.cadastradores.VeiculoCadastrador;
import com.autobots.automanager.entidades.Veiculo;
import com.autobots.automanager.excluidores.VeiculoExcluidor;
import com.autobots.automanager.modelos.VeiculoDto;
import com.autobots.automanager.entidades.Usuario;
import com.autobots.automanager.selecionadores.UsuarioSelecionador;
import com.autobots.automanager.selecionadores.VeiculoSelecionador;
import com.autobots.automanager.repositorios.VeiculoRepositorio;
import com.autobots.automanager.repositorios.UsuarioRepositorio;

@RestController
@RequestMapping("/veiculo")
public class VeiculoControle {

	@Autowired
	private AdicionadorLinkVeiculo adicionadorLink;

	@Autowired
	private VeiculoRepositorio repositorio;

	@Autowired
	private UsuarioRepositorio usuarioRepositorio;

	@Autowired
	private VeiculoSelecionador selecionador;
	
	@Autowired
	private UsuarioSelecionador usuarioSelecionador;
	
	@Autowired
	private VeiculoCadastrador cadastrador;
	
	@Autowired
	private VeiculoExcluidor excluidor;

	@GetMapping("/{id}")
	public ResponseEntity<Veiculo> obterVeiculo(@PathVariable long id) {
		List<Veiculo> Veiculos = repositorio.findAll();
		Veiculo Veiculo = selecionador.selecionar(Veiculos, id);
		if (Veiculo == null) {
			ResponseEntity<Veiculo> resposta = new ResponseEntity<>(HttpStatus.NOT_FOUND);
			return resposta;
		} else {
			adicionadorLink.adicionarLink(Veiculo);
			ResponseEntity<Veiculo> resposta = new ResponseEntity<Veiculo>(Veiculo, HttpStatus.FOUND);
			return resposta;
		}
	}
	
	@GetMapping("/veiculos")
	public ResponseEntity<List<Veiculo>> obterVeiculos() {
		List<Veiculo> Veiculos = repositorio.findAll();
		if (Veiculos.isEmpty()) {
			ResponseEntity<List<Veiculo>> resposta = new ResponseEntity<>(HttpStatus.NOT_FOUND);
			return resposta;
		} else {
			adicionadorLink.adicionarLink(Veiculos);
			ResponseEntity<List<Veiculo>> resposta = new ResponseEntity<>(Veiculos, HttpStatus.FOUND);
			return resposta;
		}
	}


	@GetMapping("/veiculo/{id}")
	public ResponseEntity<List<Veiculo>> obterusuarioVeiculo(@PathVariable long id) {
		List<Usuario> usuarios = usuarioRepositorio.findAll();
		Usuario usuario = usuarioSelecionador.selecionar(usuarios, id);
		List<Veiculo> Veiculos = usuario.getVeiculos().stream().collect(Collectors.toList());
		if (Veiculos.isEmpty()) {
			ResponseEntity<List<Veiculo>> resposta = new ResponseEntity<>(HttpStatus.NOT_FOUND);
			return resposta;
		} else {
			adicionadorLink.adicionarLink(Veiculos);
			ResponseEntity<List<Veiculo>> resposta = new ResponseEntity<>(Veiculos, HttpStatus.FOUND);
			return resposta;
		}
	}

	@PostMapping("/cadastro/{id}")
	public void cadastrarVeiculo(@RequestBody List<VeiculoDto> veiculos, @PathVariable long id) {
		Usuario usuario = usuarioRepositorio.getById(id);
		cadastrador.cadastrar(usuario, veiculos);
		usuarioRepositorio.save(usuario);
	}

	@PutMapping("/atualizar/{id}")
	public void atualizarVeiculo(@RequestBody VeiculoDto atualizacao, @PathVariable long id) {
		Veiculo veiculo = repositorio.getById(id);
		VeiculoAtualizador atualizador = new VeiculoAtualizador();
		atualizador.atualizar(veiculo, atualizacao);
		repositorio.save(veiculo);
	}

	@DeleteMapping("/excluir/{id}")
	public void excluirVeiculo(@PathVariable long id) {
		Veiculo veiculo = repositorio.getById(id);
		List<Usuario> usuarios = usuarioRepositorio.findAll();
		Usuario usuario = usuarioSelecionador.selecionar(usuarios, id);
		excluidor.excluir(usuario, veiculo);
		repositorio.delete(veiculo);
	}
}
