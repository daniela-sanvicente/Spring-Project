package com.danielasanvicente.tiendadulces.repository;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.repository.CrudRepository;

import com.danielasanvicente.tiendadulces.entity.Cliente;
import com.danielasanvicente.tiendadulces.entity.Pago;

public interface PagoRepository extends CrudRepository<Pago, Integer> {
  List<Pago> findByCliente(Cliente cliente);
  Page<Pago> findAll(Pageable pageable);
}
