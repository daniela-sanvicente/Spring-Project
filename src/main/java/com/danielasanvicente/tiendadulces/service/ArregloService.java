package com.danielasanvicente.tiendadulces.service;

import java.util.List;

import com.danielasanvicente.tiendadulces.entity.Arreglo;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface ArregloService {

  Page<Arreglo> buscarArreglos(Pageable pageable);

  List<Arreglo> getArreglos();

  List<Arreglo> getArreglosByTema(String tema);

  Arreglo getArregloById(Integer id);

  Arreglo getArregloByNombreArreglo(String nombreArreglo);

  Arreglo saveArreglo(Arreglo arreglo);

  Arreglo updateArreglo(Integer id, Arreglo arreglo);

  void deleteArreglo(Integer id);
}
