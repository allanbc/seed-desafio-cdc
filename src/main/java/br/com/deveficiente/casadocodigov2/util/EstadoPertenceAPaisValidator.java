package br.com.deveficiente.casadocodigov2.util;

import br.com.deveficiente.casadocodigov2.entity.Estado;
import br.com.deveficiente.casadocodigov2.entity.Pais;
import br.com.deveficiente.casadocodigov2.model.compra.NovaCompraRequest;
import jakarta.persistence.EntityManager;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;

@Component
public class EstadoPertenceAPaisValidator implements Validator {

    private final EntityManager manager;

    public EstadoPertenceAPaisValidator(EntityManager manager) {
        this.manager = manager;
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

        if(request.idEstado() != null) {
            Pais pais = manager.find(Pais.class, request.idPais());
            Estado estado = manager.find(Estado.class, request.idEstado());
            if(!estado.pertenceAPais(pais)) {
                errors.rejectValue("idEstado", null, "Este estado não é do país selecionado");
            }
        }
    }
}
