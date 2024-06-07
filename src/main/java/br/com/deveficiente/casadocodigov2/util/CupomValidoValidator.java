package br.com.deveficiente.casadocodigov2.util;

import br.com.deveficiente.casadocodigov2.entity.Cupom;
import br.com.deveficiente.casadocodigov2.model.compra.NovaCompraRequest;
import br.com.deveficiente.casadocodigov2.repository.CupomRepository;
import org.springframework.stereotype.Component;
import org.springframework.util.Assert;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;

import java.util.Objects;
import java.util.Optional;

@Component
public class CupomValidoValidator implements Validator {

    private final CupomRepository cupomRepository;

    public CupomValidoValidator(CupomRepository cupomRepository) {
        this.cupomRepository = cupomRepository;
    }

    @Override
    public boolean supports(Class<?> clazz) {
        return NovaCompraRequest.class.isAssignableFrom(clazz);
    }

    @Override
    public void validate(Object target, Errors errors) {
        if(errors.hasErrors()) {
            return;
        }

        NovaCompraRequest request = (NovaCompraRequest) target;
        Optional<String> possivelCodigo = Optional.ofNullable(request.codigoCupom());

        if(possivelCodigo.isPresent()) {
            Cupom cupom = cupomRepository.getByCodigo(possivelCodigo.get());
            Assert.state(Objects.nonNull(cupom), "O código do cupom precisa existir neste ponto da validacao");
            if(!cupom.isValid()) {
                errors.rejectValue("codigoCupom", null, "Este cupom não é mais válido");
            }
        }
    }
}
