package com.autobots.automanager.entidades;

import java.util.HashSet;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.ElementCollection;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToMany;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;

import org.springframework.hateoas.RepresentationModel;

import com.autobots.automanager.enumeracoes.PerfilUsuario;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
@Entity
@EqualsAndHashCode(exclude = { "mercadorias", "vendas", "veiculos" }, callSuper = false)
public class Usuario extends RepresentationModel<Usuario>{
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	
	@Column(nullable = false)
	private String nome;
	
	@Column
	private String nomeSocial;
	
	@ElementCollection(fetch = FetchType.EAGER)
	private Set<PerfilUsuario> perfis = new HashSet<>();
	
	@OneToMany(orphanRemoval = true, cascade = CascadeType.ALL, fetch = FetchType.EAGER)
	private Set<Telefone> telefones = new HashSet<>();
	
	@OneToOne(cascade = CascadeType.ALL, orphanRemoval = true)
	private Endereco endereco;
	
	@OneToMany(orphanRemoval = true, cascade = CascadeType.ALL, fetch = FetchType.EAGER)
	private Set<Documento> documentos = new HashSet<>();
	
    @OneToMany(orphanRemoval = true, cascade = CascadeType.ALL, fetch = FetchType.EAGER)
	private Set<Email> emails = new HashSet<>();
    
	@OneToMany(orphanRemoval = true, cascade = CascadeType.ALL, fetch = FetchType.EAGER)
	private Set<Credencial> credenciais = new HashSet<>();
	
	@OneToMany(cascade = { CascadeType.PERSIST, CascadeType.MERGE }, fetch = FetchType.EAGER)
	private Set<Mercadoria> mercadorias = new HashSet<>();
    
    @ManyToMany(fetch = FetchType.EAGER, cascade = { CascadeType.PERSIST, CascadeType.MERGE, CascadeType.REFRESH })
	private Set<Venda> vendas = new HashSet<>();
	
	@JsonIgnoreProperties(value = {"proprietario", "vendas"} )
    @OneToMany(fetch = FetchType.EAGER, cascade = { CascadeType.PERSIST, CascadeType.MERGE, CascadeType.REFRESH })
	private Set<Veiculo> veiculos = new HashSet<>();
    
    public Set<CredencialUsuarioSenha> getCredenciaisUsuarioSenha() {
        Set<CredencialUsuarioSenha> credenciaisUsuarioSenha = new HashSet<>();
        for (Credencial credencial : credenciais) {
            if (credencial instanceof CredencialUsuarioSenha) {
                credenciaisUsuarioSenha.add((CredencialUsuarioSenha) credencial);
            }
        }
        return credenciaisUsuarioSenha;
    }
    
    public Set<CredencialCodigoBarra> getCredenciaisCodigoBarra() {
        Set<CredencialCodigoBarra> credenciais = new HashSet<>();
        for (Credencial credencial : credenciais) {
            if (credencial instanceof CredencialCodigoBarra) {
                credenciais.add((CredencialCodigoBarra) credencial);
            }
        }
        return credenciais;
    }
}