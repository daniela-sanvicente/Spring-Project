package com.danielasanvicente.tiendadulces.controller;

import com.danielasanvicente.tiendadulces.util.RenderPagina;
import jakarta.validation.Valid;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.data.domain.Pageable;

import com.danielasanvicente.tiendadulces.entity.Arreglo;
import com.danielasanvicente.tiendadulces.service.ArregloService;

import lombok.AllArgsConstructor;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@AllArgsConstructor
@Controller
@CrossOrigin
@RequestMapping("/arreglo")
public class ArregloController {
  @Autowired
  ArregloService arregloService;

  @GetMapping("/{id}")
  public ResponseEntity<Arreglo> getById(@PathVariable Integer id) {
    return new ResponseEntity<>(arregloService.getArregloById(id), HttpStatus.OK);
  }

  @GetMapping("")
  public ResponseEntity<List<Arreglo>> getAll() {
    return new ResponseEntity<>(arregloService.getArreglos(), HttpStatus.OK);
  }

  @PostMapping("")
  public ResponseEntity<HttpStatus> createArreglo(@Valid @RequestBody Arreglo arreglo) {
    arregloService.saveArreglo(arreglo);

    return new ResponseEntity<>(HttpStatus.CREATED);
  }

  @PutMapping("/{id}")
  public ResponseEntity<Arreglo> updateArreglo(@PathVariable Integer id, @Valid @RequestBody Arreglo updatedArreglo) {
    Arreglo updated = arregloService.updateArreglo(id, updatedArreglo);

    return ResponseEntity.ok(updated);
  }

  @DeleteMapping("/{id}")
  public ResponseEntity<HttpStatus> deleteArreglo(@PathVariable Integer id) {
    arregloService.deleteArreglo(id);

    return new ResponseEntity<>(HttpStatus.NO_CONTENT);
  }

  @GetMapping("alta-arreglo")
  public String altaArreglo(Model model){
    Arreglo arreglo=new Arreglo();
    model.addAttribute("contenido","Alta de un Arreglo");
    model.addAttribute("arreglo",arreglo);
    return "arreglo/alta-arreglo";
  }
  @PostMapping("salvar-arreglo")
  public String salvarArreglo(@Valid @ModelAttribute("arreglo") Arreglo arreglo,
                              Model model, RedirectAttributes flash){
    System.out.println(arreglo);
    flash.addFlashAttribute("success","Arreglo se guardo con éxito");
    arregloService.saveArreglo(arreglo);
    return "redirect:/";
  }

  @GetMapping("lista-arreglo")
  public String listaArreglo(@RequestParam(name="page",defaultValue = "0")int page,
                             Model model){
    Pageable pagReq= PageRequest.of(page,2);
    Page<Arreglo> arregloEntities=arregloService.buscarArreglos(pagReq);
    RenderPagina<Arreglo> render=new RenderPagina<>("lista-arreglo",arregloEntities);
    model.addAttribute("arreglo",arregloEntities);
    model.addAttribute("page",render);
    model.addAttribute("contenido","Lista de Arreglos");
    return "arreglo/lista-arreglo";
  }

  @GetMapping("modificar-arreglo/{id}")
  public String saltoModificar(@PathVariable("id") Integer id,Model model){
    Arreglo arreglo=arregloService.getArregloById(id);
    model.addAttribute("arreglo",arreglo);
    model.addAttribute("contenido","Modificar Arreglo");
    return "arreglo/alta-arreglo";
  }
  @GetMapping("eliminar-arreglo/{id}")
  public String eliminar(@PathVariable("id") Integer id,RedirectAttributes flash){
    arregloService.deleteArreglo(id);
    flash.addFlashAttribute("success","Se borro con éxito Arreglo");
    return "redirect:/arreglo/lista-arreglo";
  }

}
