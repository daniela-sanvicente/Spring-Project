package com.danielasanvicente.tiendadulces.security;

public class SecurityConstants {
    private SecurityConstants() {}

    public static final String SECRET_KEY = "bQeThWmZq4t7w!z$C&F)J@NcRfUjXn2r5u8x/A?D*G-KaPdSgVkYp3s6v9y$B&E)";
    public static final int TOKEN_EXPIRATION = 86400000; // 86400000 milisegundos = 24 horas.
    public static final String BEARER = "Bearer ";
    public static final String AUTHORIZATION = "Authorization";
    public static final String REGISTER_PATH = "/cliente/registrar";
}
