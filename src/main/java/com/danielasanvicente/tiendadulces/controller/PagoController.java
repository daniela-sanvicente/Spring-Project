package com.danielasanvicente.tiendadulces.controller;

import jakarta.validation.Valid;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.support.SessionStatus;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import org.springframework.data.domain.Pageable;

import com.danielasanvicente.tiendadulces.dto.pago.CreatePagoRequestDTO;
import com.danielasanvicente.tiendadulces.entity.Cliente;
import com.danielasanvicente.tiendadulces.entity.MetodoPago;
import com.danielasanvicente.tiendadulces.entity.Orden;
import com.danielasanvicente.tiendadulces.entity.Pago;
import com.danielasanvicente.tiendadulces.service.ClienteService;
import com.danielasanvicente.tiendadulces.service.MetodoPagoService;
import com.danielasanvicente.tiendadulces.service.OrdenService;
import com.danielasanvicente.tiendadulces.service.PagoService;
import com.danielasanvicente.tiendadulces.util.RenderPagina;

import lombok.AllArgsConstructor;

@AllArgsConstructor
@Controller
@CrossOrigin
@RequestMapping("/pago")
public class PagoController {
  @Autowired
  PagoService pagoService;

  @Autowired
  MetodoPagoService metodoPagoService;

  @Autowired
  ClienteService clienteService;

  @Autowired
  OrdenService ordenService;

  @GetMapping("/{id}")
  public ResponseEntity<Pago> getById(@PathVariable Integer id) {
    return new ResponseEntity<>(pagoService.getPagoById(id), HttpStatus.OK);
  }

  @GetMapping("")
  public ResponseEntity<List<Pago>> getAll() {
    return new ResponseEntity<>(pagoService.getPagos(), HttpStatus.OK);
  }

  @PostMapping("")
  public ResponseEntity<HttpStatus> savePago(@Valid @RequestBody Pago pago) {
    pagoService.savePago(pago);

    return new ResponseEntity<>(HttpStatus.CREATED);
  }

  @PostMapping("/crear")
  public ResponseEntity<HttpStatus> createPago(
      @RequestBody CreatePagoRequestDTO request) {
    pagoService.createPago(
        request.getIdCliente(),
        request.getIdOrden(),
        request.getIdMetodoPago(),
        request.getEmisor(),
        request.getNumeroCuenta(),
        request.getFechaExpiracion(),
        request.getCvv());

    return new ResponseEntity<>(HttpStatus.CREATED);
  }

  @PutMapping("/{id}")
  public ResponseEntity<Pago> updatePago(@PathVariable Integer id, @Valid @RequestBody Pago updatedPago) {
    Pago updated = pagoService.updatePago(id, updatedPago);

    return ResponseEntity.ok(updated);
  }

  @DeleteMapping("/{id}")
  public ResponseEntity<HttpStatus> deletePago(@PathVariable Integer id) {
    pagoService.deletePago(id);

    return new ResponseEntity<>(HttpStatus.NO_CONTENT);
  }

  @GetMapping("alta-pago")
  public String altaPago(Model model) {
    Pago pago = new Pago();

    List<Cliente> selectCliente = clienteService.getClientes();
    List<MetodoPago> selectMetodoPago = metodoPagoService.getMetodoPagos();
    List<Orden> selectOrden = ordenService.getOrdenes();

    model.addAttribute("contenido", "Alta de un Pago");
    model.addAttribute("pago", pago);
    model.addAttribute("selectCliente", selectCliente);
    model.addAttribute("selectOrden", selectOrden);
    model.addAttribute("selectMetodoPago", selectMetodoPago);

    return "pago/alta-pago";
  }

  @PostMapping("salvar-pago")
  public String salvarObjectThymeleaf(@Valid @ModelAttribute("pago") Pago pago, BindingResult result,
      Model model, RedirectAttributes flash,
      SessionStatus sesion) {
    List<Cliente> selectCliente = clienteService.getClientes();
    model.addAttribute("selectCliente", selectCliente);

    List<MetodoPago> selectMetodoPago = metodoPagoService.getMetodoPagos();
    model.addAttribute("selectMetodoPago", selectMetodoPago);

    List<Orden> selectOrden = ordenService.getOrdenes();
    model.addAttribute("selectOrden", selectOrden);

    if (result.hasErrors()) {
      model.addAttribute("contenido", "Error en la entra de datos");
      return "pago/alta-pago";
    }
    pagoService.savePago(pago);
    sesion.setComplete();
    model.addAttribute("success", "Pago se almaceno con éxito");

    return "pago/alta-pago";
  }


  @GetMapping("lista-pago")
  public String listaPago(@RequestParam(name = "page", defaultValue = "0") int page,
      Model model) {
    Pageable pagReq = PageRequest.of(page, 2);
    Page<Pago> pagoEntities = pagoService.buscarPagos(pagReq);
    RenderPagina<Pago> render = new RenderPagina<>("lista-pago", pagoEntities);
    model.addAttribute("pago", pagoEntities);
    model.addAttribute("page", render);
    model.addAttribute("contenido", "Lista de Pagos");
    return "pago/lista-pago";
  }

  @GetMapping("modificar-pago/{id}")
  public String saltoModificar(@PathVariable("id") Integer id, Model model) {
    Pago pago = pagoService.getPagoById(id);
    model.addAttribute("pago", pago);
    model.addAttribute("contenido", "Modificar Pago");

    List<Cliente> selectCliente = clienteService.getClientes();
    model.addAttribute("selectCliente", selectCliente);

    List<MetodoPago> selectMetodoPago = metodoPagoService.getMetodoPagos();
    model.addAttribute("selectMetodoPago", selectMetodoPago);

    List<Orden> selectOrden = ordenService.getOrdenes();
    model.addAttribute("selectOrden", selectOrden);

    return "pago/alta-pago";
  }

  @GetMapping("eliminar-pago/{id}")
  public String eliminar(@PathVariable("id") Integer id, RedirectAttributes flash) {
    pagoService.deletePago(id);
    flash.addFlashAttribute("success", "Se borro con éxito Pago");
    return "redirect:/pago/lista-pago";
  }
}
