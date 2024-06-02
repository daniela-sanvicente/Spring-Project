package com.danielasanvicente.tiendadulces.dto.orden;

import org.springframework.lang.Nullable;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class CompleteOrderRequestDTO {
  Integer idCliente;

  Integer idMetodoPago;

  String tipoOrden;

  String direccionEntrega;

  @Nullable
  Double anticipo;

  @Nullable
  Double descuento;

  Double precioTotal;

  String emisor;

  String numeroCuenta;

  String fechaExpiracion;

  String cvv;
}
