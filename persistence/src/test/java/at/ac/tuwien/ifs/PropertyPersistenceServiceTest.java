package at.ac.tuwien.ifs;

import at.ac.tuwien.ifs.model.CharacterisationResult;
import at.ac.tuwien.ifs.model.Property;
import at.ac.tuwien.ifs.model.statistics.PropertyStatistic;
import at.ac.tuwien.ifs.model.statistics.PropertyValueStatistic;
import io.quarkus.test.junit.QuarkusTest;
import org.junit.Assert;
import org.junit.jupiter.api.Test;

import javax.inject.Inject;
import java.util.ArrayList;
import java.util.List;


@QuarkusTest
class PropertyPersistenceServiceTest {

    @Inject
    PropertyPersistenceService propertyPersistenceService;


    @Test
    void getAllCharacterisationResultsTest() {

        Iterable<CharacterisationResult> allStudents = propertyPersistenceService.getAllCharacterisationResults();

        List<CharacterisationResult> studentList = new ArrayList<>();
        allStudents.forEach(studentList::add);
        Assert.assertEquals(4, studentList.size());
    }

    @Test
    void getPropertyDistributionTest() {
        List<?> propertyDistribution = propertyPersistenceService.getPropertyDistribution();
        Assert.assertEquals(3, propertyDistribution.size());
    }

    @Test
    void getPropertValueyDistributionFormatTest() {
        List<PropertyValueStatistic> propertyValueStatistics =
                propertyPersistenceService.getValueDistributionByProperty(Property.FORMAT);


        Assert.assertEquals(2, propertyValueStatistics.size());
    }

    @Test
    void getPropertValueyDistributionTest() {
        List<PropertyValueStatistic> propertyValueStatistics =
                propertyPersistenceService.getValueDistributionByProperty(Property.MIMETYPE);


        Assert.assertEquals(new Long(1), propertyValueStatistics.get(0).getCount());
    }

    @Test
    void getCharacterisationResultsByFilepathTest() {
        Iterable<CharacterisationResult> propertyValueStatistics =
                propertyPersistenceService.getCharacterisationResultsByFilepath("/home/artur");

        List<CharacterisationResult> list = new ArrayList<>();
        propertyValueStatistics.forEach(list::add);
        Assert.assertEquals(3, list.size());
    }
}