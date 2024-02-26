package br.com.deveficiente.casadocodigov2.service;

import br.com.deveficiente.casadocodigov2.entity.Autor;
import br.com.deveficiente.casadocodigov2.model.autor.CadastroAutorRequest;
import br.com.deveficiente.casadocodigov2.repository.AutorRepository;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.dao.DataIntegrityViolationException;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@SpringBootTest
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
//        autorRepository.deleteAll();
//
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
        AutorRepository autorRepository = mock(AutorRepository.class);
        AutorService autorService = new AutorService(autorRepository);

        CadastroAutorRequest autorForm = new CadastroAutorRequest("Nome do Autor", "autor@example.com", "Descrição");

        /// Simular que a validação customizada dispara
        doThrow(new DataIntegrityViolationException("Existe autor cadastrado para o e-mail informado!"))
                .when(autorRepository).save(any());

        // Act and Assert
        DataIntegrityViolationException exception = assertThrows(DataIntegrityViolationException.class,
                () -> autorService.create(autorForm));

        // Verificar se a exceção contém a mensagem esperada
        assertNotNull(exception.getMessage());
        assertTrue(exception.getMessage().contains("Existe autor cadastrado para o e-mail informado!"));

        // Verificar se o método save não foi chamado
        verify(autorRepository).save(any());
    }

}
