package rocks.artur.loaders;

import nl.knaw.dans.fits.FitsWrap;
import org.junit.jupiter.api.Test;

import javax.inject.Inject;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class FitsLoaderTest {

    @Inject
    FitsWrap fitsWrap;
    @Test
    void getFits() {
        try {
            List<String> toolInfo = fitsWrap.getToolInfo();
            System.out.println(toolInfo);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}