package com.danielasanvicente.tiendadulces.exception;

public class EntityNotFoundException extends RuntimeException { 

    public EntityNotFoundException(String id, Class<?> entity) { 
            super("La entidad " + entity.getSimpleName().toLowerCase() + " de id '" + id + "' no fue encontrada");
    }
}
