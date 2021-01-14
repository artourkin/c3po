package at.ac.tuwien.ifs;

import at.ac.tuwien.ifs.model.CharacterisationResult;
import at.ac.tuwien.ifs.model.Filter;
import at.ac.tuwien.ifs.model.Property;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

public interface CharacterisationResultRepository extends CrudRepository<CharacterisationResult, String> {
    // @Query(
    //         value = "SELECT * FROM CharacterisationResults ORDER BY id \n-- #pageable\n",
    //         countQuery = "SELECT count(*) FROM CharacterisationResults")
    // Page<CharacterisationResult> findAllResultsWithPagination(Pageable pageable);

    @Query("select property, count(*) as count from CharacterisationResult group by property")
    List<Object[]> getPropertyDistribution();

    @Query("select COUNT(distinct value) as VALUES_COUNT, filePath, property, CASE WHEN COUNT(distinct value) = 1 " +
            "THEN value ELSE 'CONFLICTED' END AS username from CharacterisationResult where " +
            "property= ?1 group by CASE WHEN COUNT(distinct value) = 1 THEN filePath ELSE 'CONFLICTED' END; ")
    List<Object[]> getPropertyValueCountPerFile(Property property);

    @Query("Select (select COUNT(distinct value) as VALUES_COUNT, filePath from CharacterisationResult where " +
            "property= ?1 " +
            "group by filePath)")
    List<Object[]> getPropertyValueCountPerFileDistribution(Property property);


    @Query("select value, count(*) as count from CharacterisationResult where property= ?1 group by value")
    List<Object[]> getPropertyValueDistributionDO(Property property);


    List<CharacterisationResult> findAllByFilePath(String filePath);

    //List<Object[]> getPropertyValueDistribution(Property property, Filter filter);
    //  @Query("select property, count(*) as num from CharacterisationResult group by property")
    //  Collection getDistributionDEV();
}
