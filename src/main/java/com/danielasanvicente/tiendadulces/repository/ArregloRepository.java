package com.danielasanvicente.tiendadulces.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.repository.CrudRepository;

import com.danielasanvicente.tiendadulces.entity.Arreglo;

public interface ArregloRepository extends CrudRepository<Arreglo, Integer> {
    List<Arreglo> findByTema(String tema);
	Optional<Arreglo> findByNombreArreglo(String nombreArreglo);
    Page<Arreglo> findAll(Pageable pageable);
}
