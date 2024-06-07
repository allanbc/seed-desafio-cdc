package br.com.deveficiente.casadocodigov2.service;

import br.com.deveficiente.casadocodigov2.entity.*;
import br.com.deveficiente.casadocodigov2.model.compra.NovaCompraRequest;
import br.com.deveficiente.casadocodigov2.model.compra.NovoPedidoRequest;
import br.com.deveficiente.casadocodigov2.repository.*;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import org.slf4j.Logger;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Map;
import java.util.Objects;

import static org.slf4j.LoggerFactory.getLogger;

@Service
public class CompraService {
    private static final Logger LOG = getLogger(AutorService.class);
//    private final LivroRepository livroRepository;
    private final CupomRepository cupomRepository;
    @PersistenceContext
    private final EntityManager manager;

    public CompraService(/*LivroRepository livroRepository,*/
                         CupomRepository cupomRepository, EntityManager manager) {
//        this.livroRepository = livroRepository;
        this.cupomRepository = cupomRepository;
        this.manager = manager;
    }

    @Transactional
    public Compra compra(NovaCompraRequest request) {
        LOG.info("Cadastrando nova compra: {}", request);
        //Associando país e estado a compra
        Map<String, Object> paisEstado = request.getEstadoPais(manager);

        // Criar um novo pedido
        var novoPedidoRequest = new NovoPedidoRequest(request.pedido().total(), request.pedido().itens());

        var funcaoCriacaoPedido = novoPedidoRequest.toRequestItemPedido2(manager);

        // Associar o pedido à compra
        var compra = new Compra(request, (Estado) paisEstado.get("estado"), (Pais) paisEstado.get("pais"), Objects.requireNonNull(funcaoCriacaoPedido));

        //Aplicar cupom
        var novaCompra = request.aplicaCupom(compra, request, cupomRepository);

        // Salvar a compra
        manager.persist(novaCompra);

        return novaCompra;
    }
}
