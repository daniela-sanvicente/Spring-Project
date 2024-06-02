package com.danielasanvicente.tiendadulces.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;

import jakarta.persistence.*;

@Entity
@Table(name = "item_carrito")
public class ItemCarrito {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @JsonIgnore
    @ManyToOne
    @JoinColumn(name = "id_carrito", referencedColumnName = "id", nullable = false)
    private Carrito carrito;

    @ManyToOne
    @JoinColumn(name = "id_dulce", referencedColumnName = "id", nullable = true)
    private Dulce dulce;

    @ManyToOne
    @JoinColumn(name = "id_arreglo", referencedColumnName = "id", nullable = true)
    private Arreglo arreglo;

    @ManyToOne
    @JoinColumn(name = "id_mesa", referencedColumnName = "id", nullable = true)
    private Mesa mesa;

    @Column(nullable = false, columnDefinition = "INT DEFAULT 0")
    private Integer cantidad;

    public ItemCarrito() {
    }

    public ItemCarrito(Carrito carrito, Dulce dulce, Integer cantidad) {
        this.carrito = carrito;
        this.dulce = dulce;
        this.cantidad = cantidad;
    }
    
    public ItemCarrito(Carrito carrito, Arreglo arreglo, Integer cantidad) {
        this.carrito = carrito;
        this.arreglo = arreglo;
        this.cantidad = cantidad;
    }
    
    public ItemCarrito(Carrito carrito, Mesa mesa, Integer cantidad) {
        this.carrito = carrito;
        this.mesa = mesa;
        this.cantidad = cantidad;
    }

    public ItemCarrito(Carrito carrito, Dulce dulce, Arreglo arreglo, Mesa mesa, Integer cantidad) {
        this.carrito = carrito;
        this.dulce = dulce;
        this.arreglo = arreglo;
        this.mesa = mesa;
        this.cantidad = cantidad;
    }

    public ItemCarrito(Integer id, Carrito carrito, Dulce dulce, Arreglo arreglo, Mesa mesa, Integer cantidad) {
        this.id = id;
        this.carrito = carrito;
        this.dulce = dulce;
        this.arreglo = arreglo;
        this.mesa = mesa;
        this.cantidad = cantidad;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Carrito getCarrito() {
        return carrito;
    }

    public void setCarrito(Carrito carrito) {
        this.carrito = carrito;
    }

    public Dulce getDulce() {
        return dulce;
    }

    public void setDulce(Dulce dulce) {
        this.dulce = dulce;
    }

    public Arreglo getArreglo() {
        return arreglo;
    }

    public void setArreglo(Arreglo arreglo) {
        this.arreglo = arreglo;
    }

    public Mesa getMesa() {
        return mesa;
    }

    public void setMesa(Mesa mesa) {
        this.mesa = mesa;
    }

    public Integer getCantidad() {
        return cantidad;
    }

    public void setCantidad(Integer cantidad) {
        this.cantidad = cantidad;
    }

    @Override
    public String toString() {
        return "ItemCarrito [id=" + id + ", carrito=" + carrito.getId() + ", dulce=" + dulce + ", arreglo=" + arreglo
                + ", mesa=" + mesa + ", cantidad=" + cantidad + "]";
    }
}
