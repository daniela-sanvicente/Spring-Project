package com.danielasanvicente.tiendadulces.service;

import java.util.List;
import java.util.Optional;

import com.danielasanvicente.tiendadulces.util.Log;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import com.danielasanvicente.tiendadulces.entity.LineaOrden;
import com.danielasanvicente.tiendadulces.entity.Orden;
import com.danielasanvicente.tiendadulces.exception.EntityNotFoundException;
import com.danielasanvicente.tiendadulces.repository.LineaOrdenRepository;

import jakarta.transaction.Transactional;
import lombok.AllArgsConstructor;

@Service
@Transactional
@AllArgsConstructor
public class LineaOrdenServiceImpl implements LineaOrdenService {

  @Autowired
  private LineaOrdenRepository lineaOrdenRepository;

  @Autowired
  private OrdenService ordenService;

   @Override
    public Page<LineaOrden> buscarLineaOrdenes(Pageable pageable) {
        Log.out.info("Servicio LineaOrden: Buscando todos los lineaOrdenes");
        return lineaOrdenRepository.findAll(pageable);
    }

  @Override
  public List<LineaOrden> getLineaOrdens() {
    Log.out.info("Servicio LineaOrden: Buscando todos los lineaOrdens");
    return (List<LineaOrden>) lineaOrdenRepository.findAll();
  }

  @Override
  public LineaOrden getLineaOrdenById(Integer id) {
    Optional<LineaOrden> lineaOrden = lineaOrdenRepository.findById(id);
    Log.out.info("Servicio LineaOrden: Buscando lineaOrden por ID "+ id);
    return unwrapLineaOrden(lineaOrden, id);
  }

  @Override
  public List<LineaOrden> getLineaOrdenByOrden(Integer idOrden) {
    Orden orden = ordenService.getOrdenById(idOrden);

    Log.out.info("Servicio LineaOrden: Buscando lineaOrden por nombre "+ idOrden);
    return lineaOrdenRepository.findByOrden(orden);
  }

  @Override
  public LineaOrden saveLineaOrden(LineaOrden lineaOrden) {
    Log.out.info("Servicio LineaOrden: Guardando lineaOrden {} ", lineaOrden);
    return lineaOrdenRepository.save(lineaOrden);
  }

  public LineaOrden updateLineaOrden(Integer id, LineaOrden updatedLineaOrden) {
    Optional<LineaOrden> optionalLineaOrden = lineaOrdenRepository.findById(id);

    if (optionalLineaOrden.isPresent()) {
      LineaOrden lineaOrden = optionalLineaOrden.get();

      if (updatedLineaOrden.getOrden() != null) {
        lineaOrden.setOrden(updatedLineaOrden.getOrden());
      }

      if (updatedLineaOrden.getDulce() != null) {
        lineaOrden.setDulce(updatedLineaOrden.getDulce());
      }

      if (updatedLineaOrden.getArreglo() != null) {
        lineaOrden.setArreglo(updatedLineaOrden.getArreglo());
      }

      if (updatedLineaOrden.getMesa() != null) {
        lineaOrden.setMesa(updatedLineaOrden.getMesa());
      }

      if (updatedLineaOrden.getCantidad() != null) {
        lineaOrden.setCantidad(updatedLineaOrden.getCantidad());
      }

      if (updatedLineaOrden.getPrecio() != null) {
        lineaOrden.setPrecio(updatedLineaOrden.getPrecio());
      }

      Log.out.info("Servicio LineaOrden: Actualizando lineaOrden {} ", lineaOrden);
      return lineaOrdenRepository.save(lineaOrden);
    } else {
      throw new EntityNotFoundException(String.valueOf(id), LineaOrden.class);
    }
  }

  @Override
  public void deleteLineaOrden(Integer id) {
    Log.out.info("Servicio LineaOrden: Borrando lineaOrden con ID "+ id);
    lineaOrdenRepository.deleteById(id);
  }

  static LineaOrden unwrapLineaOrden(Optional<LineaOrden> entity, Object id) {
    if (entity.isPresent())
      return entity.get();

    else
      throw new EntityNotFoundException(String.valueOf(id), LineaOrden.class);
  }
}
