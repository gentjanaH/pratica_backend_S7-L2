package gentjanahani.u2w7l2.controllers;

import gentjanahani.u2w7l2.entities.Dipendente;
import gentjanahani.u2w7l2.exceptions.ValidationException;
import gentjanahani.u2w7l2.payloads.DipendenteDTO;
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


    // http://localhost:3026/dipendenti/{idDipendente}
    @PutMapping("/{idDipendente}")
    public DipendenteDTO getAndUpdate(@PathVariable UUID idDipendente, @RequestBody UpdateDipendenteDTO payload) {
        Dipendente aggiornato = dipendenteService.updateDipendente(idDipendente, payload);
        return new DipendenteDTO(aggiornato);
    }


    @GetMapping
    public List<Dipendente> getDipendenti() {
        return this.dipendenteService.getAllDipendenti();
    }

    @GetMapping("/{idDipendente}")
    public DipendenteDTO getDipendente(@PathVariable UUID idDipendente) {
        Dipendente dipendente = dipendenteService.findDipendenteById(idDipendente);
        return new DipendenteDTO(dipendente);
    }

    // http://localhost:3026/dipendenti/{idDipendente}
    @DeleteMapping("/{idDipendente}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void findAndDelete(@PathVariable UUID idDipendente) {
        dipendenteService.findAndDelete(idDipendente);
    }

    // 1. POST http://localhost:3026/dipendenti (+ Payload)
    @PreAuthorize("hasAuthority('AMMINISTRATORE')")
    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public Dipendente createDipendente(@RequestBody @Validated DipendenteDTO payload, BindingResult validationResult) {
        if (validationResult.hasErrors()) {
            List<String> errorsList = validationResult.getFieldErrors()
                    .stream()
                    .map(fieldError -> fieldError.getDefaultMessage())
                    .toList();

            throw new ValidationException(errorsList);
        } else {
            return this.dipendenteService.save(payload);
        }
    }

    // 2. PATCH http://localhost:3026/dipendenti/{idDipendente}
    @PatchMapping("/{idDipendente}/avatar")
    public Dipendente uploadImage(@PathVariable UUID idDipendente, @RequestParam("user_picture") MultipartFile file) {
        return this.dipendenteService.uploadAvatar(idDipendente, file);


    }
}
