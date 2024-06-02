package com.danielasanvicente.tiendadulces.repository;

import java.util.Optional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.repository.CrudRepository;

import com.danielasanvicente.tiendadulces.entity.Cliente;

public interface ClienteRepository extends CrudRepository<Cliente, Integer> {
	Optional<Cliente> findByCorreo(String correo);
	Optional<Cliente> findByNombre(String nombre);
	Page<Cliente> findAll(Pageable pageable);
}
