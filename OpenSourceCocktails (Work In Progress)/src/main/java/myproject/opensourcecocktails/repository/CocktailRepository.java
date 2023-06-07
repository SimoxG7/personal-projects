package myproject.opensourcecocktails.repository;

import myproject.opensourcecocktails.model.Cocktail;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.StreamSupport;

@Repository
public interface CocktailRepository extends CrudRepository<Cocktail, Integer> {
  default List<Cocktail> findByName(String name) {
    return StreamSupport.stream(findAll().spliterator(), false).filter(cocktail -> {
      return cocktail.getName().toLowerCase().contains(name.trim().toLowerCase());
    }).toList();
  }
}
