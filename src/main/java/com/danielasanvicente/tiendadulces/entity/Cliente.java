package com.danielasanvicente.tiendadulces.entity;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonIgnore;

import jakarta.persistence.*;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;

@Entity
@Table(name = "cliente")
public class Cliente {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Integer id;

    @Column(nullable = true, columnDefinition = "VARCHAR(30) DEFAULT 'N/A'")
    private String nombre;

    @Column(nullable = true, columnDefinition = "VARCHAR(80) DEFAULT 'N/A'")
    private String apellidos;

    @Email
    @NotBlank(message = "El correo no puede estar vacío.")
    @Column(nullable = false, unique = true, columnDefinition = "VARCHAR(80) DEFAULT 'N/A'")
    private String correo;

    @Column(name = "telefono_casa", nullable = true, columnDefinition = "VARCHAR(20) DEFAULT 'N/A'")
    private String telefonoCasa;

    @Column(nullable = true, columnDefinition = "VARCHAR(20) DEFAULT 'N/A'")
    private String whatsapp;

    @Column(nullable = true, columnDefinition = "VARCHAR(200) DEFAULT 'N/A'")
    private String direccion;

    @Column(nullable = true, columnDefinition = "VARCHAR(50) DEFAULT 'N/A'")
    private String tipo;

    @NotBlank(message = "La contrasena no puede estar vacía.")
    @Column(nullable = false, columnDefinition = "VARCHAR(255) DEFAULT 'N/A'")
    private String contrasena;
    
    @JsonIgnore
    @OneToMany(mappedBy = "cliente", cascade = CascadeType.ALL)
    private List<Orden> ordenes;
    
    @JsonIgnore
    @OneToMany(mappedBy = "cliente", cascade = CascadeType.ALL)
    private List<Pago> pagos;

    @JsonIgnore
    @OneToOne(mappedBy = "cliente", cascade = CascadeType.ALL)
    private Carrito carrito;

    public Cliente() {
    }

    public Cliente(@NotBlank String correo, @NotBlank String contrasena) {
        this.correo = correo;
        this.contrasena = contrasena;
    }

    public Cliente(String nombre, String apellidos, String correo, String telefonoCasa, String whatsapp, String direccion, String tipo, String contrasena) {
        this.nombre = nombre;
        this.apellidos = apellidos;
        this.correo = correo;
        this.telefonoCasa = telefonoCasa;
        this.whatsapp = whatsapp;
        this.direccion = direccion;
        this.tipo = tipo;
        this.contrasena = contrasena;
    }

    public Cliente(String nombre, String apellidos,
        @Email @NotBlank(message = "El correo no puede estar vacío.") String correo, String telefonoCasa,
        String whatsapp, String direccion, String tipo,
        @NotBlank(message = "La contrasena no puede estar vacía.") String contrasena, Carrito carrito) {
      this.nombre = nombre;
      this.apellidos = apellidos;
      this.correo = correo;
      this.telefonoCasa = telefonoCasa;
      this.whatsapp = whatsapp;
      this.direccion = direccion;
      this.tipo = tipo;
      this.contrasena = contrasena;
      this.carrito = carrito;
    }

    public Cliente(String nombre, String apellidos,
        @Email @NotBlank(message = "El correo no puede estar vacío.") String correo, String telefonoCasa,
        String whatsapp, String direccion, String tipo,
        @NotBlank(message = "La contrasena no puede estar vacía.") String contrasena, List<Orden> ordenes,
        Carrito carrito) {
      this.nombre = nombre;
      this.apellidos = apellidos;
      this.correo = correo;
      this.telefonoCasa = telefonoCasa;
      this.whatsapp = whatsapp;
      this.direccion = direccion;
      this.tipo = tipo;
      this.contrasena = contrasena;
      this.ordenes = ordenes;
      this.carrito = carrito;
    }

    public Cliente(String nombre, String apellidos,
        @Email @NotBlank(message = "El correo no puede estar vacío.") String correo, String telefonoCasa,
        String whatsapp, String direccion, String tipo,
        @NotBlank(message = "La contrasena no puede estar vacía.") String contrasena, List<Orden> ordenes,
        List<Pago> pagos, Carrito carrito) {
      this.nombre = nombre;
      this.apellidos = apellidos;
      this.correo = correo;
      this.telefonoCasa = telefonoCasa;
      this.whatsapp = whatsapp;
      this.direccion = direccion;
      this.tipo = tipo;
      this.contrasena = contrasena;
      this.ordenes = ordenes;
      this.pagos = pagos;
      this.carrito = carrito;
    }

    public Integer getId() {
      return id;
    }

    public void setId(Integer id) {
      this.id = id;
    }

    public String getNombre() {
      return nombre;
    }

    public void setNombre(String nombre) {
      this.nombre = nombre;
    }

    public String getApellidos() {
      return apellidos;
    }

    public void setApellidos(String apellidos) {
      this.apellidos = apellidos;
    }

    public String getCorreo() {
      return correo;
    }

    public void setCorreo(String correo) {
      this.correo = correo;
    }

    public String getTelefonoCasa() {
      return telefonoCasa;
    }

    public void setTelefonoCasa(String telefonoCasa) {
      this.telefonoCasa = telefonoCasa;
    }

    public String getWhatsapp() {
      return whatsapp;
    }

    public void setWhatsapp(String whatsapp) {
      this.whatsapp = whatsapp;
    }

    public String getDireccion() {
      return direccion;
    }

    public void setDireccion(String direccion) {
      this.direccion = direccion;
    }

    public String getTipo() {
      return tipo;
    }

    public void setTipo(String tipo) {
      this.tipo = tipo;
    }

    public String getContrasena() {
      return contrasena;
    }

    public void setContrasena(String contrasena) {
      this.contrasena = contrasena;
    }

    public List<Orden> getOrdenes() {
      return ordenes;
    }

    public void setOrdenes(List<Orden> ordenes) {
      this.ordenes = ordenes;
    }

    public List<Pago> getPagos() {
      return pagos;
    }

    public void setPagos(List<Pago> pagos) {
      this.pagos = pagos;
    }

    public Carrito getCarrito() {
      return carrito;
    }

    public void setCarrito(Carrito carrito) {
      this.carrito = carrito;
    }

    @Override
    public String toString() {
      return "Cliente [id=" + id + ", nombre=" + nombre + ", apellidos=" + apellidos + ", correo=" + correo
          + ", telefonoCasa=" + telefonoCasa + ", whatsapp=" + whatsapp + ", direccion=" + direccion + ", tipo=" + tipo
          + ", contrasena=" + contrasena + ", ordenes=" + ordenes + ", pagos=" + pagos + ", carrito=" + carrito + "]";
    }
}
