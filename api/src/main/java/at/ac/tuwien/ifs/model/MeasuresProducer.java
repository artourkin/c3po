package at.ac.tuwien.ifs.model;

import java.io.File;
import java.io.IOException;
import java.util.List;

public interface MeasuresProducer {
    /***
     * This method returns the version of the used tool
     * @return A version of the tool
     * @throws IOException
     */
    String getVersion() throws IOException;

    /***
     *
     * This method extracts metadata properties from a given file object.
     *
     * @param file Input File
     * @return A list of @CharacterisationResult
     * @throws IOException
     */
    List<CharacterisationResult> processFile(File file) throws IOException;


    /***
     *
     * This method extracts metadata properties from a given file object passed as a byte array.
     *
     * @param file Input File
     * @return A list of @CharacterisationResult
     * @throws IOException
     */
    List<CharacterisationResult> processFile(byte[] file) throws IOException;
}
