package com.autobots.automanager.excluidores;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.autobots.automanager.entidades.Empresa;
import com.autobots.automanager.entidades.Usuario;
import com.autobots.automanager.entidades.Veiculo;
import com.autobots.automanager.entidades.Venda;
import com.autobots.automanager.repositorios.EmpresaRepositorio;
import com.autobots.automanager.repositorios.UsuarioRepositorio;
import com.autobots.automanager.repositorios.VendaRepositorio;

@Service
public class UsuarioExcluidor {
	
	private DocumentoExcluidor documentoExcluidor = new DocumentoExcluidor();
	
	private EmailExcluidor emailExcluidor = new EmailExcluidor();
	
	private CredencialExcluidor credencialExcluidor = new CredencialExcluidor();
	
	private TelefoneExcluidor telefoneExcluidor = new TelefoneExcluidor();
	
	@Autowired
	private EmpresaRepositorio empresaRepositorio;
	
	@Autowired
	private UsuarioRepositorio repositorio;
	
	@Autowired
	private VendaRepositorio vendaRepositorio;

	public void excluir(Usuario usuario) {
		if (usuario != null) {
			for(Venda venda : vendaRepositorio.findAll()) {
				if (venda.getCliente() == usuario) {
					venda.setCliente(null);
					vendaRepositorio.save(venda);
				}
				if(venda.getFuncionario() == usuario) {
					venda.setFuncionario(null);
					vendaRepositorio.save(venda);
				}
				usuario.getVendas().remove(venda);
			}
			
			documentoExcluidor.excluir(usuario, usuario.getDocumentos().stream().collect(Collectors.toList()));
			emailExcluidor.excluir(usuario, usuario.getEmails().stream().collect(Collectors.toList()));
			credencialExcluidor.excluir(usuario, usuario.getCredenciais().stream().collect(Collectors.toList()));
            usuario.getCredenciais().clear();
			telefoneExcluidor.excluir(usuario, usuario.getTelefones().stream().collect(Collectors.toList()));
			
			
			for (Veiculo veiculo : usuario.getVeiculos()) {
				veiculo.setProprietario(null);
			}
			
			List<Empresa> empresas = empresaRepositorio.findAll();
			for (Empresa empresa : empresas) {
                empresa.getUsuarios().removeIf(user -> user.getId().equals(usuario.getId()));
			}

			repositorio.delete(usuario);

		}
	}

}
