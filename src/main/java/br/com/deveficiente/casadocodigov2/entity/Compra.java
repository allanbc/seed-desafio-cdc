package br.com.deveficiente.casadocodigov2.entity;

import br.com.deveficiente.casadocodigov2.model.compra.NovaCompraRequest;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import jakarta.persistence.*;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;
import lombok.Generated;
import lombok.Getter;
import lombok.Setter;
import org.springframework.util.Assert;

import java.util.function.Function;

@Getter
@Setter
@Entity
@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonIgnoreProperties(value = {"hibernateLazyInitializer", "handler"}, ignoreUnknown = true)
public class Compra {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String email;
    private String nome;
    private String sobrenome;
    private String documento;
    private String endereco;
    private String complemento;
    private String cidade;
    @ManyToOne(fetch = FetchType.LAZY)
    private Pais pais;
    @ManyToOne(fetch = FetchType.LAZY)
    private Estado estado;
    private String telefone;
    private String cep;
    @OneToOne(mappedBy = "compra", cascade = CascadeType.PERSIST)
    private Pedido pedido;
    @Embedded
    private CupomAplicado cupomAplicado;
    public Compra(NovaCompraRequest request, Estado estado, Pais pais, Function<Compra, Pedido> funcaoCriacaoPedido) {
        this.email = request.email();
        this.nome = request.nome();
        this.sobrenome = request.sobrenome();
        this.documento = request.documento();
        this.endereco = request.endereco();
        this.complemento = request.complemento();
        this.cidade = request.cidade();
        this.pais = pais;
        this.estado = estado;
        this.telefone = request.telefone();
        this.cep = request.cep();
        this.pedido = funcaoCriacaoPedido.apply(this);
    }

    public void setEstado(@NotNull @Valid Estado estado) {
        Assert.notNull(pais,"Não é possível associar um estado enquanto o pais for nulo");
        Assert.isTrue(estado.pertenceAPais(pais),"Este estado não é do país associado a compra");
        this.estado = estado;
    }

    public void aplicaCupom(Cupom cupom) {
        Assert.isTrue(cupom.isValid(),"Olha, o cupom que está sendo aplicado não está mais válido");
        Assert.isNull(cupomAplicado,"Olha, você não pode trocar um cupom de uma compra");
        this.cupomAplicado = new CupomAplicado(cupom);
    }


}
