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

import com.autobots.automanager.adicionadores.AdicionadorLinkEmpresa;
import com.autobots.automanager.atualizadores.EmpresaAtualizador;
import com.autobots.automanager.cadastradores.EmpresaCadastrador;
import com.autobots.automanager.entidades.Empresa;
import com.autobots.automanager.modelos.EmpresaDto;
import com.autobots.automanager.repositorios.EmpresaRepositorio;
import com.autobots.automanager.selecionadores.EmpresaSelecionador;

@RestController
@RequestMapping("/empresa")
public class EmpresaControle {
	
	@Autowired
	private EmpresaRepositorio repositorio;
	
	@Autowired
	private AdicionadorLinkEmpresa adicionadorLink;
	
	@Autowired
	private EmpresaSelecionador selecionador;
	
	@GetMapping("/{id}")
	public ResponseEntity<Empresa> obterEmpresa(@PathVariable long id) {
		List<Empresa> empresas = repositorio.findAll();
		Empresa empresa = selecionador.selecionar(empresas, id);
		if (empresa == null) {
			ResponseEntity<Empresa> resposta = new ResponseEntity<>(HttpStatus.NOT_FOUND);
			return resposta;
		} else {
			adicionadorLink.adicionarLink(empresa);
			ResponseEntity<Empresa> resposta = new ResponseEntity<Empresa>(empresa, HttpStatus.FOUND);
			return resposta;
		}
	}

	@GetMapping("/empresas")
	public ResponseEntity<List<Empresa>> obterEmpresas() {
		List<Empresa> empresas = repositorio.findAll();
		if (empresas.isEmpty()) {
			ResponseEntity<List<Empresa>> resposta = new ResponseEntity<>(HttpStatus.NOT_FOUND);
			return resposta;
		} else {
			adicionadorLink.adicionarLink(empresas);
			ResponseEntity<List<Empresa>> resposta = new ResponseEntity<>(empresas, HttpStatus.FOUND);
			return resposta;
		}
	}
	
	@PostMapping("/cadastro")
	public void cadastrarEmpresa(@RequestBody EmpresaDto empresa) {
		EmpresaCadastrador cadastrador = new EmpresaCadastrador();
		Empresa novaEmpresa = cadastrador.cadastrar(empresa);
		repositorio.save(novaEmpresa);
	}

	@PutMapping("/atualizar/{id}")
	public void atualizarEmpresa(@RequestBody EmpresaDto atualizacao, @PathVariable long id) {
		Empresa empresa = repositorio.getById(id);
		EmpresaAtualizador atualizador = new EmpresaAtualizador();
		atualizador.atualizar(empresa, atualizacao);
		repositorio.save(empresa);
	}

	@DeleteMapping("/excluir/{id}")
	public void excluirEmpresa(@PathVariable long id) {
		Empresa empresa = repositorio.getById(id);
		repositorio.delete(empresa);
	}
}
