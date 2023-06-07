package myproject.opensourcecocktails.controller;

import jakarta.validation.ConstraintViolationException;
import jakarta.validation.Valid;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Positive;
import jakarta.validation.constraints.PositiveOrZero;
import myproject.opensourcecocktails.model.CompleteCocktail;
import myproject.opensourcecocktails.service.CompleteCocktailService;
import myproject.opensourcecocktails.utils.Constants;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import tech.tablesaw.aggregate.AggregateFunction;
import tech.tablesaw.aggregate.AggregateFunctions;
import tech.tablesaw.aggregate.Summarizer;
import tech.tablesaw.api.IntColumn;
import tech.tablesaw.api.Row;
import tech.tablesaw.api.StringColumn;
import tech.tablesaw.api.Table;
import tech.tablesaw.selection.Selection;

import java.io.ByteArrayOutputStream;
import java.io.OutputStream;
import java.io.Writer;
import java.util.*;
import java.util.stream.Collectors;

@RestController
@RequestMapping(value = Constants.COMPLETE_COCKTAIL_PATH)
public class CompleteCocktailController {

  @Autowired
  private CompleteCocktailService completeCocktailService;

  private final String homePath = Constants.COMPLETE_COCKTAIL_PATH;

  private int maxId = 90;

  @GetMapping(value = "", produces = MediaType.APPLICATION_JSON_VALUE)
  public ResponseEntity<List<CompleteCocktail>> getAllCompleteCocktails() {
    HttpHeaders httpHeaders = new HttpHeaders();
    httpHeaders.set(HttpHeaders.DATE, Calendar.getInstance().getTime().toString());
    httpHeaders.set("Method", "GET");
    httpHeaders.set("Path", homePath);
    try {
      List<CompleteCocktail> body = completeCocktailService.getAllCompleteCocktails();
//      if (body.isEmpty()) {
//        return new ResponseEntity<>(
//            httpHeaders,
//            HttpStatus.OK //6.6
//        );
//      }
      //6.6 Returning HTTP 200 vs. HTTP 400 for Data-Centric Queries
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
  public ResponseEntity<CompleteCocktail> getCompleteCocktailById(@PathVariable Integer id) {
    HttpHeaders httpHeaders = new HttpHeaders();
    httpHeaders.set(HttpHeaders.DATE, Calendar.getInstance().getTime().toString());
    httpHeaders.set("Method", "GET");
    httpHeaders.set("Path", homePath + "/" + id);
    try {
      CompleteCocktail body = completeCocktailService.getCompleteCocktailById(id);
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
  public ResponseEntity<List<CompleteCocktail>> getCompleteCocktailByName(@PathVariable @NotBlank String name) {
    HttpHeaders httpHeaders = new HttpHeaders();
    httpHeaders.set(HttpHeaders.DATE, Calendar.getInstance().getTime().toString());
    httpHeaders.set("Method", "GET");
    httpHeaders.set("Path", homePath + "/search/" + name);
    try {
      List<CompleteCocktail> body = completeCocktailService.getCompleteCocktailByName(name);
      if (body.isEmpty()) {
        return new ResponseEntity<>(
            httpHeaders,
            HttpStatus.OK //? or nocontent? or 404?
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
  public ResponseEntity<CompleteCocktail> addCompleteCocktail(@RequestBody @Valid CompleteCocktail completeCocktail) {
    HttpHeaders httpHeaders = new HttpHeaders();
    httpHeaders.set(HttpHeaders.DATE, Calendar.getInstance().getTime().toString());
    httpHeaders.set("Method", "POST");
    httpHeaders.set("Path", homePath);
    maxId++;
    completeCocktail.setId(maxId);
    try {
      completeCocktailService.addCompleteCocktail(completeCocktail);
      CompleteCocktail body = completeCocktailService.getCompleteCocktailById(completeCocktail.getId());
      //CompleteCocktail body = completeCocktailService.addCompleteCocktail(completeCocktail);
      if (body == null) {
        return new ResponseEntity<>(
            httpHeaders,
            HttpStatus.INTERNAL_SERVER_ERROR);
      }
      httpHeaders.set(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON_VALUE);
      httpHeaders.set(HttpHeaders.LOCATION, homePath + "/" + completeCocktail.getId());
      return new ResponseEntity<>(
          body,
          httpHeaders,
          HttpStatus.CREATED
      );
    } catch (Exception e) {
      e.printStackTrace();
      return new ResponseEntity<>(
          httpHeaders,
          HttpStatus.INTERNAL_SERVER_ERROR
      );
    }
  }

  @PutMapping(value = "/{id}", consumes = MediaType.APPLICATION_JSON_VALUE)
  public ResponseEntity<CompleteCocktail> updateCompleteCocktail(@PathVariable Integer id, @RequestBody @Valid CompleteCocktail completeCocktail) {
    HttpHeaders httpHeaders = new HttpHeaders();
    httpHeaders.set(HttpHeaders.DATE, Calendar.getInstance().getTime().toString());
    httpHeaders.set("Method", "PUT");
    httpHeaders.set("Path", homePath + "/" + id);
    try {
      CompleteCocktail body = completeCocktailService.getCompleteCocktailById(id);
      if (body == null) {
        //return addCompleteCocktail(completeCocktail);
        httpHeaders = new HttpHeaders();
        maxId++;
        completeCocktail.setId(maxId);
        completeCocktailService.addCompleteCocktail(completeCocktail);
        body = completeCocktailService.getCompleteCocktailById(completeCocktail.getId());
        if (body == null) {
          return new ResponseEntity<>(
              httpHeaders,
              HttpStatus.INTERNAL_SERVER_ERROR);
        }
        httpHeaders.set(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON_VALUE);
        httpHeaders.set(HttpHeaders.LOCATION, homePath + "/" + completeCocktail.getId());
        return new ResponseEntity<>(
            body,
            httpHeaders,
            HttpStatus.CREATED
        );
      }
      completeCocktailService.updateCompleteCocktail(id, completeCocktail);
      body = completeCocktailService.getCompleteCocktailById(id);
      httpHeaders.set(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON_VALUE);
      httpHeaders.set(HttpHeaders.DATE, Calendar.getInstance().getTime().toString());
      httpHeaders.set(HttpHeaders.LOCATION, homePath + "/" + completeCocktail.getId());
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
  public ResponseEntity<CompleteCocktail> deleteCompleteCocktail(@PathVariable Integer id) {
    HttpHeaders httpHeaders = new HttpHeaders();
    httpHeaders.set(HttpHeaders.DATE, Calendar.getInstance().getTime().toString());
    httpHeaders.set("Method", "DELETE");
    httpHeaders.set("Path", homePath + "/" + id);
    try {
      CompleteCocktail body = completeCocktailService.getCompleteCocktailById(id);
      if (body == null) {
        return new ResponseEntity<>(
            httpHeaders,
            HttpStatus.NOT_FOUND
        );
      }
      completeCocktailService.deleteCompleteCocktail(id);
      body = completeCocktailService.getCompleteCocktailById(id);
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
//    return new ResponseEntity<>("Not valid due to validation error: " + e.getMessage(), HttpStatus.BAD_REQUEST);
//  }
}
