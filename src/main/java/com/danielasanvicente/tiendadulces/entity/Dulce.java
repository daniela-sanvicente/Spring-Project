package com.danielasanvicente.tiendadulces.entity;

import java.util.Set;
import java.util.stream.Collectors;

import com.fasterxml.jackson.annotation.JsonIgnore;

import jakarta.persistence.*;

@Entity
@Table(name = "dulces")
public class Dulce {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Integer id;

    @Column(name = "nombre_dulce", nullable = false, unique = true, columnDefinition = "VARCHAR(50) DEFAULT 'N/A'")
    private String nombreDulce;

    @Column(nullable = false, columnDefinition = "DECIMAL(5,2) DEFAULT 0.00")
    private Double precio;

    @Column(nullable = true, columnDefinition = "VARCHAR(100) DEFAULT 'N/A'")
    private String tema;

    @Column(nullable = false, columnDefinition = "VARCHAR(50) DEFAULT 'N/A'")
    private String tipo;

    @Column(nullable = false, columnDefinition = "INT DEFAULT 0")
    private Integer stock;

    @Column(columnDefinition = "VARCHAR(200) DEFAULT NULL")
    private String imagen;

    @Column(name = "tiempo_elaboracion", nullable = false, columnDefinition = "VARCHAR(100) DEFAULT 'N/A'")
    private String tiempoElaboracion;
    
    @Column(nullable = true, columnDefinition = "VARCHAR(300) DEFAULT 'N/A'")
    private String descripcion;

    @JsonIgnore
    @ManyToMany(mappedBy = "dulces")
    private Set<Arreglo> arreglos;

    @JsonIgnore
    @ManyToMany(mappedBy = "dulces")
    private Set<Mesa> mesas;

    public Dulce() {
    }

    public Dulce(String nombreDulce, Double precio, String tema, String tipo, Integer stock, String imagen,
        String tiempoElaboracion) {
      this.nombreDulce = nombreDulce;
      this.precio = precio;
      this.tema = tema;
      this.tipo = tipo;
      this.stock = stock;
      this.imagen = imagen;
      this.tiempoElaboracion = tiempoElaboracion;
    }

    public Dulce(String nombreDulce, Double precio, String tema, String tipo, Integer stock, String imagen,
        String tiempoElaboracion, String descripcion) {
      this.nombreDulce = nombreDulce;
      this.precio = precio;
      this.tema = tema;
      this.tipo = tipo;
      this.stock = stock;
      this.imagen = imagen;
      this.tiempoElaboracion = tiempoElaboracion;
      this.descripcion = descripcion;
    }

    public Dulce(Integer id, String nombreDulce, Double precio, String tema, String tipo, Integer stock, String imagen,
            String tiempoElaboracion, String descripcion) {
        this.id = id;
        this.nombreDulce = nombreDulce;
        this.precio = precio;
        this.tema = tema;
        this.tipo = tipo;
        this.stock = stock;
        this.imagen = imagen;
        this.tiempoElaboracion = tiempoElaboracion;
        this.descripcion = descripcion;
    }

    public Dulce(Integer id, String nombreDulce, Double precio, String tema, String tipo, Integer stock, String imagen,
            String tiempoElaboracion, String descripcion, Set<Arreglo> arreglos, Set<Mesa> mesas) {
        this.id = id;
        this.nombreDulce = nombreDulce;
        this.precio = precio;
        this.tema = tema;
        this.tipo = tipo;
        this.stock = stock;
        this.imagen = imagen;
        this.tiempoElaboracion = tiempoElaboracion;
        this.descripcion = descripcion;
        this.arreglos = arreglos;
        this.mesas = mesas;
    }

    public Integer getId() {
      return id;
    }

    public void setId(Integer id) {
      this.id = id;
    }

    public String getNombreDulce() {
      return nombreDulce;
    }

    public void setNombreDulce(String nombreDulce) {
      this.nombreDulce = nombreDulce;
    }

    public Double getPrecio() {
      return precio;
    }

    public void setPrecio(Double precio) {
      this.precio = precio;
    }

    public String getTema() {
      return tema;
    }

    public void setTema(String tema) {
      this.tema = tema;
    }

    public String getTipo() {
      return tipo;
    }

    public void setTipo(String tipo) {
      this.tipo = tipo;
    }

    public Integer getStock() {
      return stock;
    }

    public void setStock(Integer stock) {
      this.stock = stock;
    }

    public String getImagen() {
      return imagen;
    }

    public void setImagen(String imagen) {
      this.imagen = imagen;
    }

    public String getTiempoElaboracion() {
      return tiempoElaboracion;
    }

    public void setTiempoElaboracion(String tiempoElaboracion) {
      this.tiempoElaboracion = tiempoElaboracion;
    }

    public String getDescripcion() {
      return descripcion;
    }

    public void setDescripcion(String descripcion) {
      this.descripcion = descripcion;
    }

    public Set<Arreglo> getArreglos() {
      return arreglos;
    }

    public void setArreglos(Set<Arreglo> arreglos) {
      this.arreglos = arreglos;
    }

    public Set<Mesa> getMesas() {
      return mesas;
    }

    public void setMesas(Set<Mesa> mesas) {
      this.mesas = mesas;
    }

    @Override
    public String toString() {
        var arrs = arreglos.stream().map(Arreglo::getNombreArreglo)
            .collect(Collectors.joining(", "));

        var mes = mesas.stream().map(Mesa::getNombreMesa)
            .collect(Collectors.joining(", "));

        return "Dulce [id=" + id + ", nombreDulce=" + nombreDulce + ", precio=" + precio + ", tema=" + tema + ", tipo="
                + tipo + ", stock=" + stock + ", imagen=" + imagen + ", tiempoElaboracion=" + tiempoElaboracion
                + ", descripcion=" + descripcion + ", arreglos=" + arrs + ", mesas=" + mes + "]";
    }
}
