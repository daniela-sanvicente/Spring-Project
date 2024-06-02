package com.danielasanvicente.tiendadulces.dto.pago;

import org.springframework.lang.Nullable;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class CreatePagoRequestDTO {
  Integer idCliente;

  @Nullable
  Integer idOrden;
  
  Integer idMetodoPago;
  
  String emisor;
  
  String numeroCuenta;
  
  String fechaExpiracion;
  
  String cvv;
}
