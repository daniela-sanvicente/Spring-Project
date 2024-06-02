package com.danielasanvicente.tiendadulces.service;

import java.util.List;
import java.util.Optional;

import com.danielasanvicente.tiendadulces.util.Log;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import com.danielasanvicente.tiendadulces.entity.MetodoPago;
import com.danielasanvicente.tiendadulces.exception.EntityNotFoundException;
import com.danielasanvicente.tiendadulces.repository.MetodoPagoRepository;

import jakarta.transaction.Transactional;
import lombok.AllArgsConstructor;

@Service
@Transactional
@AllArgsConstructor
public class MetodoPagoServiceImpl implements MetodoPagoService {
  @Autowired
  private MetodoPagoRepository metodoPagoRepository;

  @Override
  public Page<MetodoPago> buscarMetodosPagos(Pageable pageable) {
      Log.out.info("Servicio MetodoPago: Buscando todos los metodoPagos");
      return metodoPagoRepository.findAll(pageable);
  }

  @Override
  public List<MetodoPago> getMetodoPagos() {
    Log.out.info("Servicio MetodoPago: Buscando todos los metodoPagos");
    return (List<MetodoPago>) metodoPagoRepository.findAll();
  }

  @Override
  public MetodoPago getMetodoPagoById(Integer id) {
    Optional<MetodoPago> metodoPago = metodoPagoRepository.findById(id);
    Log.out.info("Servicio MetodoPago: Buscando metodoPago por ID "+ id);
    return unwrapMetodoPago(metodoPago, id);
  }

  @Override
  public MetodoPago getMetodoPagoByMetodo(String metodo) {
    Optional<MetodoPago> metodoPago = metodoPagoRepository.findByMetodo(metodo);
    Log.out.info("Servicio MetodoPago: Buscando metodoPago por nombre "+ metodo);
    return unwrapMetodoPago(metodoPago, metodo);
  }

  @Override
  public MetodoPago saveMetodoPago(MetodoPago metodoPago) {
    Log.out.info("Servicio MetodoPago: Guardando metodoPago {} ", metodoPago);
    return metodoPagoRepository.save(metodoPago);
  }

  public MetodoPago updateMetodoPago(Integer id, MetodoPago updatedMetodoPago) {
    Optional<MetodoPago> optionalMetodoPago = metodoPagoRepository.findById(id);

    if (optionalMetodoPago.isPresent()) {
      MetodoPago metodoPago = optionalMetodoPago.get();

      if (updatedMetodoPago.getMetodo() != null) {
        metodoPago.setMetodo(updatedMetodoPago.getMetodo());
      }

      if (updatedMetodoPago.getPagos() != null) {
        metodoPago.setPagos(updatedMetodoPago.getPagos());
      }

      Log.out.info("Servicio MetodoPago: Actualizando metodoPago {} ", metodoPago);
      return metodoPagoRepository.save(metodoPago);
    } else {
      throw new EntityNotFoundException(String.valueOf(id), MetodoPago.class);
    }
  }

  @Override
  public void deleteMetodoPago(Integer id) {
    Log.out.info("Servicio MetodoPago: Borrando metodoPago con ID "+ id);
    metodoPagoRepository.deleteById(id);
  }

  static MetodoPago unwrapMetodoPago(Optional<MetodoPago> entity, Object id) {
    if (entity.isPresent())
      return entity.get();

    else
      throw new EntityNotFoundException(String.valueOf(id), MetodoPago.class);
  }
}
