package com.danielasanvicente.tiendadulces.service;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import com.danielasanvicente.tiendadulces.entity.ItemCarrito;

public interface ItemCarritoService {

  Page<ItemCarrito> buscarItemCarritos(Pageable pageable);

  List<ItemCarrito> getItemCarritos();

  List<ItemCarrito> getItemCarritoByCarrito(Integer idCarrito);

  ItemCarrito getItemCarritoById(Integer id);

  ItemCarrito saveItemCarrito(ItemCarrito itemCarrito);

  ItemCarrito updateItemCarrito(Integer id, ItemCarrito itemCarrito);

  void deleteItemCarrito(Integer id);
}
