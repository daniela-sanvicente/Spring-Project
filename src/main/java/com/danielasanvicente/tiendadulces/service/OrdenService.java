package com.danielasanvicente.tiendadulces.service;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.lang.Nullable;

import com.danielasanvicente.tiendadulces.entity.Orden;

public interface OrdenService {

  Page<Orden> buscarOrdenes(Pageable pageable);

  List<Orden> getOrdenes();

  List<Orden> getOrdenByCliente(Integer idCliente);

  Orden getOrdenById(Integer id);

  Orden saveOrden(Orden orden);

  Orden createOrden(
    Integer idCliente, 
    String tipoOrden, 
    String direccionEntrega, 
    @Nullable Double anticipo,
    @Nullable Double descuento,
    Double precioTotal
  );

  Orden placeOrder(
    Integer idCliente, 
    Integer idMetodoPago, 
    String tipoOrden, 
    String direccionEntrega, 
    @Nullable Double anticipo,
    @Nullable Double descuento,
    Double precioTotal, 
    String emisor, 
    String numeroCuenta, 
    String fechaExpiracion, 
    String cvv
  );

  Orden updateOrden(Integer id, Orden orden);

  void deleteOrden(Integer id);
}
