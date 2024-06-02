package com.danielasanvicente.tiendadulces.controller;

import jakarta.validation.Valid;

import java.util.ArrayList;
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

import com.danielasanvicente.tiendadulces.dto.orden.CompleteOrderRequestDTO;
import com.danielasanvicente.tiendadulces.dto.orden.CreateOrdenRequestDTO;
import com.danielasanvicente.tiendadulces.entity.Cliente;
import com.danielasanvicente.tiendadulces.entity.Orden;
import com.danielasanvicente.tiendadulces.service.ClienteService;
import com.danielasanvicente.tiendadulces.service.OrdenService;
import com.danielasanvicente.tiendadulces.util.RenderPagina;

import lombok.AllArgsConstructor;

@AllArgsConstructor
@Controller
@CrossOrigin
@RequestMapping("/orden")
public class OrdenController {

  @Autowired
  ClienteService clienteService;

  @Autowired
  OrdenService ordenService;

  @GetMapping("/{id}")
  public ResponseEntity<Orden> getById(@PathVariable Integer id) {
    return new ResponseEntity<>(ordenService.getOrdenById(id), HttpStatus.OK);
  }

  @GetMapping("")
  public ResponseEntity<List<Orden>> getAll() {
    return new ResponseEntity<>(ordenService.getOrdenes(), HttpStatus.OK);
  }

  @PostMapping("")
  public ResponseEntity<HttpStatus> saveOrden(@Valid @RequestBody Orden orden) {
    ordenService.saveOrden(orden);

    return new ResponseEntity<>(HttpStatus.CREATED);
  }

  @PostMapping("/crear")
  public ResponseEntity<HttpStatus> createOrden(
      @RequestBody CreateOrdenRequestDTO request) {
    ordenService.createOrden(
        request.getIdCliente(),
        request.getTipoOrden(),
        request.getDireccionEntrega(),
        request.getAnticipo(),
        request.getDescuento(),
        request.getPrecioTotal());

    return new ResponseEntity<>(HttpStatus.CREATED);
  }

  @PostMapping("/realizar")
  public ResponseEntity<HttpStatus> completeOrder(
      @RequestBody CompleteOrderRequestDTO request) {
    ordenService.placeOrder(
        request.getIdCliente(),
        request.getIdMetodoPago(),
        request.getTipoOrden(),
        request.getDireccionEntrega(),
        request.getAnticipo(),
        request.getDescuento(),
        request.getPrecioTotal(),
        request.getEmisor(),
        request.getNumeroCuenta(),
        request.getFechaExpiracion(),
        request.getCvv());

    return new ResponseEntity<>(HttpStatus.CREATED);
  }

  @PutMapping("/{id}")
  public ResponseEntity<Orden> updateOrden(@PathVariable Integer id, @Valid @RequestBody Orden updatedOrden) {
    Orden updated = ordenService.updateOrden(id, updatedOrden);

    return ResponseEntity.ok(updated);
  }

  @DeleteMapping("/{id}")
  public ResponseEntity<HttpStatus> deleteOrden(@PathVariable Integer id) {
    ordenService.deleteOrden(id);

    return new ResponseEntity<>(HttpStatus.NO_CONTENT);
  }

  @GetMapping("alta-orden")
  public String altaOrdenRender(Model model) {
    Orden orden = new Orden();
    List<Cliente> selectCliente = clienteService.getClientes();

    model.addAttribute("contenido", "Alta Orden");
    model.addAttribute("orden", orden);
    model.addAttribute("selectCliente", selectCliente);

    return "orden/alta-orden";
  }

  @PostMapping("salvar-orden")
  public String salvarObjectThymeleaf(@Valid @ModelAttribute("orden") Orden orden, BindingResult result,
      Model model, RedirectAttributes flash,
      SessionStatus sesion) {
    List<Cliente> selectCliente = clienteService.getClientes();

    model.addAttribute("selectCliente", selectCliente);

    if (result.hasErrors()) {
      model.addAttribute("contenido", "Error en la entra de datos");
      return "orden/alta-orden";
    }
    ordenService.saveOrden(orden);
    sesion.setComplete();
    model.addAttribute("success", "Orden se almaceno con éxito");

    return "orden/alta-orden";
  }

  @GetMapping("lista-orden")
  public String listaOrden(@RequestParam(name = "page", defaultValue = "0") int page,
      Model model) {
    Pageable pagReq = PageRequest.of(page, 2);
    Page<Orden> ordenEntities = ordenService.buscarOrdenes(pagReq);
    RenderPagina<Orden> render = new RenderPagina<>("lista-orden", ordenEntities);
    model.addAttribute("orden", ordenEntities);
    model.addAttribute("page", render);
    model.addAttribute("contenido", "Lista de Orden");
    return "orden/lista-orden";
  }

  @GetMapping("modificar-orden/{id}")
  public String saltoModificar(@PathVariable("id") Integer id, Model model) {
    List<Cliente> cliente = clienteService.getClientes();

    Orden orden = ordenService.getOrdenById(id);

    model.addAttribute("selectCliente", cliente);
    model.addAttribute("orden", orden);
    model.addAttribute("contenido", "Modificar Orden");

    return "orden/alta-orden";
  }

  @GetMapping("eliminar-orden/{id}")
  public String saltoEliminar(@PathVariable("id") Integer id, Model model,
      RedirectAttributes flash) {
    ordenService.deleteOrden(id);
    flash.addFlashAttribute("success", "Se borro la Orden");
    return "redirect:/orden/lista-orden";
  }

  @GetMapping(value = "buscar-orden")
  public String buscarOrdenPagina(Model model) {
    Orden entity = new Orden();
    List<Orden> lista = new ArrayList<>();
    model.addAttribute("orden", entity);
    model.addAttribute("datos", lista);
    model.addAttribute("contenido", "Buscar orden por nombre");
    return "orden/buscar-orden";
  }

  @PostMapping("buscar-orden")
  public String buscarReservaconTabla(@Valid @ModelAttribute("orden") Orden orden, BindingResult result, Model model,
      RedirectAttributes flash) {
    Orden ordenB = ordenService.getOrdenById(orden.getId());
    model.addAttribute("datos", ordenB);
    return "orden/buscar-orden";
  }

}
