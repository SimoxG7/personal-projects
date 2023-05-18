package myproject.opensourcecocktails.service;

import myproject.opensourcecocktails.model.Ingredient;
import myproject.opensourcecocktails.repository.IngredientRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Configurable;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.Optional;

@Service
@Configurable
public class IngredientService {

  //injected at creation of IngredientService
  @Autowired
  private IngredientRepository ingredientRepository;

  public List<Ingredient> getAllIngredients() {
    List<Ingredient> lst = new ArrayList<>();
    ingredientRepository.findAll().forEach(lst::add);
    return lst;
  }

  public Ingredient getIngredientById(Integer id) {
    Optional<Ingredient> result = ingredientRepository.findById(id);
    return result.orElse(null);
  }

  public void addIngredient(Ingredient ingredient) {
    ingredientRepository.save(ingredient);
  }

  public void updateIngredient(Integer id, Ingredient ingredient) {
    if (!Objects.equals(id, ingredient.getId())) return;
    ingredientRepository.deleteById(id);
    ingredientRepository.save(ingredient);
  }

  public void deleteIngredient(Integer id) {
    ingredientRepository.deleteById(id);
  }

  public void deleteIngredient(Ingredient ingredient) {
    ingredientRepository.delete(ingredient);
  }

}
