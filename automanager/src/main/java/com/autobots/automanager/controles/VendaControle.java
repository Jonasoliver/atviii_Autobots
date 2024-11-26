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

import com.autobots.automanager.adicionadores.AdicionadorLinkVenda;
import com.autobots.automanager.atualizadores.VendaAtualizador;
import com.autobots.automanager.cadastradores.VendaCadastrador;
import com.autobots.automanager.entidades.Venda;
import com.autobots.automanager.excluidores.VendaExcluidor;
import com.autobots.automanager.modelos.VendaDto;
import com.autobots.automanager.entidades.Empresa;
import com.autobots.automanager.entidades.Usuario;
import com.autobots.automanager.selecionadores.EmpresaSelecionador;
import com.autobots.automanager.selecionadores.UsuarioSelecionador;
import com.autobots.automanager.selecionadores.VendaSelecionador;
import com.autobots.automanager.repositorios.VendaRepositorio;
import com.autobots.automanager.repositorios.EmpresaRepositorio;
import com.autobots.automanager.repositorios.UsuarioRepositorio;

@RestController
@RequestMapping("/venda")
public class VendaControle {

	@Autowired
	private AdicionadorLinkVenda adicionadorLink;

	@Autowired
	private VendaRepositorio repositorio;

	@Autowired
	private UsuarioRepositorio usuarioRepositorio;
	
	@Autowired
	private EmpresaRepositorio empresaRepositorio;
	
	@Autowired
	private EmpresaSelecionador empresaSelecionador;

	@Autowired
	private VendaSelecionador selecionador;
	
	@Autowired
	private UsuarioSelecionador usuarioSelecionador;
	
	@Autowired
	private VendaCadastrador cadastrador;
	
	@Autowired
	private VendaAtualizador atualizador;
	
	@Autowired
	private VendaExcluidor excluidor;
	
	@GetMapping("/{id}")
	public ResponseEntity<Venda> obterVenda(@PathVariable long id) {
		List<Venda> vendas = repositorio.findAll();
		Venda venda = selecionador.selecionar(vendas, id);
		if (venda == null) {
			ResponseEntity<Venda> resposta = new ResponseEntity<>(HttpStatus.NOT_FOUND);
			return resposta;
		} else {
			adicionadorLink.adicionarLink(venda);
			ResponseEntity<Venda> resposta = new ResponseEntity<Venda>(venda, HttpStatus.FOUND);
			return resposta;
		}
	}
	
	@GetMapping("/vendas")
	public ResponseEntity<List<Venda>> obterVendas() {
		List<Venda> vendas = repositorio.findAll();
		if (vendas.isEmpty()) {
			ResponseEntity<List<Venda>> resposta = new ResponseEntity<>(HttpStatus.NOT_FOUND);
			return resposta;
		} else {
			adicionadorLink.adicionarLink(vendas);
			ResponseEntity<List<Venda>> resposta = new ResponseEntity<>(vendas, HttpStatus.FOUND);
			return resposta;
		}
	}


	@GetMapping("/venda/usuario/{id}")
	public ResponseEntity<List<Venda>> obterUsuarioVenda(@PathVariable long id) {
		List<Usuario> usuarios = usuarioRepositorio.findAll();
		Usuario usuario = usuarioSelecionador.selecionar(usuarios, id);
		List<Venda> Vendas = usuario.getVendas().stream().collect(Collectors.toList());
		if (Vendas.isEmpty()) {
			ResponseEntity<List<Venda>> resposta = new ResponseEntity<>(HttpStatus.NOT_FOUND);
			return resposta;
		} else {
			adicionadorLink.adicionarLink(Vendas);
			ResponseEntity<List<Venda>> resposta = new ResponseEntity<>(Vendas, HttpStatus.FOUND);
			return resposta;
		}
	}
	
	@GetMapping("/venda/empresa/{id}")
	public ResponseEntity<List<Venda>> obterEmpresaVenda(@PathVariable long id) {
		List<Empresa> empresas = empresaRepositorio.findAll();
		Empresa empresa = empresaSelecionador.selecionar(empresas, id);
		List<Venda> Vendas = empresa.getVendas().stream().collect(Collectors.toList());
		if (Vendas.isEmpty()) {
			ResponseEntity<List<Venda>> resposta = new ResponseEntity<>(HttpStatus.NOT_FOUND);
			return resposta;
		} else {
			adicionadorLink.adicionarLink(Vendas);
			ResponseEntity<List<Venda>> resposta = new ResponseEntity<>(Vendas, HttpStatus.FOUND);
			return resposta;
		}
	}

	@PostMapping("/cadastro/{id}")
	public void cadastrarVenda(@RequestBody List<VendaDto> venda, @PathVariable long id) {
		Empresa empresa = empresaRepositorio.getById(id);
		cadastrador.cadastrar(empresa.getVendas(), venda);
		empresaRepositorio.save(empresa);
	}

	@PutMapping("/atualizar/{id}")
	public void atualizarVenda(@RequestBody VendaDto atualizacao, @PathVariable long id) {
		List<Venda> vendas = repositorio.findAll();
		Venda venda = selecionador.selecionar(vendas, id);
		VendaAtualizador atualizador = new VendaAtualizador();
		atualizador.atualizar(venda, atualizacao);
		repositorio.save(venda);
	}
	
	@PutMapping("/atualizar/empresa/{id}")
	public void atualizarEmpresaVenda(@RequestBody List<VendaDto> atualizacao, @PathVariable long id) {
		List<Empresa> empresas = empresaRepositorio.findAll();
		Empresa empresa = empresaSelecionador.selecionar(empresas, id);
		atualizador.atualizar(empresa.getVendas(), atualizacao);
		empresaRepositorio.save(empresa);
	}

	@DeleteMapping("/excluir/{id}")
	public void excluirVenda(@RequestBody List<VendaDto> vendas, @PathVariable long id) {
		Empresa empresa = empresaRepositorio.getById(id);
		excluidor.excluir(empresa.getVendas(), vendas);
		empresaRepositorio.save(empresa);
	}
}
