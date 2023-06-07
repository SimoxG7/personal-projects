package myproject.opensourcecocktails.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.validation.ConstraintViolation;
import jakarta.validation.ConstraintViolationException;
import jakarta.validation.Valid;
import jakarta.validation.Validator;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Positive;
import jakarta.validation.constraints.PositiveOrZero;
import myproject.opensourcecocktails.model.CompleteCocktail;
import myproject.opensourcecocktails.model.Ingredient;
import myproject.opensourcecocktails.service.IngredientService;
import myproject.opensourcecocktails.utils.Constants;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.DefaultHandlerExceptionResolver;

import java.util.Calendar;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@RestController
@RequestMapping(value = Constants.INGREDIENT_PATH)
public class IngredientController {

  @Autowired
  private IngredientService ingredientService;

  @Autowired
  private Validator validator;

  private final String homePath = Constants.INGREDIENT_PATH;

  private int maxId = 153;

  @GetMapping(value = "", produces = MediaType.APPLICATION_JSON_VALUE)
  public ResponseEntity<List<Ingredient>> getAllIngredients() {
    HttpHeaders httpHeaders = new HttpHeaders();
    httpHeaders.set("Method", "GET");
    httpHeaders.set("Path", homePath);
    httpHeaders.set(HttpHeaders.DATE, Calendar.getInstance().getTime().toString());
    try {
      List<Ingredient> body = ingredientService.getAllIngredients();
//    if (body.isEmpty()) {
//      return new ResponseEntity<>(
//          httpHeaders,
//          HttpStatus.OK //6.6
//      );
//    }
      //6.6
      httpHeaders.set(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON_VALUE);
//      httpHeaders.set(HttpHeaders.LOCATION, homePath);
      return new ResponseEntity<>(
          body,
          httpHeaders,
          HttpStatus.OK
      );
    } catch (Exception e) {
      e.printStackTrace();
      return new ResponseEntity<>(
          httpHeaders,
          HttpStatus.INTERNAL_SERVER_ERROR
      );
    }
  }

