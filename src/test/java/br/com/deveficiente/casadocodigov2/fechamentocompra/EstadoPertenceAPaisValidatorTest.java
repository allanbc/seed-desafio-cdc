package br.com.deveficiente.casadocodigov2.fechamentocompra;

import br.com.deveficiente.casadocodigov2.builder.CompraRequestBuilder;
import br.com.deveficiente.casadocodigov2.entity.Estado;
import br.com.deveficiente.casadocodigov2.entity.Pais;
import br.com.deveficiente.casadocodigov2.model.compra.NovaCompraRequest;
import br.com.deveficiente.casadocodigov2.model.compra.NovoPedidoItemRequest;
import br.com.deveficiente.casadocodigov2.model.compra.NovoPedidoRequest;
import br.com.deveficiente.casadocodigov2.util.EstadoPertenceAPaisValidator;
import jakarta.persistence.EntityManager;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.validation.BeanPropertyBindingResult;
import org.springframework.validation.Errors;

import java.math.BigDecimal;
import java.util.Set;

public class EstadoPertenceAPaisValidatorTest {

    private EntityManager manager = Mockito.mock(EntityManager.class);
    private Pais paisPadrao = new Pais("teste");
    private Set<NovoPedidoItemRequest> itens = Set.of(new NovoPedidoItemRequest(1L, 10));
    private NovoPedidoRequest pedidoRequest = new NovoPedidoRequest(BigDecimal.TEN, itens);
    private NovaCompraRequest request = CompraRequestBuilder.comEstadoECupom(
            "email@email.com", "nome", "sobrenome", "8967548654",
            "endereco", "complemento", "cidade",
            1L, 1L, "54534534", "25036240", pedidoRequest, "NAMORA20"
    );

    @Test
    @DisplayName("deveria validar uma compra com pais e estado pertencente ao pais")
    void testValidaCompraComPaisEEstadoPertecentePais() throws Exception {
        Estado estadoDoPais = new Estado("estado", paisPadrao);
        Mockito.when(manager.find(Pais.class, 1L)).thenReturn(paisPadrao);
        Mockito.when(manager.find(Estado.class, 1L)).thenReturn(estadoDoPais);

        Errors errors = new BeanPropertyBindingResult(request , "target");
        EstadoPertenceAPaisValidator validador = new EstadoPertenceAPaisValidator(
                manager);
        validador.validate(request, errors);

        Assertions.assertFalse(errors.hasErrors());
    }

    @Test
    @DisplayName("deveria bloquear compra com pais e estado não pertecente a compra")
    void testRejeitaEstadoQueNaoPertenceAoPais() throws Exception {
        // 1. Simula país padrão e outro país (para conflito)
        Pais paisInformado = new Pais("Brasil");
        Pais outroPais = new Pais("Argentina");

        // 2. Simula estado que pertence ao outro país
        Estado estadoDeOutroPais = new Estado("Buenos Aires", outroPais);

        // 3. Simula persistência no EntityManager
        Mockito.when(manager.find(Pais.class, 1L)).thenReturn(paisInformado);
        Mockito.when(manager.find(Estado.class, 1L)).thenReturn(estadoDeOutroPais);

        // 4. Cria request com país "Brasil" e estado "Buenos Aires"
        NovaCompraRequest request = CompraRequestBuilder.comEstadoECupom(
                "cliente@email.com",
                "Fulano",
                "Silva",
                "12345678900",
                "Rua das Acácias",
                "Apto 301",
                "São Paulo",
                1L,
                1L,
                "11999999999",
                "04567-000",
                pedidoRequest,
                "CUPOM20"
        );


        Errors errors = new BeanPropertyBindingResult(request , "target");
        EstadoPertenceAPaisValidator validador = new EstadoPertenceAPaisValidator(
                manager);
        validador.validate(request, errors);

        Assertions.assertEquals(1, errors.getAllErrors().size());
        Assertions.assertTrue(errors.hasFieldErrors("idEstado"));

        Assertions.assertEquals("Este estado não é do país selecionado", errors.getFieldError("idEstado").getDefaultMessage());
    }

    @Test
    @DisplayName("deveria deixar passar se a compra ta sem estado")
    void testPermteCompraSemEstado() throws Exception {

        NovaCompraRequest request = CompraRequestBuilder.comCupomSemEstado(
                "cliente@email.com",
                "Fulano",
                "Silva",
                "12345678900",
                "Rua das Acácias",
                "Apto 301",
                "São Paulo",
                1L,
                "11999999999",
                "04567-000",
                pedidoRequest,
                "CUPOM20"
        );

        Errors errors = new BeanPropertyBindingResult(request , "target");
        EstadoPertenceAPaisValidator validador = new EstadoPertenceAPaisValidator(
                manager);
        validador.validate(request, errors);

        Assertions.assertFalse(errors.hasErrors());
    }

    @Test
    @DisplayName("deveria parar se já tem erro de validacao no fluxo")
    void testDevePararSeTemErroDeValidacao() throws Exception {

        Errors errors = new BeanPropertyBindingResult(request , "target");
        errors.reject("codigoQualquer");

        EstadoPertenceAPaisValidator validador = new EstadoPertenceAPaisValidator(
                manager);
        validador.validate(request, errors);

        Assertions.assertEquals(1, errors.getAllErrors().size());
        Assertions.assertEquals("codigoQualquer",errors.getGlobalErrors().get(0).getCode());
    }
}
