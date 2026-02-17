package gentjanahani.u2w7l2.controllers;

import gentjanahani.u2w7l2.entities.Viaggio;
import gentjanahani.u2w7l2.exceptions.ValidationException;
import gentjanahani.u2w7l2.payloads.ViaggioDTO;
import gentjanahani.u2w7l2.payloads.ViaggioStatusDTO;
import gentjanahani.u2w7l2.services.ViaggioService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/viaggi")
public class ViaggioController {

    private final ViaggioService viaggioService;

    @Autowired
    public ViaggioController(ViaggioService viaggioService) {
        this.viaggioService = viaggioService;
    }

    //I viaggi sono gestiti dall'organizzatore ma anche dall'amministratore
    //l'organizzatore può:
    //-aggiungere viaggi;
    //-modificare viaggi;
    //-settare lo stato di un viaggio;
    //-eliminare un viaggio;

    // 1. POST http://localhost:3026/viaggi (+ Payload)
    @PreAuthorize("hasAnyAuthority('AMMINISTRATORE', 'ORGANIZZATORE')")
    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public Viaggio createViaggio(@RequestBody @Validated ViaggioDTO payload, BindingResult validationResult) {
        if (validationResult.hasErrors()) {
            List<String> errorsList = validationResult.getFieldErrors()
                    .stream()
                    .map(fieldError -> fieldError.getDefaultMessage())
                    .toList();

            throw new ValidationException(errorsList);
        } else {
            return this.viaggioService.save(payload);
        }
    }

    // 2. PUT http://localhost:3026/viaggi/{idViaggio} (+ Payload)
    @PreAuthorize("hasAnyAuthority('AMMINISTRATORE', 'ORGANIZZATORE')")
    @PatchMapping("/{idViaggio}")
    @ResponseStatus(HttpStatus.ACCEPTED)
    public Viaggio findAndUpdateViaggio(@PathVariable UUID idViaggio, @RequestBody @Validated ViaggioDTO payload, BindingResult validationResult) {
        if (validationResult.hasErrors()) {
            List<String> errorsList = validationResult.getFieldErrors()
                    .stream()
                    .map(fieldError -> fieldError.getDefaultMessage())
                    .toList();

            throw new ValidationException(errorsList);
        } else {
            return this.viaggioService.findAndUpdateViaggio(idViaggio, payload);
        }
    }

    // 3. PATCH http://localhost:3026/viaggi/{idViaggio} (+ Payload)
    @PreAuthorize("hasAnyAuthority('AMMINISTRATORE', 'ORGANIZZATORE')")
    @PatchMapping("/{idViaggio}")
    @ResponseStatus(HttpStatus.ACCEPTED)
    public Viaggio setStatusViaggio(@PathVariable UUID idViaggio, @RequestBody @Validated ViaggioStatusDTO payload, BindingResult validationResult) {
        if (validationResult.hasErrors()) {
            List<String> errorsList = validationResult.getFieldErrors()
                    .stream()
                    .map(fieldError -> fieldError.getDefaultMessage())
                    .toList();

            throw new ValidationException(errorsList);
        } else {
            return this.viaggioService.findAndUpdateStato(idViaggio, payload);
        }
    }

    //4. DELETE http://localhost:3026/viaggi/{idViaggio}
    @DeleteMapping("/{idViaggio}")
    @PreAuthorize("hasAnyAuthority('AMMINISTRATORE', 'ORGANIZZATORE')")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void findAndDelete(@PathVariable UUID idViaggio) {
        viaggioService.findAndDelete(idViaggio);
    }
}
