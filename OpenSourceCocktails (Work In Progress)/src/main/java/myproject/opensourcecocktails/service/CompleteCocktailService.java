package myproject.opensourcecocktails.service;

import myproject.opensourcecocktails.model.CompleteCocktail;
import myproject.opensourcecocktails.repository.CompleteCocktailRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.Optional;

@Service
public class CompleteCocktailService {

  @Autowired
  private CompleteCocktailRepository completeCocktailRepository;

  public List<CompleteCocktail> getAllCompleteCocktails() {
    List<CompleteCocktail> lst = new ArrayList<>();
    completeCocktailRepository.findAll().forEach(lst::add);
    return lst;
  }

  public CompleteCocktail getCompleteCocktailById(Integer id) {
    Optional<CompleteCocktail> result = completeCocktailRepository.findById(id);
    return result.orElse(null);
  }

  public List<CompleteCocktail> getCompleteCocktailByName(String name) {
    return getAllCompleteCocktails().stream().filter(c ->
        (c.getName().trim().toLowerCase().contains(name.trim().toLowerCase()))).toList();
  }

  public void addCompleteCocktail(CompleteCocktail completeCocktail) {
    completeCocktailRepository.save(completeCocktail);
  }

  public void updateCompleteCocktail(Integer id, CompleteCocktail completeCocktail) {
    if (!Objects.equals(id, completeCocktail.getId())) return;
    completeCocktailRepository.deleteById(id);
    completeCocktailRepository.save(completeCocktail);
  }

  public void deleteCompleteCocktail(Integer id) {
    completeCocktailRepository.deleteById(id);
  }

  public void deleteCompleteCocktail(CompleteCocktail completeCocktail) {
    completeCocktailRepository.delete(completeCocktail);
  }

}
