package rocks.artur.processing;

import java.net.URI;
import java.util.Iterator;

public interface DigitalObjectIterator {
    Iterator<URI> getIterator();
    DigitalObject next();
    Boolean hasNext();
}
