package com.danielasanvicente.tiendadulces.dto.carrito;

import org.springframework.lang.Nullable;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class AddToCartRequestDTO {
    @Nullable
    private Integer idDulce;

    @Nullable
    private Integer idArreglo;

    @Nullable
    private Integer idMesa;
    
    private Integer cantidad;
}
