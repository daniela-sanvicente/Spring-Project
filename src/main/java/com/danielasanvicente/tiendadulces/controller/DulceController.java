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
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.data.domain.Pageable;

import com.danielasanvicente.tiendadulces.entity.Dulce;
import com.danielasanvicente.tiendadulces.service.DulceService;

import lombok.AllArgsConstructor;
import org.springframework.web.bind.support.SessionStatus;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@AllArgsConstructor
@Controller
@CrossOrigin
@RequestMapping("/dulce")
public class DulceController {
  @Autowired
  DulceService dulceService;

  @GetMapping("/{id}")
  public ResponseEntity<Dulce> getById(@PathVariable Integer id) {
    return new ResponseEntity<>(dulceService.getDulceById(id), HttpStatus.OK);
  }

  @GetMapping("")
  public ResponseEntity<List<Dulce>> getAll() {
    return new ResponseEntity<>(dulceService.getDulces(), HttpStatus.OK);
  }

  @PostMapping("")
  public ResponseEntity<HttpStatus> createDulce(@Valid @RequestBody Dulce dulce) {
    dulceService.saveDulce(dulce);

    return new ResponseEntity<>(HttpStatus.CREATED);
  }

  @PutMapping("/{id}")
  public ResponseEntity<Dulce> updateDulce(@PathVariable Integer id, @Valid @RequestBody Dulce updatedDulce) {
    Dulce updated = dulceService.updateDulce(id, updatedDulce);

    return ResponseEntity.ok(updated);
  }

  @DeleteMapping("/{id}")
  public ResponseEntity<HttpStatus> deleteDulce(@PathVariable Integer id) {
    dulceService.deleteDulce(id);

    return new ResponseEntity<>(HttpStatus.NO_CONTENT);
  }


  @GetMapping("alta-dulce")
  public String altaDulce(Model model){
    Dulce dulce=new Dulce();
    model.addAttribute("contenido","Alta de un Dulce");
    model.addAttribute("dulce",dulce);
    return "dulce/alta-dulce";
  }

  @PostMapping("salvar-dulce")
  public String salvarDulce(@Valid @ModelAttribute("dulce") Dulce dulce, BindingResult result,
                            Model model, RedirectAttributes flash,
                            SessionStatus sesion){

    if (result.hasErrors()) {
      model.addAttribute("contenido", "Error en la entra de datos");
      return "dulce/alta-dulce";
    }

    dulceService.saveDulce(dulce);

    flash.addFlashAttribute("success","Dulce se guardo con éxito");

    sesion.setComplete();

    model.addAttribute("success","Dulce se almaceno con éxito");

    return "redirect:/";
  }

  @GetMapping("lista-dulce")
  public String listaDulce(@RequestParam(name="page",defaultValue = "0")int page,
                           Model model){
    Pageable pagReq= PageRequest.of(page,2);
    Page<Dulce> dulceEntities=dulceService.buscarDulces(pagReq);
    RenderPagina<Dulce> render=new RenderPagina<>("lista-dulce",dulceEntities);
    model.addAttribute("dulce",dulceEntities);
    model.addAttribute("page",render);
    model.addAttribute("contenido","Lista de Dulces");
    return "dulce/lista-dulce";
  }

  @GetMapping("modificar-dulce/{id}")
  public String saltoModificar(@PathVariable("id") Integer id,Model model){
    Dulce dulce=dulceService.getDulceById(id);
    model.addAttribute("dulce",dulce);
    model.addAttribute("contenido","Modificar Dulce");
    return "dulce/alta-dulce";
  }
  @GetMapping("eliminar-dulce/{id}")
  public String eliminar(@PathVariable("id") Integer id,RedirectAttributes flash){
    dulceService.deleteDulce(id);
    flash.addFlashAttribute("success","Se borro con éxito Dulce");
    return "redirect:/dulce/lista-dulce";
  }

}
