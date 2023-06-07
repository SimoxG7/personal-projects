package myproject.opensourcecocktails;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import myproject.opensourcecocktails.container.Container;
import myproject.opensourcecocktails.model.CIRelation;
import myproject.opensourcecocktails.model.Cocktail;
import myproject.opensourcecocktails.model.CompleteCocktail;
import myproject.opensourcecocktails.model.Ingredient;
import myproject.opensourcecocktails.repository.CIRelationRepository;
import myproject.opensourcecocktails.repository.CocktailRepository;
import myproject.opensourcecocktails.repository.CompleteCocktailRepository;
import myproject.opensourcecocktails.repository.IngredientRepository;
import myproject.opensourcecocktails.formatter.Formatter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.core.io.Resource;
import org.springframework.core.io.ResourceLoader;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.web.client.RestTemplate;
import java.io.File;
import java.util.Optional;
import java.util.stream.StreamSupport;


@SpringBootApplication
public class TestingApplication {

  @Autowired
//	private IngredientService ingredientService;
//  private IngredientController ingredientController;
  private IngredientRepository ingredientRepository;

  @Autowired
//	private CocktailService cocktailService;
//  private CocktailController cocktailController;
  private CocktailRepository cocktailRepository;


  @Autowired
//	private CIRelationService ciRelationService;
//  private CIRelationController ciRelationController;
  private CIRelationRepository ciRelationRepository;


  @Autowired
//	private CompleteCocktailService completeCocktailService;
//  private CompleteCocktailController completeCocktailController;
  private CompleteCocktailRepository completeCocktailRepository;


  @Autowired
  private ResourceLoader resourceLoader;

  @Autowired
  private ApplicationContext applicationContext;

  public static void main(String[] args) {
    SpringApplication.run(TestingApplication.class, args);
  }

  @Bean
  public CommandLineRunner runner() {
    return (args) -> {
//      ObjectMapper objectMapper = applicationContext.getBean(ObjectMapper.class);
      System.out.println("SERVICE READY");
//      generateCSVsFromJSONs();
//      generateJSONsFromCSVs();
//      smalltest();
    };
  }


  public void generateCSVsFromJSONs() {
    Formatter.JSONtoCSV("completecocktail_nc.json", "completecocktail.csv");
    Formatter.JSONtoCSV("cocktail_nc.json", "cocktail.csv");
    Formatter.JSONtoCSV("ingredient_nc.json", "ingredient.csv");
    Formatter.JSONtoCSV("ci_relation_nc.json", "ci_relation.csv");
  }

  public void generateJSONsFromCSVs() {
    Formatter.CSVtoJSON("completecocktail.csv", "completecocktail_t.json");
    Formatter.CSVtoJSON("cocktail.csv", "cocktail_t.json");
    Formatter.CSVtoJSON("ingredient.csv", "ingredient_t.json");
    Formatter.CSVtoJSON("ci_relation.csv", "ci_relation_t.json");
  }

  public void smalltest() throws JsonProcessingException {

    //TEST SETUP
    ObjectMapper objectMapper = applicationContext.getBean(ObjectMapper.class);
    System.out.println("STARTING TEST");
    RestTemplate restTemplate = new RestTemplate();
    HttpHeaders headers = new HttpHeaders();
    headers.setContentType(MediaType.APPLICATION_JSON);
    HttpEntity<String> request;

    //TEST POST
    //objectMapper.writeValueAsString(cocktailController.getCocktailById(10));
    Optional<Cocktail> totest1 = cocktailRepository.findById(1);
    if (totest1.isPresent()) {
      String tt1 = objectMapper.writeValueAsString(totest1.get());
      //System.out.println(objectMapper.writeValueAsString(cocktailController.getCocktailById(12)));
      request = new HttpEntity<>(tt1, headers);
      restTemplate.postForEntity("http://localhost:8080/cocktail", request, String.class);
      System.out.println("\n\nNEW COCKTAIL SHOULD BE ADDED WITH ID 123");
      System.out.println(cocktailRepository.findById(123));
    }

    //TEST PUT
    Optional<Cocktail> totest2 = cocktailRepository.findById(90);
    if (totest2.isPresent()) {
      totest2.get().setName("really bad cocktail");
      String tt2 = objectMapper.writeValueAsString(totest2);
      request = new HttpEntity<>(tt2, headers);
      restTemplate.put("http://localhost:8080/cocktail/90", request, String.class);
      System.out.println("\n\nCOCKTAIL NAME SHOULD BE MODIFIED FROM 'Zombie' to 'really bad cocktail'");
      System.out.println(cocktailRepository.findById(90));
    }

    //TEST DELETE
    Optional<Cocktail> totest3 = cocktailRepository.findById(10);
    if (totest3.isPresent()) {
      String tt3 = objectMapper.writeValueAsString(totest3.get());
      request = new HttpEntity<>(tt3, headers);
      restTemplate.delete("http://localhost:8080/cocktail/10", request, String.class);
      System.out.println("\n\nSHOULD BE NULL, CAUSE COCKTAIL SHOULD BE DELETED");
      System.out.println(cocktailRepository.findById(10));
    }

  }


