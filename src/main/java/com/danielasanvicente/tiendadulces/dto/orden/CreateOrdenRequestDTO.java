package com.danielasanvicente.tiendadulces.dto.orden;

import org.springframework.lang.Nullable;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class CreateOrdenRequestDTO {
  Integer idCliente;
  
  String tipoOrden;
  
  String direccionEntrega;

  @Nullable
  Double anticipo;
  
  @Nullable
  Double descuento;
  
  Double precioTotal;
}
