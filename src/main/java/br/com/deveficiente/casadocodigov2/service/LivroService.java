package br.com.deveficiente.casadocodigov2.service;

import br.com.deveficiente.casadocodigov2.entity.Autor;
import br.com.deveficiente.casadocodigov2.entity.Categoria;
import br.com.deveficiente.casadocodigov2.entity.Livro;
import br.com.deveficiente.casadocodigov2.exception.LivroNotFoundException;
import br.com.deveficiente.casadocodigov2.model.livro.NovoLivroRequest;
import br.com.deveficiente.casadocodigov2.model.livro.LivroDetalheResponse;
import br.com.deveficiente.casadocodigov2.model.livro.LivroResponse;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import org.slf4j.Logger;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

import static org.slf4j.LoggerFactory.getLogger;

@Service
public class LivroService {
    private static final Logger LOG = getLogger(LivroService.class);

    @PersistenceContext
    private final EntityManager manager;

    public LivroService(EntityManager manager) {
        this.manager = manager;
    }

    @Transactional
    public Livro create(NovoLivroRequest request) {
        LOG.info("Cadastrando um livro: {}", request);

        Livro novoLivro = request.toModelLivro(
                id -> manager.find(Autor.class, request.idAutor()),
                id -> manager.find(Categoria.class, request.idCategoria())
        );

        manager.persist(novoLivro);

        return novoLivro;
    }
//    public List<LivroResponse> listar() {
//        LOG.info("Buscando livros");
//        return livroRepository
//                .findAll()
//                .stream()
//                .map(LivroResponse::new)
//                .toList();
//    }
    public LivroDetalheResponse getByProdutoDetalhe(Long id) {
        LOG.info("Buscando detalhe de um livro");
        return Optional.ofNullable(manager.find(Livro.class, id))
                .map(LivroDetalheResponse::new)
                .orElseThrow(() -> {
                    LOG.warn("Erro ao detalhar livro");
                    return new LivroNotFoundException(id);
                });
    }
}
