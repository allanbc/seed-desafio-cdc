package br.com.deveficiente.casadocodigov2.service;

import br.com.deveficiente.casadocodigov2.entity.Estado;
import br.com.deveficiente.casadocodigov2.entity.Pais;
import br.com.deveficiente.casadocodigov2.model.livro.LivroDetalheResponse;
import br.com.deveficiente.casadocodigov2.model.livro.LivroResponse;
import br.com.deveficiente.casadocodigov2.repository.EstadoRepository;
import br.com.deveficiente.casadocodigov2.repository.PaisRepository;
import org.slf4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

import static org.slf4j.LoggerFactory.getLogger;

@Service
public class DominioService {
    private static final Logger LOG = getLogger(DominioService.class);
    @Autowired
    private EstadoRepository estadoRepository;

    @Autowired
    private PaisRepository paisRepository;

    public Estado createEstado(Estado request) {
        LOG.info("Service que cadastra um estado");
        Pais pais = paisRepository.getReferenceById(request.getPais().getId());
        return estadoRepository.save(new Estado().toRequest(request, pais));
    }
    public Pais createPais(Pais request) {
        LOG.info("Service que cadastra um pais");
        return paisRepository.save(new Pais().toRequest(request));
    }

    public List<Pais> getPaises() {
        LOG.info("Buscando países");
        return paisRepository
                .findAll()
                .stream()
                .map(Pais::toResponse)
                .toList();
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
