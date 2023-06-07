package myproject.opensourcecocktails.controller;

import jakarta.validation.Valid;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import myproject.opensourcecocktails.model.Cocktail;
import myproject.opensourcecocktails.service.CocktailService;
import myproject.opensourcecocktails.utils.Constants;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import jakarta.validation.ConstraintViolationException;
import java.util.Calendar;
import java.util.List;

@RestController
@RequestMapping(value = Constants.COCKTAIL_PATH)
public class CocktailController {

  @Autowired
  private CocktailService cocktailService;
  private final String homePath = Constants.COCKTAIL_PATH;

  private int maxId = 90;

  @GetMapping(value = "", produces = MediaType.APPLICATION_JSON_VALUE)
  public ResponseEntity<List<Cocktail>> getAllCocktails() {
    HttpHeaders httpHeaders = new HttpHeaders();
    httpHeaders.set(HttpHeaders.DATE, Calendar.getInstance().getTime().toString());
    httpHeaders.set("Method", "GET");
    httpHeaders.set("Path", homePath);
    try {
      List<Cocktail> body = cocktailService.getAllCocktails();
//    if (body.isEmpty()) {
//      return new ResponseEntity<>(
//          body,
//          httpHeaders,
//          HttpStatus.NOT_FOUND
//      );
//    }
      //even if empty, should be 200 according to 6.6
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
  public ResponseEntity<Cocktail> getCocktailById(@PathVariable Integer id) {
    HttpHeaders httpHeaders = new HttpHeaders();
    httpHeaders.set(HttpHeaders.DATE, Calendar.getInstance().getTime().toString());
    httpHeaders.set("Method", "GET");
    httpHeaders.set("Path", homePath + "/" + id);
    try {
      Cocktail body = cocktailService.getCocktailById(id);
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
  public ResponseEntity<List<Cocktail>> getCocktailByName(@PathVariable @NotBlank String name) {
    HttpHeaders httpHeaders = new HttpHeaders();
    httpHeaders.set(HttpHeaders.DATE, Calendar.getInstance().getTime().toString());
    httpHeaders.set("Method", "GET");
    httpHeaders.set("Path", homePath + "/search/" + name);
    try {
      List<Cocktail> body = cocktailService.getCocktailByName(name);
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

  //create new cocktail
  @PostMapping(value = "", consumes = MediaType.APPLICATION_JSON_VALUE)
  public ResponseEntity<Cocktail> addCocktail(@RequestBody @Valid Cocktail cocktail) {
    HttpHeaders httpHeaders = new HttpHeaders();
    httpHeaders.set(HttpHeaders.DATE, Calendar.getInstance().getTime().toString());
    httpHeaders.set("Method", "POST");
    httpHeaders.set("Path", homePath);
    maxId++;
    cocktail.setId(maxId);
    try {
      cocktailService.addCocktail(cocktail);
      Cocktail body = cocktailService.getCocktailById(cocktail.getId());
      if (body == null) {
        return new ResponseEntity<>(
            httpHeaders,
            HttpStatus.INTERNAL_SERVER_ERROR);
      }
      httpHeaders.set(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON_VALUE);
      httpHeaders.set(HttpHeaders.LOCATION, homePath + "/" + cocktail.getId());
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

  //update cocktail
  @PutMapping(value = "/{id}", consumes = MediaType.APPLICATION_JSON_VALUE)
  public ResponseEntity<Cocktail> updateCocktail(@PathVariable Integer id, @RequestBody @Valid Cocktail cocktail) {
    HttpHeaders httpHeaders = new HttpHeaders();
    httpHeaders.set(HttpHeaders.DATE, Calendar.getInstance().getTime().toString());
    httpHeaders.set("Method", "PUT");
    httpHeaders.set("Path", homePath + "/" + id);
    try {
      Cocktail body = cocktailService.getCocktailById(id);
      if (body == null) {
        //return addCocktail(cocktail);
        maxId++;
        cocktail.setId(maxId);
        cocktailService.addCocktail(cocktail);
        body = cocktailService.getCocktailById(cocktail.getId());
        if (body == null) {
          return new ResponseEntity<>(
              httpHeaders,
              HttpStatus.INTERNAL_SERVER_ERROR);
        }
        body = cocktailService.getCocktailById(cocktail.getId());
        httpHeaders.set(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON_VALUE);
        httpHeaders.set(HttpHeaders.LOCATION, homePath + "/" + cocktail.getId());
        return new ResponseEntity<>(
            body,
            httpHeaders,
            HttpStatus.CREATED
        );
      }
      cocktailService.updateCocktail(id, cocktail);
      body = cocktailService.getCocktailById(id);
      httpHeaders.set(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON_VALUE);
      httpHeaders.set(HttpHeaders.DATE, Calendar.getInstance().getTime().toString());
      httpHeaders.set(HttpHeaders.LOCATION, homePath + "/" + cocktail.getId());
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
  public ResponseEntity<Cocktail> deleteCocktail(@PathVariable Integer id) {
    HttpHeaders httpHeaders = new HttpHeaders();
    httpHeaders.set(HttpHeaders.DATE, Calendar.getInstance().getTime().toString());
    httpHeaders.set("Method", "DELETE");
    httpHeaders.set("Path", homePath + "/" + id);
    try {
      Cocktail body = cocktailService.getCocktailById(id);
      if (body == null) {
        return new ResponseEntity<>(
            httpHeaders,
            HttpStatus.NOT_FOUND
        );
      }
      cocktailService.deleteCocktail(id);
      body = cocktailService.getCocktailById(id);
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

  @ExceptionHandler(ConstraintViolationException.class)
  @ResponseStatus(HttpStatus.BAD_REQUEST)
  ResponseEntity<String> handleConstraintViolationException(ConstraintViolationException e) {
    System.out.println("do i even get in here");
    return new ResponseEntity<>("Not valid due to validation error: " + e.getMessage(), HttpStatus.BAD_REQUEST);
  }
}
