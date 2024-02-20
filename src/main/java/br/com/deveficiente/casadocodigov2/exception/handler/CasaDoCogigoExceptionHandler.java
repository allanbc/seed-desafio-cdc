package br.com.deveficiente.casadocodigov2.exception.handler;

import br.com.deveficiente.casadocodigov2.exception.CasaDoCodigoException;
import br.com.deveficiente.casadocodigov2.model.ErrorResponse;
import com.fasterxml.jackson.databind.exc.InvalidFormatException;
import jakarta.validation.ConstraintViolationException;
import org.slf4j.Logger;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindException;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.support.WebExchangeBindException;

import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

import static org.slf4j.LoggerFactory.getLogger;

/**
 * Exception handler para erros que acontecem na camada do controller.
 */
@ControllerAdvice
public class CasaDoCogigoExceptionHandler {

	private static final Logger LOG = getLogger(CasaDoCogigoExceptionHandler.class);

	@ExceptionHandler(CasaDoCodigoException.class)
	public ResponseEntity<ErrorResponse> handlePortalSisException(CasaDoCodigoException exception) {
		var body = ErrorResponse.builder()
						.httpStatusCode(exception.getHttpStatus().value())
						.errorCode(exception.getErrorCode())
						.message(exception.getErrorDescription())
						.fields(exception.getFields())
						.build();

		if (LOG.isWarnEnabled()) {
			LOG.warn("Ocorreu a seguinte exceção: {}", exception.toString());
		}

		return ResponseEntity.status(exception.getHttpStatus())
				.contentType(MediaType.APPLICATION_JSON)
				.body(body);
	}

	/**
	 * Handler para erros de deserialização do jackson. Trata enums com valores errados.
	 *
	 * @param exception a exceção
	 * @return response
	 */
	@ExceptionHandler(InvalidFormatException.class)
	public ResponseEntity<ErrorResponse> handleInvalidFormatException(InvalidFormatException exception) {
		var fields = new HashMap<String, String>();
		var validValues = Arrays.toString(exception.getTargetType().getEnumConstants());

		exception.getPath()
				.forEach(e -> fields.put(e.getFieldName(), "valores válidos: " + validValues));

		String fieldsMessage = String.join(", ", fields.keySet());

		if (LOG.isWarnEnabled()) {
			LOG.warn("Ocorreu uma exceção de validação: {}", exception.toString());
		}

		return ResponseEntity.unprocessableEntity()
				.contentType(MediaType.APPLICATION_JSON)
				.body(ErrorResponse.builder()
					.httpStatusCode(HttpStatus.UNPROCESSABLE_ENTITY.value())
					.errorCode("invalid_request")
					.message(String.format("Campos inválidos: %s", fieldsMessage))
					.fields(fields)
					.build());
	}

	@ExceptionHandler({BindException.class, WebExchangeBindException.class, ConstraintViolationException.class})
	public ResponseEntity<ErrorResponse> handleValidationException(Throwable exception) {
		Map<String, String> fields = new HashMap<>();

		if (exception instanceof BindingResult result) {
			result
				.getFieldErrors()
				.forEach(error -> fields.put(error.getField(), error.getDefaultMessage()));
		} else if (exception instanceof ConstraintViolationException violation) {
			violation
				.getConstraintViolations()
				.forEach(error -> fields.put(error.getPropertyPath().toString(), error.getMessage()));
	}

		String fieldsMessage = String.join(", ", fields.keySet());

		if (LOG.isWarnEnabled()) {
			LOG.warn("Ocorreu uma exceção de validação: {}", exception.toString());
		}

		return ResponseEntity.unprocessableEntity()
				.contentType(MediaType.APPLICATION_JSON)
				.body(ErrorResponse.builder()
					.httpStatusCode(HttpStatus.UNPROCESSABLE_ENTITY.value())
					.errorCode("invalid_request")
					.message(String.format("Campos inválidos: %s", fieldsMessage))
					.fields(fields)
					.build());
	}

	@ExceptionHandler(RuntimeException.class)
	public ResponseEntity<ErrorResponse> handleRuntimeException(RuntimeException exception) {
		var body = ErrorResponse.builder()
				.httpStatusCode(HttpStatus.INTERNAL_SERVER_ERROR.value())
				.errorCode("internal_server_error")
				.message(exception.getMessage())
				.build();

		if (LOG.isWarnEnabled()) {
			LOG.warn("Ocorreu a seguinte exceção: {}", exception.toString());
		}

		return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
				.contentType(MediaType.APPLICATION_JSON)
				.body(body);
	}

}
