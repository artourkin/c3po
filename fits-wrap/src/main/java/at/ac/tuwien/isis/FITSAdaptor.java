package at.ac.tuwien.isis;

import edu.harvard.hul.ois.fits.Fits;
import edu.harvard.hul.ois.fits.FitsOutput;
import edu.harvard.hul.ois.fits.exceptions.FitsConfigurationException;
import edu.harvard.hul.ois.fits.exceptions.FitsException;
import edu.harvard.hul.ois.fits.tools.ToolBelt;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;

public class FITSAdaptor {
    private String FITS_HOME;
    private Fits fits;

    public FITSAdaptor(String fitsHome) {
        try {
            fits = new Fits(FITS_HOME);
        } catch (FitsConfigurationException e) {
            e.printStackTrace();
        }
    }

    public String examine(String file) throws FitsException, IOException {
        FitsOutput examine = fits.examine(new File(file));
        ByteArrayOutputStream outStream = new ByteArrayOutputStream();
        examine.output(outStream);
        String outputString = outStream.toString();
        return outputString;
    }

    public ToolBelt getToolbelt() {
        return fits.getToolbelt();
    }


}
