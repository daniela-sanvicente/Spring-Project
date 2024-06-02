package com.danielasanvicente.tiendadulces.service;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.lang.Nullable;

import com.danielasanvicente.tiendadulces.entity.Pago;

public interface PagoService {

  Page<Pago> buscarPagos(Pageable pageable);

  List<Pago> getPagos();

  List<Pago> getPagoByCliente(Integer idCliente);

  Pago getPagoById(Integer id);

  Pago savePago(Pago pago);

  Pago createPago(
    Integer idCliente,
    @Nullable Integer idOrden,
    Integer idMetodoPago,
    String emisor,
    String numeroCuenta,
    String fechaExpiracion,
    String cvv
  );

  Pago updatePago(Integer id, Pago pago);

  void deletePago(Integer id);
}
