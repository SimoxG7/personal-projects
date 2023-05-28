package myproject.opensourcecocktails.controller;

import myproject.opensourcecocktails.model.Cocktail;
import myproject.opensourcecocktails.model.CompleteCocktail;
import myproject.opensourcecocktails.service.CocktailService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

import java.util.Comparator;
import java.util.List;

@RestController
public class CocktailController {

  @Autowired
  private CocktailService cocktailService;

  @GetMapping(value = "/cocktail", produces = MediaType.APPLICATION_JSON_VALUE)
  public List<Cocktail> getAllCocktails() {
    return cocktailService.getAllCocktails();
  }

  @GetMapping(value = "/cocktail/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
  public Cocktail getCocktailById(@PathVariable Integer id) {
    return cocktailService.getCocktailById(id);
  }

  @GetMapping(value = "/cocktail/search/{name}", produces = MediaType.APPLICATION_JSON_VALUE)
  public List<Cocktail> getCocktailByName(@PathVariable String name) {
    return cocktailService.getCocktailByName(name);
  }

  //create new cocktail
  @PostMapping(value = "/cocktail", consumes = MediaType.APPLICATION_JSON_VALUE)
  public void addCocktail(@RequestBody Cocktail cocktail) {
    cocktailService.addCocktail(cocktail);
  }

  //update cocktail
  @PutMapping(value = "/cocktail/{id}", consumes = MediaType.APPLICATION_JSON_VALUE)
  public void updateCocktail(@PathVariable Integer id, @RequestBody Cocktail cocktail) {
    cocktailService.updateCocktail(id, cocktail);
  }

  @DeleteMapping(value = "/cocktail/{id}")
  public void deleteCocktail(@PathVariable Integer id) {
    cocktailService.deleteCocktail(id);
  }
}
