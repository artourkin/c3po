package at.ac.tuwien.ifs.utils;

import at.ac.tuwien.ifs.FITSObjects.FilenameProperty;
import at.ac.tuwien.ifs.model.CharacterisationResult;
import at.ac.tuwien.ifs.model.Property;
import at.ac.tuwien.ifs.model.Source;
import at.ac.tuwien.ifs.model.ValueType;
import com.jayway.jsonpath.Configuration;
import com.jayway.jsonpath.DocumentContext;
import com.jayway.jsonpath.JsonPath;
import com.jayway.jsonpath.spi.mapper.JacksonMappingProvider;
import com.jayway.jsonpath.spi.mapper.MappingProvider;
import org.json.JSONObject;
import org.json.XML;

public class JSONToolkit {
    public static int PRETTY_PRINT_INDENT_FACTOR = 4;

    public static String translateXML(String xmlString) {
        JSONObject xmlJSONObj = XML.toJSONObject(xmlString);
        String jsonPrettyPrintString = xmlJSONObj.toString(PRETTY_PRINT_INDENT_FACTOR);
        return jsonPrettyPrintString;
    }

    public static CharacterisationResult getCharacterisationResult(String jsonPath, String jsonString) {
        CharacterisationResult result = new CharacterisationResult();

        Configuration configuration = Configuration.defaultConfiguration();
        MappingProvider mappingProvider = new JacksonMappingProvider();
        configuration = configuration.mappingProvider(mappingProvider);
        DocumentContext document = JsonPath.parse(jsonString, configuration);// (DocumentContext)Configuration.defaultConfiguration().jsonProvider().parse(jsonString);

        FilenameProperty read = document.read(jsonPath, FilenameProperty.class);

        result.setProperty(Property.FILENAME);
        result.setValueType(ValueType.STRING);
        result.setValue(read.getContent());
        result.setSource(Source.DROID3);//read.getToolname());

        return result;
    }
}
