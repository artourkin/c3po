package at.ac.tuwien.ifs.utils;

import at.ac.tuwien.ifs.FITSClientTest;
import at.ac.tuwien.ifs.FITSObjects.FITSPropertyJsonPath;
import at.ac.tuwien.ifs.model.CharacterisationResult;
import io.quarkus.test.junit.QuarkusTest;
import org.junit.Assert;
import org.junit.jupiter.api.Test;

import java.util.List;

@QuarkusTest
class JSONToolkitTest {

    @Test
    void translateXMLTest() {
        String s = JSONToolkit.translateXML(FITSClientTest.VALID_FITS_RESULT);
        System.out.println(s);
    }

    @Test
    void getCharacterisationResultTest() {
        String jsonString = JSONToolkit.translateXML(FITSClientTest.VALID_FITS_RESULT);
        List<CharacterisationResult> results = JSONToolkit.getCharacterisationResult(FITSPropertyJsonPath.FILENAME,
                jsonString);

        Assert.assertEquals("CharacterisationResult{property=FILENAME, value=README.md, valueType=STRING, " +
                "source=DROID3}", results.get(0).toString());
    }


    @Test
    void getCharacterisationResultIdentificationTest() {
        String jsonString = JSONToolkit.translateXML(FITSClientTest.VALID_FITS_RESULT);
        List<CharacterisationResult> results = JSONToolkit.getCharacterisationResult(FITSPropertyJsonPath.IDENTIFICATION,
                jsonString);

        Assert.assertEquals("CharacterisationResult{property=FILENAME, value=README.md, valueType=STRING, " +
                "source=DROID3}", results.get(0).toString());


    }
}