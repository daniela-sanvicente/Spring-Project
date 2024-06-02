package com.danielasanvicente.tiendadulces.security.manager;

import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Component;

import com.danielasanvicente.tiendadulces.entity.Cliente;
import com.danielasanvicente.tiendadulces.service.ClienteService;

import lombok.AllArgsConstructor;

@Component
@AllArgsConstructor
//Compara el valor criptografiado de la contraseña enviada con la contraseña criptografiada del usuario que está en el banco de datos con
public class CustomAuthenticationManager implements AuthenticationManager {    
    private ClienteService clienteServiceImpl;
    private BCryptPasswordEncoder bCryptPasswordEncoder;

    @Override
    public Authentication authenticate(Authentication authentication) throws AuthenticationException {
        Cliente cliente = clienteServiceImpl.getClienteByEmail(authentication.getName());
        
        if (!bCryptPasswordEncoder.matches(authentication.getCredentials().toString(), cliente.getContrasena())) {
            throw new BadCredentialsException("You provided an incorrect password.");
        }

        return new UsernamePasswordAuthenticationToken(authentication.getName(), cliente.getContrasena());
    }
}
