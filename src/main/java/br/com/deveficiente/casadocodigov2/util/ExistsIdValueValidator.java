package br.com.deveficiente.casadocodigov2.util;

import br.com.deveficiente.casadocodigov2.entity.Categoria;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.persistence.Query;
import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;
import org.springframework.util.Assert;

import java.util.List;

public class ExistsIdValueValidator implements ConstraintValidator<ExistsId, Object> {

    private String domainAttribute;
    private Class<?> klass;
    @PersistenceContext
    private final EntityManager manager;

    public ExistsIdValueValidator(EntityManager manager) {
        this.manager = manager;
    }

    @Override
    public void initialize(ExistsId params) {
        domainAttribute = params.fieldName();
        klass = params.domainClass();
    }

    @Override
    public boolean isValid(Object value, ConstraintValidatorContext context) {
        Query query = manager.createQuery("select 1 from " +klass.getName() + " where " +domainAttribute+"=:value");
        Long valueParam = ((Categoria) value).getId();
        query.setParameter("value", valueParam);
        List<?> list = query.getResultList();
        Assert.isTrue(list.size() <=1, "Foi encontrado mais de um " + klass+ "com o atributo " +domainAttribute+"=+value");
        return !list.isEmpty();
    }
}
