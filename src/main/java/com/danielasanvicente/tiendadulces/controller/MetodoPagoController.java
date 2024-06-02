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
import org.springframework.data.domain.Pageable;

import com.danielasanvicente.tiendadulces.entity.MetodoPago;
import com.danielasanvicente.tiendadulces.service.MetodoPagoService;
import com.danielasanvicente.tiendadulces.util.RenderPagina;

import lombok.AllArgsConstructor;

@AllArgsConstructor
@Controller
@CrossOrigin
@RequestMapping("/metodoPago")
public class MetodoPagoController {
  @Autowired
  MetodoPagoService metodoPagoService;

  @GetMapping("/{id}")
  public ResponseEntity<MetodoPago> getById(@PathVariable Integer id) {
    return new ResponseEntity<>(metodoPagoService.getMetodoPagoById(id), HttpStatus.OK);
  }

  @GetMapping("/{metodo}")
  public ResponseEntity<MetodoPago> getById(@PathVariable String metodo) {
    return new ResponseEntity<>(metodoPagoService.getMetodoPagoByMetodo(metodo), HttpStatus.OK);
  }

  @GetMapping("")
  public ResponseEntity<List<MetodoPago>> getAll() {
    return new ResponseEntity<>(metodoPagoService.getMetodoPagos(), HttpStatus.OK);
  }

  @PostMapping("")
  public ResponseEntity<HttpStatus> createMetodoPago(@Valid @RequestBody MetodoPago metodoPago) {
    metodoPagoService.saveMetodoPago(metodoPago);

    return new ResponseEntity<>(HttpStatus.CREATED);
  }

  @PutMapping("/{id}")
  public ResponseEntity<MetodoPago> updateMetodoPago(
    @PathVariable Integer id,
    @RequestBody MetodoPago updatedMetodoPago
  ) {
    MetodoPago updated = metodoPagoService.updateMetodoPago(id, updatedMetodoPago);

    return ResponseEntity.ok(updated);
  }

  @DeleteMapping("/{id}")
  public ResponseEntity<HttpStatus> deleteMetodoPago(@PathVariable Integer id) {
    metodoPagoService.deleteMetodoPago(id);

    return new ResponseEntity<>(HttpStatus.NO_CONTENT);
  }
  
    @GetMapping("alta-metodoPago")
    public String altaMetodoPago(Model model){
        MetodoPago metodoPago=new MetodoPago();
        model.addAttribute("contenido","Alta de un MetodoPago");
        model.addAttribute("metodoPago",metodoPago);
        return "metodoPago/alta-metodoPago";
    }
    @PostMapping("salvar-metodoPago")
    public String salvarMetodoPago(@Valid @ModelAttribute("metodoPago") MetodoPago metodoPago,
                              Model model, RedirectAttributes flash){
        System.out.println(metodoPago);
        flash.addFlashAttribute("success","MetodoPago se guardo con éxito");
        metodoPagoService.saveMetodoPago(metodoPago);
        return "redirect:/";
    }

    @GetMapping("lista-metodoPago")
    public String listaMetodoPago(@RequestParam(name="page",defaultValue = "0")int page,
                             Model model){
        Pageable pagReq= PageRequest.of(page,2);
        Page<MetodoPago> metodoPagoEntities=metodoPagoService.buscarMetodosPagos(pagReq);
        RenderPagina<MetodoPago> render=new RenderPagina<>("lista-metodoPago",metodoPagoEntities);
        model.addAttribute("metodoPago",metodoPagoEntities);
        model.addAttribute("page",render);
        model.addAttribute("contenido","Lista de MetodoPagos");
        return "metodoPago/lista-metodoPago";
    }

    @GetMapping("modificar-metodoPago/{id}")
    public String saltoModificar(@PathVariable("id") Integer id,Model model){
        MetodoPago metodoPago=metodoPagoService.getMetodoPagoById(id);
        model.addAttribute("metodoPago",metodoPago);
        model.addAttribute("contenido","Modificar MetodoPago");
        return "metodoPago/alta-metodoPago";
    }
    @GetMapping("eliminar-metodoPago/{id}")
    public String eliminar(@PathVariable("id") Integer id,RedirectAttributes flash){
        metodoPagoService.deleteMetodoPago(id);
        flash.addFlashAttribute("success","Se borro con éxito MetodoPago");
        return "redirect:/metodoPago/lista-metodoPago";
    }
}
