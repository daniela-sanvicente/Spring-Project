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

import com.danielasanvicente.tiendadulces.dto.carrito.AddToCartRequestDTO;
import com.danielasanvicente.tiendadulces.entity.Carrito;
import com.danielasanvicente.tiendadulces.service.CarritoService;
import com.danielasanvicente.tiendadulces.util.RenderPagina;

import lombok.AllArgsConstructor;

@AllArgsConstructor
@Controller
@CrossOrigin
@RequestMapping("/carrito")
public class CarritoController {
  @Autowired
  CarritoService carritoService;

  @GetMapping("/{id}")
  public ResponseEntity<Carrito> getById(@PathVariable Integer id) {
    return new ResponseEntity<>(carritoService.getCarritoById(id), HttpStatus.OK);
  }

  @GetMapping("")
  public ResponseEntity<List<Carrito>> getAll() {
    return new ResponseEntity<>(carritoService.getCarritos(), HttpStatus.OK);
  }

  @PostMapping("")
  public ResponseEntity<HttpStatus> saveCarrito(@Valid @RequestBody Carrito carrito) {
    carritoService.saveCarrito(carrito);

    return new ResponseEntity<>(HttpStatus.CREATED);
  }

  @PostMapping("/{idCarrito}/cliente/{idCliente}/items")
  public ResponseEntity<HttpStatus> addToCart(
    @PathVariable Integer idCarrito,
    @PathVariable Integer idCliente,
    @RequestBody AddToCartRequestDTO request
  ) {
    carritoService.addItemToCart(
        idCarrito,
        idCliente,
        request.getIdDulce(),
        request.getIdArreglo(),
        request.getIdMesa(),
        request.getCantidad()
    );

    return new ResponseEntity<>(HttpStatus.CREATED);
  }

  @PutMapping("/{id}")
  public ResponseEntity<Carrito> updateCarrito(@PathVariable Integer id, @Valid @RequestBody Carrito updatedCarrito) {
    Carrito updated = carritoService.updateCarrito(id, updatedCarrito);

    return ResponseEntity.ok(updated);
  }

  @DeleteMapping("/{id}")
  public ResponseEntity<HttpStatus> deleteCarrito(@PathVariable Integer id) {
    carritoService.deleteCarrito(id);

    return new ResponseEntity<>(HttpStatus.NO_CONTENT);
  }

  
  @GetMapping("alta-carrito")
  public String altaCarrito(Model model){
    Carrito carrito=new Carrito();
    model.addAttribute("contenido","Alta de un Carrito");
    model.addAttribute("carrito",carrito);
    return "carrito/alta-carrito";
  }

  @PostMapping("salvar-carrito")
  public String salvarCarrito(@Valid @ModelAttribute("carrito") Carrito carrito, BindingResult result,
                            Model model, RedirectAttributes flash,
                            SessionStatus sesion){

    if (result.hasErrors()) {
      model.addAttribute("contenido", "Error en la entra de datos");
      return "carrito/alta-carrito";
    }

    carritoService.saveCarrito(carrito);

    flash.addFlashAttribute("success","Carrito se guardo con éxito");

    sesion.setComplete();

    model.addAttribute("success","Carrito se almaceno con éxito");

    return "redirect:/";
  }

  @GetMapping("lista-carrito")
  public String listaCarrito(@RequestParam(name="page",defaultValue = "0")int page,
                           Model model){
    Pageable pagReq= PageRequest.of(page,1);
    Page<Carrito> carritoEntities=carritoService.buscarCarritos(pagReq);
    RenderPagina<Carrito> render=new RenderPagina<>("lista-carrito",carritoEntities);
    model.addAttribute("carrito",carritoEntities);
    model.addAttribute("page",render);
    model.addAttribute("contenido","Lista de Carritos");
    return "carrito/lista-carrito";
  }

  @GetMapping("modificar-carrito/{id}")
  public String saltoModificar(@PathVariable("id") Integer id,Model model){
    Carrito carrito=carritoService.getCarritoById(id);
    model.addAttribute("carrito",carrito);
    model.addAttribute("contenido","Modificar Carrito");
    return "carrito/alta-carrito";
  }
  
  @GetMapping("eliminar-carrito/{id}")
  public String eliminar(@PathVariable("id") Integer id,RedirectAttributes flash){
    carritoService.deleteCarrito(id);
    flash.addFlashAttribute("success","Se borro con éxito Carrito");
    return "redirect:/carrito/lista-carrito";
  }
}
