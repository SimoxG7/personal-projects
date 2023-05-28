package myproject.opensourcecocktails.service;

import myproject.opensourcecocktails.model.CIRelation;
import myproject.opensourcecocktails.model.CIRelationPK;
import myproject.opensourcecocktails.repository.CIRelationRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Configurable;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.Optional;

@Service
@Configurable
public class CIRelationService {

  @Autowired
  private CIRelationRepository ciRelationRepository;

  public List<CIRelation> getAllCIRelations() {
    List<CIRelation> lst = new ArrayList<>();
    ciRelationRepository.findAll().forEach(lst::add);
    return lst;
  }

  public CIRelation getCIRelationByIds(Integer c_id, Integer i_id) {
    Optional<CIRelation> result = ciRelationRepository.findById(new CIRelationPK(c_id, i_id));
    return result.orElse(null);
  }

  public void addCIRelation(CIRelation ciRelation) {
    ciRelationRepository.save(ciRelation);
  }

  public void updateCIRelation(Integer c_id, Integer i_id, CIRelation ciRelation) {
    CIRelationPK pk = new CIRelationPK(c_id, i_id);
    if (!Objects.equals(pk, new CIRelationPK(ciRelation.getC_id(), ciRelation.getI_id()))) return;
    ciRelationRepository.deleteById(pk);
    ciRelationRepository.save(ciRelation);
  }

  public void deleteCIRelation(Integer c_id, Integer i_id) {
    ciRelationRepository.deleteById(new CIRelationPK(c_id, i_id));
  }

  public void deleteCIRelation(CIRelationPK id) {
    ciRelationRepository.deleteById(id);
  }

  public void deleteCIRelation(CIRelation ciRelation) {
    ciRelationRepository.delete(ciRelation);
  }


}
