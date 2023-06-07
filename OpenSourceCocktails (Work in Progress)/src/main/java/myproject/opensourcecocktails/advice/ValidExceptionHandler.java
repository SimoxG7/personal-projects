package myproject.opensourcecocktails.advice;

import jakarta.validation.ConstraintViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestControllerAdvice
public class ValidExceptionHandler {

  @ResponseStatus(HttpStatus.BAD_REQUEST)
  @ExceptionHandler(MethodArgumentNotValidException.class)
  public Map<String, Map<String, String>> handleInvalidArgument(MethodArgumentNotValidException e) {
    Map<String, String> notValidMap = new HashMap<>();
    e.getBindingResult().getFieldErrors().forEach(error -> {
      notValidMap.put((error.getField()), error.getDefaultMessage());
    });
    Map<String, Map<String, String>> errorMap = new HashMap<>();
    errorMap.put("Bad request", notValidMap);
    return errorMap;
  }

  @ResponseStatus(HttpStatus.BAD_REQUEST)
  @ExceptionHandler(ConstraintViolationException.class)
  public Map<String, List<String>> handleInvalidArgument(ConstraintViolationException e) {
    List<String> notValid = new ArrayList<>();
    e.getConstraintViolations().forEach(error -> {
      notValid.add(error.getMessage());
    });
    Map<String, List<String>> errors = new HashMap<>();
    errors.put("Bad request", notValid);
    return errors;
  }

  @ResponseStatus(HttpStatus.BAD_REQUEST)
  @ExceptionHandler(HttpMessageNotReadableException.class)
  public Map<String, String> handleInvalidArgument(HttpMessageNotReadableException e) {
    Map<String, String> errors = new HashMap<>();
    errors.put("Bad request", e.getMessage());
    return errors;
  }

}
