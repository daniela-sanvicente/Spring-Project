package com.danielasanvicente.tiendadulces.service;

import java.util.List;
import java.util.Optional;

import com.danielasanvicente.tiendadulces.util.Log;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.lang.Nullable;
import org.springframework.stereotype.Service;

import com.danielasanvicente.tiendadulces.entity.Cliente;
import com.danielasanvicente.tiendadulces.entity.MetodoPago;
import com.danielasanvicente.tiendadulces.entity.Orden;
import com.danielasanvicente.tiendadulces.entity.Pago;
import com.danielasanvicente.tiendadulces.exception.EntityNotFoundException;
import com.danielasanvicente.tiendadulces.repository.OrdenRepository;
import com.danielasanvicente.tiendadulces.repository.PagoRepository;

import jakarta.transaction.Transactional;
import lombok.AllArgsConstructor;

@Service
@Transactional
@AllArgsConstructor
public class PagoServiceImpl implements PagoService {
  @Autowired
  private ClienteService clienteService;

  @Autowired
  private MetodoPagoService metodoPagoService;

  @Autowired
  private OrdenRepository ordenRepository;

  @Autowired
  private PagoRepository pagoRepository;

  @Override
  public Page<Pago> buscarPagos(Pageable pageable) {
      Log.out.info("Servicio Pago: Buscando todos los pagos");
      return pagoRepository.findAll(pageable);
  }

  @Override
  public List<Pago> getPagos() {
    Log.out.info("Servicio Pago: Buscando todos los pagos");
    return (List<Pago>) pagoRepository.findAll();
  }

  @Override
  public Pago getPagoById(Integer id) {
    Optional<Pago> pago = pagoRepository.findById(id);
    Log.out.info("Servicio Pago: Buscando pago por ID "+ id);
    return unwrapPago(pago, id);
  }

  @Override
  public List<Pago> getPagoByCliente(Integer idCliente) {
    Cliente cliente = clienteService.getClienteById(idCliente);
    Log.out.info("Servicio Pago: Buscando pago por nombre "+ idCliente);
    return pagoRepository.findByCliente(cliente);
  }

  @Override
  public Pago savePago(Pago pago) {
    Log.out.info("Servicio Pago: Guardando pago {} ", pago);
    return pagoRepository.save(pago);
  }

  @Override
  public Pago createPago(
    Integer idCliente,
    @Nullable Integer idOrden,
    Integer idMetodoPago,
    String emisor,
    String numeroCuenta,
    String fechaExpiracion,
    String cvv
  ) {
    Cliente cliente = clienteService.getClienteById(idCliente);

    MetodoPago metodoPago = metodoPagoService.getMetodoPagoById(idMetodoPago);

    Pago pago = new Pago();

    if (idOrden != null) {
      Optional<Orden> orden = ordenRepository.findById(idOrden);
      
      if (orden.isPresent()) 
        pago.setOrden(orden.get());
    }

    pago.setCliente(cliente);
    pago.setMetodoPago(metodoPago);
    pago.setEmisor(emisor);
    pago.setNumeroCuenta(numeroCuenta);
    pago.setFechaExpiracion(fechaExpiracion);
    pago.setCvv(cvv);

    return pagoRepository.save(pago);
  }

  @Override
  public Pago updatePago(Integer id, Pago updatedPago) {
    Optional<Pago> optionalPago = pagoRepository.findById(id);

    if (optionalPago.isPresent()) {
      Pago pago = optionalPago.get();

      if (updatedPago.getCliente() != null) {
        pago.setCliente(updatedPago.getCliente());
      }

      if (updatedPago.getOrden() != null) {
        pago.setOrden(updatedPago.getOrden());
      }

      if (updatedPago.getMetodoPago() != null) {
        pago.setMetodoPago(updatedPago.getMetodoPago());
      }

      if (updatedPago.getEmisor() != null) {
        pago.setEmisor(updatedPago.getEmisor());
      }

      if (updatedPago.getNumeroCuenta() != null) {
        pago.setNumeroCuenta(updatedPago.getNumeroCuenta());
      }

      if (updatedPago.getFechaExpiracion() != null) {
        pago.setFechaExpiracion(updatedPago.getFechaExpiracion());
      }

      if (updatedPago.getCvv() != null) {
        pago.setCvv(updatedPago.getCvv());
      }

      Log.out.info("Servicio Pago: Actualizando pago {} ", pago);
      return pagoRepository.save(pago);
    } else {
      throw new EntityNotFoundException(String.valueOf(id), Pago.class);
    }
  }

  @Override
  public void deletePago(Integer id) {
    Log.out.info("Servicio Pago: Borrando pago con ID "+ id);
    pagoRepository.deleteById(id);
  }

  static Pago unwrapPago(Optional<Pago> entity, Object id) {
    if (entity.isPresent())
      return entity.get();

    else
      throw new EntityNotFoundException(String.valueOf(id), Pago.class);
  }
}
