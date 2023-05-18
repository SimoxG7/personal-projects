package myproject.opensourcecocktails.model;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
public class Ingredient {
  @Id
  private Integer id;
  private String name;

  @Override
  public String toString() {
    StringBuilder sb = new StringBuilder();
    sb.append("Ingredient ID: ").append(id)
        .append("\nName: ").append(name).append("\n\n");
    return sb.toString();
  }
}
