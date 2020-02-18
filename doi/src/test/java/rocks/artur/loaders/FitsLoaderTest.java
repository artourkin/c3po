package rocks.artur.loaders;

import io.quarkus.test.junit.QuarkusTest;
import org.jboss.resteasy.spi.InjectorFactory;
import org.junit.jupiter.api.Test;

import javax.inject.Inject;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertNotNull;

@QuarkusTest
class FitsLoaderTest {

    @Inject
    FitsLoader fitsLoader;

    @Test
    void getFits() {
        try {
            System.out.println(fitsLoader.ping());
          //  List<String> toolInfo = fitsLoader.getFits().getToolInfo();
            //List<String> toolInfo = fitsLoader.getFits().getTo∑ƒlInfo();
         //   System.out.println(toolInfo);
          //  assertNotNull(toolInfo);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}