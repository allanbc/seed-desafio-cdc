package br.com.deveficiente.casadocodigov2.service;

import br.com.deveficiente.casadocodigov2.entity.*;
import br.com.deveficiente.casadocodigov2.model.compra.NovaCompraRequest;
import br.com.deveficiente.casadocodigov2.model.compra.NovoPedidoRequest;
import br.com.deveficiente.casadocodigov2.repository.*;
import org.slf4j.Logger;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.text.Format;
import java.util.Objects;
import java.util.function.Function;

import static org.slf4j.LoggerFactory.getLogger;

@Service
public class CompraService {

    private final CompraRepository compraRepository;
    private final PaisRepository paisRepository;
    private final EstadoRepository estadoRepository;
    private final LivroRepository livroRepository;

    private static final Logger LOG = getLogger(AutorService.class);

    public CompraService(CompraRepository compraRepository, PaisRepository paisRepository, EstadoRepository estadoRepository, LivroRepository livroRepository) {
        this.compraRepository = compraRepository;
        this.paisRepository = paisRepository;
        this.estadoRepository = estadoRepository;
        this.livroRepository = livroRepository;
    }

    @Transactional
    public Compra compra(NovaCompraRequest request) {
        LOG.info("Cadastrando nova compra: {}", request);
        Pais pais = paisRepository.getReferenceById(request.idPais());
        Estado estado = new Estado();
        if(request.idEstado() != null) {
            estado = estadoRepository.getReferenceById(request.idEstado());
        }

        // Criar um novo pedido
        NovoPedidoRequest novoPedidoRequest = new NovoPedidoRequest(request.pedido().total(), request.pedido().itens());
//        Pedido pedido = novoPedidoRequest.toRequestItemPedido(request.pedido(), livroRepository);

        Function<Compra, Pedido> funcaoCriacaoPedido = novoPedidoRequest.toRequestItemPedido2(livroRepository);

        // Associar o pedido à compra
        var compra = new Compra(request, estado, pais, Objects.requireNonNull(funcaoCriacaoPedido));
//        compra.setPedido(pedido);

        // Salvar o pedido antes de salvar a compra
//        pedidoRepository.save(compra.getPedido());

        // Salvar a compra
        return compraRepository.save(compra);
    }
}
