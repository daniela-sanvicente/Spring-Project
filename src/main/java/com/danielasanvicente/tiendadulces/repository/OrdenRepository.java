package com.danielasanvicente.tiendadulces.repository;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.repository.CrudRepository;

import com.danielasanvicente.tiendadulces.entity.Cliente;
import com.danielasanvicente.tiendadulces.entity.Orden;

public interface OrdenRepository extends CrudRepository<Orden, Integer> {
  List<Orden> findByCliente(Cliente cliente);
  Page<Orden> findAll(Pageable pageable);
}
