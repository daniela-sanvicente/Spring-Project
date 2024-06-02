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

import com.danielasanvicente.tiendadulces.entity.ItemCarrito;
import com.danielasanvicente.tiendadulces.service.ItemCarritoService;
import com.danielasanvicente.tiendadulces.util.RenderPagina;

import lombok.AllArgsConstructor;

@AllArgsConstructor
@Controller
@CrossOrigin
@RequestMapping("/itemCarrito")
public class ItemCarritoController {
  @Autowired
  ItemCarritoService itemCarritoService;

  @GetMapping("/{id}")
  public ResponseEntity<ItemCarrito> getById(@PathVariable Integer id) {
    return new ResponseEntity<>(itemCarritoService.getItemCarritoById(id), HttpStatus.OK);
  }

  @GetMapping("")
  public ResponseEntity<List<ItemCarrito>> getAll() {
    return new ResponseEntity<>(itemCarritoService.getItemCarritos(), HttpStatus.OK);
  }

  @PostMapping("")
  public ResponseEntity<HttpStatus> createItemCarrito(@Valid @RequestBody ItemCarrito itemCarrito) {
    itemCarritoService.saveItemCarrito(itemCarrito);

    return new ResponseEntity<>(HttpStatus.CREATED);
  }

  @PutMapping("/{id}")
  public ResponseEntity<ItemCarrito> updateItemCarrito(@PathVariable Integer id, @Valid @RequestBody ItemCarrito updatedItemCarrito) {
    ItemCarrito updated = itemCarritoService.updateItemCarrito(id, updatedItemCarrito);

    return ResponseEntity.ok(updated);
  }

  @DeleteMapping("/{id}")
  public ResponseEntity<HttpStatus> deleteItemCarrito(@PathVariable Integer id) {
    itemCarritoService.deleteItemCarrito(id);

    return new ResponseEntity<>(HttpStatus.NO_CONTENT);
  }

  @GetMapping("alta-itemCarrito")
  public String altaItemCarrito(Model model){
    ItemCarrito itemCarrito=new ItemCarrito();
    model.addAttribute("contenido","Alta de un ItemCarrito");
    model.addAttribute("itemCarrito",itemCarrito);
    return "itemCarrito/alta-itemCarrito";
  }

  @PostMapping("salvar-itemCarrito")
  public String salvarItemCarrito(@Valid @ModelAttribute("itemCarrito") ItemCarrito itemCarrito, BindingResult result,
                            Model model, RedirectAttributes flash,
                            SessionStatus sesion){

    if (result.hasErrors()) {
      model.addAttribute("contenido", "Error en la entra de datos");
      return "itemCarrito/alta-itemCarrito";
    }

    itemCarritoService.saveItemCarrito(itemCarrito);

    flash.addFlashAttribute("success","ItemCarrito se guardo con éxito");

    sesion.setComplete();

    model.addAttribute("success","ItemCarrito se almaceno con éxito");

    return "redirect:/";
  }

  @GetMapping("lista-itemCarrito")
  public String listaItemCarrito(@RequestParam(name="page",defaultValue = "0")int page,
                           Model model){
    Pageable pagReq= PageRequest.of(page,2);
    Page<ItemCarrito> itemCarritoEntities=itemCarritoService.buscarItemCarritos(pagReq);
    RenderPagina<ItemCarrito> render=new RenderPagina<>("lista-itemCarrito",itemCarritoEntities);
    model.addAttribute("itemCarrito",itemCarritoEntities);
    model.addAttribute("page",render);
    model.addAttribute("contenido","Lista de ItemCarritos");
    return "itemCarrito/lista-itemCarrito";
  }

  @GetMapping("modificar-itemCarrito/{id}")
  public String saltoModificar(@PathVariable("id") Integer id,Model model){
    ItemCarrito itemCarrito=itemCarritoService.getItemCarritoById(id);
    model.addAttribute("itemCarrito",itemCarrito);
    model.addAttribute("contenido","Modificar ItemCarrito");
    return "itemCarrito/alta-itemCarrito";
  }
  @GetMapping("eliminar-itemCarrito/{id}")
  public String eliminar(@PathVariable("id") Integer id,RedirectAttributes flash){
    itemCarritoService.deleteItemCarrito(id);
    flash.addFlashAttribute("success","Se borro con éxito ItemCarrito");
    return "redirect:/itemCarrito/lista-itemCarrito";
  }

}
