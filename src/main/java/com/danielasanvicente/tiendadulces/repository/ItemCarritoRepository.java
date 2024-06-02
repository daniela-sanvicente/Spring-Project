package com.danielasanvicente.tiendadulces.repository;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.repository.CrudRepository;

import com.danielasanvicente.tiendadulces.entity.Carrito;
import com.danielasanvicente.tiendadulces.entity.ItemCarrito;

public interface ItemCarritoRepository extends CrudRepository<ItemCarrito, Integer> {
  List<ItemCarrito> findByCarrito(Carrito carrito);
  Page<ItemCarrito> findAll(Pageable pageable);
}
