package myproject.opensourcecocktails.model;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
public class CompleteCocktail {

  @Id
  private Integer id;
  private String name;
  private String category;
  @Column(columnDefinition = "TEXT")
  private String method;
  private String garnish;
  @Column(columnDefinition = "TEXT")
  private String history;
  private String note;
  private Integer simoxrate;
  private String link;
  private String imageurl;
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
        .append("\nSimoxrate: ").append(simoxrate)
        .append("\nImageurl: ").append(imageurl)
        .append("\nIngredients and quantities: ").append(ingredientsAndQuantities).append("\n\n");
    return sb.toString();
  }
}
