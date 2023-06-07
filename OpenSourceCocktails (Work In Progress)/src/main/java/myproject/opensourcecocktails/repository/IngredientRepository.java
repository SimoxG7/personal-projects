package myproject.opensourcecocktails.repository;

import aj.org.objectweb.asm.ConstantDynamic;
import myproject.opensourcecocktails.model.Cocktail;
import myproject.opensourcecocktails.model.Ingredient;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.stream.StreamSupport;

@Repository
public interface IngredientRepository extends CrudRepository<Ingredient, Integer> {
  default List<Ingredient> findByName(String name) {
    return StreamSupport.stream(findAll().spliterator(), false).filter(ingredient -> {
      return ingredient.getName().toLowerCase().contains(name.trim().toLowerCase());
    }).toList();
  }
}