  public void init() {
    //SETUP -------------------------------------------------------------------------------------
    //ObjectMapper objectMapper = new ObjectMapper();
    ObjectMapper objectMapper = applicationContext.getBean(ObjectMapper.class);

    try {

      //COCKTAILS ---------------------------------------------------------------------------------
      Resource resource = resourceLoader.getResource("classpath:cocktail.json");
      File cocktailjson = resource.getFile();
      Container<Cocktail> cocktailContainer = objectMapper.readValue(cocktailjson, new TypeReference<>() {
      });

      for (Cocktail cocktail : cocktailContainer.getContainer()) {
        if (cocktail.getGarnish().equals("NULL")) cocktail.setGarnish("N/A");
        if (cocktail.getHistory().equals("NULL")) cocktail.setHistory("N/A");
        if (cocktail.getNote().equals("NULL")) cocktail.setNote("N/A");
        cocktailRepository.save(cocktail);
      }

      //INGREDIENTS -------------------------------------------------------------------------------
      resource = resourceLoader.getResource("classpath:ingredient.json");
      File ingredientjson = resource.getFile();
      Container<Ingredient> ingredientContainer = objectMapper.readValue(ingredientjson, new TypeReference<>() {
      });

      for (Ingredient ingredient : ingredientContainer.getContainer()) {
        ingredientRepository.save(ingredient);
      }

      //CIRELATIONS -------------------------------------------------------------------------------
      resource = resourceLoader.getResource("classpath:ci_relation.json");
      File cirelationsjson = resource.getFile();
      Container<CIRelation> ciRelationContainer = objectMapper.readValue(cirelationsjson, new TypeReference<>() {
      });

      for (CIRelation ciRelation : ciRelationContainer.getContainer()) {
        ciRelationRepository.save(ciRelation);
      }

      //TEST --------------------------------------------------------------------------------------

      for (Cocktail cocktail : cocktailRepository.findAll()) {
        System.out.println(cocktail);
      }

      for (Ingredient ingredient : ingredientRepository.findAll()) {
        System.out.println(ingredient);
      }

      for (CIRelation ciRelation : ciRelationRepository.findAll()) {
        System.out.println(ciRelation);
      }

      //CREATE COMPLETECOCKTAIL -------------------------------------------------------------------

      StringBuilder[] ingredientsAndQuantities = new StringBuilder[(int) StreamSupport.stream(cocktailRepository.findAll().spliterator(), false).count()];

      for (CIRelation ingredientAndQuantity : ciRelationRepository.findAll()) {
        Optional<Ingredient> ingredient = ingredientRepository.findById(ingredientAndQuantity.getI_id());
        if (ingredientsAndQuantities[ingredientAndQuantity.getC_id() - 1] == null) {
          ingredient.ifPresent(value -> ingredientsAndQuantities[ingredientAndQuantity.getC_id() - 1] = new StringBuilder(value.getName() + " - " + ingredientAndQuantity.getI_quantity()));
        } else {
          ingredient.ifPresent(value -> ingredientsAndQuantities[ingredientAndQuantity.getC_id() - 1].append(ingredient.get().getName()).append(" - ").append(ingredientAndQuantity.getI_quantity()));
//          ingredientsAndQuantities[ingredientAndQuantity.getC_id() - 1].append(", ").append(ingredientRepository.findById(ingredientAndQuantity.getI_id()).getName()).append(" - ").append(ingredientAndQuantity.getI_quantity());
        }
      }

      for (Cocktail cocktail : cocktailRepository.findAll()) {
        CompleteCocktail completeCocktail = new CompleteCocktail(
            cocktail.getId(),
            cocktail.getName(),
            cocktail.getCategory(),
            cocktail.getMethod(),
            cocktail.getGarnish(),
            cocktail.getHistory(),
            cocktail.getNote(),
            cocktail.getSimoxrate(),
            cocktail.getLink(),
            cocktail.getImageurl(),
            ingredientsAndQuantities[cocktail.getId() - 1].toString()
        );
        completeCocktailRepository.save(completeCocktail);
      }

      for (CompleteCocktail completeCocktail : completeCocktailRepository.findAll()) {
        System.out.println(completeCocktail);
      }

      System.out.println(completeCocktailRepository.findByName("dry martini"));
      System.out.println(completeCocktailRepository.findByName("martini"));

    } catch (Exception e) {
      e.printStackTrace();
    }
  }

//	//@Bean
//	public CommandLineRunner test() {
//		return (args) -> {
//			ingredientService.addIngredient(new Ingredient(1, "Water"));
//			ingredientService.addIngredient(new Ingredient(2, "Lemon"));
//			ingredientService.addIngredient(new Ingredient(3, "Coke-Cola"));
//			ingredientService.addIngredient(new Ingredient(4, "Whisky"));
//
//			List<Ingredient> lst = ingredientService.getAllIngredients();
//
//			for (Ingredient ingredient : lst) {
//				System.out.println(ingredient.getId() + " " + ingredient.getName());
//			}
//
//			//--------------
//
//
//			cocktailService.addCocktail(new Cocktail(1, "Trinidad Sour", "", "", "", "", "", 4, "", ""));
//			cocktailService.addCocktail(new Cocktail(2, "Bramble", "", "", "", "", "", 3, "", ""));
//			cocktailService.addCocktail(new Cocktail(3, "Spritz", "", "", "", "", "", 2, "", ""));
//			cocktailService.addCocktail(new Cocktail(4, "tua madre", "", "", "", "", "", 1, "", ""));
//
//			List<Cocktail> lst2 = cocktailService.getAllCocktails();
//
//			for (Cocktail cocktail : lst2) {
//				System.out.println(cocktail.getId() + " " + cocktail.getName() + " " + cocktail.getSimoxrate());
//			}
//
//			cocktailService.deleteCocktail(1);
//			cocktailService.deleteCocktail(2);
//			Cocktail temp = cocktailService.getCocktailById(3);
//			temp.setName("spritz schifo");
//			cocktailService.updateCocktail(3, temp);
//
//			lst2 = cocktailService.getAllCocktails();
//
//			for (Cocktail cocktail : lst2) {
//				System.out.println(cocktail.getId() + " " + cocktail.getName() + " " + cocktail.getSimoxrate());
//			}
//
//			//--------------
//
//			ciRelationService.addCIRelation(new CIRelation(1, 1, "10ml"));
//			ciRelationService.addCIRelation(new CIRelation(2, 2, "15ml"));
//			ciRelationService.addCIRelation(new CIRelation(3, 4, "2 liters"));
//			ciRelationService.addCIRelation(new CIRelation(5, 10, "2 spoons"));
//
//			List<CIRelation> lst3 = ciRelationService.getAllCIRelations();
//
//			for (CIRelation ciRelation : lst3) {
//				System.out.println(ciRelation.getC_id() + " " + ciRelation.getI_id() + " " + ciRelation.getI_quantity());
//			}
//
//			ciRelationService.deleteCIRelation(1, 1);
//			ciRelationService.deleteCIRelation(3, 4);
//			CIRelation temp2 = ciRelationService.getCIRelationByIds(5, 10);
//			temp2.setI_quantity("14941 liters");
//			ciRelationService.updateCIRelation(temp2.getC_id(), temp2.getI_id(), temp2);
//
//			lst3 = ciRelationService.getAllCIRelations();
//
//			for (CIRelation ciRelation : lst3) {
//				System.out.println(ciRelation.getC_id() + " " + ciRelation.getI_id() + " " + ciRelation.getI_quantity());
//			}
//
//		};
//	}

}

