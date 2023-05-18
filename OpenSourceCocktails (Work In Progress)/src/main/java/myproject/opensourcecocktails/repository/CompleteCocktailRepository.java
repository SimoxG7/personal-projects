package myproject.opensourcecocktails.repository;

import myproject.opensourcecocktails.model.CompleteCocktail;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CompleteCocktailRepository extends CrudRepository<CompleteCocktail, Integer> {
}
