package myproject.opensourcecocktails.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.validation.constraints.Min;
import myproject.opensourcecocktails.model.CIRelation;
import myproject.opensourcecocktails.model.CompleteCocktail;
import myproject.opensourcecocktails.service.CIRelationService;
import myproject.opensourcecocktails.service.CompleteCocktailService;
import myproject.opensourcecocktails.service.IngredientService;
import myproject.opensourcecocktails.utils.Constants;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import tech.tablesaw.aggregate.AggregateFunctions;
import tech.tablesaw.api.IntColumn;
import tech.tablesaw.api.StringColumn;
import tech.tablesaw.api.Table;
import tech.tablesaw.io.DataFrameReader;
import tech.tablesaw.io.DataReader;
import tech.tablesaw.io.ReadOptions;
import java.util.*;

@RestController
@RequestMapping(value = Constants.TABLESAW_PATH)
public class TableSawController {

  @Autowired
  CompleteCocktailService completeCocktailService;

  @Autowired
  IngredientService ingredientService;

  @Autowired
  CIRelationService ciRelationService;

  private final String homePath = Constants.TABLESAW_PATH;

  @GetMapping(value = "/ccstats", produces = "text/plain")
  public ResponseEntity<String> completeCocktailIngStats() {
    HttpHeaders httpHeaders = new HttpHeaders();
    httpHeaders.set(HttpHeaders.DATE, Calendar.getInstance().getTime().toString());
    httpHeaders.set("Method", "GET");
    httpHeaders.set("Path", homePath + "/ccstats");
    try {
//      List<String> names = new ArrayList<>();
//      List<String> ingsAndQuants = new ArrayList<>();
//      completeCocktailService.getAllCompleteCocktails().forEach(cc -> {
//        names.add(cc.getName());
//        ingsAndQuants.add(cc.getIngredientsAndQuantities());
//      });
//      Table table = Table.create("CompleteCocktails").addColumns(StringColumn.create("Name", names), StringColumn.create("IngsAndQuants", ingsAndQuants));
//      System.out.println(table.print());
//      OutputStream body = new ByteArrayOutputStream();
//      table.write().csv(body);
//      return new ResponseEntity<>(
//          body.toString(),
//          httpHeaders,
//          HttpStatus.OK
//      );
      List<String> names = new ArrayList<>();
      List<Integer> ingCounter = new ArrayList<>();
      completeCocktailService.getAllCompleteCocktails().forEach(cc -> {
        names.add(cc.getName());
        ingCounter.add(cc.getIngredientsAndQuantities().split(",").length);
      });
      Table table = Table.create("CompleteCocktails").addColumns(StringColumn.create("Name", names), IntColumn.create("IngredientsNumbers", ingCounter.toArray(Integer[]::new)));
//      String body = table.printAll();
      Table summaryIngNums = table.summarize("IngredientsNumbers", AggregateFunctions.max, AggregateFunctions.min, AggregateFunctions.mean, AggregateFunctions.median, AggregateFunctions.variance).apply();
//      body += "\n\n" + summary.printAll();
      String body = summaryIngNums.printAll();
//      body += "\n\n" + table.selectColumns("Name").printAll();
      return new ResponseEntity<>(
          body,
          httpHeaders,
          HttpStatus.OK
      );
    } catch (Exception e) {
      e.printStackTrace();
      return new ResponseEntity<>(
          httpHeaders,
          HttpStatus.INTERNAL_SERVER_ERROR
      );
    }
  }

  @GetMapping(value = "/filter/>={ingnum}", produces = "text/plain")
  public ResponseEntity<String> filterByIngnum(@PathVariable @Min(1) int ingnum) {
    HttpHeaders httpHeaders = new HttpHeaders();
    httpHeaders.set(HttpHeaders.DATE, Calendar.getInstance().getTime().toString());
    httpHeaders.set("Method", "GET");
    httpHeaders.set("Path", homePath + "/filter/>=" + ingnum);
    try {
      List<String> names = new ArrayList<>();
      List<Integer> ingCounter = new ArrayList<>();
      completeCocktailService.getAllCompleteCocktails().forEach(cc -> {
        names.add(cc.getName());
        ingCounter.add(cc.getIngredientsAndQuantities().split(",").length);
      });
      Table table = Table.create("CompleteCocktails").addColumns(StringColumn.create("Name", names), IntColumn.create("IngredientsNumbers", ingCounter.toArray(Integer[]::new)));
      String body = table.where(table.intColumn("IngredientsNumbers").isGreaterThanOrEqualTo(ingnum)).printAll();
      return new ResponseEntity<>(
          body,
          httpHeaders,
          HttpStatus.OK
      );
    } catch (Exception e) {
      e.printStackTrace();
      return new ResponseEntity<>(
          httpHeaders,
          HttpStatus.INTERNAL_SERVER_ERROR
      );
    }
  }

  @GetMapping(value = "/ingstats")
  public ResponseEntity<String> ingredientStats() {
    HttpHeaders httpHeaders = new HttpHeaders();
    httpHeaders.set(HttpHeaders.DATE, Calendar.getInstance().getTime().toString());
    httpHeaders.set("Method", "GET");
    httpHeaders.set("Path", homePath + "/ingstats");
    try {
      Map<Integer, Integer> ingIdToOccurr = new HashMap<>();
      ingredientService.getAllIngredients().forEach(ing -> {
        ingIdToOccurr.put(ing.getId(), 0);
      });
      ciRelationService.getAllCIRelations().forEach(cir -> {
        ingIdToOccurr.put(cir.getI_id(), ingIdToOccurr.get(cir.getI_id()) + 1);
      });
      List<String> names = new ArrayList<>();
      List<Integer> occurrences = new ArrayList<>();
      for (Map.Entry<Integer, Integer> e : ingIdToOccurr.entrySet()) {
        names.add(ingredientService.getIngredientById(e.getKey()).getName());
        occurrences.add(e.getValue());
      }
      Table table = Table.create("IngredientOccurrences").addColumns(StringColumn.create("Name", names), IntColumn.create("Occurrences", occurrences.toArray(Integer[]::new)));
      String body = table.sortDescendingOn("Occurrences").printAll();
      return new ResponseEntity<>(
          body,
          httpHeaders,
          HttpStatus.OK
      );
    } catch (Exception e) {
      e.printStackTrace();
      return new ResponseEntity<>(
          httpHeaders,
          HttpStatus.INTERNAL_SERVER_ERROR
      );
    }
  }

  @GetMapping(value = "ccviewer")
  public ResponseEntity<String> ccViewer() {
    HttpHeaders httpHeaders = new HttpHeaders();
    httpHeaders.set(HttpHeaders.DATE, Calendar.getInstance().getTime().toString());
    httpHeaders.set("Method", "GET");
    httpHeaders.set("Path", homePath + "/ccviewer");
    try {
      int[] ingCount = new int[completeCocktailService.getAllCompleteCocktails().size()];
      for (CIRelation cir : ciRelationService.getAllCIRelations()) {
        ingCount[cir.getC_id()-1]++;
      }
      Table body = Table.read().csv("src/main/resources/completecocktail.csv").addColumns(IntColumn.create("IngNum", ingCount));
      return new ResponseEntity<>(
          body.printAll(),
          httpHeaders,
          HttpStatus.OK
      );
    } catch (Exception e) {
      e.printStackTrace();
      return new ResponseEntity<>(
          httpHeaders,
          HttpStatus.INTERNAL_SERVER_ERROR
      );
    }
  }
}
