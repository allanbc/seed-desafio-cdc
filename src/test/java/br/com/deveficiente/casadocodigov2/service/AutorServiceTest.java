package br.com.deveficiente.casadocodigov2.service;

import br.com.deveficiente.casadocodigov2.entity.Autor;
import br.com.deveficiente.casadocodigov2.exception.AutorCadastradoException;
import br.com.deveficiente.casadocodigov2.model.CadastroAutorRequest;
import br.com.deveficiente.casadocodigov2.repository.AutorRepository;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@Transactional
class AutorServiceTest {

    @Autowired
    private AutorService autorService;

    @Autowired
    private AutorRepository autorRepository;

    @Test
    @DisplayName("Dado os parâmetros para criação, deverá ser possível cadastrar um autor.")
    void testDeveCriarUmAutor() {
        System.out.println("Testando...");

        //Arrange
        CadastroAutorRequest request = new CadastroAutorRequest("Allan Campos", "teste-dev@gmail.com", "testando");

        //limpar o repositório antes do teste
        autorRepository.deleteAll();

        // Act
        Autor result = autorService.create((request));

        //Assert
        assertNotNull(result);
        assertEquals(request.nome(), result.getNome());
        assertEquals(request.email(), result.getEmail());
    }

    @Test
    @DisplayName("Deve tentar cadastrar um autor com o mesmo email e deverá retornar uma exceção")
    void testDeveTentarCriarUmAutorComOMesmoEmailERetornarExcecao() {
        //Arrange
        CadastroAutorRequest request = new CadastroAutorRequest("Allan Campos", "teste-dev@gmail.com", "teste de descricao");

        // Adicionar um autor com o mesmo e-mail
        var repoAutor = autorRepository.save(new Autor(request));

        // Act and Assert
        assertThrows(AutorCadastradoException.class, () -> autorService.create(request));
        assertEquals(repoAutor.getEmail(), request.email());
    }
}
