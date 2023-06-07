package myproject.opensourcecocktails.model;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import jakarta.validation.ConstraintViolationException;
import jakarta.validation.constraints.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
public class Ingredient {

  @Id
//  @Min(1)
//  @NotNull
//  @PositiveOrZero(message = "Ingredient's id must be a positive Integer or 0")
  private Integer id;

  //[a-zA-Z 1-9]+[-]+[a-zA-Z 1-9]+[;]+[a-zA-Z 1-9]+[-]+[a-zA-Z 1-9]
  @NotBlank(message = "Ingredient name cannot be blank or null")
  private String name;

  @Override
  public String toString() {
    StringBuilder sb = new StringBuilder();
    sb.append("Ingredient ID: ").append(id)
        .append("\nName: ").append(name).append("\n\n");
    return sb.toString();
  }
}
