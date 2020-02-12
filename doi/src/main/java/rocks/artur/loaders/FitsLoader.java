package rocks.artur.loaders;

import edu.harvard.hul.ois.fits.exceptions.FitsConfigurationException;
import nl.knaw.dans.fits.FitsWrap;

import javax.inject.Singleton;
import javax.ws.rs.Produces;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

@Singleton
public class FitsLoader {
    @Produces
    FitsWrap getFits() throws IOException, FitsConfigurationException {
        try (InputStream ins = FitsWrap.class.getClassLoader().getResourceAsStream("fits.properties");) {
            Properties props = new Properties();
            props.load(ins);
            String base = new File(props.getProperty("project.build.sourceDirectory")).getParentFile().getParentFile().getParent();
            String fitsHome = base + File.separator + props.getProperty("fits.version");
            FitsWrap.setFitsHome(fitsHome);
            return FitsWrap.instance();
        }
    }
}
