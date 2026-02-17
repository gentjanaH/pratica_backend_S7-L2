package gentjanahani.u2w7l2.services;

import gentjanahani.u2w7l2.entities.Viaggio;
import gentjanahani.u2w7l2.exceptions.NotFoundException;
import gentjanahani.u2w7l2.payloads.ViaggioDTO;
import gentjanahani.u2w7l2.payloads.ViaggioStatusDTO;
import gentjanahani.u2w7l2.repository.ViaggioRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
@Slf4j
public class ViaggioService {

    private final ViaggioRepository viaggioRepository;

    @Autowired
    public ViaggioService(ViaggioRepository viaggioRepository) {
        this.viaggioRepository = viaggioRepository;
    }

    public Viaggio save(ViaggioDTO payload) {

        Viaggio newViaggio = new Viaggio(payload.destination(), payload.travelDate());
        Viaggio savedViaggio = this.viaggioRepository.save(newViaggio);
        log.info("IL viaggio è stato salvato correttamente con id {}", savedViaggio.getIdViaggio());
        return savedViaggio;
    }

    public Viaggio findViaggioById(UUID idViaggio) {
        Viaggio viaggio = viaggioRepository.findByIdViaggio(idViaggio);
        if (viaggio == null) throw new NotFoundException(idViaggio);
        return viaggio;
    }

    public Viaggio findAndUpdateStato(UUID idViaggio, ViaggioStatusDTO payload) {
        Viaggio viaggio = viaggioRepository.findByIdViaggio(idViaggio);
        if (viaggio == null) throw new NotFoundException(idViaggio);
        viaggio.setStato(payload.stato());
        return viaggioRepository.save(viaggio);
    }

    public Viaggio findAndUpdateViaggio(UUID idViaggio, ViaggioDTO payload) {
        Viaggio viaggio = viaggioRepository.findByIdViaggio(idViaggio);
        if (viaggio == null) throw new NotFoundException(idViaggio);
        viaggio.setDestination(payload.destination());
        viaggio.setTravelDate(payload.travelDate());
        return viaggioRepository.save(viaggio);
    }

    public void findAndDelete(UUID idViaggio) {
        Viaggio viaggio = viaggioRepository.findByIdViaggio(idViaggio);
        viaggioRepository.delete(viaggio);
    }
}
