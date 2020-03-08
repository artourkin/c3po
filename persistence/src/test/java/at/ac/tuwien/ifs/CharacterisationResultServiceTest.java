package at.ac.tuwien.ifs;

import at.ac.tuwien.ifs.model.CharacterisationResult;
import at.ac.tuwien.ifs.model.Property;
import at.ac.tuwien.ifs.model.statistics.PropertyStatistics;
import at.ac.tuwien.ifs.model.statistics.PropertyValueStatistics;
import io.quarkus.test.junit.QuarkusTest;
import org.junit.Assert;
import org.junit.jupiter.api.Test;

import javax.inject.Inject;
import java.util.ArrayList;
import java.util.List;


@QuarkusTest
class CharacterisationResultServiceTest {

    @Inject
    CharacterisationResultService characterisationResultService;


    @Test
    void getAllCharacterisationResultsTest() {

        Iterable<CharacterisationResult> allStudents = characterisationResultService.getAllCharacterisationResults();

        List<CharacterisationResult> studentList = new ArrayList<>();
        allStudents.forEach(studentList::add);
        Assert.assertEquals(4, studentList.size());
    }

    @Test
    void getPropertyDistributionTest() {
        List<PropertyStatistics> propertyDistribution = characterisationResultService.getPropertyDistribution();
        Assert.assertEquals(3, propertyDistribution.size());
    }

    @Test
    void getPropertValueyDistributionTest() {
        List<PropertyValueStatistics> propertyValueStatistics =
                characterisationResultService.getValueDistributionByProperty(Property.FORMAT);


        Assert.assertEquals(2, propertyValueStatistics.size());
    }

    @Test
    void getCharacterisationResultsByFilepathTest() {
        Iterable<CharacterisationResult> propertyValueStatistics =
                characterisationResultService.getCharacterisationResultsByFilepath("/home/artur");

        List<CharacterisationResult> list = new ArrayList<>();
        propertyValueStatistics.forEach(list::add);
        Assert.assertEquals(3, list.size());
    }
}