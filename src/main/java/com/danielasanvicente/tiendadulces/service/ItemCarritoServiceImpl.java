package com.danielasanvicente.tiendadulces.service;

import java.util.List;
import java.util.Optional;

import com.danielasanvicente.tiendadulces.util.Log;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import com.danielasanvicente.tiendadulces.entity.Carrito;
import com.danielasanvicente.tiendadulces.entity.ItemCarrito;
import com.danielasanvicente.tiendadulces.exception.EntityNotFoundException;
import com.danielasanvicente.tiendadulces.repository.ItemCarritoRepository;

import jakarta.transaction.Transactional;
import lombok.AllArgsConstructor;

@Service
@Transactional
@AllArgsConstructor
public class ItemCarritoServiceImpl implements ItemCarritoService {
  @Autowired
  private ItemCarritoRepository itemCarritoRepository;

  @Autowired
  private CarritoService carritoService;

  @Override
  public Page<ItemCarrito> buscarItemCarritos(Pageable pageable) {
      Log.out.info("Servicio ItemCarrito: Buscando todos los items de carrito");
      return itemCarritoRepository.findAll(pageable);
  }

  @Override
  public List<ItemCarrito> getItemCarritos() {
    Log.out.info("Servicio ItemCarrito: Buscando todos los items de carrito");
    return (List<ItemCarrito>) itemCarritoRepository.findAll();
  }

  @Override
  public ItemCarrito getItemCarritoById(Integer id) {
    Optional<ItemCarrito> itemCarrito = itemCarritoRepository.findById(id);

    Log.out.info("Servicio ItemCarrito: Buscando item de carrito por ID "+ id);
    return unwrapItemCarrito(itemCarrito, id);
  }

  @Override
  public List<ItemCarrito> getItemCarritoByCarrito(Integer idCarrito) {
    Carrito carrito = carritoService.getCarritoById(idCarrito);

    Log.out.info("Servicio ItemCarrito: Buscando item de carrito por nombre "+ idCarrito);
    return itemCarritoRepository.findByCarrito(carrito);
  }

  @Override
  public ItemCarrito saveItemCarrito(ItemCarrito itemCarrito) {
    Log.out.info("Servicio ItemCarrito: Guardando item de carrito {} ", itemCarrito);
    return itemCarritoRepository.save(itemCarrito);
  }

  public ItemCarrito updateItemCarrito(Integer id, ItemCarrito updatedItemCarrito) {
    Optional<ItemCarrito> optionalItemCarrito = itemCarritoRepository.findById(id);

    if (optionalItemCarrito.isPresent()) {
      ItemCarrito itemCarrito = optionalItemCarrito.get();

      if (updatedItemCarrito.getCarrito() != null) {
        itemCarrito.setCarrito(updatedItemCarrito.getCarrito());
      }

      if (updatedItemCarrito.getDulce() != null) {
        itemCarrito.setDulce(updatedItemCarrito.getDulce());
      }

      if (updatedItemCarrito.getArreglo() != null) {
        itemCarrito.setArreglo(updatedItemCarrito.getArreglo());
      }

      if (updatedItemCarrito.getMesa() != null) {
        itemCarrito.setMesa(updatedItemCarrito.getMesa());
      }

      if (updatedItemCarrito.getCantidad() != null) {
        itemCarrito.setCantidad(updatedItemCarrito.getCantidad());
      }

      Log.out.info("Servicio ItemCarrito: Actualizando item de carrito {} ", itemCarrito);
      return itemCarritoRepository.save(itemCarrito);
    } else {
      throw new EntityNotFoundException(String.valueOf(id), ItemCarrito.class);
    }
  }

  @Override
  public void deleteItemCarrito(Integer id) {
    Log.out.info("Servicio ItemCarrito: Borrando item de carrito con ID "+ id);
    itemCarritoRepository.deleteById(id);
  }

  static ItemCarrito unwrapItemCarrito(Optional<ItemCarrito> entity, Object id) {
    if (entity.isPresent())
      return entity.get();

    else
      throw new EntityNotFoundException(String.valueOf(id), ItemCarrito.class);
  }
}
