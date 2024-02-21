package br.com.deveficiente.casadocodigov2.service;

import br.com.deveficiente.casadocodigov2.entity.Autor;
import br.com.deveficiente.casadocodigov2.exception.AutorCadastradoException;
import br.com.deveficiente.casadocodigov2.exception.AutorNotFoundException;
import br.com.deveficiente.casadocodigov2.model.AutorResponse;
import br.com.deveficiente.casadocodigov2.model.CadastroAutorRequest;
import br.com.deveficiente.casadocodigov2.repository.AutorRepository;
import org.slf4j.Logger;
import org.springframework.stereotype.Service;

import static org.slf4j.LoggerFactory.getLogger;

@Service
public class AutorService {
    private static final Logger LOG = getLogger(AutorService.class);
    private final AutorRepository autorRepository;

    public AutorService(AutorRepository autorRepository) {
        this.autorRepository = autorRepository;
    }

    public Autor create(CadastroAutorRequest autorForm) {
        LOG.info("Cadastrando autor: {}", autorForm);
        var verificaCadastro = autorRepository.existsByEmail(autorForm.email());
        if(verificaCadastro) {
            throw new AutorCadastradoException("Autor já está cadastrado para o e-mail informado!");
        }
        return autorRepository.save(new Autor(autorForm));
    }

}
