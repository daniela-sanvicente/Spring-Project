package com.danielasanvicente.tiendadulces.service;

import java.util.List;

import com.danielasanvicente.tiendadulces.entity.Dulce;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface DulceService {

  Page<Dulce> buscarDulces(Pageable pageable);

  List<Dulce> getDulces();

  List<Dulce> getDulcesByTema(String tema);

  Dulce getDulceById(Integer id);

  Dulce getDulceByNombreDulce(String nombreDulce);

  Dulce saveDulce(Dulce dulce);

  Dulce updateDulce(Integer id, Dulce dulce);

  void deleteDulce(Integer id);
}
