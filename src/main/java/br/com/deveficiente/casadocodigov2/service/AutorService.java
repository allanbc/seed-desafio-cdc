package br.com.deveficiente.casadocodigov2.service;

import br.com.deveficiente.casadocodigov2.entity.Autor;
import br.com.deveficiente.casadocodigov2.exception.AutorNotFoundException;
import br.com.deveficiente.casadocodigov2.model.autor.AutorResponse;
import br.com.deveficiente.casadocodigov2.model.autor.CadastroAutorRequest;
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

    public Autor create(CadastroAutorRequest request) {
        LOG.info("Cadastrando autor: {}", request);
        return autorRepository.save(new Autor(request));
    }

    public AutorResponse getById(Long id) {

        LOG.info("Buscando autor por ID: {}", id);
        var autor = autorRepository.findById(id)
                .map(AutorResponse::new)
                .orElseThrow(() -> {
                    LOG.warn("Autor não encontrado");
                    return new AutorNotFoundException(id);
                });

        LOG.info("Autor encontrado: {}", autor);
        return autor;
    }
}
