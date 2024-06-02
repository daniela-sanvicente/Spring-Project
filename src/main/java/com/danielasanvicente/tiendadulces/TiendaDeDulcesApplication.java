package com.danielasanvicente.tiendadulces;

import com.danielasanvicente.tiendadulces.entity.*;
import com.danielasanvicente.tiendadulces.repository.*;
import com.danielasanvicente.tiendadulces.service.ArregloService;
import com.danielasanvicente.tiendadulces.service.DulceService;

import com.danielasanvicente.tiendadulces.util.Log;
import jakarta.transaction.Transactional;
import lombok.AllArgsConstructor;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@SpringBootApplication
@AllArgsConstructor
public class TiendaDeDulcesApplication implements CommandLineRunner {
    @Autowired
    private ClienteRepository clienteRepository;

    @Autowired
    private MetodoPagoRepository metodoPagoRepository;

    @Autowired
    private PagoRepository pagoRepository;

    @Autowired
    private OrdenRepository ordenRepository;

    @Autowired
    private DulceRepository dulceRepository;

    @Autowired
    private ArregloRepository arregloRepository;

    @Autowired
    private DulceService dulceService;

    @Autowired
    private ArregloService arregloService;

    @Autowired
    private CarritoRepository carritoRepository;

    @Autowired
    private MesaRepository mesaRepository;

    public static void main(String[] args) {
        SpringApplication.run(TiendaDeDulcesApplication.class, args);
    }

