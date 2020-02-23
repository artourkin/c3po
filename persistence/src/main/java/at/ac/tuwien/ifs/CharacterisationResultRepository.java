package at.ac.tuwien.ifs;

import at.ac.tuwien.ifs.model.CharacterisationResult;
import at.ac.tuwien.ifs.model.Property;
import at.ac.tuwien.ifs.statistics.PropertyStatistics;
import at.ac.tuwien.ifs.statistics.PropertyValueStatistics;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

public interface CharacterisationResultRepository extends CrudRepository<CharacterisationResult, Long> {
    // @Query(
    //         value = "SELECT * FROM CharacterisationResults ORDER BY id \n-- #pageable\n",
    //         countQuery = "SELECT count(*) FROM CharacterisationResults")
    // Page<CharacterisationResult> findAllResultsWithPagination(Pageable pageable);

    @Query("select property, count(*) as count from CharacterisationResult group by property")
    List<PropertyStatistics> getPropertyDistribution();

    @Query("select value, count(*) as count from CharacterisationResult where property= ?1 group by value")
    List<PropertyValueStatistics> getPropertyValueDistribution(Property property);


    List<CharacterisationResult> findAllByFilePath(String filePath);
    //  @Query("select property, count(*) as num from CharacterisationResult group by property")
    //  Collection getDistributionDEV();
}