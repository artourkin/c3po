package at.ac.tuwien.ifs;

import at.ac.tuwien.ifs.model.CharacterisationResult;
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

        characterisationResultService.addCharacterisationResult(new CharacterisationResult());

        Iterable<CharacterisationResult> allStudents = characterisationResultService.getAllStudents();
        List<CharacterisationResult> studentList = new ArrayList<>();
        allStudents.forEach(studentList::add);
        Assert.assertEquals(1, studentList.size());
    }
}