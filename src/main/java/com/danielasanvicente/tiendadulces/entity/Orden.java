package com.danielasanvicente.tiendadulces.entity;

import java.time.LocalDateTime;

import com.fasterxml.jackson.annotation.JsonIgnore;

import jakarta.persistence.*;

@Entity
@Table(name = "orden")
public class Orden {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Integer id;

    @ManyToOne
    @JoinColumn(name = "id_cliente", referencedColumnName = "id", nullable = false)
    private Cliente cliente;

    @JsonIgnore
    @OneToOne(mappedBy = "orden", cascade = CascadeType.ALL)
    private Pago pago;

    @Column(name = "tipo_orden", nullable = false)
    private String tipoOrden;

    @Column(nullable = false)
    private String estatus;

    @Column(name = "direccion_entrega", nullable = false)
    private String direccionEntrega;

    private Double anticipo;

    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "momento_pedido", nullable = false)
    private LocalDateTime momentoPedido;

    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "momento_entrega", unique = true)
    private LocalDateTime momentoEntrega;

    private Double descuento;

    @Column(name = "precio_total", nullable = false)
    private Double precioTotal;

    public Orden() {
    }

    public Orden(Integer id, Cliente cliente, String tipoOrden, String estatus, String direccionEntrega,
            Double anticipo, LocalDateTime momentoPedido, LocalDateTime momentoEntrega, Double descuento, Double precioTotal) {
        this.id = id;
        this.cliente = cliente;
        this.tipoOrden = tipoOrden;
        this.estatus = estatus;
        this.direccionEntrega = direccionEntrega;
        this.anticipo = anticipo;
        this.momentoPedido = momentoPedido;
        this.momentoEntrega = momentoEntrega;
        this.descuento = descuento;
        this.precioTotal = precioTotal;
    }

    public Orden(Cliente cliente, Pago pago, String tipoOrden, String estatus, String direccionEntrega, Double anticipo, LocalDateTime momentoPedido, LocalDateTime momentoEntrega, Double descuento, Double precioTotal) {
        this.cliente = cliente;
        this.pago = pago;
        this.tipoOrden = tipoOrden;
        this.estatus = estatus;
        this.direccionEntrega = direccionEntrega;
        this.anticipo = anticipo;
        this.momentoPedido = momentoPedido;
        this.momentoEntrega = momentoEntrega;
        this.descuento = descuento;
        this.precioTotal = precioTotal;
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

    public Pago getPago() {
        return pago;
    }

    public void setPago(Pago pago) {
        this.pago = pago;
    }

    public String getTipoOrden() {
        return tipoOrden;
    }

    public void setTipoOrden(String tipoOrden) {
        this.tipoOrden = tipoOrden;
    }

    public String getEstatus() {
        return estatus;
    }

    public void setEstatus(String estatus) {
        this.estatus = estatus;
    }

    public String getDireccionEntrega() {
        return direccionEntrega;
    }

    public void setDireccionEntrega(String direccionEntrega) {
        this.direccionEntrega = direccionEntrega;
    }

    public Double getAnticipo() {
        return anticipo;
    }

    public void setAnticipo(Double anticipo) {
        this.anticipo = anticipo;
    }

    public LocalDateTime getMomentoPedido() {
        return momentoPedido;
    }

    public void setMomentoPedido(LocalDateTime momentoPedido) {
        this.momentoPedido = momentoPedido;
    }

    public LocalDateTime getMomentoEntrega() {
        return momentoEntrega;
    }

    public void setMomentoEntrega(LocalDateTime momentoEntrega) {
        this.momentoEntrega = momentoEntrega;
    }

    public Double getDescuento() {
        return descuento;
    }

    public void setDescuento(Double descuento) {
        this.descuento = descuento;
    }

    public Double getPrecioTotal() {
        return precioTotal;
    }

    public void setPrecioTotal(Double precioTotal) {
        this.precioTotal = precioTotal;
    }

    @Override
    public String toString() {
      return "Orden [id=" + id + ", cliente=" + cliente.getCorreo() + ", pago=" + pago.getId() + ", tipoOrden=" + tipoOrden + ", estatus="
          + estatus + ", direccionEntrega=" + direccionEntrega + ", anticipo=" + anticipo + ", momentoPedido="
          + momentoPedido + ", momentoEntrega=" + momentoEntrega + ", descuento=" + descuento + ", precioTotal="
          + precioTotal + "]";
    }    
}
