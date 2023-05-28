package myproject.opensourcecocktails.controller;

import myproject.opensourcecocktails.model.CIRelation;
import myproject.opensourcecocktails.service.CIRelationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
public class CIRelationController {

  @Autowired
  private CIRelationService ciRelationService;

  @GetMapping(value = "/ci_relation", produces = MediaType.APPLICATION_JSON_VALUE)
  public List<CIRelation> getAllCIRelations() {
    return ciRelationService.getAllCIRelations();
  }

  @GetMapping(value = "/ci_relation/{c_id}_{i_id}", produces = MediaType.APPLICATION_JSON_VALUE)
  public CIRelation getCIRelationByIds(@PathVariable Integer c_id, @PathVariable Integer i_id) {
    return ciRelationService.getCIRelationByIds(c_id, i_id);
  }

  @PostMapping(value = "/ci_relation", consumes = MediaType.APPLICATION_JSON_VALUE)
  public void addCIRelation(@RequestBody CIRelation ciRelation) {
    ciRelationService.addCIRelation(ciRelation);
  }

  @PutMapping(value = "/ci_relation/{c_id}_{i_id}", consumes = MediaType.APPLICATION_JSON_VALUE)
  public void updateCIRelation(@PathVariable Integer c_id, @PathVariable Integer i_id, @RequestBody CIRelation ciRelation) {
    ciRelationService.updateCIRelation(c_id, i_id, ciRelation);
  }

  @DeleteMapping(value = "/ci_relation/{c_id}_{i_id}")
  public void deleteCIRelation(@PathVariable Integer c_id, @PathVariable Integer i_id) {
    ciRelationService.deleteCIRelation(c_id, i_id);
  }

}
