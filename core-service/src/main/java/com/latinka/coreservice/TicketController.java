package com.latinka.coreservice;

import com.latinka.model.Ticket;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.UUID;

@RestController
public class TicketController {

    @Autowired
    private TicketRepository repository;

    // URL 1: Muestra todos los tickets guardados en la BD
    @GetMapping("/")
    public List<Ticket> verTickets() {
        return repository.findAll();
    }

    // URL 2: Crea y guarda un ticket nuevo automáticamente
    @GetMapping("/comprar")
    public Ticket comprarTicket() {
        Ticket t = new Ticket();
        // Generamos un código único aleatorio
        t.setNumeroBoleto("TINKA-" + UUID.randomUUID().toString().substring(0, 5));
        t.setMonto(5.00);

        return repository.save(t); // ¡Aquí se guarda en RDS!
    }
}