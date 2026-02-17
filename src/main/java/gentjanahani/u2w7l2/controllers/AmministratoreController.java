package gentjanahani.u2w7l2.controllers;

import gentjanahani.u2w7l2.entities.Dipendente;
import gentjanahani.u2w7l2.exceptions.ValidationException;
import gentjanahani.u2w7l2.payloads.DipendenteDTO;
import gentjanahani.u2w7l2.services.DipendenteService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/amministratore")
public class AmministratoreController {
    private final DipendenteService dipendenteService;

    @Autowired
    public AmministratoreController(DipendenteService dipendenteService) {
        this.dipendenteService = dipendenteService;
    }

    @PreAuthorize("hasAuthority('AMMINISTRATORE')")
    // 1. POST http://localhost:3026/dipendenti (+ Payload)
    @PostMapping("/dipendenti")

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

}
