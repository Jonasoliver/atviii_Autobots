package com.autobots.automanager.repositorios;

import org.springframework.data.jpa.repository.JpaRepository;

import com.autobots.automanager.entidades.Credencial;

public interface CredencialRepositorio extends JpaRepository<Credencial, Long>{

}
