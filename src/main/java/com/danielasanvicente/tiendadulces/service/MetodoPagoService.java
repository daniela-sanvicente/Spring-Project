package com.danielasanvicente.tiendadulces.service;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import com.danielasanvicente.tiendadulces.entity.MetodoPago;

public interface MetodoPagoService {

  Page<MetodoPago> buscarMetodosPagos(Pageable pageable);

  List<MetodoPago> getMetodoPagos();

  MetodoPago getMetodoPagoByMetodo(String metodo);

  MetodoPago getMetodoPagoById(Integer id);

  MetodoPago saveMetodoPago(MetodoPago metodoPago);

  MetodoPago updateMetodoPago(Integer id, MetodoPago metodoPago);

  void deleteMetodoPago(Integer id);
}
