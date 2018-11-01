package pl.michal.olszewski.typer;

import java.util.List;
import java.util.stream.Collectors;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.validation.ObjectError;

class ExceptionMapperHelper {

  private static final String INVALID_VALUE_DETAILS_PREFIX = "Invalid value: ";
  private static final String OBJECT_ERROR_REJECTED_VALUE = "unknown";

  List<Error> errorsFromBindResult(Exception exception, BindingResult bindingResult) {
    return bindingResult.getAllErrors().stream()
        .map(objectError ->
            Error.error(exception.getClass().getSimpleName())
                .withMessage(INVALID_VALUE_DETAILS_PREFIX + getRejectedValue(objectError))
                .withUserMessage(objectError.getDefaultMessage())
                .withPath(getPath(objectError))
                .withDetails("")
                .build()
        ).collect(Collectors.toList());
  }

  private String getPath(ObjectError objectError) {
    return (objectError instanceof FieldError) ? ((FieldError) objectError).getField() : objectError.getObjectName();
  }

  private Object getRejectedValue(ObjectError objectError) {
    return (objectError instanceof FieldError) ? ((FieldError) objectError).getRejectedValue() : OBJECT_ERROR_REJECTED_VALUE;
  }

  ResponseEntity<ErrorsHolder> mapResponseWithoutLogging(ErrorsHolder errors, HttpStatus httpStatus) {
    return ResponseEntity.status(httpStatus).contentType(MediaType.APPLICATION_JSON).body(errors);
  }
}
