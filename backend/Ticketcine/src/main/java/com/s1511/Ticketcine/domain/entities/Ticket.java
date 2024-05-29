package com.s1511.Ticketcine.domain.entities;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import java.time.LocalDateTime;
import java.util.List;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Ticket {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private String id;
    private String userId;
    private Double value;
    private String movieName;
    private LocalDateTime functionDate;
    private String cinemaName;
    private String screenId;
    @OneToMany(fetch = FetchType.LAZY)
    private List<Seat> seatsNames;
    private Boolean active;


}

/*
Chatty dice mucho (bastante interesante) y resume en esto:

Si la facilidad de navegación y la claridad del código son más importantes y el consumo
de memoria adicional no es un problema, usar entidades como atributos es preferible.

Si el rendimiento y la eficiencia en el uso de memoria son críticos y las relaciones no se
 utilizan frecuentemente, usar IDs para referenciar entidades puede ser más adecuado.

En muchos casos, una combinación de ambos enfoques puede ser la mejor solución,
utilizando referencias directas para relaciones críticas y IDs para aquellas que son menos
importantes o menos frecuentes.
* */
