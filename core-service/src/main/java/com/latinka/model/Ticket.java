package com.latinka.model;

import jakarta.persistence.*;
import lombok.Data;

@Entity
@Table(name = "tickets_sorteo")
@Data // Lombok genera los getters y setters automáticamente
public class Ticket {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "numero_boleto")
    private String numeroBoleto;

    private Double monto;
}