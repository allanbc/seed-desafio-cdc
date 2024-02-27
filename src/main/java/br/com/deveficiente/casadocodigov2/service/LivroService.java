package br.com.deveficiente.casadocodigov2.service;

import br.com.deveficiente.casadocodigov2.entity.Autor;
import br.com.deveficiente.casadocodigov2.entity.Categoria;
import br.com.deveficiente.casadocodigov2.entity.Livro;
import br.com.deveficiente.casadocodigov2.exception.LivroNotFoundException;
import br.com.deveficiente.casadocodigov2.model.livro.CadastroLivroRequest;
import br.com.deveficiente.casadocodigov2.model.livro.LivroDetalheResponse;
import br.com.deveficiente.casadocodigov2.model.livro.LivroResponse;
import br.com.deveficiente.casadocodigov2.repository.AutorRepository;
import br.com.deveficiente.casadocodigov2.repository.CategoriaRepository;
import br.com.deveficiente.casadocodigov2.repository.LivroRepository;
import org.slf4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

import static org.slf4j.LoggerFactory.getLogger;

@Service
public class LivroService {
    private static final Logger LOG = getLogger(LivroService.class);
    @Autowired
    private LivroRepository livroRepository;
    @Autowired
    private AutorRepository  autorRepository;
    @Autowired
    private CategoriaRepository categoriaRepository;

    @Transactional
    public Livro create(CadastroLivroRequest request) {
        LOG.info("Cadastrando um livro: {}", request);
        Autor autor = autorRepository.getReferenceById(request.autor().getId());
        Categoria categoria = categoriaRepository.getReferenceById(request.categoria().getId());
        return livroRepository.save(new Livro(autor, categoria, request));
    }
    public List<LivroResponse> listar() {
        LOG.info("Buscando livros");
        return livroRepository
                .findAll()
                .stream()
                .map(LivroResponse::new)
                .toList();
    }

    public LivroDetalheResponse getByProdutoDetalhe(Long id) {
        LOG.info("Buscando detalhe de um livro");
        return livroRepository
                .findById(id)
                .map(LivroDetalheResponse::new)
                .orElseThrow(() -> {
                    LOG.warn("Erro ao detalhar livro");
                    return new LivroNotFoundException(id);
                });
    }
}
