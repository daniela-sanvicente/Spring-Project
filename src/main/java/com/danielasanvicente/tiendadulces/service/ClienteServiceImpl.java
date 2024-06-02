package com.danielasanvicente.tiendadulces.service;

import java.util.List;
import java.util.Optional;

import com.danielasanvicente.tiendadulces.util.Log;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import com.danielasanvicente.tiendadulces.entity.Cliente;
import com.danielasanvicente.tiendadulces.exception.EntityNotFoundException;
import com.danielasanvicente.tiendadulces.repository.ClienteRepository;

import jakarta.transaction.Transactional;
import lombok.AllArgsConstructor;

@Service
@Transactional
@AllArgsConstructor
public class ClienteServiceImpl implements ClienteService {
    private ClienteRepository clienteRepository;
    private BCryptPasswordEncoder bCryptPasswordEncoder;

    @Override
    public Page<Cliente> buscarClientes(Pageable pageable) {
        Log.out.info("Servicio Cliente: Buscando todos los clientes");
        return clienteRepository.findAll(pageable);
    }

    @Override
    public List<Cliente> getClientes() {
        Log.out.info("Servicio Cliente: Buscando todos los clientes");
        return (List<Cliente>) clienteRepository.findAll();
    }

    @Override
    public Cliente getClienteById(Integer id) {
        Optional<Cliente> cliente = clienteRepository.findById(id);

        Log.out.info("Servicio Cliente: Buscando cliente por ID " + id);

        return unwrapCliente(cliente, id);
    }

    @Override
    public Cliente getClienteByEmail(String email) {
        Optional<Cliente> cliente = clienteRepository.findByCorreo(email);

        Log.out.info("Servicio Cliente: Buscando cliente por correo "+ email);

        return unwrapCliente(cliente, email);
    }

    @Override
    public Cliente saveCliente(Cliente cliente) {
        cliente.setContrasena(bCryptPasswordEncoder.encode(cliente.getContrasena()));

        Log.out.info("Servicio Cliente: Guardando cliente {} ", cliente);

        return clienteRepository.save(cliente);
    }

    public Cliente updateCliente(Integer id, Cliente updatedCliente) {
        Optional<Cliente> optionalCliente = clienteRepository.findById(id);

        if (optionalCliente.isPresent()) {
            Cliente cliente = optionalCliente.get();

            if (updatedCliente.getNombre() != null) {
                cliente.setNombre(updatedCliente.getNombre());
            }

            if (updatedCliente.getApellidos() != null) {
                cliente.setApellidos(updatedCliente.getApellidos());
            }

            if (updatedCliente.getCorreo() != null) {
                cliente.setCorreo(updatedCliente.getCorreo());
            }

            if (updatedCliente.getTelefonoCasa() != null) {
                cliente.setTelefonoCasa(updatedCliente.getTelefonoCasa());
            }

            if (updatedCliente.getWhatsapp() != null) {
                cliente.setWhatsapp(updatedCliente.getWhatsapp());
            }

            if (updatedCliente.getDireccion() != null) {
                cliente.setDireccion(updatedCliente.getDireccion());
            }

            if (updatedCliente.getTipo() != null) {
                cliente.setTipo(updatedCliente.getTipo());
            }

            if (updatedCliente.getContrasena() != null) {
                cliente.setContrasena(
                        bCryptPasswordEncoder.encode(updatedCliente.getContrasena()));
            }

            Log.out.info("Servicio Cliente: Actualizando cliente {} ", cliente);

            return clienteRepository.save(cliente);
        } else {
            throw new EntityNotFoundException(String.valueOf(id), Cliente.class);
        }
    }

    @Override
    public void deleteCliente(Integer id) {
        Log.out.info("Servicio Cliente: Borrando cliente con ID "+ id);
        clienteRepository.deleteById(id);
    }

    static Cliente unwrapCliente(Optional<Cliente> entity, Object id) {
        if (entity.isPresent())
            return entity.get();

        else
            throw new EntityNotFoundException(String.valueOf(id), Cliente.class);
    }
}
