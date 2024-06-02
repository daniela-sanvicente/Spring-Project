package com.danielasanvicente.tiendadulces.service;

import java.util.List;
import java.util.Optional;

import com.danielasanvicente.tiendadulces.util.Log;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import com.danielasanvicente.tiendadulces.entity.Arreglo;
import com.danielasanvicente.tiendadulces.exception.EntityNotFoundException;
import com.danielasanvicente.tiendadulces.repository.ArregloRepository;

import jakarta.transaction.Transactional;
import lombok.AllArgsConstructor;

@Service
@Transactional
@AllArgsConstructor
public class ArregloServiceImpl implements ArregloService {
  @Autowired
  private ArregloRepository arregloRepository;

  @Override
  public Page<Arreglo> buscarArreglos(Pageable pageable) {
    Log.out.info("Servicio Arreglo: Buscando todos los arreglos");
    return arregloRepository.findAll(pageable);
  }

  @Override
  public List<Arreglo> getArreglos() {
    Log.out.info("Servicio Arreglo: Buscando todos los arreglos");
    return (List<Arreglo>) arregloRepository.findAll();
  }

  @Override
  public Arreglo getArregloById(Integer id) {
    Optional<Arreglo> arreglo = arregloRepository.findById(id);
    Log.out.info("Servicio Arreglo: Buscando arreglo por ID "+ id);
    return unwrapArreglo(arreglo, id);
  }

  @Override
  public Arreglo getArregloByNombreArreglo(String nombreArreglo) {
    Optional<Arreglo> arreglo = arregloRepository.findByNombreArreglo(nombreArreglo);
    Log.out.info("Servicio Arreglo: Buscando arreglo por nombre "+ nombreArreglo);
    return unwrapArreglo(arreglo, nombreArreglo);
  }

  @Override
  public List<Arreglo> getArreglosByTema(String tema) {
    Log.out.info("Servicio Arreglo: Buscando arreglo por tema "+ tema);
    return arregloRepository.findByTema(tema);
  }

  @Override
  public Arreglo saveArreglo(Arreglo arreglo) {
    Log.out.info("Servicio Arreglo: Guardando arreglo {} ", arreglo);
    return arregloRepository.save(arreglo);
  }

  public Arreglo updateArreglo(Integer id, Arreglo updatedArreglo) {
    Optional<Arreglo> optionalArreglo = arregloRepository.findById(id);

    if (optionalArreglo.isPresent()) {
      Arreglo arreglo = optionalArreglo.get();

      if (updatedArreglo.getNombreArreglo() != null) {
        arreglo.setNombreArreglo(updatedArreglo.getNombreArreglo());
      }

      if (updatedArreglo.getPrecio() != null) {
        arreglo.setPrecio(updatedArreglo.getPrecio());
      }

      if (updatedArreglo.getTema() != null) {
        arreglo.setTema(updatedArreglo.getTema());
      }

      if (updatedArreglo.getTipo() != null) {
        arreglo.setTipo(updatedArreglo.getTipo());
      }

      if (updatedArreglo.getStock() != null) {
        arreglo.setStock(updatedArreglo.getStock());
      }

      if (updatedArreglo.getImagen() != null) {
        arreglo.setImagen(updatedArreglo.getImagen());
      }

      if (updatedArreglo.getTiempoElaboracion() != null) {
        arreglo.setTiempoElaboracion(updatedArreglo.getTiempoElaboracion());
      }

      if (updatedArreglo.getDescripcion() != null) {
        arreglo.setDescripcion(updatedArreglo.getDescripcion());
      }

      if (updatedArreglo.getDulces() != null) {
        arreglo.setDulces(updatedArreglo.getDulces());
      }

      if (updatedArreglo.getMesas() != null) {
        arreglo.setMesas(updatedArreglo.getMesas());
      }

      Log.out.info("Servicio Arreglo: Actualizando arreglo {} ", arreglo);
      return arregloRepository.save(arreglo);
    } else {
      throw new EntityNotFoundException(String.valueOf(id), Arreglo.class);
    }
  }

  @Override
  public void deleteArreglo(Integer id) {
    Log.out.info("Servicio Arreglo: Borrando arreglo con ID "+ id);
    arregloRepository.deleteById(id);
  }

  static Arreglo unwrapArreglo(Optional<Arreglo> entity, Object id) {
    if (entity.isPresent())
      return entity.get();

    else
      throw new EntityNotFoundException(String.valueOf(id), Arreglo.class);
  }
}
