package com.danielasanvicente.tiendadulces.service;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import com.danielasanvicente.tiendadulces.entity.LineaOrden;

public interface LineaOrdenService {

  Page<LineaOrden> buscarLineaOrdenes(Pageable pageable);

  List<LineaOrden> getLineaOrdens();

  List<LineaOrden> getLineaOrdenByOrden(Integer idOrden);

  LineaOrden getLineaOrdenById(Integer id);

  LineaOrden saveLineaOrden(LineaOrden lineaOrden);

  LineaOrden updateLineaOrden(Integer id, LineaOrden lineaOrden);

  void deleteLineaOrden(Integer id);
}
