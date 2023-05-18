package myproject.opensourcecocktails.model;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.IdClass;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
@IdClass(CIRelationPK.class)
public class CIRelation {
  @Id
  private Integer c_id;
  @Id
  private Integer i_id;
  private String i_quantity;

  @Override
  public String toString() {
    StringBuilder sb = new StringBuilder();
    sb.append("Cocktail ID: ").append(c_id)
        .append("\nIngredient ID: ").append(i_id)
        .append("\nIngredient quantity: ").append(i_quantity).append("\n\n");
    return sb.toString();
  }
}
