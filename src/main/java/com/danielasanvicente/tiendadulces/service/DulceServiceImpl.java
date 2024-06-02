package com.danielasanvicente.tiendadulces.service;

import java.util.List;
import java.util.Optional;

import com.danielasanvicente.tiendadulces.util.Log;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import com.danielasanvicente.tiendadulces.entity.Dulce;
import com.danielasanvicente.tiendadulces.exception.EntityNotFoundException;
import com.danielasanvicente.tiendadulces.repository.DulceRepository;

import jakarta.transaction.Transactional;
import lombok.AllArgsConstructor;

@Service
@Transactional
@AllArgsConstructor
public class DulceServiceImpl implements DulceService {
  @Autowired
  private DulceRepository dulceRepository;

  @Override
  public Page<Dulce> buscarDulces(Pageable pageable) {
    Log.out.info("Servicio Dulce: Buscando todos los dulces");
    return dulceRepository.findAll(pageable);
  }

  @Override
  public List<Dulce> getDulces() {
    Log.out.info("Servicio Dulce: Buscando todos los dulces");
    return (List<Dulce>) dulceRepository.findAll();
  }

  @Override
  public Dulce getDulceById(Integer id) {
    Optional<Dulce> dulce = dulceRepository.findById(id);

    Log.out.info("Servicio Dulce: Buscando dulce por ID "+ id);
    return unwrapDulce(dulce, id);
  }

  @Override
  public Dulce getDulceByNombreDulce(String nombreDulce) {
    Optional<Dulce> dulce = dulceRepository.findByNombreDulce(nombreDulce);

    Log.out.info("Servicio Dulce: Buscando dulce por nombre "+ nombreDulce);
    return unwrapDulce(dulce, nombreDulce);
  }

  @Override
  public List<Dulce> getDulcesByTema(String tema) {
    Log.out.info("Servicio Dulce: Buscando dulce por tema "+ tema);
    return dulceRepository.findByTema(tema);
  }

  @Override
  public Dulce saveDulce(Dulce dulce) {
    Log.out.info("Servicio Dulce: Guardando dulce {} ", dulce);
    return dulceRepository.save(dulce);
  }

  public Dulce updateDulce(Integer id, Dulce updatedDulce) {
    Optional<Dulce> optionalDulce = dulceRepository.findById(id);

    if (optionalDulce.isPresent()) {
      Dulce dulce = optionalDulce.get();

      if (updatedDulce.getNombreDulce() != null) {
        dulce.setNombreDulce(updatedDulce.getNombreDulce());
      }

      if (updatedDulce.getPrecio() != null) {
        dulce.setPrecio(updatedDulce.getPrecio());
      }

      if (updatedDulce.getTema() != null) {
        dulce.setTema(updatedDulce.getTema());
      }

      if (updatedDulce.getTipo() != null) {
        dulce.setTipo(updatedDulce.getTipo());
      }

      if (updatedDulce.getStock() != null) {
        dulce.setStock(updatedDulce.getStock());
      }

      if (updatedDulce.getImagen() != null) {
        dulce.setImagen(updatedDulce.getImagen());
      }

      if (updatedDulce.getTiempoElaboracion() != null) {
        dulce.setTiempoElaboracion(updatedDulce.getTiempoElaboracion());
      }

      if (updatedDulce.getDescripcion() != null) {
        dulce.setDescripcion(updatedDulce.getDescripcion());
      }

      if (updatedDulce.getArreglos() != null) {
        dulce.setArreglos(updatedDulce.getArreglos());
      }

      if (updatedDulce.getMesas() != null) {
        dulce.setMesas(updatedDulce.getMesas());
      }

      Log.out.info("Servicio Dulce: Actualizando dulce {} ", dulce);
      return dulceRepository.save(dulce);
    } else {
      throw new EntityNotFoundException(String.valueOf(id), Dulce.class);
    }
  }

  @Override
  public void deleteDulce(Integer id) {
    Log.out.info("Servicio Dulce: Borrando dulce con ID "+ id);
    dulceRepository.deleteById(id);
  }

  static Dulce unwrapDulce(Optional<Dulce> entity, Object id) {
    if (entity.isPresent())
      return entity.get();

    else
      throw new EntityNotFoundException(String.valueOf(id), Dulce.class);
  }
}
