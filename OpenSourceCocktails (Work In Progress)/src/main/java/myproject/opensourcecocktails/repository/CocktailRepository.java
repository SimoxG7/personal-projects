package myproject.opensourcecocktails.repository;

import myproject.opensourcecocktails.model.Cocktail;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CocktailRepository extends CrudRepository<Cocktail, Integer> {
}
