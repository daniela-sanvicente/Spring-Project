package com.danielasanvicente.tiendadulces.repository;

import java.util.Optional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.repository.CrudRepository;

import com.danielasanvicente.tiendadulces.entity.MetodoPago;

public interface MetodoPagoRepository extends CrudRepository<MetodoPago, Integer> {
  Optional<MetodoPago> findByMetodo(String metodo);
  Page<MetodoPago> findAll(Pageable pageable);
}
