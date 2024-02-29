package br.com.deveficiente.casadocodigov2.service;

import br.com.deveficiente.casadocodigov2.entity.Estado;
import br.com.deveficiente.casadocodigov2.entity.Pais;
import br.com.deveficiente.casadocodigov2.repository.EstadoRepository;
import br.com.deveficiente.casadocodigov2.repository.PaisRepository;
import org.slf4j.Logger;
import org.springframework.stereotype.Service;

import java.util.List;

import static org.slf4j.LoggerFactory.getLogger;

@Service
public class EstadoService {
    private static final Logger LOG = getLogger(EstadoService.class);
    private final EstadoRepository estadoRepository;
    private final PaisRepository paisRepository;

    public EstadoService(EstadoRepository estadoRepository, PaisRepository paisRepository) {
        this.estadoRepository = estadoRepository;
        this.paisRepository = paisRepository;
    }

    public Estado createEstado(Estado request) {
        LOG.info("Service que cadastra um estado");
        Pais pais = paisRepository.getReferenceById(request.getPais().getId());
        return estadoRepository.save(new Estado().toRequest(request, pais));
    }
    public List<Estado> getEstados() {
        LOG.info("Buscando estados");
        return estadoRepository
                .findAll()
                .stream()
                .map(Estado::toResponse)
                .toList();
    }
}
