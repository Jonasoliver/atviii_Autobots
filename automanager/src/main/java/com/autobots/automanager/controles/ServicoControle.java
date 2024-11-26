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

import com.autobots.automanager.adicionadores.AdicionadorLinkServico;
import com.autobots.automanager.atualizadores.ServicoAtualizador;
import com.autobots.automanager.cadastradores.ServicoCadastrador;
import com.autobots.automanager.entidades.Empresa;
import com.autobots.automanager.entidades.Servico;
import com.autobots.automanager.selecionadores.EmpresaSelecionador;
import com.autobots.automanager.selecionadores.ServicoSelecionador;
import com.autobots.automanager.repositorios.EmpresaRepositorio;
import com.autobots.automanager.repositorios.ServicoRepositorio;

@RestController
@RequestMapping("/servico")
public class ServicoControle {

	@Autowired
	private AdicionadorLinkServico adicionadorLink;

	@Autowired
	private ServicoRepositorio repositorio;

	@Autowired
	private ServicoSelecionador selecionador;
	
	@Autowired
	private EmpresaRepositorio empresaRepositorio;
	
	@Autowired
	private EmpresaSelecionador empresaSelecionador;
	
	@Autowired
	ServicoCadastrador cadastrador;

	@GetMapping("/{id}")
	public ResponseEntity<Servico> obterServico(@PathVariable long id) {
		List<Servico> servicos = repositorio.findAll();
		Servico servico = selecionador.selecionar(servicos, id);
		if (servico == null) {
			ResponseEntity<Servico> resposta = new ResponseEntity<>(HttpStatus.NOT_FOUND);
			return resposta;
		} else {
			adicionadorLink.adicionarLink(servico);
			ResponseEntity<Servico> resposta = new ResponseEntity<Servico>(servico, HttpStatus.FOUND);
			return resposta;
		}
	}
	
	@GetMapping("/servicos")
	public ResponseEntity<List<Servico>> obterServicos() {
		List<Servico> servicos = repositorio.findAll();
		if (servicos.isEmpty()) {
			ResponseEntity<List<Servico>> resposta = new ResponseEntity<>(HttpStatus.NOT_FOUND);
			return resposta;
		} else {
			adicionadorLink.adicionarLink(servicos);
			ResponseEntity<List<Servico>> resposta = new ResponseEntity<>(servicos, HttpStatus.FOUND);
			return resposta;
		}
	}


	@GetMapping("/servico/{id}")
	public ResponseEntity<List<Servico>> obterempresaServico(@PathVariable long id) {
		List<Empresa> empresas = empresaRepositorio.findAll();
		Empresa empresa = empresaSelecionador.selecionar(empresas, id);
		List<Servico> servicos = empresa.getServicos().stream().collect(Collectors.toList());
		if (servicos.isEmpty()) {
			ResponseEntity<List<Servico>> resposta = new ResponseEntity<>(HttpStatus.NOT_FOUND);
			return resposta;
		} else {
			adicionadorLink.adicionarLink(servicos);
			ResponseEntity<List<Servico>> resposta = new ResponseEntity<>(servicos, HttpStatus.FOUND);
			return resposta;
		}
	}

	@PostMapping("/cadastro/{id}")
	public void cadastrarServico(@RequestBody List<Servico> Servico, @PathVariable long id) {
		List<Empresa> empresas = empresaRepositorio.findAll();
		Empresa empresa = empresaSelecionador.selecionar(empresas, id);
		cadastrador.cadastrar(empresa.getServicos(), Servico);
		empresaRepositorio.save(empresa);
	}

	@PutMapping("/atualizar/{id}")
	public void atualizarServico(@RequestBody Servico atualizacao, @PathVariable long id) {
		List<Servico> servicos = repositorio.findAll();
		Servico servico = selecionador.selecionar(servicos, id);
		ServicoAtualizador atualizador = new ServicoAtualizador();
		atualizador.atualizar(servico, atualizacao);
		repositorio.save(servico);
	}
	
	@PutMapping("/atualizar/empresa/{id}")
	public void atualizarEmpresaServico(@RequestBody List<Servico> atualizacao, @PathVariable long id) {
		List<Empresa> empresas = empresaRepositorio.findAll();
		Empresa empresa = empresaSelecionador.selecionar(empresas, id);
		ServicoAtualizador atualizador = new ServicoAtualizador();
		atualizador.atualizar(empresa.getServicos(), atualizacao);
		empresaRepositorio.save(empresa);
	}

	@DeleteMapping("/excluir/{id}")
	public void excluirempresaServico(@PathVariable long id) {
		Servico servico = repositorio.getById(id);
		repositorio.delete(servico);
	}
}
