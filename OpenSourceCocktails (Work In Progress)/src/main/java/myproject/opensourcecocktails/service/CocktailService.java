package myproject.opensourcecocktails.service;

import myproject.opensourcecocktails.model.Cocktail;
import myproject.opensourcecocktails.model.CompleteCocktail;
import myproject.opensourcecocktails.repository.CocktailRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class CocktailService {
  @Autowired
  private CocktailRepository cocktailRepository;

  public List<Cocktail> getAllCocktails() {
    List<Cocktail> lst = new ArrayList<>();
    cocktailRepository.findAll().forEach(lst::add);
    return lst;
  }

  public Cocktail getCocktailById(Integer id) {
    Optional<Cocktail> result = cocktailRepository.findById(id);
    return result.orElse(null);
  }

  public void addCocktail(Cocktail cocktail) {
    cocktailRepository.save(cocktail);
  }

  public void updateCocktail(Integer id, Cocktail cocktail) {
//    if (!id.equals(cocktail.getId())) return;
    cocktailRepository.deleteById(id);
    cocktail.setId(id);
    cocktailRepository.save(cocktail);
  }

  public void deleteCocktail(Integer id) {
    cocktailRepository.deleteById(id);
  }

  public void deleteCocktail(Cocktail cocktail) {
    cocktailRepository.delete(cocktail);
  }

  public List<Cocktail> getCocktailByName(String name) {
    return getAllCocktails().stream().filter(c ->
        (c.getName().trim().toLowerCase().contains(name.trim().toLowerCase()))).toList();
  }
}
