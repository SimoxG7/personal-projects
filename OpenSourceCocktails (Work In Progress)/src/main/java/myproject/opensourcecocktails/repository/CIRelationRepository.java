package myproject.opensourcecocktails.repository;

import myproject.opensourcecocktails.model.CIRelation;
import myproject.opensourcecocktails.model.CIRelationPK;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CIRelationRepository extends CrudRepository<CIRelation, CIRelationPK> {

  //CIRelation findByCIRelationPK(CIRelationPK ciRelationPK);

}
