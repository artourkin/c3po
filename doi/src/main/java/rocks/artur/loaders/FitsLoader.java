package rocks.artur.loaders;

import edu.harvard.hul.ois.fits.exceptions.FitsConfigurationException;
import nl.knaw.dans.fits.FitsWrap;

import javax.enterprise.context.ApplicationScoped;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Properties;

@ApplicationScoped
public class FitsLoader {

    String ping() {
        return "amazing";
    }


    //FitsWrap fitsWrap;


    FitsWrap getFits() throws IOException, FitsConfigurationException {
        try (InputStream ins = FitsWrap.class.getClassLoader().getResourceAsStream("fits.properties");) {
            Properties props = new Properties();
            props.load(ins);
            String fitsHome =
                    Paths.get("").toAbsolutePath().getParent().resolveSibling("fits-api/fits-0.8.5").normalize().toString();
           // String base =
           //         new File(props.getProperty("project.build.sourceDirectory")).getParentFile().getParentFile()
            //         .getParent();
           // String fitsHome = base + File.separator + props.getProperty("fits.version");
            FitsWrap.setFitsHome(fitsHome);
            return FitsWrap.instance();
        }
    }
}
