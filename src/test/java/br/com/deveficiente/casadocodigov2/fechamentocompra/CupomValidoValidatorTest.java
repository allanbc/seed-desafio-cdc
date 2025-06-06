package br.com.deveficiente.casadocodigov2.fechamentocompra;

import br.com.deveficiente.casadocodigov2.builder.CompraRequestBuilder;
import br.com.deveficiente.casadocodigov2.entity.Cupom;
import br.com.deveficiente.casadocodigov2.model.compra.NovaCompraRequest;
import br.com.deveficiente.casadocodigov2.model.compra.NovoPedidoItemRequest;
import br.com.deveficiente.casadocodigov2.model.compra.NovoPedidoRequest;
import br.com.deveficiente.casadocodigov2.repository.CupomRepository;
import br.com.deveficiente.casadocodigov2.util.CupomValidoValidator;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.test.util.ReflectionTestUtils;
import org.springframework.validation.BeanPropertyBindingResult;
import org.springframework.validation.Errors;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class CupomValidoValidatorTest {

    private CupomRepository cupomRepository  = Mockito.mock(CupomRepository.class);
    private Set<NovoPedidoItemRequest> itens = Set.of(new NovoPedidoItemRequest(1L, 10));
    private NovoPedidoRequest pedidoRequest = new NovoPedidoRequest(BigDecimal.TEN, itens);
    private NovaCompraRequest request = CompraRequestBuilder.comCupomSemEstado(
            "email@email.com", "nome", "sobrenome", "8967548654",
            "endereco", "complemento", "cidade",
            1L, "987454778", "54534534", pedidoRequest, "NAMORA20"
    );

    @Test
    @DisplayName("deveria parar caso o cupom esteja invalido")
    void testDevePararSeCupomInvalido() throws Exception {
        // Resultado esperado
        // Sem mais Expected 0 arguments but found 1 ✅
        // A instância é corretamente imutável ✅
        // O codigoCupom é passado na criação do record, como deve ser ✅
        // O cupom é simulado como expirado via ReflectionTestUtils ✅
        // A validação funciona como esperado ✅
        Cupom cupom = new Cupom("NAMORA20", BigDecimal.TEN, LocalDate.now().plusDays(1));
        //o cupom precisa ficar com uma validade para trás do dia de hoje
        ReflectionTestUtils.setField(cupom, "validade", LocalDate.now().minusDays(1));

        Mockito.when(cupomRepository.getByCodigo("NAMORA20")).thenReturn(cupom);

        //quando
        Errors errors = new BeanPropertyBindingResult(request , "target");
        CupomValidoValidator validador = new CupomValidoValidator(cupomRepository);
        validador.validate(request, errors);

        //então
        assertEquals(1, errors.getAllErrors().size());
        assertEquals("Este cupom não é mais válido",errors.getFieldError("codigoCupom").getDefaultMessage());
    }
    @Test
    @DisplayName("deve rejeitar cupom expirado")
    void deveRejeitarCupomExpirado() {
        // 1. Simula cupom com validade expirada
        Cupom cupomExpirado = new Cupom("FLA10", BigDecimal.TEN, LocalDate.now().plusDays(5));
        ReflectionTestUtils.setField(cupomExpirado, "validade", LocalDate.now().minusDays(1));
        Mockito.when(cupomRepository.getByCodigo("FLA10")).thenReturn(cupomExpirado);

        // 2. Monta pedido e compra com esse cupom
        Set<NovoPedidoItemRequest> itens = Set.of(new NovoPedidoItemRequest(1L, 1));
        NovoPedidoRequest pedidoRequest = new NovoPedidoRequest(BigDecimal.TEN, itens);

        NovaCompraRequest request = CompraRequestBuilder.comCupomSemEstado(
                "cliente@email.com",
                "Fulano",
                "da Silva",
                "12345678900",
                "Rua das Flores",
                "Apto 101",
                "São Paulo",
                1L,
                "11999999999",
                "01000-000",
                pedidoRequest,
                "FLA10"
        );

        // 3. Valida
        Errors errors = new BeanPropertyBindingResult(request, "novaCompraRequest");
        CupomValidoValidator validator = new CupomValidoValidator(cupomRepository);
        validator.validate(request, errors);

        // 4. Verifica erro esperado
        assertEquals(1, errors.getErrorCount());
        assertEquals("Este cupom não é mais válido", errors.getFieldError("codigoCupom").getDefaultMessage());
    }

}
