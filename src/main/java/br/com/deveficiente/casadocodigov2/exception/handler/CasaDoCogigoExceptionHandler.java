package br.com.deveficiente.casadocodigov2.exception.handler;

import br.com.deveficiente.casadocodigov2.exception.CasaDoCodigoException;
import br.com.deveficiente.casadocodigov2.model.ErrorResponse;
import com.fasterxml.jackson.databind.exc.InvalidFormatException;
import jakarta.validation.ConstraintViolationException;
import org.slf4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.bind.support.WebExchangeBindException;

import java.util.HashMap;

import static org.slf4j.LoggerFactory.getLogger;

/**
 * Exception handler para erros que acontecem na camada do controller.
 */
@RestControllerAdvice
public class CasaDoCogigoExceptionHandler {

	private static final Logger LOG = getLogger(CasaDoCogigoExceptionHandler.class);

	@Autowired
	private final HandleValidationRetrieveFields retrieveFields;

	@Autowired
	private final HandleInvalidFormatRetrieveFields invalidFormatRetrieveFields;

	public CasaDoCogigoExceptionHandler(HandleValidationRetrieveFields retrieveFields, HandleInvalidFormatRetrieveFields invalidFormatRetrieveFields) {
		this.retrieveFields = retrieveFields;
		this.invalidFormatRetrieveFields = invalidFormatRetrieveFields;
	}

	@ExceptionHandler(CasaDoCodigoException.class)
	public ResponseEntity<ErrorResponse> handleCasaDoCodigoException(CasaDoCodigoException exception) {
		messageLog(exception);
		return ResponseEntity.status(exception.getHttpStatus())
				.contentType(MediaType.APPLICATION_JSON)
				.body(ErrorResponse.builder()
						.httpStatusCode(exception.getHttpStatus().value())
						.errorCode(exception.getErrorCode())
						.message(exception.getErrorDescription())
						.fields(exception.getFields())
						.build());
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

		String fieldsMessage = invalidFormatRetrieveFields.returnFiledsHandleInvalid(exception, fields);

		messageLog(exception);

		return ResponseEntity.unprocessableEntity()
				.contentType(MediaType.APPLICATION_JSON)
				.body(ErrorResponse.builder()
					.httpStatusCode(HttpStatus.BAD_REQUEST.value())
					.errorCode("invalid_request")
					.message(String.format("Campos inválidos: %s", fieldsMessage))
					.fields(fields)
					.build());
	}

	@ExceptionHandler({BindException.class, WebExchangeBindException.class, ConstraintViolationException.class})
	public ResponseEntity<ErrorResponse> handleValidationException(Throwable exception) {
		var fields = new HashMap<String, String>();

		String fieldsMessage = retrieveFields.returnFieldsHandleValidation(exception, fields);

		messageLog(exception);

		return ResponseEntity.unprocessableEntity()
				.contentType(MediaType.APPLICATION_JSON)
				.body(ErrorResponse.builder()
					.httpStatusCode(HttpStatus.BAD_REQUEST.value())
					.errorCode("invalid_request")
					.message(String.format("Campos inválidos: %s", fieldsMessage))
					.fields(fields)
					.build());
	}

	@ExceptionHandler(RuntimeException.class)
	public ResponseEntity<ErrorResponse> handleRuntimeException(RuntimeException exception) {
		messageLog(exception);
		return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
				.contentType(MediaType.APPLICATION_JSON)
				.body(ErrorResponse.builder()
						.httpStatusCode(HttpStatus.INTERNAL_SERVER_ERROR.value())
						.errorCode("internal_server_error")
						.message(exception.getMessage())
						.build());
	}

	private <T> void messageLog (T exception) {
		if (LOG.isWarnEnabled()) {
			LOG.warn("Ocorreu a seguinte exceção: {}", exception.toString());
		}
	}

}