/*
@Bean
	public CommandLineRunner test() {
		return (args) -> {
			ingredientService.addIngredient(new Ingredient(1, "Water"));
			ingredientService.addIngredient(new Ingredient(2, "Lemon"));
			ingredientService.addIngredient(new Ingredient(3, "Coke-Cola"));
			ingredientService.addIngredient(new Ingredient(4, "Whisky"));

			List<Ingredient> lst = ingredientService.getAllIngredients();

			for (Ingredient ingredient : lst) {
				System.out.println(ingredient.getId() + " " + ingredient.getName());
			}

			cocktailService.addCocktail(new Cocktail(1, "Trinidad Sour", "", "", "", "", "", 4, "", ""));
			cocktailService.addCocktail(new Cocktail(2, "Bramble", "", "", "", "", "", 3, "", ""));
			cocktailService.addCocktail(new Cocktail(3, "Spritz", "", "", "", "", "", 2, "", ""));
			cocktailService.addCocktail(new Cocktail(4, "tua madre", "", "", "", "", "", 1, "", ""));

			List<Cocktail> lst2 = cocktailService.getAllCocktails();

			for (Cocktail cocktail : lst2) {
				System.out.println(cocktail.getId() + " " + cocktail.getName() + " " + cocktail.getSimoxrate());
			}

			cocktailService.deleteCocktail(1);
			cocktailService.deleteCocktail(2);
			Cocktail temp = cocktailService.getCocktailById(3);
			temp.setName("spritz schifo");
			cocktailService.updateCocktail(3, temp);

			lst2 = cocktailService.getAllCocktails();

			for (Cocktail cocktail : lst2) {
				System.out.println(cocktail.getId() + " " + cocktail.getName() + " " + cocktail.getSimoxrate());
			}

		};
	}
 */
