package gentjanahani.u2w7l2.repository;

import gentjanahani.u2w7l2.entities.Amministratore;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;
import java.util.UUID;

@Repository
public interface AmministratoreRepository extends JpaRepository<Amministratore, UUID> {
    Optional<Amministratore> findByEmail(String email);

    Amministratore findByIdDipendente(UUID idAmministratore);
}
