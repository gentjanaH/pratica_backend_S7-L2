package gentjanahani.u2w7l2.controllers;

import gentjanahani.u2w7l2.entities.Dipendente;
import gentjanahani.u2w7l2.exceptions.ValidationException;
import gentjanahani.u2w7l2.payloads.DipendenteDTO;
import gentjanahani.u2w7l2.payloads.RuoloDipendenteDTO;
import gentjanahani.u2w7l2.services.DipendenteService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/admin")
public class AmministratoreController {
    private final DipendenteService dipendenteService;

    @Autowired
    public AmministratoreController(DipendenteService dipendenteService) {
        this.dipendenteService = dipendenteService;
    }
    //controller amministratore
    //l'amministratore può:
    //-accedere alla lista dipendenti;
    //-accedere alle informazioni di un singolo dipendente;
    //-aggiungere dipendenti;
    //-eliminare dipendenti;
    //-modificare il ruolo dei dipendenti

    @GetMapping
    public List<Dipendente> getDipendenti() {
        return this.dipendenteService.getAllDipendenti();
    }

    @GetMapping("/{idDipendente}")
    public DipendenteDTO getDipendente(@PathVariable UUID idDipendente) {
        Dipendente dipendente = dipendenteService.findDipendenteById(idDipendente);
        return new DipendenteDTO(dipendente);
    }

    // 1. POST http://localhost:3026/admin (+ Payload)
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

    //3. PATCH http://localhost:3026/admin/dipendenti/{idDipendente}/role
    @PatchMapping("/{idDipendente}/role")
    @PreAuthorize("hasAuthority('AMMINISTRATORE')")
    public Dipendente cambiaRuolo(@PathVariable UUID idDipendente, @RequestBody @Validated RuoloDipendenteDTO payload) {
        return dipendenteService.cambiaRuolo(idDipendente, payload.role());

    }

    //4. DELETE http://localhost:3026/dipendenti/{idDipendente}
    @DeleteMapping("/{idDipendente}")
    @PreAuthorize("hasAuthority('AMMINISTRATORE')")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void findAndDelete(@PathVariable UUID idDipendente) {
        dipendenteService.findAndDelete(idDipendente);
    }
}
