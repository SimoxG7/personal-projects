package myproject.opensourcecocktails.repository;

import myproject.opensourcecocktails.model.Cocktail;
import myproject.opensourcecocktails.model.CompleteCocktail;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.StreamSupport;

@Repository
public interface CompleteCocktailRepository extends CrudRepository<CompleteCocktail, Integer> {
  default List<CompleteCocktail> findByName(String name) {
    return StreamSupport.stream(findAll().spliterator(), false).filter(completeCocktail -> {
      return completeCocktail.getName().toLowerCase().contains(name.trim().toLowerCase());
    }).toList();
  }
}
