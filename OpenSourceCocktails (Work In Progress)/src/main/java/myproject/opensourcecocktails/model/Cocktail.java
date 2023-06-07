package myproject.opensourcecocktails.model;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.validation.constraints.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
public class Cocktail {

  @Id
//  @NotNull
//  @Min(1)
//  @PositiveOrZero(message = "Cocktail's id must be a positive Integer or 0")
  private Integer id;

  @NotBlank(message = "Cocktail's name cannot be blank or null")
  private String name;

//  @NotBlank
  private String category;

  @NotBlank(message = "Cocktail's method cannot be blank or null")
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

  @Override
  public String toString() {
    StringBuilder sb = new StringBuilder();
    sb.append("Cocktail ID: ").append(id)
        .append("\nName: ").append(name)
        .append("\nCategory: ").append(category)
        .append("\nMethod: ").append(method)
        .append("\nGarnish: ").append(garnish)
        .append("\nHistory: ").append(history)
        .append("\nNote: ").append(note)
        .append("\nSimoxrate: ").append(simoxrate)
        .append("\nImageurl: ").append(imageurl).append("\n\n");
    return sb.toString();
  }
}
