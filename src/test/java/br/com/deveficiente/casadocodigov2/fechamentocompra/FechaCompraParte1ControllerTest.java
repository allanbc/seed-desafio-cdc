package br.com.deveficiente.casadocodigov2.fechamentocompra;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import com.github.javafaker.Faker;
import net.jqwik.api.ForAll;
import net.jqwik.api.Label;
import net.jqwik.api.Property;
import net.jqwik.api.constraints.AlphaChars;
import net.jqwik.api.constraints.IntRange;
import net.jqwik.api.constraints.NumericChars;
import net.jqwik.api.constraints.StringLength;
import net.jqwik.spring.JqwikSpringSupport;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.ResultActions;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@JqwikSpringSupport
@SpringBootTest
@AutoConfigureMockMvc
public class FechaCompraParte1ControllerTest {

    @Autowired
    private MockMvc mvc;

    @Autowired
    private ObjectMapper objectMapper;

    @BeforeEach
    void setup() {
        objectMapper = new ObjectMapper();
        objectMapper.registerModule(new JavaTimeModule());
    }

    @Property(tries = 10)
    @Label("fluxo inicial de compra")
    @DirtiesContext(methodMode = DirtiesContext.MethodMode.BEFORE_METHOD)
    void testFluxoDeCompra(
            @ForAll @AlphaChars @StringLength(min = 1, max = 50) String email,
            @ForAll @AlphaChars @StringLength(min = 1, max = 255) String nome,
            @ForAll @AlphaChars @StringLength(min = 1, max = 255) String sobrenome,
            @ForAll @AlphaChars @StringLength(min = 1, max = 255) String endereco,
            @ForAll @AlphaChars @StringLength(min = 1, max = 255) String complemento,
            @ForAll @AlphaChars @StringLength(min = 1, max = 255) String cidade,
            @ForAll @NumericChars @StringLength(min = 1, max = 20) String telefone,
            @ForAll @NumericChars @StringLength(min = 1, max = 8) String cep,
            @ForAll @IntRange(min = 1,max = 50) int quantidade)
            throws Exception {
        Faker faker = new Faker();
        String nomeAutor = faker.name().fullName() + UUID.randomUUID().toString().substring(0, 3);
        String emailAutor = faker.internet().emailAddress() + "-" + UUID.randomUUID().toString().substring(0, 3);
        String categoria = faker.book().genre() + UUID.randomUUID().toString().substring(0, 3);
        String tituloLivro = faker.book().title() + UUID.randomUUID().toString().substring(0, 5);;
        String isbn = faker.idNumber().valid() + "-" + UUID.randomUUID().toString().substring(0, 10);
        String pais = faker.country().name() + "-" + UUID.randomUUID().toString().substring(0, 5);;
        String estado = faker.country().capital() + "-" + UUID.randomUUID().toString().substring(0, 5);;

        Map<String, Object> novoAutor = Map.of(
                "nome", nomeAutor,
                "email", emailAutor,
                "descricao", "teste descricao");

        MvcResult resultadoAutor = mvc.perform(post("/autores")
                .content(objectMapper.writeValueAsString(novoAutor))
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isCreated())
                .andReturn();

        String jsonRespostaAutor = resultadoAutor.getResponse().getContentAsString();

        Map<String, Object> respostaIdAutor = objectMapper.readValue(jsonRespostaAutor, new TypeReference<>() {});
        Long idAutor = Long.valueOf(respostaIdAutor.get("id").toString());

        Assertions.assertNotNull(idAutor);

        Map<String, Object> novaCategoria = Map.of("nome", categoria);
        MvcResult resultadoCategoria = mvc.perform(post("/categorias")
                .content(objectMapper.writeValueAsString(novaCategoria))
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isCreated())
                .andReturn();

        String jsonRespostaCategoria = resultadoCategoria.getResponse().getContentAsString();
        Map<String, Object> respostaIdcategoria = objectMapper.readValue(jsonRespostaCategoria, new TypeReference<>() {});
        Long idCategoria = Long.valueOf(respostaIdcategoria.get("id").toString());

        Assertions.assertNotNull(idCategoria);

        BigDecimal valorLivro = new BigDecimal("20");

        Map<String, Object> novoLivro =Map.of("titulo", tituloLivro,
                "resumo","resumo",
                "sumario","sumario",
                "preco",valorLivro.toString(),
                "numPaginas",110,
                "isbn",isbn,
                "dataPublicacao", LocalDate.now().plusDays(1)
                        .format(DateTimeFormatter.ofPattern("yyyy-MM-dd")),
                "idCategoria","10",
                "idAutor","8");

        MvcResult resultadoLivros = mvc.perform(post("/livros")
                .content(objectMapper.writeValueAsString(novoLivro))
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isCreated())
                .andReturn();

        String jsonRespostaLivro = resultadoLivros.getResponse().getContentAsString();
        Map<String, Object> respostaIdLivro = objectMapper.readValue(jsonRespostaLivro, new TypeReference<>() {});
        Long idLivro = Long.valueOf(respostaIdLivro.get("id").toString());

        Assertions.assertNotNull(idLivro);

        Map<String, Object> novoPais = Map.of("nome", pais);
        MvcResult resultado = mvc.perform(post("/dominios/paises")
                .content(objectMapper.writeValueAsString(novoPais))
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andReturn();
        // Obtem o JSON de resposta
        String jsonRespostaPais = resultado.getResponse().getContentAsString();

        Map<String, Object> respostaIdPais = objectMapper.readValue(jsonRespostaPais, new TypeReference<>() {});
        Long idPais = Long.valueOf(respostaIdPais.get("id").toString());

        Assertions.assertNotNull(idPais);

        Map<String, Object> novoEstado = Map.of("nome", estado, "pais", Map.of("id", 5));

        mvc.perform(post("/dominios/estados")
                .content(objectMapper.writeValueAsString(novoEstado))
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andReturn();

        // Obtem o JSON de resposta
        String jsonRespostaEstado = resultado.getResponse().getContentAsString();
        Map<String, Object> respostaIdEstado = objectMapper.readValue(jsonRespostaEstado, new TypeReference<>() {});
        Long idEstado = Long.valueOf(respostaIdEstado.get("id").toString());

        Assertions.assertNotNull(idEstado);

        HashMap<String, Object> dados = new HashMap<>();
        dados.put("email", email + "@gmail.com");
        dados.put("nome", nome);
        dados.put("sobrenome", sobrenome);
        dados.put("documento", "49431616009");
        dados.put("endereco", endereco);
        dados.put("complemento", complemento);
        dados.put("cidade", cidade);
        dados.put("idPais", "5");
        dados.put("idEstado", "3");
        dados.put("telefone", telefone);
        dados.put("cep", cep);
        List<Map<String, Object>> itens = List.of(Map.of("idLivro", "6", "quantidade",
                quantidade));
        Map<String, Object> pedido = Map.of("total",
                valorLivro.multiply(new BigDecimal(quantidade)),
                "itens", itens);
        dados.put("pedido", pedido);

        ResultActions actions = mvc.perform(post("/compras")
                        .content(objectMapper.writeValueAsString(dados))
                        .contentType(MediaType.APPLICATION_JSON));
        actions.andExpect(status().is2xxSuccessful());
    }
}
