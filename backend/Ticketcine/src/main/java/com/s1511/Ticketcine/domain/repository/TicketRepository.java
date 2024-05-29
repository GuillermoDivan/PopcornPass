package com.s1511.Ticketcine.domain.repository;

import com.s1511.Ticketcine.domain.entities.Ticket;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface TicketRepository extends JpaRepository<Ticket, String> {
    List<Ticket> getTicketsByUserId(String userId);
}
