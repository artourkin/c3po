package at.ac.tuwien.ifs;

import at.ac.tuwien.ifs.model.CharacterisationResult;
import org.h2.engine.User;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

public interface CharacterisationResultRepository extends CrudRepository<CharacterisationResult, Long> {
   // @Query(
   //         value = "SELECT * FROM CharacterisationResults ORDER BY id \n-- #pageable\n",
   //         countQuery = "SELECT count(*) FROM CharacterisationResults")
   // Page<CharacterisationResult> findAllResultsWithPagination(Pageable pageable);
}
