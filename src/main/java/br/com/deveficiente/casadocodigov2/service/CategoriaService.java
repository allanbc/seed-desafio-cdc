package br.com.deveficiente.casadocodigov2.service;

import br.com.deveficiente.casadocodigov2.entity.Categoria;
import br.com.deveficiente.casadocodigov2.model.categoria.CadastroCategoriaRequest;
import br.com.deveficiente.casadocodigov2.repository.CategoriaRepository;
import org.slf4j.Logger;
import org.springframework.stereotype.Service;

import java.util.Optional;

import static org.slf4j.LoggerFactory.getLogger;

@Service
public class CategoriaService {

    private static final Logger LOG = getLogger(CategoriaService.class);
    private final CategoriaRepository categoriaRepository;

    public CategoriaService(CategoriaRepository categoriaRepository) {
        this.categoriaRepository = categoriaRepository;
    }

    public Categoria create(CadastroCategoriaRequest request) {
        LOG.info("Cadastrando uma categoria: {}", request);
        return categoriaRepository.save(new Categoria(request));
    }

    public Categoria findById(Long id) {
        Optional<Categoria> categoria = categoriaRepository.findById(id);
        return categoria.orElseThrow(() -> new RuntimeException("Categoria não encontrada"));
    }
}
