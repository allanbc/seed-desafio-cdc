package br.com.deveficiente.casadocodigov2.entity;

import br.com.deveficiente.casadocodigov2.util.UniqueValue;
import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import jakarta.persistence.*;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;
import org.springframework.beans.BeanUtils;

@Getter
@Setter
@Entity
@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonIgnoreProperties(value = {"hibernateLazyInitializer", "handler"}, ignoreUnknown = true)
public class Estado {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Valid
    @NotBlank
    @UniqueValue(domainClass = Estado.class, fieldName = "nome", message = "O Estado já está cadastrado")
    private String nome;

    @JsonBackReference
    @ManyToOne(fetch = FetchType.LAZY)
    private Pais pais;
    public Estado() {
    }
    public Estado toRequest(@Valid Estado source, Pais pais) {
        Estado target = new Estado();
        BeanUtils.copyProperties(source, target);
        target.setId(source.getId());
        target.setNome(source.getNome());
        target.setPais(pais);
        return target;
    }
    public static Estado toResponse(Estado source) {
        Estado target = new Estado();
        BeanUtils.copyProperties(source, target);
        return target;

    }

    public Estado(@NotBlank String nome, @NotNull @Valid Pais pais) {
        this.nome = nome;
        this.pais = pais;
    }

    public boolean pertenceAPais(Pais pais) {
        return this.pais.equals(pais);
    }
}
