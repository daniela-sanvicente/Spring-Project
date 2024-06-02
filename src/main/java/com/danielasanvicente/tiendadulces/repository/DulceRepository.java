package com.danielasanvicente.tiendadulces.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.repository.CrudRepository;

import com.danielasanvicente.tiendadulces.entity.Dulce;

public interface DulceRepository extends CrudRepository<Dulce, Integer> {
    List<Dulce> findByTema(String tema);
    Optional<Dulce> findByNombreDulce(String nombreDulce);
    Page<Dulce> findAll(Pageable pageable);
}
