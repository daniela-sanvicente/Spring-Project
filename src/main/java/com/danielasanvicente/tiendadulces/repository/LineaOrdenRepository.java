package com.danielasanvicente.tiendadulces.repository;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.repository.CrudRepository;

import com.danielasanvicente.tiendadulces.entity.LineaOrden;
import com.danielasanvicente.tiendadulces.entity.Orden;

public interface LineaOrdenRepository extends CrudRepository<LineaOrden, Integer> {
  List<LineaOrden> findByOrden(Orden orden);
  Page<LineaOrden> findAll(Pageable pageable);
}
