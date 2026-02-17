package gentjanahani.u2w7l2.services;

import gentjanahani.u2w7l2.entities.Dipendente;
import gentjanahani.u2w7l2.entities.Prenotazione;
import gentjanahani.u2w7l2.entities.Viaggio;
import gentjanahani.u2w7l2.exceptions.BadRequestException;
import gentjanahani.u2w7l2.payloads.PrenotazioneDTO;
import gentjanahani.u2w7l2.repository.PrenotazioneRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
@Slf4j
public class PrenotazioneService {

    private final PrenotazioneRepository prenotazioneRepository;
    private final ViaggioService viaggioService;
    private final DipendenteService dipendenteService;

    @Autowired
    public PrenotazioneService(PrenotazioneRepository prenotazioneRepository, ViaggioService viaggioService, DipendenteService dipendenteService) {
        this.prenotazioneRepository = prenotazioneRepository;
        this.viaggioService = viaggioService;
        this.dipendenteService = dipendenteService;
    }

    public Prenotazione save(PrenotazioneDTO payload) {
        Viaggio viaggio = viaggioService.findViaggioById(payload.idViaggio());
        Dipendente dipendente = dipendenteService.findDipendenteById(payload.idDipendente());

        boolean alreadyBookedSameDay = prenotazioneRepository.existsByDipendente_idDipendenteAndDataRichiesta(payload.idDipendente(), payload.dataRichiesta());
        if (alreadyBookedSameDay)
            throw new BadRequestException("Il dipendente ha gia una prenotazione in questo giorno");

        boolean alreadyBookedSameTrip = prenotazioneRepository.existsByDipendente_idDipendenteAndViaggio_idViaggio(payload.idDipendente(), payload.idViaggio());
        if (alreadyBookedSameTrip)
            throw new BadRequestException("Il dipendente risulta gia registrato per questo viaggio");

        Prenotazione prenotazione = new Prenotazione(payload.dataRichiesta(), payload.note(), viaggio, dipendente);
        viaggio.setStato("prenotato");
        return prenotazioneRepository.save(prenotazione);
    }

}