  @GetMapping(value = "/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
  public ResponseEntity<Ingredient> getIngredientById(@PathVariable Integer id) {
    HttpHeaders httpHeaders = new HttpHeaders();
    httpHeaders.set("Method", "GET");
    httpHeaders.set("Path", homePath + "/" + id);
    httpHeaders.set(HttpHeaders.DATE, Calendar.getInstance().getTime().toString());
    try {
      Ingredient body = ingredientService.getIngredientById(id);
      if (body == null) {
        return new ResponseEntity<>(
            httpHeaders,
            HttpStatus.NOT_FOUND
        );
      }
      httpHeaders.set(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON_VALUE);
//      httpHeaders.set(HttpHeaders.LOCATION, homePath + "/" + id);
      return new ResponseEntity<>(
          body,
          httpHeaders,
          HttpStatus.OK
      );
    } catch (Exception e) {
      e.printStackTrace();
      return new ResponseEntity<>(
          httpHeaders,
          HttpStatus.INTERNAL_SERVER_ERROR
      );
    }
  }

  @GetMapping(value = "/search/{name}", produces = MediaType.APPLICATION_JSON_VALUE)
  public ResponseEntity<List<Ingredient>> getIngredientByName(@PathVariable @NotBlank String name) {
    HttpHeaders httpHeaders = new HttpHeaders();
    httpHeaders.set("Method", "GET");
    httpHeaders.set("Path", homePath + "/search/" + name);
    httpHeaders.set(HttpHeaders.DATE, Calendar.getInstance().getTime().toString());
    try {
      List<Ingredient> body = ingredientService.getIngredientByName(name);
      if (body.isEmpty()) {
        return new ResponseEntity<>(
            httpHeaders,
            HttpStatus.NOT_FOUND
        );
      }
      httpHeaders.set(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON_VALUE);
//      httpHeaders.set(HttpHeaders.LOCATION, homePath + "/search/" + name);
      return new ResponseEntity<>(
          body,
          httpHeaders,
          HttpStatus.OK
      );
    } catch (Exception e) {
      e.printStackTrace();
      return new ResponseEntity<>(
          httpHeaders,
          HttpStatus.INTERNAL_SERVER_ERROR
      );
    }
  }

  @PostMapping(value = "", consumes = MediaType.APPLICATION_JSON_VALUE)
  public ResponseEntity<Ingredient> addIngredient(@RequestBody @Valid Ingredient ingredient) {
    HttpHeaders httpHeaders = new HttpHeaders();
    httpHeaders.set("Method", "POST");
    httpHeaders.set("Path", homePath);
    httpHeaders.set(HttpHeaders.DATE, Calendar.getInstance().getTime().toString());
//    Set<ConstraintViolation<Ingredient>> violations = validator.validate(ingredient, ValidateIngredient.class);
//    if (!violations.isEmpty()) {
//      List<String> validationMessages = violations.stream().map(ConstraintViolation::getMessage).toList();
//      validationMessages.forEach(System.out::println);
//      return new ResponseEntity<>(
//          httpHeaders,
//          HttpStatus.BAD_REQUEST
//      );
//    }
    maxId++;
    ingredient.setId(maxId);
    try {
      ingredientService.addIngredient(ingredient);
      Ingredient body = ingredientService.getIngredientById(ingredient.getId());
      if (body == null) {
        return new ResponseEntity<>(
            httpHeaders,
            HttpStatus.INTERNAL_SERVER_ERROR);
      }
      httpHeaders.set(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON_VALUE);
      httpHeaders.set(HttpHeaders.LOCATION, homePath + "/" + ingredient.getId());
      return new ResponseEntity<>(
          body,
          httpHeaders,
          HttpStatus.CREATED
      );
    } catch (Exception e) {
      e.printStackTrace();
      httpHeaders.remove(HttpHeaders.LOCATION);
      return new ResponseEntity<>(
          httpHeaders,
          HttpStatus.INTERNAL_SERVER_ERROR
      );
    }
  }

  @PutMapping(value = "/{id}", consumes = MediaType.APPLICATION_JSON_VALUE)
  public ResponseEntity<Ingredient> updateIngredient(@PathVariable Integer id, @RequestBody @Valid Ingredient ingredient) {
    HttpHeaders httpHeaders = new HttpHeaders();
    httpHeaders.set("Method", "PUT");
    httpHeaders.set("Path", homePath + "/" + id);
    try {
      Ingredient body = ingredientService.getIngredientById(id);
      if (body == null) {
        //return addIngredient(ingredient);
        httpHeaders.set(HttpHeaders.DATE, Calendar.getInstance().getTime().toString());
        maxId++;
        ingredient.setId(maxId);
        ingredientService.addIngredient(ingredient);
        body = ingredientService.getIngredientById(ingredient.getId());
        if (body == null) {
          return new ResponseEntity<>(
              httpHeaders,
              HttpStatus.INTERNAL_SERVER_ERROR);
        }
        httpHeaders.set(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON_VALUE);
        httpHeaders.set(HttpHeaders.LOCATION, homePath + "/" + ingredient.getId());
        return new ResponseEntity<>(
            body,
            httpHeaders,
            HttpStatus.CREATED
        );
      }
      ingredientService.updateIngredient(id, ingredient);
      body = ingredientService.getIngredientById(id);
      httpHeaders.set(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON_VALUE);
      httpHeaders.set(HttpHeaders.DATE, Calendar.getInstance().getTime().toString());
      httpHeaders.set(HttpHeaders.LOCATION, homePath + "/" + ingredient.getId());
      return new ResponseEntity<>(
          body,
          httpHeaders,
          HttpStatus.OK
      );
    } catch (Exception e) {
      e.printStackTrace();
      return new ResponseEntity<>(
          httpHeaders,
          HttpStatus.INTERNAL_SERVER_ERROR
      );
    }
  }

  @DeleteMapping(value = "/{id}")
  public ResponseEntity<Ingredient> deleteIngredient(@PathVariable Integer id) {
    HttpHeaders httpHeaders = new HttpHeaders();
    httpHeaders.set("Method", "DELETE");
    httpHeaders.set("Path", homePath + "/" + id);
    httpHeaders.set(HttpHeaders.DATE, Calendar.getInstance().getTime().toString());
    try {
      Ingredient body = ingredientService.getIngredientById(id);
      if (body == null) {
        return new ResponseEntity<>(
            httpHeaders,
            HttpStatus.NOT_FOUND
        );
      }
      ingredientService.deleteIngredient(id);
      body = ingredientService.getIngredientById(id);
      if (body != null) {
        return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
      }
      httpHeaders.set(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON_VALUE);
//      httpHeaders.set(HttpHeaders.LOCATION, homePath);
      return new ResponseEntity<>(
          httpHeaders,
          HttpStatus.OK
      );
    } catch (Exception e) {
      e.printStackTrace();
      return new ResponseEntity<>(
          httpHeaders,
          HttpStatus.INTERNAL_SERVER_ERROR
      );
    }
  }

//  @ExceptionHandler(ConstraintViolationException.class)
//  @ResponseStatus(HttpStatus.BAD_REQUEST)
//  ResponseEntity<String> handleConstraintViolationException(ConstraintViolationException e) {
//    System.out.println("do i even get in here");
//    return new ResponseEntity<>("Not valid due to validation error: " + e.getMessage(), HttpStatus.BAD_REQUEST);
//  }
}
