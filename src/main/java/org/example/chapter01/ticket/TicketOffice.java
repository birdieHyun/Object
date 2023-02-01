package org.example.chapter01.ticket;

import java.util.ArrayList;
import java.util.List;

public class TicketOffice {

    private Long amount;
    private List<Ticket> ticket = new ArrayList<>();

    public TicketOffice(Long amount, List<Ticket> ticket) {
        this.amount = amount;
        this.ticket.addAll(ticket);
    }

    public Ticket getTicket() {
        return ticket.remove(0);
    }

    public void minusAmount(Long amount) {
        this.amount -= amount;
    }

    public void plusAmount(Long amount) {
        this.amount += amount;
    }
}
