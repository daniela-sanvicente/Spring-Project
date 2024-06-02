package com.danielasanvicente.tiendadulces.repository;

import java.util.Optional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.repository.CrudRepository;

import com.danielasanvicente.tiendadulces.entity.Carrito;
import com.danielasanvicente.tiendadulces.entity.Cliente;

public interface CarritoRepository extends CrudRepository<Carrito, Integer> {
  Optional<Carrito> findByCliente(Cliente cliente);
  Page<Carrito> findAll(Pageable pageable);
}
