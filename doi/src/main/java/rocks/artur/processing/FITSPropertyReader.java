package rocks.artur.processing;

import edu.harvard.hul.ois.fits.exceptions.FitsConfigurationException;
import edu.harvard.hul.ois.fits.exceptions.FitsException;
import nl.knaw.dans.fits.FitsWrap;
import org.jdom.Document;

import java.io.File;

public class FITSPropertyReader extends PropertyReader {
    void read(DigitalObject digitalObject){
        digitalObject.getPath();
        try {
            FitsWrap fits = FitsWrap.instance();
            Document doc = fits.extract(new File(digitalObject.getPath()));
            //doc.
        } catch (FitsConfigurationException e) {
            e.printStackTrace();

        } catch (FitsException e) {
            e.printStackTrace();
        }
    }


}
