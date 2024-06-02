package com.danielasanvicente.tiendadulces.service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

import com.danielasanvicente.tiendadulces.util.Log;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.lang.Nullable;
import org.springframework.stereotype.Service;

import com.danielasanvicente.tiendadulces.entity.Carrito;
import com.danielasanvicente.tiendadulces.entity.Cliente;
import com.danielasanvicente.tiendadulces.entity.ItemCarrito;
import com.danielasanvicente.tiendadulces.entity.LineaOrden;
import com.danielasanvicente.tiendadulces.entity.Orden;
import com.danielasanvicente.tiendadulces.entity.Pago;
import com.danielasanvicente.tiendadulces.exception.EntityNotFoundException;
import com.danielasanvicente.tiendadulces.repository.LineaOrdenRepository;
import com.danielasanvicente.tiendadulces.repository.OrdenRepository;
import com.danielasanvicente.tiendadulces.util.OrderConstants;

import jakarta.transaction.Transactional;
import lombok.AllArgsConstructor;

@Service
@Transactional
@AllArgsConstructor
public class OrdenServiceImpl implements OrdenService {
  @Autowired
  private ClienteService clienteService;

  @Autowired
  private PagoService pagoService;

  @Autowired
  private CarritoService carritoService;

  @Autowired
  private LineaOrdenRepository lineaOrdenRepository;

  @Autowired
  private OrdenRepository ordenRepository;

  @Override
  public Page<Orden> buscarOrdenes(Pageable pageable) {
      Log.out.info("Servicio Orden: Buscando todos los ordens");
      return ordenRepository.findAll(pageable);
  }

  @Override
  public List<Orden> getOrdenes() {
    Log.out.info("Servicio Orden: Buscando todos los ordens");
    return (List<Orden>) ordenRepository.findAll();
  }

  @Override
  public Orden getOrdenById(Integer id) {
    Optional<Orden> orden = ordenRepository.findById(id);
    Log.out.info("Servicio Orden: Buscando orden por ID "+ id);
    return unwrapOrden(orden, id);
  }

  @Override
  public List<Orden> getOrdenByCliente(Integer idCliente) {
    Cliente cliente = clienteService.getClienteById(idCliente);
    Log.out.info("Servicio Orden: Buscando orden por nombre "+ idCliente);
    return ordenRepository.findByCliente(cliente);
  }

  @Override
  public Orden saveOrden(Orden orden) {
    Log.out.info("Servicio Orden: Guardando orden {} ", orden);
    return ordenRepository.save(orden);
  }

  @Override
  public Orden createOrden(
    Integer idCliente, 
    String tipoOrden, 
    String direccionEntrega, 
    @Nullable Double anticipo,
    @Nullable Double descuento,
    Double precioTotal
  ) {
    Cliente cliente = clienteService.getClienteById(idCliente);

    Orden orden = new Orden();

    orden.setCliente(cliente);
    orden.setTipoOrden(tipoOrden);
    orden.setDireccionEntrega(direccionEntrega);

    if (anticipo != null) {
      precioTotal -= anticipo;
    }

    if (descuento != null) {
      precioTotal -= descuento;
    }

    orden.setPrecioTotal(precioTotal);
  
    orden.setMomentoPedido(LocalDateTime.now());

    orden.setEstatus(OrderConstants.PROCESSING);

    return ordenRepository.save(orden);
  }

