package com.danielasanvicente.tiendadulces.entity;

import java.util.HashSet;
import java.util.Set;

import jakarta.persistence.*;

@Entity
@Table(name = "carrito")
public class Carrito {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @OneToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "id_cliente", referencedColumnName = "id")
    private Cliente cliente;

    @OneToMany(mappedBy = "carrito", cascade = CascadeType.ALL, orphanRemoval = true)
    private Set<ItemCarrito> items = new HashSet<>();

    public Carrito() {
        this.items = new HashSet<>();
    }

    public Carrito(Cliente cliente) {
        this.cliente = cliente;
        this.items = new HashSet<>();
    }

    public Carrito(Cliente cliente, Set<ItemCarrito> items) {
        this.cliente = cliente;
        this.items = items;
    }

    public Carrito(Integer id, Cliente cliente, Set<ItemCarrito> items) {
        this.id = id;
        this.cliente = cliente;
        this.items = items;
    }

    public void addItem(ItemCarrito itemCarrito) {
        if (itemCarrito != null) {
            itemCarrito.setCarrito(this);
            
            this.items.add(itemCarrito);
        }
    }
    
    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Cliente getCliente() {
        return cliente;
    }

    public void setCliente(Cliente cliente) {
        this.cliente = cliente;
    }

    public Set<ItemCarrito> getItems() {
        return items;
    }

    public void setItems(Set<ItemCarrito> items) {
        this.items = items;
    }

    @Override
    public String toString() {
        return "Carrito [id=" + id + ", cliente=" + cliente + ", items=" + items + "]";
    }
}
