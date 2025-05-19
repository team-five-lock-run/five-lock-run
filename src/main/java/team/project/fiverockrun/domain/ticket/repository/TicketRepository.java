package team.project.fiverockrun.domain.ticket.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import team.project.fiverockrun.domain.ticket.entity.Ticket;

public interface TicketRepository extends JpaRepository<Ticket, Long> {
}
