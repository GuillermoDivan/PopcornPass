package com.s1511.ticketcine.application.implementations;

import com.mercadopago.client.payment.PaymentClient;
import com.mercadopago.client.preference.PreferenceClient;
import com.mercadopago.client.preference.PreferenceItemRequest;
import com.mercadopago.client.preference.PreferenceRequest;
import com.mercadopago.exceptions.MPApiException;
import com.mercadopago.exceptions.MPException;
import com.mercadopago.resources.payment.Payment;
import com.mercadopago.resources.preference.Preference;
import com.s1511.ticketcine.application.dto.mercadopago.MercadoPagoResponse;
import com.s1511.ticketcine.application.dto.mercadopago.RequestTicketDto;
import com.s1511.ticketcine.domain.entities.Seat;
import com.s1511.ticketcine.domain.entities.Ticket;
import com.s1511.ticketcine.domain.repository.SeatRepository;
import com.s1511.ticketcine.domain.repository.TicketRepository;
import com.s1511.ticketcine.domain.services.MercadoPagoService;
import com.s1511.ticketcine.domain.services.SeatService;
import com.s1511.ticketcine.domain.services.TicketService;

import com.s1511.ticketcine.domain.utils.SeatEnum;
import jakarta.persistence.EntityNotFoundException;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class MercadoPagoServiceImpl implements MercadoPagoService {
    public final TicketService ticketService;
    public final TicketRepository ticketRepository;
    public final SeatService seatService;
    public final SeatRepository seatRepository;

    @Override
    @Transactional
    public String createPayment(RequestTicketDto requestTicketDto)
            throws MPException, MPApiException {
        String ticketId = ticketService.createTicket(requestTicketDto);
        PreferenceClient client = new PreferenceClient(); //Línea de comunicación a MP.

        // Este es el objeto comprado. Por body el front manda título, cantidad y precio.
        PreferenceItemRequest item =
                PreferenceItemRequest.builder()
                        .title(requestTicketDto.movieName())
                        .quantity(requestTicketDto.amountOfSeats())
                        .unitPrice(new BigDecimal(requestTicketDto.unitPrice()))
                        .build();

        List<PreferenceItemRequest> items = new ArrayList<>();
        items.add(item);

        PreferenceRequest request =
                PreferenceRequest.builder().items(items).externalReference(ticketId)
                        .notificationUrl("https://7332-2803-9800-9885-50d3-ddab-e829-9479-23f0.ngrok-free.app/mp/response?source_news=webhooks").build();
        //Con la lista se hace la petición, que funciona como cuerpo del post a MP.
        Preference preferenceResponse = client.create(request); //Genera el link para pagar esto (la request).

        return preferenceResponse.getSandboxInitPoint(); //Recupera el link que creó.
    }

    @Override
    public void processPayment(MercadoPagoResponse mercadoPagoResponse)
            throws MPException, MPApiException {
        PaymentClient client = new PaymentClient(); //Genera cliente de consulta de pago.
        Payment payment = client.get(mercadoPagoResponse.data().id()); //Recupera el pago que hizo.
        String ticketId = payment.getExternalReference(); //Id del ticket de esta app, generado en  createPayment.
        String paymentStatus = payment.getStatus(); //Status del pago que indica MP.
        if (paymentStatus.equals("approved")) {
            Ticket ticket = ticketRepository.findById(ticketId)
                    .orElseThrow(() -> new EntityNotFoundException(
                            "No se encuentra ticket con el id " + ticketId));
            ticket.setActive(true);

            for (Seat seat : ticket.getSeatsIds()){
                seat.setOccupied(true);
                seat.setSeatEnum(SeatEnum.OCCUPIED);
                seat.setTicket(ticket);
                seatRepository.save(seat);
            }

            Ticket savedTicket = ticketRepository.save(ticket);
        }
        seatService.lookForPreviousUser(ticketId);

        }
}
/* Nota de Guille hasta que esto esté en prod: Cada vez que se apaga el ngrok hay que
correr el comando  ngrok http http://localhost:8080/   de nuevo. =D */