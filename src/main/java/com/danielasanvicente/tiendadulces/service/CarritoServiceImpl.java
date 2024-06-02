package com.danielasanvicente.tiendadulces.service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import com.danielasanvicente.tiendadulces.util.Log;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.lang.Nullable;
import org.springframework.stereotype.Service;

import com.danielasanvicente.tiendadulces.entity.Cliente;
import com.danielasanvicente.tiendadulces.entity.Dulce;
import com.danielasanvicente.tiendadulces.entity.ItemCarrito;
import com.danielasanvicente.tiendadulces.entity.Mesa;
import com.danielasanvicente.tiendadulces.entity.Arreglo;
import com.danielasanvicente.tiendadulces.entity.Carrito;
import com.danielasanvicente.tiendadulces.exception.EntityNotFoundException;
import com.danielasanvicente.tiendadulces.repository.ArregloRepository;
import com.danielasanvicente.tiendadulces.repository.CarritoRepository;
import com.danielasanvicente.tiendadulces.repository.DulceRepository;
import com.danielasanvicente.tiendadulces.repository.MesaRepository;

import jakarta.transaction.Transactional;
import lombok.AllArgsConstructor;

@Service
@Transactional
@AllArgsConstructor
public class CarritoServiceImpl implements CarritoService {
  @Autowired
  private ClienteService clienteService;

  @Autowired
  private DulceRepository dulceRepository;

  @Autowired
  private ArregloRepository arregloRepository;

  @Autowired
  private MesaRepository mesaRepository;

  @Autowired
  private CarritoRepository carritoRepository;

  @Override
    public Page<Carrito> buscarCarritos(Pageable pageable) {
        Log.out.info("Servicio Carrito: Buscando todos los carritos");
        return carritoRepository.findAll(pageable);
    }

  @Override
  public List<Carrito> getCarritos() {
    Log.out.info("Servicio Carrito: Buscando todos los carritos");
    return (List<Carrito>) carritoRepository.findAll();
  }

  @Override
  public Carrito getCarritoById(Integer id) {
    Optional<Carrito> carrito = carritoRepository.findById(id);

    Log.out.info("Servicio Carrito: Buscando carrito por ID "+ id);
    return unwrapCarrito(carrito, id);
  }

  @Override
  public Carrito getCarritoByCliente(Integer idCliente) {
    Cliente cliente = clienteService.getClienteById(idCliente);

    Optional<Carrito> carrito = carritoRepository.findByCliente(cliente);

    Log.out.info("Servicio Carrito: Buscando carrito por cliente "+ idCliente);
    return unwrapCarrito(carrito, null);
  }

  @Override
  public Carrito saveCarrito(Carrito carrito) {
    Log.out.info("Servicio Carrito: Guardando carrito {} ", carrito);
    return carritoRepository.save(carrito);
  }

  @Override
  public Carrito createCarrito(Integer idCliente) {
    Carrito carrito = new Carrito();

    Cliente cliente = clienteService.getClienteById(idCliente);

    carrito.setCliente(cliente);

    Log.out.info("Servicio Carrito: Creando carrito {} ", carrito);
    return carritoRepository.save(carrito);
  }

  @Override
  public void addItemToCart(
    Integer idCarrito, 
    Integer idCliente,
    @Nullable Integer idDulce, 
    @Nullable Integer idArreglo,
    @Nullable Integer idMesa,  
    Integer cantidad
  ) {
    ItemCarrito itemCarrito = new ItemCarrito();

    Carrito carrito = this.getCarritoById(idCarrito);
    Cliente cliente = clienteService.getClienteById(idCliente);

    itemCarrito.setCarrito(carrito);

    carrito.setCliente(cliente);

    if (idDulce != null) {
      Optional<Dulce> dulce = dulceRepository.findById(idDulce);
      
      if (dulce.isPresent()) itemCarrito.setDulce(dulce.get());
    }

    if (idArreglo != null) {
      Optional<Arreglo> arreglo = arregloRepository.findById(idArreglo);

      if (arreglo.isPresent()) itemCarrito.setArreglo(arreglo.get());
    }

    if (idMesa != null) {
      Optional<Mesa> mesa = mesaRepository.findById(idMesa);

      if (mesa.isPresent()) itemCarrito.setMesa(mesa.get());
    }

    itemCarrito.setCantidad(cantidad);

    carrito.addItem(itemCarrito);

    carritoRepository.save(carrito);
  }

  public List<ItemCarrito> getItemsFromCart(Carrito carrito) {
    return new ArrayList<>(carrito.getItems());
  }

  public Carrito updateCarrito(Integer id, Carrito updatedCarrito) {
    Optional<Carrito> optionalCarrito = carritoRepository.findById(id);

    if (optionalCarrito.isPresent()) {
      Carrito carrito = optionalCarrito.get();

      if (updatedCarrito.getCliente() != null) {
        carrito.setCliente(updatedCarrito.getCliente());
      }

      if (updatedCarrito.getItems() != null) {
        carrito.setItems(updatedCarrito.getItems());
      }

      Log.out.info("Servicio Carrito: Actualizando carrito {} ", carrito);
      return carritoRepository.save(carrito);
    } else {
      throw new EntityNotFoundException(String.valueOf(id), Carrito.class);
    }
  }

  @Override
  public void deleteCarrito(Integer id) {
    Log.out.info("Servicio Carrito: Borrando carrito con ID "+ id);
    carritoRepository.deleteById(id);
  }

  static Carrito unwrapCarrito(Optional<Carrito> entity, Object id) {
    if (entity.isPresent())
      return entity.get();

    else
      throw new EntityNotFoundException(String.valueOf(id), Carrito.class);
  }
}
