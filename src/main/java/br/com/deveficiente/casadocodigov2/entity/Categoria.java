package br.com.deveficiente.casadocodigov2.entity;

import br.com.deveficiente.casadocodigov2.model.categoria.CadastroCategoriaRequest;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Builder
@Entity
@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonIgnoreProperties(value = {"hibernateLazyInitializer", "handler"}, ignoreUnknown = true)
public class Categoria {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String nome;
    public Categoria() {
    }
    public Categoria(CadastroCategoriaRequest request) {
        this.nome = request.nome();
    }
    public Categoria(Long id, String nome) {
        this.id = id;
        this.nome = nome;
    }

    public Categoria(String categoria) {
        this.nome = nome;
    }
}
