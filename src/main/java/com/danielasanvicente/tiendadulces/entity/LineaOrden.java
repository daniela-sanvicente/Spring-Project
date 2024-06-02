package com.danielasanvicente.tiendadulces.entity;

import jakarta.persistence.*;

@Entity
@Table(name = "linea_orden")
public class LineaOrden {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @ManyToOne
    @JoinColumn(name = "id_orden", referencedColumnName = "id", nullable = false)
    private Orden orden;

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

    @Column(nullable = false, columnDefinition = "DECIMAL(6,2) DEFAULT 0.00")
    private Double precio;

    public LineaOrden() {
    }

    public LineaOrden(Orden orden, Dulce dulce, Arreglo arreglo, Mesa mesa, Integer cantidad, Double precio) {
        this.orden = orden;
        this.dulce = dulce;
        this.arreglo = arreglo;
        this.mesa = mesa;
        this.cantidad = cantidad;
        this.precio = precio;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public Orden getOrden() {
        return orden;
    }

    public void setOrden(Orden orden) {
        this.orden = orden;
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

    public Double getPrecio() {
        return precio;
    }

    public void setPrecio(Double precio) {
        this.precio = precio;
    }

    @Override
    public String toString() {
        return "LineaOrden [id=" + id + ", orden=" + orden + ", dulce=" + dulce + ", arreglo=" + arreglo + ", mesa="
                + mesa + ", cantidad=" + cantidad + ", precio=" + precio + "]";
    }
}
