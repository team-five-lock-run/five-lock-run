package team.project.fiverockrun.domain.ticket.repository;

import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import team.project.fiverockrun.domain.ticket.entity.Ticket;

public interface TicketRepository extends JpaRepository<Ticket, Long> {
    List<Ticket> findAllByUserId(Long userId);
}