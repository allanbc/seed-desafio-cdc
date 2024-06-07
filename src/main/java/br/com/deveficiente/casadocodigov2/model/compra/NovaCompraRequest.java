package br.com.deveficiente.casadocodigov2.model.compra;

import br.com.deveficiente.casadocodigov2.entity.Compra;
import br.com.deveficiente.casadocodigov2.entity.Cupom;
import br.com.deveficiente.casadocodigov2.entity.Estado;
import br.com.deveficiente.casadocodigov2.entity.Pais;
import br.com.deveficiente.casadocodigov2.repository.CupomRepository;
import br.com.deveficiente.casadocodigov2.util.Documento;
import br.com.deveficiente.casadocodigov2.util.ExistsId;
import jakarta.persistence.EntityManager;
import jakarta.validation.Valid;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import org.springframework.util.StringUtils;

import java.util.HashMap;
import java.util.Map;

public record NovaCompraRequest(
        @Email
        @NotBlank
        String email,
        @NotBlank
        String nome,
        @NotBlank
        String sobrenome,
        @NotBlank
        @Documento
        String documento,
        @NotBlank
        String endereco,
        @NotBlank
        String complemento,
        @NotBlank
        String cidade,
        @NotNull
        @ExistsId(domainClass = Pais.class, fieldName = "id")
        Long idPais,
        @NotNull
        @ExistsId(domainClass = Estado.class, fieldName = "id")
        Long idEstado,
        @NotBlank
        String telefone,
        @NotBlank
        String cep,
        @Valid
        @NotNull
        NovoPedidoRequest pedido,
        @ExistsId(domainClass = Cupom.class, fieldName = "codigo")
        String codigoCupom) {

        public Compra aplicaCupom(Compra compra, NovaCompraRequest request, CupomRepository cupomRepository) {
                if(StringUtils.hasText(request.codigoCupom())){
                        compra.aplicaCupom(cupomRepository.getByCodigo(request.codigoCupom()));
                }
                return compra;
        }

        public Map<String, Object> getEstadoPais(EntityManager manager) {
                Map<String, Object> resultado = new HashMap<>();
//                Pais pais = paisRepository.getReferenceById(idPais);
                Pais pais = manager.find(Pais.class, idPais);
                if(idEstado != null) {
//                        Estado estado = estadoRepository.getReferenceById(idEstado);
                        Estado estado = manager.find(Estado.class, idEstado);
                        resultado.put("pais", pais);
                        resultado.put("estado", estado);
                }
                return resultado;
        }
}
