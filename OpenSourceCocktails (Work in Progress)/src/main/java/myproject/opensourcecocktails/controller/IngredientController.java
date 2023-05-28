package myproject.opensourcecocktails.controller;

import myproject.opensourcecocktails.model.Ingredient;
import myproject.opensourcecocktails.service.IngredientService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
public class IngredientController {

  @Autowired
  private IngredientService ingredientService;

  @GetMapping(value = "/ingredient", produces = MediaType.APPLICATION_JSON_VALUE)
  public List<Ingredient> getAllIngredients() {
    return ingredientService.getAllIngredients();
  }

  @GetMapping(value = "/ingredient/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
  public Ingredient getIngredientById(@PathVariable Integer id) {
    return ingredientService.getIngredientById(id);
  }

  @GetMapping(value = "/ingredient/search/{name}", produces = MediaType.APPLICATION_JSON_VALUE)
  public List<Ingredient> getIngredientByName(@PathVariable String name) {
    return ingredientService.getIngredientByName(name);
  }

  @PostMapping(value = "/ingredient", consumes = MediaType.APPLICATION_JSON_VALUE)
  public void addIngredient(@RequestBody Ingredient ingredient) {
    ingredientService.addIngredient(ingredient);
  }

  @PutMapping(value = "/ingredient/{id}", consumes = MediaType.APPLICATION_JSON_VALUE)
  public void updateIngredient(@PathVariable Integer id, @RequestBody Ingredient ingredient) {
    ingredientService.updateIngredient(id, ingredient);
  }

  @DeleteMapping(value = "/ingredient/{id}")
  public void deleteIngredient(@PathVariable Integer id) {
    ingredientService.deleteIngredient(id);
  }


}
