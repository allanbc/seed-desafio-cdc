package br.com.deveficiente.casadocodigov2.service;

import br.com.deveficiente.casadocodigov2.entity.Pais;
import br.com.deveficiente.casadocodigov2.repository.PaisRepository;
import org.slf4j.Logger;
import org.springframework.stereotype.Service;

import java.util.List;

import static org.slf4j.LoggerFactory.getLogger;

@Service
public class PaisService {
    private static final Logger LOG = getLogger(PaisService.class);
    private final PaisRepository paisRepository;

    public PaisService(PaisRepository paisRepository) {
        this.paisRepository = paisRepository;
    }

    public Pais createPais(Pais request) {
        LOG.info("Service que cadastra um pais");
        return paisRepository.save(request);
    }

    public List<Pais> getPaises() {
        LOG.info("Buscando países");
        return paisRepository
                .findAll()
                .stream()
                .map(Pais::toResponse)
                .toList();
    }
}
