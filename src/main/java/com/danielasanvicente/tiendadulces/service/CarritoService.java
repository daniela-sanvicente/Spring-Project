package com.danielasanvicente.tiendadulces.service;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.lang.Nullable;

import com.danielasanvicente.tiendadulces.entity.Carrito;
import com.danielasanvicente.tiendadulces.entity.ItemCarrito;

public interface CarritoService {

  Page<Carrito> buscarCarritos(Pageable pageable);

  List<Carrito> getCarritos();

  List<ItemCarrito> getItemsFromCart(Carrito carrito);
  
  Carrito getCarritoByCliente(Integer idCliente);
  
  Carrito getCarritoById(Integer id);

  Carrito saveCarrito(Carrito carrito);
  
  Carrito createCarrito(Integer idCliente);

  void addItemToCart(
    Integer idCarrito, 
    Integer idCliente,
    @Nullable Integer idDulce, 
    @Nullable Integer idArreglo, 
    @Nullable Integer idMesa, 
    Integer cantidad
  );

  Carrito updateCarrito(Integer id, Carrito carrito);

  void deleteCarrito(Integer id);
}