  @Override
  public Orden placeOrder(
    Integer idCliente, 
    Integer idMetodoPago, 
    String tipoOrden, 
    String direccionEntrega, 
    @Nullable Double anticipo,
    @Nullable Double descuento,
    Double precioTotal, 
    String emisor, 
    String numeroCuenta, 
    String fechaExpiracion, 
    String cvv
  ) {
    Orden orden = this.createOrden(
      idCliente, 
      tipoOrden, 
      direccionEntrega, 
      anticipo, 
      descuento, 
      precioTotal
    );

    Pago pago = pagoService.createPago(
      idCliente, 
      orden.getId(), 
      idMetodoPago, 
      emisor, 
      numeroCuenta, 
      fechaExpiracion, 
      cvv
    );

    orden.setPago(pago);
    orden.setEstatus(OrderConstants.PLACED);

    ordenRepository.save(orden);

    Cliente cliente = clienteService.getClienteById(idCliente);

    Carrito carrito = cliente.getCarrito();

    List<ItemCarrito> items = carritoService.getItemsFromCart(carrito);

    for (ItemCarrito itemCarrito : items) {
        LineaOrden lineaOrden = new LineaOrden();

        lineaOrden.setOrden(orden);
        lineaOrden.setDulce(itemCarrito.getDulce());
        lineaOrden.setArreglo(itemCarrito.getArreglo());
        lineaOrden.setMesa(itemCarrito.getMesa());
        lineaOrden.setCantidad(itemCarrito.getCantidad());

        if (itemCarrito.getDulce() != null) {
            lineaOrden.setPrecio(
              itemCarrito.getDulce().getPrecio() * itemCarrito.getCantidad()
            );
        } 
        
        else if (itemCarrito.getArreglo() != null) {
            lineaOrden.setPrecio(
              itemCarrito.getArreglo().getPrecio() * itemCarrito.getCantidad()
            );
        } 
        
        else if (itemCarrito.getMesa() != null) {
            lineaOrden.setPrecio(
              itemCarrito.getMesa().getPrecio() * itemCarrito.getCantidad()
            );
        }

        lineaOrdenRepository.save(lineaOrden);
    }

    carrito.getItems().clear();
    
    carritoService.saveCarrito(carrito);

    return orden;
  }

  public Orden updateOrden(Integer id, Orden updatedOrden) {
    Optional<Orden> optionalOrden = ordenRepository.findById(id);

    if (optionalOrden.isPresent()) {
      Orden orden = optionalOrden.get();

      if (updatedOrden.getCliente() != null) {
        orden.setCliente(updatedOrden.getCliente());
      }

      if (updatedOrden.getPago() != null) {
        orden.setPago(updatedOrden.getPago());
      }

      if (updatedOrden.getTipoOrden() != null) {
        orden.setTipoOrden(updatedOrden.getTipoOrden());
      }

      if (updatedOrden.getEstatus() != null) {
        orden.setEstatus(updatedOrden.getEstatus());
      }

      if (updatedOrden.getDireccionEntrega() != null) {
        orden.setDireccionEntrega(updatedOrden.getDireccionEntrega());
      }

      if (updatedOrden.getAnticipo() != null) {
        orden.setAnticipo(updatedOrden.getAnticipo());
      }

      if (updatedOrden.getMomentoPedido() != null) {
        orden.setMomentoPedido(updatedOrden.getMomentoPedido());
      }

      if (updatedOrden.getMomentoEntrega() != null) {
        orden.setMomentoEntrega(updatedOrden.getMomentoEntrega());
      }

      if (updatedOrden.getDescuento() != null) {
        orden.setDescuento(updatedOrden.getDescuento());
      }

      if (updatedOrden.getPrecioTotal() != null) {
        orden.setPrecioTotal(updatedOrden.getPrecioTotal());
      }

      Log.out.info("Servicio Orden: Actualizando orden {} ", orden);
      return ordenRepository.save(orden);
    } else {
      throw new EntityNotFoundException(String.valueOf(id), Orden.class);
    }
  }

  @Override
  public void deleteOrden(Integer id) {
    Log.out.info("Servicio Orden: Borrando orden con ID "+ id);
    ordenRepository.deleteById(id);
  }

  static Orden unwrapOrden(Optional<Orden> entity, Object id) {
    if (entity.isPresent())
      return entity.get();

    else
      throw new EntityNotFoundException(String.valueOf(id), Orden.class);
  }
}
