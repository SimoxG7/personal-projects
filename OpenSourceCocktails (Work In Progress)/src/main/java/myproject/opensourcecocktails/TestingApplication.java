package myproject.opensourcecocktails;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import myproject.opensourcecocktails.model.CIRelation;
import myproject.opensourcecocktails.model.Cocktail;
import myproject.opensourcecocktails.container.Container;
import myproject.opensourcecocktails.model.CompleteCocktail;
import myproject.opensourcecocktails.model.Ingredient;
import myproject.opensourcecocktails.service.CIRelationService;
import myproject.opensourcecocktails.service.CocktailService;
import myproject.opensourcecocktails.service.CompleteCocktailService;
import myproject.opensourcecocktails.service.IngredientService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.core.io.Resource;
import org.springframework.core.io.ResourceLoader;

import java.io.File;

@SpringBootApplication
public class TestingApplication {

	@Autowired
	private IngredientService ingredientService;

	@Autowired
	private CocktailService cocktailService;

	@Autowired
	private CIRelationService ciRelationService;

	@Autowired
	private CompleteCocktailService completeCocktailService;

	@Autowired
	private ResourceLoader resourceLoader;

	public static void main(String[] args) {
		SpringApplication.run(TestingApplication.class, args);
	}

	@Bean
	public CommandLineRunner populateFromJSON() {
		return (args) -> {

			//SETUP -------------------------------------------------------------------------------------
			ObjectMapper objectMapper = new ObjectMapper();

			//COCKTAILS ---------------------------------------------------------------------------------
			Resource resource = resourceLoader.getResource("classpath:cocktail.json");
			File cocktailjson = resource.getFile();
			Container<Cocktail> cocktailContainer = objectMapper.readValue(cocktailjson, new TypeReference<>() {
			});

			for (Cocktail cocktail : cocktailContainer.getContainer()) {
				cocktailService.addCocktail(cocktail);
			}

			//INGREDIENTS -------------------------------------------------------------------------------
			resource = resourceLoader.getResource("classpath:ingredient.json");
			File ingredientjson = resource.getFile();
			Container<Ingredient> ingredientContainer = objectMapper.readValue(ingredientjson, new TypeReference<>() {
			});

			for (Ingredient ingredient : ingredientContainer.getContainer()) {
				ingredientService.addIngredient(ingredient);
			}

			//CIRELATIONS -------------------------------------------------------------------------------
			resource = resourceLoader.getResource("classpath:ci_relation.json");
			File cirelationsjson = resource.getFile();
			Container<CIRelation> ciRelationContainer = objectMapper.readValue(cirelationsjson, new TypeReference<>() {
			});

			for (CIRelation ciRelation : ciRelationContainer.getContainer()) {
				ciRelationService.addCIRelation(ciRelation);
			}

			//TEST --------------------------------------------------------------------------------------

			for (Cocktail cocktail : cocktailService.getAllCocktails()) {
				System.out.println(cocktail);
			}

			for (Ingredient ingredient : ingredientService.getAllIngredients()) {
				System.out.println(ingredient);
			}

			for (CIRelation ciRelation : ciRelationService.getAllCIRelations()) {
				System.out.println(ciRelation);
			}

			//CREATE COMPLETECOCKTAIL -------------------------------------------------------------------

			StringBuilder[] ingredientsAndQuantities = new StringBuilder[cocktailService.getAllCocktails().size()];

			for (CIRelation ingredientAndQuantity : ciRelationService.getAllCIRelations()) {
				if (ingredientsAndQuantities[ingredientAndQuantity.getC_id()-1] == null) {
					ingredientsAndQuantities[ingredientAndQuantity.getC_id()-1] = new StringBuilder(ingredientService.getIngredientById(ingredientAndQuantity.getI_id()).getName() + " - " + ingredientAndQuantity.getI_quantity());
				} else {
					ingredientsAndQuantities[ingredientAndQuantity.getC_id() - 1].append(", ").append(ingredientService.getIngredientById(ingredientAndQuantity.getI_id()).getName()).append(" - ").append(ingredientAndQuantity.getI_quantity());
				}
			}

			for (Cocktail cocktail : cocktailService.getAllCocktails()) {
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
						ingredientsAndQuantities[cocktail.getId()-1].toString()
						);
				completeCocktailService.addCompleteCocktail(completeCocktail);
			}

			for (CompleteCocktail completeCocktail : completeCocktailService.getAllCompleteCocktails()) {
				System.out.println(completeCocktail);
			}

			System.out.println(completeCocktailService.getCompleteCocktailByName("dry martini"));
			System.out.println(completeCocktailService.getCompleteCocktailByName("martini"));

		};
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
