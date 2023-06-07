package myproject.opensourcecocktails.model;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.validation.constraints.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.checkerframework.checker.regex.qual.Regex;

import java.util.List;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
public class CompleteCocktail {

  @Id
//  @NotNull
//  @Min(1)
//  @PositiveOrZero(message = "CompleteCocktail's id must be a positive Integer or 0")
  private Integer id;

  @NotBlank(message = "CompleteCocktail's name cannot be blank or null")
  private String name;

//  @NotBlank
  private String category;

  @NotBlank(message = "CompleteCocktail's method cannot be blank or null")
  @Column(columnDefinition = "TEXT")
  private String method;

//  @NotNull
  private String garnish;

//  @NotNull
  @Column(columnDefinition = "TEXT")
  private String history;

//  @NotNull
  private String note;

  @Min(1)
  @Max(10)
//  @NotNull
  private Integer simoxrate;

//  @NotNull
  private String link;

//  @NotNull
  private String imageurl;

  //(([A-Za-z0-9]+( [A-Za-z0-9]+)*) - ([A-Za-z0-9]+( [A-Za-z0-9]+)*))(, |$)
  //(([\w+]+( [\w+]+)*) - ([\w+]+( [\w+]+)*))(, |$)
  //((([\w+]+( [\w+]+)*) - ([\w+]+( [\w+]+)*))(, |$))+ //works well
  //((([[\p{L}\p{N}_]+]+( [[\p{L}\p{N}_]]+)*) - ([[\p{L}\p{N}_]+]+( [[\p{L}\p{N}_]+]+)*))(, |$))+ //works well unicode
  //((([[\p{L}\p{N}_'()/]+]+( [[\p{L}\p{N}_'()/]]+)*) - ([[\p{L}\p{N}_'()/]+]+( [[\p{L}\p{N}_'()/]+]+)*))(, |$))+ //unicode + some symbols
  //note: this still accepts 'aaaa, ', the last part should be trimmed accordingly...
  @NotBlank(message = "CompleteCocktail's ingredients and quantities cannot be blank or null")
//  @Pattern(regexp = "((([[\\p{L}\\p{N}_'()/]+]+( [[\\p{L}\\p{N}_'()/]]+)*) - ([[\\p{L}\\p{N}_'()/]+]+( [[\\p{L}\\p{N}_'()/]+]+)*))(, |$))+", message = "Invalid ingredient and quantities format, must be: 'Name1 - Quantity1, Name2 - Quantity2, ... , Namen - Quantityn' (spaces, commas and dashes are necessary)")
  @Column(columnDefinition = "TEXT")
  private String ingredientsAndQuantities;

  @Override
  public String toString() {
    StringBuilder sb = new StringBuilder();
    sb.append("CompleteCocktail ID: ").append(id)
        .append("\nName: ").append(name)
        .append("\nCategory: ").append(category)
        .append("\nMethod: ").append(method)
        .append("\nGarnish: ").append(garnish)
        .append("\nHistory: ").append(history)
        .append("\nNote: ").append(note)
        .append("\nSimoxrate: ").append(simoxrate)
        .append("\nImageurl: ").append(imageurl)
        .append("\nIngredients and quantities: ").append(ingredientsAndQuantities).append("\n\n");
    return sb.toString();
  }
}
