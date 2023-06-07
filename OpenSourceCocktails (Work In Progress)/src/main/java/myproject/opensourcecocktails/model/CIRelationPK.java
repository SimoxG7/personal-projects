package myproject.opensourcecocktails.model;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class CIRelationPK {

  @Min(1)
  @NotNull
  private Integer c_id;

  @Min(1)
  @NotNull
  private Integer i_id;
}
