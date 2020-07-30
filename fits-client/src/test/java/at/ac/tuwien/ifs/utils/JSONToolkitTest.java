package at.ac.tuwien.ifs.utils;

import at.ac.tuwien.ifs.FITSClientTest;
import at.ac.tuwien.ifs.FITSObjects.FITSPropertyJsonPath;
import at.ac.tuwien.ifs.model.CharacterisationResult;
import io.quarkus.test.junit.QuarkusTest;
import org.junit.Assert;
import org.junit.jupiter.api.Test;

import java.util.Arrays;
import java.util.List;
import java.util.Set;

@QuarkusTest
class JSONToolkitTest {

    @Test
    void translateXMLTest() {
        String s = JSONToolkit.translateXML(FITSClientTest.VALID_FITS_RESULT);
        System.out.println(s);
    }

    @Test
    void getCharacterisationResult2Test() {
        String jsonString = JSONToolkit.translateXML(FITSClientTest.VALID_FITS_RESULT2);
        List<CharacterisationResult> results = JSONToolkit.getCharacterisationResults(FITSPropertyJsonPath.IDENTIFICATION,
                jsonString);
        System.out.println(results);
    }

    @Test
    void getCharacterisationResultTest() {
        String jsonString = JSONToolkit.translateXML(FITSClientTest.VALID_FITS_RESULT);
        List<CharacterisationResult> results = JSONToolkit.getCharacterisationResults(FITSPropertyJsonPath.FILENAME,
                jsonString);

        Assert.assertEquals("CharacterisationResult{id=null, property=FILENAME, value='README.md', valueType=STRING, source='OIS File Information:1', filePath='null'}", results.get(0).toString());
    }


    @Test
    void getCharacterisationResultIdentificationTest() {
        String jsonString = JSONToolkit.translateXML(FITSClientTest.VALID_FITS_RESULT);
        List<CharacterisationResult> results = JSONToolkit.getCharacterisationResults(FITSPropertyJsonPath.IDENTIFICATION,
                jsonString);

        System.out.println(results);
        Assert.assertEquals(7, results.size());
    }

    @Test
    void getAvailableFitsPropertiesTest() {
        String jsonString = JSONToolkit.translateXML(FITSClientTest.VALID_FITS_RESULT);
        Set availableFitsProperties = JSONToolkit.getAvailableFitsProperties(jsonString);
        List<Object> objects = Arrays.asList(availableFitsProperties.toArray());

        Assert.assertEquals("[filename, size, filepath, md5checksum, fslastmodified]", objects.toString());
    }
}