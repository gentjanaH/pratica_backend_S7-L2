package gentjanahani.u2w7l2.controllers;

import gentjanahani.u2w7l2.entities.Dipendente;
import gentjanahani.u2w7l2.exceptions.ValidationException;
import gentjanahani.u2w7l2.payloads.DipendenteDTO;
import gentjanahani.u2w7l2.payloads.LoginDTO;
import gentjanahani.u2w7l2.payloads.LoginResponseDTO;
import gentjanahani.u2w7l2.services.AuthorizationService;
import gentjanahani.u2w7l2.services.DipendenteService;
import org.springframework.http.HttpStatus;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/auth")
public class AthorizationController {

    private final DipendenteService dipendenteService;
    private final AuthorizationService authorizationService;

    public AthorizationController(DipendenteService dipendenteService, AuthorizationService authorizationService) {
        this.dipendenteService = dipendenteService;
        this.authorizationService = authorizationService;
    }

    // http://localhost:3026/auth/login
    @PostMapping("/login")
    public LoginResponseDTO login(@RequestBody LoginDTO bodyLogin) {
        return new LoginResponseDTO(this.authorizationService.checkAndGenerate(bodyLogin));
    }


}
