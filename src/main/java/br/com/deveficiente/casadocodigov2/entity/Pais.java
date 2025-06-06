package br.com.deveficiente.casadocodigov2.entity;

import br.com.deveficiente.casadocodigov2.util.Generated;
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
    @OneToMany(mappedBy = "pais", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Estado> estados = new ArrayList<>();

    public Pais() {
    }

    public Pais(@NotBlank String nome) {
        this.nome = nome;
    }

    public static Pais toResponse(Pais source) {
        if (source == null) {
            return null;
        }
        Pais target = new Pais();
        BeanUtils.copyProperties(source, target);
        return target;
    }

    @Override
    public String toString() {
        return "Pais [id=" + id + ", nome=" + nome + "]";
    }
    @Override
    @Generated(Generated.ECLIPSE)
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + ((nome == null) ? 0 : nome.hashCode());
        return result;
    }

    @Override
    @Generated(Generated.ECLIPSE)
    public boolean equals(Object obj) {
        if (this == obj)
            return true;
        if (obj == null)
            return false;
        if (getClass() != obj.getClass())
            return false;
        Pais other = (Pais) obj;
        if (nome == null) {
            if (other.nome != null)
                return false;
        } else if (!nome.equals(other.nome))
            return false;
        return true;
    }

}
