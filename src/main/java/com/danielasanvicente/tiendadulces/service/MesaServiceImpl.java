package com.danielasanvicente.tiendadulces.service;

import java.util.List;
import java.util.Optional;

import com.danielasanvicente.tiendadulces.util.Log;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import com.danielasanvicente.tiendadulces.entity.Mesa;
import com.danielasanvicente.tiendadulces.exception.EntityNotFoundException;
import com.danielasanvicente.tiendadulces.repository.MesaRepository;

import jakarta.transaction.Transactional;
import lombok.AllArgsConstructor;

@Service
@Transactional
@AllArgsConstructor
public class MesaServiceImpl implements MesaService {
  @Autowired
  private MesaRepository mesaRepository;

  @Override
  public Page<Mesa> buscarMesas(Pageable pageable) {
    Log.out.info("Servicio Mesa: Buscando todas las mesas");
    return mesaRepository.findAll(pageable);
  }

  @Override
  public List<Mesa> getMesas() {
    Log.out.info("Servicio Mesa: Buscando todos los mesas");
    return (List<Mesa>) mesaRepository.findAll();
  }

  @Override
  public Mesa getMesaById(Integer id) {
    Optional<Mesa> mesa = mesaRepository.findById(id);
    Log.out.info("Servicio Mesa: Buscando mesa por ID "+ id);
    return unwrapMesa(mesa, id);
  }

  @Override
  public Mesa getMesaByNombreMesa(String nombreMesa) {
    Optional<Mesa> mesa = mesaRepository.findByNombreMesa(nombreMesa);
    Log.out.info("Servicio Mesa: Buscando mesa por nombre "+ nombreMesa);
    return unwrapMesa(mesa, nombreMesa);
  }

  @Override
  public List<Mesa> getMesasByTema(String tema) {
    Log.out.info("Servicio Mesa: Buscando mesa por tema "+ tema);
    return mesaRepository.findByTema(tema);
  }

  @Override
  public Mesa saveMesa(Mesa mesa) {
    Log.out.info("Servicio Mesa: Guardando mesa {} ", mesa);
    return mesaRepository.save(mesa);
  }

  public Mesa updateMesa(Integer id, Mesa updatedMesa) {
    Optional<Mesa> optionalMesa = mesaRepository.findById(id);

    if (optionalMesa.isPresent()) {
      Mesa mesa = optionalMesa.get();

      if (updatedMesa.getNombreMesa() != null) {
        mesa.setNombreMesa(updatedMesa.getNombreMesa());
      }

      if (updatedMesa.getPrecio() != null) {
        mesa.setPrecio(updatedMesa.getPrecio());
      }

      if (updatedMesa.getTema() != null) {
        mesa.setTema(updatedMesa.getTema());
      }

      if (updatedMesa.getTipo() != null) {
        mesa.setTipo(updatedMesa.getTipo());
      }

      if (updatedMesa.getImagen() != null) {
        mesa.setImagen(updatedMesa.getImagen());
      }

      if (updatedMesa.getCotizacion() != null) {
        mesa.setCotizacion(updatedMesa.getCotizacion());
      }

      if (updatedMesa.getTiempoElaboracion() != null) {
        mesa.setTiempoElaboracion(updatedMesa.getTiempoElaboracion());
      }

      if (updatedMesa.getDescripcion() != null) {
        mesa.setDescripcion(updatedMesa.getDescripcion());
      }

      if (updatedMesa.getDulces() != null) {
        mesa.setDulces(updatedMesa.getDulces());
      }

      if (updatedMesa.getArreglos() != null) {
        mesa.setArreglos(updatedMesa.getArreglos());
      }

      Log.out.info("Servicio Mesa: Actualizando mesa {} ", mesa);
      return mesaRepository.save(mesa);
    } else {
      throw new EntityNotFoundException(String.valueOf(id), Mesa.class);
    }
  }

  @Override
  public void deleteMesa(Integer id) {
    Log.out.info("Servicio Mesa: Borrando mesa con ID "+ id);
    mesaRepository.deleteById(id);
  }

  static Mesa unwrapMesa(Optional<Mesa> entity, Object id) {
    if (entity.isPresent())
      return entity.get();

    else
      throw new EntityNotFoundException(String.valueOf(id), Mesa.class);
  }
}
