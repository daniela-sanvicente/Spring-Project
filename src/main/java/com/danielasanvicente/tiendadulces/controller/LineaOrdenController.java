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

import com.danielasanvicente.tiendadulces.entity.LineaOrden;
import com.danielasanvicente.tiendadulces.service.LineaOrdenService;
import com.danielasanvicente.tiendadulces.util.RenderPagina;

import lombok.AllArgsConstructor;

@AllArgsConstructor
@Controller
@CrossOrigin
@RequestMapping("/lineaOrden")
public class LineaOrdenController {
  @Autowired
  LineaOrdenService lineaOrdenService;

  @GetMapping("/{id}")
  public ResponseEntity<LineaOrden> getById(@PathVariable Integer id) {
    return new ResponseEntity<>(lineaOrdenService.getLineaOrdenById(id), HttpStatus.OK);
  }

  @GetMapping("")
  public ResponseEntity<List<LineaOrden>> getAll() {
    return new ResponseEntity<>(lineaOrdenService.getLineaOrdens(), HttpStatus.OK);
  }

  @PostMapping("")
  public ResponseEntity<HttpStatus> createLineaOrden(@Valid @RequestBody LineaOrden lineaOrden) {
    lineaOrdenService.saveLineaOrden(lineaOrden);

    return new ResponseEntity<>(HttpStatus.CREATED);
  }

  @PutMapping("/{id}")
  public ResponseEntity<LineaOrden> updateLineaOrden(@PathVariable Integer id, @Valid @RequestBody LineaOrden updatedLineaOrden) {
    LineaOrden updated = lineaOrdenService.updateLineaOrden(id, updatedLineaOrden);

    return ResponseEntity.ok(updated);
  }

  @DeleteMapping("/{id}")
  public ResponseEntity<HttpStatus> deleteLineaOrden(@PathVariable Integer id) {
    lineaOrdenService.deleteLineaOrden(id);

    return new ResponseEntity<>(HttpStatus.NO_CONTENT);
  }

  @GetMapping("alta-lineaOrden")
  public String altaLineaOrden(Model model){
    LineaOrden lineaOrden=new LineaOrden();
    model.addAttribute("contenido","Alta de un LineaOrden");
    model.addAttribute("lineaOrden",lineaOrden);
    return "lineaOrden/alta-lineaOrden";
  }

  @PostMapping("salvar-lineaOrden")
  public String salvarLineaOrden(@Valid @ModelAttribute("lineaOrden") LineaOrden lineaOrden, BindingResult result,
                            Model model, RedirectAttributes flash,
                            SessionStatus sesion){

    if (result.hasErrors()) {
      model.addAttribute("contenido", "Error en la entra de datos");
      return "lineaOrden/alta-lineaOrden";
    }

    lineaOrdenService.saveLineaOrden(lineaOrden);

    flash.addFlashAttribute("success","LineaOrden se guardo con éxito");

    sesion.setComplete();

    model.addAttribute("success","LineaOrden se almaceno con éxito");

    return "redirect:/";
  }

  @GetMapping("lista-lineaOrden")
  public String listaLineaOrden(@RequestParam(name="page",defaultValue = "0")int page,
                           Model model){
    Pageable pagReq= PageRequest.of(page,2);
    Page<LineaOrden> lineaOrdenEntities=lineaOrdenService.buscarLineaOrdenes(pagReq);
    RenderPagina<LineaOrden> render=new RenderPagina<>("lista-lineaOrden",lineaOrdenEntities);
    model.addAttribute("lineaOrden",lineaOrdenEntities);
    model.addAttribute("page",render);
    model.addAttribute("contenido","Lista de LineaOrdens");
    return "lineaOrden/lista-lineaOrden";
  }

  @GetMapping("modificar-lineaOrden/{id}")
  public String saltoModificar(@PathVariable("id") Integer id,Model model){
    LineaOrden lineaOrden=lineaOrdenService.getLineaOrdenById(id);
    model.addAttribute("lineaOrden",lineaOrden);
    model.addAttribute("contenido","Modificar LineaOrden");
    return "lineaOrden/alta-lineaOrden";
  }
  @GetMapping("eliminar-lineaOrden/{id}")
  public String eliminar(@PathVariable("id") Integer id,RedirectAttributes flash){
    lineaOrdenService.deleteLineaOrden(id);
    flash.addFlashAttribute("success","Se borro con éxito LineaOrden");
    return "redirect:/lineaOrden/lista-lineaOrden";
  }
}
