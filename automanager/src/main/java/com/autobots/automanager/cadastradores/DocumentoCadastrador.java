package com.autobots.automanager.cadastradores;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.autobots.automanager.entidades.Documento;
import com.autobots.automanager.entidades.Usuario;
import com.autobots.automanager.modelos.StringVerificadorNulo;
import com.autobots.automanager.repositorios.DocumentoRepositorio;
import com.autobots.automanager.repositorios.UsuarioRepositorio;

@Service
public class DocumentoCadastrador {
	private StringVerificadorNulo verificador = new StringVerificadorNulo();
	
	@Autowired
	private UsuarioRepositorio usuarioRepositorio;
	
	@Autowired
	private DocumentoRepositorio documentoRepositorio;
	
	public void cadastrar(Usuario usuario, Documento documento) {
		if (documento != null) {
			Documento novoDocumento = new Documento();
			novoDocumento.setTipo(documento.getTipo());
			if (!verificador.verificar(documento.getNumero())) {
				novoDocumento.setNumero(documento.getNumero());
				novoDocumento.setDataEmissao(documento.getDataEmissao());
			}
			
			usuario.getDocumentos().add(novoDocumento);
			documentoRepositorio.save(novoDocumento);
			usuarioRepositorio.save(usuario);
		}
		
	}

	public void cadastrar(Usuario usuario, List<Documento> documentosNovos) {
		for (Documento documento : documentosNovos) {
			cadastrar(usuario, documento);
		}
	}
}
