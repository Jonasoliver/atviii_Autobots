package com.autobots.automanager.repositorios;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.autobots.automanager.entidades.Veiculo;

@Repository
public interface VeiculoRepositorio extends JpaRepository<Veiculo, Long>{

}
