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

import com.danielasanvicente.tiendadulces.entity.Mesa;
import com.danielasanvicente.tiendadulces.service.MesaService;

import lombok.AllArgsConstructor;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@AllArgsConstructor
@Controller
@CrossOrigin
@RequestMapping("/mesa")
public class MesaController {
  @Autowired
  MesaService mesaService;

  @GetMapping("/{id}")
  public ResponseEntity<Mesa> getById(@PathVariable Integer id) {
    return new ResponseEntity<>(mesaService.getMesaById(id), HttpStatus.OK);
  }

  @GetMapping("")
  public ResponseEntity<List<Mesa>> getAll() {
    return new ResponseEntity<>(mesaService.getMesas(), HttpStatus.OK);
  }

  @PostMapping("")
  public ResponseEntity<HttpStatus> createMesa(@Valid @RequestBody Mesa mesa) {
    mesaService.saveMesa(mesa);

    return new ResponseEntity<>(HttpStatus.CREATED);
  }

  @PutMapping("/{id}")
  public ResponseEntity<Mesa> updateMesa(@PathVariable Integer id, @Valid @RequestBody Mesa updatedMesa) {
    Mesa updated = mesaService.updateMesa(id, updatedMesa);

    return ResponseEntity.ok(updated);
  }

  @DeleteMapping("/{id}")
  public ResponseEntity<HttpStatus> deleteMesa(@PathVariable Integer id) {
    mesaService.deleteMesa(id);

    return new ResponseEntity<>(HttpStatus.NO_CONTENT);
  }

  @GetMapping("alta-mesa")
  public String altaMesa(Model model){
    Mesa mesa=new Mesa();
    model.addAttribute("contenido","Alta de un Mesa");
    model.addAttribute("mesa",mesa);
    return "mesa/alta-mesa";
  }
  @PostMapping("salvar-mesa")
  public String salvarMesa(@Valid @ModelAttribute("mesa") Mesa mesa,
                           Model model, RedirectAttributes flash){
    System.out.println(mesa);
    flash.addFlashAttribute("success","Mesa se guardo con éxito");
    mesaService.saveMesa(mesa);
    return "redirect:/";
  }

  @GetMapping("lista-mesa")
  public String listaMesa(@RequestParam(name="page",defaultValue = "0")int page,
                          Model model){
    Pageable pagReq= PageRequest.of(page,2);
    Page<Mesa> mesaEntities=mesaService.buscarMesas(pagReq);
    RenderPagina<Mesa> render=new RenderPagina<>("lista-mesa",mesaEntities);
    model.addAttribute("mesa",mesaEntities);
    model.addAttribute("page",render);
    model.addAttribute("contenido","Lista de Mesas");
    return "mesa/lista-mesa";
  }

  @GetMapping("modificar-mesa/{id}")
  public String saltoModificar(@PathVariable("id") Integer id,Model model){
    Mesa mesa=mesaService.getMesaById(id);
    model.addAttribute("mesa",mesa);
    model.addAttribute("contenido","Modificar Mesa");
    return "mesa/alta-mesa";
  }
  @GetMapping("eliminar-mesa/{id}")
  public String eliminar(@PathVariable("id") Integer id,RedirectAttributes flash){
    mesaService.deleteMesa(id);
    flash.addFlashAttribute("success","Se borro con éxito Mesa");
    return "redirect:/mesa/lista-mesa";
  }



}
