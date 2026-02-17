package gentjanahani.u2w7l2.controllers;

import gentjanahani.u2w7l2.entities.Dipendente;
import gentjanahani.u2w7l2.exceptions.ValidationException;
import gentjanahani.u2w7l2.payloads.DipendenteDTO;
import gentjanahani.u2w7l2.payloads.RuoloDipendenteDTO;
import gentjanahani.u2w7l2.payloads.UpdateDipendenteDTO;
import gentjanahani.u2w7l2.services.DipendenteService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/dipendenti")
public class DipendenteController {

    private final DipendenteService dipendenteService;

    @Autowired
    public DipendenteController(DipendenteService dipendenteService) {
        this.dipendenteService = dipendenteService;
    }

    //controller dipendenti
    //un dipendente può:
    //-accedere al proprio profilo;
    //-modificare il proprio proprio profilo;
    //-aggiornare l'immagine profilo;


    // http://localhost:3026/dipendenti/{idDipendente}
    @PutMapping("/{idDipendente}")
    public DipendenteDTO getAndUpdate(@PathVariable UUID idDipendente, @RequestBody UpdateDipendenteDTO payload) {
        Dipendente aggiornato = dipendenteService.updateDipendente(idDipendente, payload);
        return new DipendenteDTO(aggiornato);
    }


    // 2. PATCH http://localhost:3026/dipendenti/{idDipendente}
    @PatchMapping("/{idDipendente}/avatar")
    public Dipendente uploadImage(@PathVariable UUID idDipendente, @RequestParam("user_picture") MultipartFile file) {
        return this.dipendenteService.uploadAvatar(idDipendente, file);


    }


}
