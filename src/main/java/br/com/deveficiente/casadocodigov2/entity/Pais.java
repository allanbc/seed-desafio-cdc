package br.com.deveficiente.casadocodigov2.entity;

import br.com.deveficiente.casadocodigov2.util.UniqueValue;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import jakarta.persistence.*;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotBlank;
import lombok.Getter;
import lombok.Setter;
import org.springframework.beans.BeanUtils;

import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
@Entity
@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonIgnoreProperties(value = {"hibernateLazyInitializer", "handler"}, ignoreUnknown = true)
public class Pais {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @NotBlank
    @UniqueValue(domainClass = Pais.class, fieldName = "nome", message = "O país já está cadastrado")
    private String nome;

    @JsonManagedReference
    @OneToMany(mappedBy = "pais")
    private List<Estado> estados = new ArrayList<>();

    public Pais() {
    }

    public static Pais toResponse(Pais source) {
        Pais target = new Pais();
        BeanUtils.copyProperties(source, target);
        return target;
    }

    public Pais toRequest(@Valid Pais source) {
        Pais target = new Pais();
        BeanUtils.copyProperties(source, target);
        target.setId(source.getId());
        target.setNome(source.getNome());
        return target;
    }
}
