package myproject.opensourcecocktails.controller;

import myproject.opensourcecocktails.model.Cocktail;
import myproject.opensourcecocktails.model.CompleteCocktail;
import myproject.opensourcecocktails.service.CompleteCocktailService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
public class CompleteCocktailController {

  @Autowired
  CompleteCocktailService completeCocktailService;

  @GetMapping(value = "/completecocktail", produces = MediaType.APPLICATION_JSON_VALUE)
  public List<CompleteCocktail> getAllCompleteCocktails() {
    return completeCocktailService.getAllCompleteCocktails();
  }

  @GetMapping(value = "/completecocktail/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
  public CompleteCocktail getCompleteCocktailById(@PathVariable Integer id) {
    return completeCocktailService.getCompleteCocktailById(id);
  }

  @GetMapping(value = "/completecocktail/search/{name}", produces = MediaType.APPLICATION_JSON_VALUE)
  public List<CompleteCocktail> getCompleteCocktailByName(@PathVariable String name) {
    return completeCocktailService.getCompleteCocktailByName(name);
  }

  //create new cocktail
  @PostMapping(value = "/completecocktail", consumes = MediaType.APPLICATION_JSON_VALUE)
  public void addCompleteCocktail(@RequestBody CompleteCocktail completeCocktail) {
    //maxId++;
    //cocktail.setId(maxId);
    completeCocktailService.addCompleteCocktail(completeCocktail);
  }

  //update cocktail
  @PutMapping(value = "/completecocktail/{id}", consumes = MediaType.APPLICATION_JSON_VALUE)
  public void updateCompleteCocktail(@PathVariable Integer id, @RequestBody CompleteCocktail completeCocktail) {
    completeCocktailService.updateCompleteCocktail(id, completeCocktail);
  }

  @DeleteMapping(value = "/completecocktail/{id}")
  public void deleteCompleteCocktail(@PathVariable Integer id) {
    completeCocktailService.deleteCompleteCocktail(id);
  }

}
