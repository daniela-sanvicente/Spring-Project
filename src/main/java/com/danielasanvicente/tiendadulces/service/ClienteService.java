package com.danielasanvicente.tiendadulces.service;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import com.danielasanvicente.tiendadulces.entity.Cliente;

public interface ClienteService {

    Page<Cliente> buscarClientes(Pageable pageable);

    List<Cliente> getClientes();

    Cliente getClienteByEmail(String email);

    Cliente getClienteById(Integer id);

    Cliente saveCliente(Cliente cliente);

    Cliente updateCliente(Integer id, Cliente cliente);

    void deleteCliente(Integer id);
}
