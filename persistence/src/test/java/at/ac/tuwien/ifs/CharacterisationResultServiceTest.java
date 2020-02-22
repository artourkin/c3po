package at.ac.tuwien.ifs;

import at.ac.tuwien.ifs.model.CharacterisationResult;
import at.ac.tuwien.ifs.model.Property;
import at.ac.tuwien.ifs.statistics.PropertyStatistics;
import at.ac.tuwien.ifs.statistics.PropertyValueStatistics;
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
    void getAllTest() {


        Iterable<CharacterisationResult> allStudents = characterisationResultService.getAllStudents();


        List<PropertyStatistics> propertyDistribution = characterisationResultService.getPropertyDistribution();

        List<PropertyValueStatistics> valueDistributionByProperty = characterisationResultService.getValueDistributionByProperty(Property.FORMAT);

        List<CharacterisationResult> studentList = new ArrayList<>();
        allStudents.forEach(studentList::add);
        Assert.assertEquals(3, studentList.size());
    }
}