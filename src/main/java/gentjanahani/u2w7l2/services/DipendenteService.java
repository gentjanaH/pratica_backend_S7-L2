package gentjanahani.u2w7l2.services;

import com.cloudinary.Cloudinary;
import com.cloudinary.utils.ObjectUtils;
import gentjanahani.u2w7l2.entities.Dipendente;
import gentjanahani.u2w7l2.exceptions.BadRequestException;
import gentjanahani.u2w7l2.exceptions.NotFoundException;
import gentjanahani.u2w7l2.payloads.DipendenteDTO;
import gentjanahani.u2w7l2.payloads.UpdateDipendenteDTO;
import gentjanahani.u2w7l2.repository.DipendenteRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;
import java.util.Map;
import java.util.UUID;

@Service
@Slf4j
public class DipendenteService {

    private final DipendenteRepository dipendenteRepository;
    private final Cloudinary cloudinaryUploader;
    private final PasswordEncoder passwordEncoder;//dopo aver creato  il bean, lo passo nel costruttore del dipendenteService

    @Autowired
    public DipendenteService(DipendenteRepository dipendenteRepository, Cloudinary cloudinaryUploader, PasswordEncoder passwordEncoder) {
        this.dipendenteRepository = dipendenteRepository;
        this.cloudinaryUploader = cloudinaryUploader;
        this.passwordEncoder = passwordEncoder;
    }

    public Dipendente save(DipendenteDTO payload) {

        this.dipendenteRepository.findByEmail(payload.mail()).ifPresent(dipentente -> {
            throw new BadRequestException("L'email " + dipentente.getEmail() + "  è già in uso!");
        });

        this.dipendenteRepository.findByUsername(payload.username()).ifPresent(dipentente -> {
            throw new BadRequestException("Lo username" + dipentente.getUsername() + "  è già in uso!");
        });
        //ora invece di passarli direttamente la password dal payoload, uso il bean passwordEncoder
        Dipendente newDipendente = new Dipendente(payload.username(), payload.name(), payload.surname(), payload.mail(), passwordEncoder.encode(payload.password()));
        newDipendente.setAvatar("https://ui-avatars.com/api?name=" + payload.surname());

        Dipendente savedDip = this.dipendenteRepository.save(newDipendente);
        log.info("Il dipendente con id {} è stato salvato correttamente.", payload.surname());

        return savedDip;
    }

    public Dipendente findDipendenteById(UUID idDipendente) {
        Dipendente dipendente = dipendenteRepository.findByIdDipendente(idDipendente);
        if (dipendente == null) throw new NotFoundException(idDipendente);
        return dipendente;
    }

    public Dipendente findByEmail(String mail) {
        return this.dipendenteRepository.findByEmail(mail).orElseThrow(() -> new NotFoundException("Il dipendente con email " + mail + " non è stato trovato."));
    }

    public Dipendente updateDipendente(UUID idDipendente, UpdateDipendenteDTO payload) {
        Dipendente dipModificato = findDipendenteById(idDipendente);

        this.dipendenteRepository.findByUsername(payload.username()).ifPresent(dipentente -> {
            if (!dipentente.getIdDipendente().equals(idDipendente)) {
                throw new BadRequestException("Lo username" + dipentente.getUsername() + "  è già in uso!");
            }
        });
        dipModificato.setName(payload.name());
        dipModificato.setSurname(payload.surname());
        dipModificato.setUsername(payload.username());

        return dipendenteRepository.save(dipModificato);
    }

    public List<Dipendente> getAllDipendenti() {
        return dipendenteRepository.findAll();
    }

    public void findAndDelete(UUID idDipendente) {
        Dipendente dipendente = findDipendenteById(idDipendente);

        dipendenteRepository.delete(dipendente);
    }

    public Dipendente uploadAvatar(UUID idDipendente, MultipartFile file) {
        if (file.isEmpty()) throw new BadRequestException("Il file è vuoto");
        if (file.getSize() > 3_000_000) throw new BadRequestException("Il file è troppo grande");

        String contentType = file.getContentType();
        if (contentType == null || !(contentType.equals("image/jpeg") || contentType.equals("image/png") || contentType.equals("image/gif"))) {
            throw new BadRequestException("Sono ammessi solo file JPG, PNG, o GIF");
        }

        try {
            Map result = cloudinaryUploader.uploader().upload(file.getBytes(), ObjectUtils.emptyMap());
            String imgUrl = (String) result.get("secure_url");

            Dipendente dip = dipendenteRepository.findByIdDipendente(idDipendente);
            dip.setAvatar(imgUrl);
            dipendenteRepository.save(dip);


            return dip;


        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

}
