package com.danielasanvicente.tiendadulces.controller;

import jakarta.validation.Valid;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
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
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.danielasanvicente.tiendadulces.entity.Carrito;
import com.danielasanvicente.tiendadulces.entity.Cliente;
import com.danielasanvicente.tiendadulces.service.CarritoService;
import com.danielasanvicente.tiendadulces.service.ClienteService;
import com.danielasanvicente.tiendadulces.util.RenderPagina;

import lombok.AllArgsConstructor;

@AllArgsConstructor
@Controller
@CrossOrigin
@RequestMapping("/cliente")
public class ClienteController {
  @Autowired
  ClienteService clienteService;

  @Autowired
  CarritoService carritoService;

  @GetMapping("/{id}")
  public ResponseEntity<Cliente> getById(@PathVariable Integer id) {
    return new ResponseEntity<>(clienteService.getClienteById(id), HttpStatus.OK);
  }

  @GetMapping("/correo/{email}")
  public ResponseEntity<Cliente> getByEmail(@PathVariable String email) {
    return new ResponseEntity<>(clienteService.getClienteByEmail(email), HttpStatus.OK);
  }

  @GetMapping("")
  public ResponseEntity<List<Cliente>> getAll() {
    return new ResponseEntity<>(clienteService.getClientes(), HttpStatus.OK);
  }

  // responsable por el login y autenticación
  @PostMapping("/registrar")
  public ResponseEntity<HttpStatus> createCliente(@Valid @RequestBody Cliente cliente) {
    clienteService.saveCliente(cliente);

    return new ResponseEntity<>(HttpStatus.CREATED);
  }

  @PutMapping("/{id}")
  public ResponseEntity<Cliente> updateCliente(@PathVariable Integer id, @Valid @RequestBody Cliente updatedCliente) {
    Cliente updated = clienteService.updateCliente(id, updatedCliente);

    return ResponseEntity.ok(updated);
  }

  @DeleteMapping("/{id}")
  public ResponseEntity<HttpStatus> deleteCliente(@PathVariable Integer id) {
    clienteService.deleteCliente(id);

    return new ResponseEntity<>(HttpStatus.NO_CONTENT);
  } // fin del CRUD

  /* Carrito */

  @GetMapping("/{idCliente}/carrito")
  public ResponseEntity<Carrito> getCarrito(@PathVariable Integer idCliente) {
    return new ResponseEntity<>(carritoService.getCarritoByCliente(idCliente), HttpStatus.OK);
  }

  @PostMapping("/{idCliente}/carrito/crear")
  public ResponseEntity<HttpStatus> createCarrito(@PathVariable Integer idCliente) {
    carritoService.createCarrito(idCliente);

    return new ResponseEntity<>(HttpStatus.CREATED);
  }

  // Spring Web

  @GetMapping("/registrar")
  public String registrarCliente(Model model) {
    Cliente cliente = new Cliente();
    model.addAttribute("contenido", "Crea tu cuenta");
    model.addAttribute("cliente", cliente);
    return "registrar";
  }

  @GetMapping("alta-cliente")
  public String altaCliente(Model model) {
    Cliente cliente = new Cliente();
    model.addAttribute("contenido", "Registro");
    model.addAttribute("cliente", cliente);
    return "cliente/alta-cliente";
  }

  @PostMapping("salvar-cliente")
  public String salvarCliente(@Valid @ModelAttribute("cliente") Cliente cliente,
      Model model, RedirectAttributes flash) {
    System.out.println(cliente);
    flash.addFlashAttribute("success", "Cliente se guardo con éxito");
    clienteService.saveCliente(cliente);
    return "redirect:/";
  }

  @GetMapping("lista-cliente")
  public String listaCliente(@RequestParam(name = "page", defaultValue = "0") int page,
      Model model) {
    Pageable pagReq = PageRequest.of(page, 2);
    Page<Cliente> clienteEntities = clienteService.buscarClientes(pagReq);
    RenderPagina<Cliente> render = new RenderPagina<>("lista-cliente", clienteEntities);
    model.addAttribute("cliente", clienteEntities);
    model.addAttribute("page", render);
    model.addAttribute("contenido", "Lista de Clientes");
    return "cliente/lista-cliente";
  }

  @GetMapping("modificar-cliente/{id}")
  public String saltoModificar(@PathVariable("id") Integer id, Model model) {
    Cliente cliente = clienteService.getClienteById(id);
    model.addAttribute("cliente", cliente);
    model.addAttribute("contenido", "Modificar Cliente");
    return "cliente/alta-cliente";
  }

  @GetMapping("eliminar-cliente/{id}")
  public String eliminar(@PathVariable("id") Integer id, RedirectAttributes flash) {
    clienteService.deleteCliente(id);
    flash.addFlashAttribute("success", "Se borro con éxito Cliente");
    return "redirect:/cliente/lista-cliente";
  }
}
