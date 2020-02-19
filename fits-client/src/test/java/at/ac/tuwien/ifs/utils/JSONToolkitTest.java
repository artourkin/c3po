package at.ac.tuwien.ifs.utils;

import at.ac.tuwien.ifs.FITSClientTest;
import at.ac.tuwien.ifs.model.CharacterisationResult;
import io.quarkus.test.junit.QuarkusTest;
import org.junit.jupiter.api.Test;

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
        CharacterisationResult filename = JSONToolkit.getCharacterisationResult("$..filename", jsonString);

        System.out.println(filename);


    }
}