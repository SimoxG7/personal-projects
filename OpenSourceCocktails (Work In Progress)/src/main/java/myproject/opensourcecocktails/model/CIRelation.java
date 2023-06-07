package myproject.opensourcecocktails.model;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.IdClass;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
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
  @NotNull
  @Min(1)
  private Integer c_id;

  @Id
  @NotNull
  @Min(1)
  private Integer i_id;

  @NotBlank(message = "CIRelation's i_quanity cannot be blank or null")
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
