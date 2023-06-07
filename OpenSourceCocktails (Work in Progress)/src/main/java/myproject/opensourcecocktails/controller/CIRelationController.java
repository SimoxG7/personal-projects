package myproject.opensourcecocktails.controller;

import jakarta.validation.Valid;
import myproject.opensourcecocktails.model.CIRelation;
import myproject.opensourcecocktails.model.CompleteCocktail;
import myproject.opensourcecocktails.service.CIRelationService;
import myproject.opensourcecocktails.utils.Constants;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Calendar;
import java.util.LinkedList;
import java.util.List;
import java.util.Queue;

@RestController
@RequestMapping(value = Constants.CI_RELATION_PATH)
public class CIRelationController {

  @Autowired
  private CIRelationService ciRelationService;

  private final String homePath = Constants.CI_RELATION_PATH;


  @GetMapping(value = "", produces = MediaType.APPLICATION_JSON_VALUE)
  public ResponseEntity<List<CIRelation>> getAllCIRelations() {
    HttpHeaders httpHeaders = new HttpHeaders();
    httpHeaders.set(HttpHeaders.DATE, Calendar.getInstance().getTime().toString());
    httpHeaders.set("Method", "GET");
    httpHeaders.set("Path", homePath);
    try {
      List<CIRelation> body = ciRelationService.getAllCIRelations();
//      if (body.isEmpty()) {
//        return new ResponseEntity<>(
//            body,
//            httpHeaders,
//            HttpStatus.OK //6.6 Returning HTTP 200 vs. HTTP 400 for Data-Centric Queries
//        );
//      }
      //6.6 Returning HTTP 200 vs. HTTP 400 for Data-Centric Queries
      httpHeaders.set(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON_VALUE);
//      httpHeaders.set(HttpHeaders.LOCATION, homePath);
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

  @GetMapping(value = "/{c_id}_{i_id}", produces = MediaType.APPLICATION_JSON_VALUE)
  public ResponseEntity<CIRelation> getCIRelationByIds(@PathVariable Integer c_id, @PathVariable Integer i_id) {
    HttpHeaders httpHeaders = new HttpHeaders();
    httpHeaders.set(HttpHeaders.DATE, Calendar.getInstance().getTime().toString());
    httpHeaders.set("Method", "GET");
    httpHeaders.set("Path", homePath + "/" + c_id + "_" + i_id);
    try {
      CIRelation body = ciRelationService.getCIRelationByIds(c_id, i_id);
      if (body == null) {
        return new ResponseEntity<>(
            httpHeaders,
            HttpStatus.NOT_FOUND
        );
      }
      httpHeaders.set(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON_VALUE);
//      httpHeaders.set(HttpHeaders.LOCATION, homePath + "/" + c_id + "_" + i_id);
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

  @PostMapping(value = "", consumes = MediaType.APPLICATION_JSON_VALUE)
  public ResponseEntity<CIRelation> addCIRelation(@RequestBody @Valid CIRelation ciRelation) {
    HttpHeaders httpHeaders = new HttpHeaders();
    httpHeaders.set(HttpHeaders.DATE, Calendar.getInstance().getTime().toString());
    httpHeaders.set("Method", "POST");
    httpHeaders.set("Path", homePath);
    try {
      ciRelationService.addCIRelation(ciRelation);
      CIRelation body = ciRelationService.getCIRelationByIds(ciRelation.getC_id(), ciRelation.getI_id());
      if (body == null) {
        return new ResponseEntity<>(
            httpHeaders,
            HttpStatus.INTERNAL_SERVER_ERROR);
      }
      httpHeaders.set(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON_VALUE);
      httpHeaders.set(HttpHeaders.LOCATION, homePath + "/" + ciRelation.getC_id() + "_" + ciRelation.getI_id());
      return new ResponseEntity<>(
          body,
          httpHeaders,
          HttpStatus.CREATED
      );
    } catch (Exception e) {
      e.printStackTrace();
      return new ResponseEntity<>(
          httpHeaders,
          HttpStatus.INTERNAL_SERVER_ERROR
      );
    }
  }

  @PutMapping(value = "/{c_id}_{i_id}", consumes = MediaType.APPLICATION_JSON_VALUE)
  public ResponseEntity<CIRelation> updateCIRelation(@PathVariable Integer c_id, @PathVariable Integer i_id, @RequestBody @Valid CIRelation ciRelation) {
    HttpHeaders httpHeaders = new HttpHeaders();
    httpHeaders.set(HttpHeaders.DATE, Calendar.getInstance().getTime().toString());
    httpHeaders.set("Method", "PUT");
    httpHeaders.set("Path", homePath + "/" + c_id + "_" + i_id);
    try {
      CIRelation body = ciRelationService.getCIRelationByIds(c_id, i_id);
      if (body == null) {
        //return addCIRelation(ciRelation);
        ciRelationService.addCIRelation(ciRelation);
        body = ciRelationService.getCIRelationByIds(ciRelation.getC_id(), ciRelation.getI_id());
        if (body == null) {
          return new ResponseEntity<>(
              httpHeaders,
              HttpStatus.INTERNAL_SERVER_ERROR);
        }
        httpHeaders.set(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON_VALUE);
        httpHeaders.set(HttpHeaders.LOCATION, homePath + "/" + ciRelation.getC_id() + "_" + ciRelation.getI_id());
        return new ResponseEntity<>(
            body,
            httpHeaders,
            HttpStatus.CREATED
        );
      }
      ciRelationService.updateCIRelation(c_id, i_id, ciRelation);
      body = ciRelationService.getCIRelationByIds(c_id, i_id);
      httpHeaders.set(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON_VALUE);
      httpHeaders.set(HttpHeaders.DATE, Calendar.getInstance().getTime().toString());
      httpHeaders.set(HttpHeaders.LOCATION, homePath + "/" + ciRelation.getC_id() + "_" + ciRelation.getI_id());
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

  @DeleteMapping(value = "/{c_id}_{i_id}")
  public ResponseEntity<CIRelation> deleteCIRelation(@PathVariable Integer c_id, @PathVariable Integer i_id) {
    HttpHeaders httpHeaders = new HttpHeaders();
    httpHeaders.set(HttpHeaders.DATE, Calendar.getInstance().getTime().toString());
    httpHeaders.set("Method", "DELETE");
    httpHeaders.set("Path", homePath + "/" + c_id + "_" + i_id);
    try {
      CIRelation body = ciRelationService.getCIRelationByIds(c_id, i_id);
      if (body == null) {
        return new ResponseEntity<>(
            httpHeaders,
            HttpStatus.NOT_FOUND
        );
      }
      ciRelationService.deleteCIRelation(c_id, i_id);
      body = ciRelationService.getCIRelationByIds(c_id, i_id);
      if (body != null) {
        return new ResponseEntity<>(
            httpHeaders,
            HttpStatus.INTERNAL_SERVER_ERROR
        );
      }
      httpHeaders.set(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON_VALUE);
//      httpHeaders.set(HttpHeaders.LOCATION, homePath);
      return new ResponseEntity<>(
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