    @Override
	@Transactional
    public void run(String... args) throws Exception {

        Log.out.info("Cargando la aplicación...");

        List<Cliente> clientes = Arrays.asList(
			new Cliente("Angelino", "Lionel Petrocleo","petro@lionel.com", "0987654321", "0987654321", "Avenida Central 456", "revendedor", new BCryptPasswordEncoder().encode("contrasena436")),
			new Cliente("torotoro.susuki@gmail.com", new BCryptPasswordEncoder().encode("123456")),
            new Cliente("María", "Gómez", "maria@example.com", "1234567890", "1234567890", "Calle Principal 123", "clienteFinal", new BCryptPasswordEncoder().encode("contrasena123")),
            new Cliente("Pedro", "Martínez", "pedro@example.com", "0987654321", "0987654321", "Avenida Central 456", "revendedor", new BCryptPasswordEncoder().encode("contrasena456")),
            new Cliente("Juan", "González", "juan@example.com", "1122334455", "1122334455", "Calle Secundaria 789", "clienteFinal", new BCryptPasswordEncoder().encode("contrasena789")),
            new Cliente("Ana", "Hernández", "ana@example.com", "5544332211", "5544332211", "Calle Transversal 1011", "revendedor", new BCryptPasswordEncoder().encode("contrasena1011")),
            new Cliente("Carlos", "López", "carlos@example.com", "6677889900", "6677889900", "Avenida Principal 1213", "clienteFinal", new BCryptPasswordEncoder().encode("contrasena1213"))
        );

        clienteRepository.saveAll(clientes);

        List<MetodoPago> metodosPago = Arrays.asList(
            new MetodoPago("TDC"),
            new MetodoPago("TDB"),
            new MetodoPago("transferencia bancaria"),
            new MetodoPago("cheque"),
            new MetodoPago("efectivo"),
            new MetodoPago("pago móvil")
        );

        metodoPagoRepository.saveAll(metodosPago);
				
        List<Orden> ordenes = Arrays.asList(
            new Orden(clienteRepository.findById(1).orElse(null), pagoRepository.findById(1).orElse(null), "mayoreo", "realizado", "Calle de la Amistad 789", 50.00, LocalDateTime.parse("2024-03-25T10:00:00"), null, 0.00, 150.00),
            new Orden(clienteRepository.findById(2).orElse(null), pagoRepository.findById(2).orElse(null), "menudeo", "entregado", "Avenida Principal 321", 0.00, LocalDateTime.parse("2024-03-24T15:00:00"), LocalDateTime.parse("2024-03-26T14:00:00"), 10.00, 90.00),
            new Orden(clienteRepository.findById(3).orElse(null), pagoRepository.findById(3).orElse(null), "mayoreo", "realizado", "Avenida de la Paz 567", 75.00, LocalDateTime.parse("2024-03-26T09:00:00"), null, 5.00, 120.00),
            new Orden(clienteRepository.findById(4).orElse(null), pagoRepository.findById(4).orElse(null), "mixto", "entregado", "Calle de la Libertad 901", 0.00, LocalDateTime.parse("2024-03-27T12:00:00"), LocalDateTime.parse("2024-03-28T11:00:00"), 15.00, 85.00),
            new Orden(clienteRepository.findById(5).orElse(null), pagoRepository.findById(5).orElse(null), "mayoreo", "realizado", "Boulevard de las Flores 1234", 100.00, LocalDateTime.parse("2024-03-28T08:00:00"), null, 20.00, 180.00)
        );

        ordenRepository.saveAll(ordenes);

        List<Pago> pagos = Arrays.asList(
            new Pago(clienteRepository.findById(1).orElse(null), ordenRepository.findById(1).orElse(null), metodoPagoRepository.findById(1).orElse(null), "VISA", "1234567890123456", "03/26", "123"),
            new Pago(clienteRepository.findById(2).orElse(null), ordenRepository.findById(2).orElse(null), metodoPagoRepository.findById(1).orElse(null), "MASTERCARD", "9876543210987654", "06/27", "456"),
            new Pago(clienteRepository.findById(3).orElse(null), ordenRepository.findById(3).orElse(null), metodoPagoRepository.findById(1).orElse(null), "MASTERCARD", "6543210987654321", "09/28", "789"),
            new Pago(clienteRepository.findById(4).orElse(null), ordenRepository.findById(4).orElse(null), metodoPagoRepository.findById(1).orElse(null), "MASTERCARD", "0123456789012345", "12/29", "987"),
            new Pago(clienteRepository.findById(5).orElse(null), ordenRepository.findById(5).orElse(null), metodoPagoRepository.findById(1).orElse(null), "VISA", "5432109876543210", "03/30", "654")
        );

        pagoRepository.saveAll(pagos);


        List<Dulce> dulces = Arrays.asList(
            new Dulce("Paleta de Chocolate", 5.50, "N/A", "Chocolate", 100, "paleta_chocolate.jpg", "2 horas"),
            new Dulce("Paleta de Amaranto", 8.50, "N/A", "Chocolate", 100, "paleta_amaranto.jpg", "3 horas"),
            new Dulce("Paleta de Navidad", 12.50, "Navidad", "Chocolate", 100, "paleta_navidad.jpg", "3 horas"),
            new Dulce("Bombones", 15.25, "N/A", "Bombón", 80, "bombones.jpg", "3 horas"),
            new Dulce("Bombones de Café", 20.50, "N/A", "Bombón", 80, "bombones_café.jpg", "3 horas"),
            new Dulce("Bombones de Navidad", 40.25, "Navidad", "Bombón", 80, "bombones_navidad.jpg", "3 horas"),
            new Dulce("Bombones de Boda", 50.20, "Boda", "Bombón", 80, "bombones_boda.jpg", "5 horas"),
            new Dulce("Enjambre", 25.20, "N/A", "Chocolate", 80, "enjambre.jpg", "5 horas"),
            new Dulce("Brocheta de Gomita", 8.50, "N/A", "Gomita", 120, "brocheta_gomita.jpg", "4 horas"),
            new Dulce("Brocheta de Gomita de Navidad", 10.50, "Navidad", "Gomita", 120, "brocheta_gomita_nav.jpg", "4 horas"),
            new Dulce("Brocheta de Gomita de Boda", 12.50, "Boda", "Gomita", 120, "brocheta_gomita_boda.jpg", "4 horas"),
            new Dulce("Gomita Suelta", 8.50, "N/A", "Gomita", 120, "gomita_suelta.jpg", "4 horas")
        );

        dulceRepository.saveAll(dulces);

        List<Arreglo> arreglos = Arrays.asList(
            new Arreglo("Arreglo de Paletas", 60.50, "N/A", "Chocolate", 50, "arreglo_paletas.jpg", "3 horas", "Arreglo de paletas de chocolate surtidas.", Arrays.asList(dulces.get(0), dulces.get(1), dulces.get(2))),
            new Arreglo("Arreglo de Brochetas de Gomita", 75.00, "N/A", "Gomita", 30, "arreglo_brochetas.jpg", "5 horas", "Arreglo de brochetas de gomita.", Arrays.asList(dulces.get(8), dulces.get(9), dulces.get(10))),
            new Arreglo("Arreglo de Bombones", 100.75, "N/A", "Bombón", 40, "arreglo_bombones.jpg", "4 horas", "Arreglo de bombones surtidos.", Arrays.asList(dulces.get(3), dulces.get(4), dulces.get(5), dulces.get(6))),
            new Arreglo("Arreglo de Bombones Navideños", 130.60, "Navidad", "Bombón", 10, "arreglo_bombones_navidad.jpg", "4 horas", "Arreglo de bombones surtidos.", Arrays.asList(dulces.get(3), dulces.get(4), dulces.get(5), dulces.get(6)))
        );

        arregloRepository.saveAll(arreglos);

        List<Dulce> paletasChocolate = Arrays.asList(dulces.get(0), dulces.get(1), dulces.get(2));
        Set<Arreglo> arregloBombones = new HashSet<>(Arrays.asList(arreglos.get(2)));

        List<Mesa> mesas = Arrays.asList(
            new Mesa("Mesa de Paletas", 120.50, "N/A", "Chocolate", "mesa_paletas.jpg", null, "3 horas", "Mesa de paletas de chocolate surtidas.", paletasChocolate),
            new Mesa("Mesa de Brochetas de Gomita", 150.00, "N/A", "Gomita", "mesa_brochetas.jpg", null, "5 horas", "Mesa de brochetas de gomita.", Arrays.asList(dulces.get(8), dulces.get(9), dulces.get(10))),
            new Mesa("Mesa de Bombones", 200.75, "N/A", "Bombón", "mesa_bombones.jpg", null, "4 horas", "Mesa de bombones surtidos.", Arrays.asList(dulces.get(3), dulces.get(4), dulces.get(5), dulces.get(6))),
            new Mesa("Mesa de Arreglos de Bombones", 300.75, "N/A", "Bombón", "mesa_arreglo_bombones.jpg", null, "4 horas", "Mesa de arreglos de bombones surtidos.", arregloBombones),
            new Mesa("Mesa Surtida de Cumpleaños", 500.75, "Cumpleaños", "Surtido", "mesa_surtida.jpg", null, "4 horas", "Mesa surtida.", paletasChocolate, arregloBombones)
        );

        mesaRepository.saveAll(mesas);

		List<Dulce> listaDulces = dulceService.getDulces();
        List<Arreglo> listaArreglos = arregloService.getArreglos();

        Cliente newCliente = new Cliente("Daniela", "Sanvicente Enríquez", "d.sanvicente.e@gmail.com", "0987654322", "0987654322", "Avenida Central 457", "revendedor", new BCryptPasswordEncoder().encode("contrasena437"));

        Carrito carritoDaniela = new Carrito(newCliente);

        carritoDaniela.addItem(new ItemCarrito(carritoDaniela, listaDulces.get(0), 3)); // Paleta de Chocolate
        carritoDaniela.addItem(new ItemCarrito(carritoDaniela, listaDulces.get(3), 2)); // Bombones
        carritoDaniela.addItem(new ItemCarrito(carritoDaniela, listaArreglos.get(0), 1)); // Arreglo de Paletas

        newCliente.setCarrito(carritoDaniela);

        carritoRepository.save(carritoDaniela);

        clienteRepository.save(newCliente);
	}

    @Bean
    public BCryptPasswordEncoder bCryptPasswordEncoder() {
        return new BCryptPasswordEncoder();
    }
}
