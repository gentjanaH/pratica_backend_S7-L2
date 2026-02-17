package gentjanahani.u2w7l2.repository;

import gentjanahani.u2w7l2.entities.Viaggio;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.UUID;

@Repository
public interface ViaggioRepository extends JpaRepository<Viaggio, UUID> {
    Viaggio findByIdViaggio(UUID idViaggio);
}
