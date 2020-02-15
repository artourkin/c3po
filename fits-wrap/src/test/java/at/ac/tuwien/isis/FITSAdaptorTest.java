package at.ac.tuwien.isis;

import edu.harvard.hul.ois.fits.FitsOutput;
import edu.harvard.hul.ois.fits.exceptions.FitsConfigurationException;
import edu.harvard.hul.ois.fits.exceptions.FitsException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.io.File;
import java.io.IOException;
import java.net.URL;

import static org.junit.jupiter.api.Assertions.assertNotNull;

class FITSAdaptorTest {

    FITSAdaptor fitsAdaptor;

    @BeforeEach
    public void setup() {
        String FITS_HOME = System.getenv("FITS_HOME") == null ? "/Users/artur/rnd/software/fits-1.5.0" : System.getenv("FITS_HOME");

        System.out.println("FITS_HOME is set to: " + FITS_HOME);
        fitsAdaptor = new FITSAdaptor(FITS_HOME);

    }

    @Test
    void examine() throws FitsException, IOException {
        URL resource = getClass().getResource("/objects/pom.xml");
        String examine = fitsAdaptor.examine(resource.getPath());
        assertNotNull(examine);

    }

}