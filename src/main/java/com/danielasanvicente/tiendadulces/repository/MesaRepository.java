package com.danielasanvicente.tiendadulces.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.repository.CrudRepository;

import com.danielasanvicente.tiendadulces.entity.Mesa;

public interface MesaRepository extends CrudRepository<Mesa, Integer> {
    List<Mesa> findByTema(String tema);
	Optional<Mesa> findByNombreMesa(String nombreMesa);
    Page<Mesa> findAll(Pageable pageable);
    

}
