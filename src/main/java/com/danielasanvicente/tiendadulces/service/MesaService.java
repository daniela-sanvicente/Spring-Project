package com.danielasanvicente.tiendadulces.service;

import java.util.List;


import com.danielasanvicente.tiendadulces.entity.Mesa;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface MesaService {

  Page<Mesa> buscarMesas(Pageable pageable);

  List<Mesa> getMesas();

  List<Mesa> getMesasByTema(String tema);

  Mesa getMesaById(Integer id);

  Mesa getMesaByNombreMesa(String nombreMesa);

  Mesa saveMesa(Mesa mesa);

  Mesa updateMesa(Integer id, Mesa mesa);

  void deleteMesa(Integer id);
}
