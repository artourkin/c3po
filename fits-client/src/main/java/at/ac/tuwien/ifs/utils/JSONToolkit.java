package at.ac.tuwien.ifs.utils;

import at.ac.tuwien.ifs.FITSObjects.FITSPropertyJsonPath;
import at.ac.tuwien.ifs.FITSObjects.GenericProperty;
import at.ac.tuwien.ifs.FITSObjects.IdentityProperty;
import at.ac.tuwien.ifs.model.CharacterisationResult;
import at.ac.tuwien.ifs.model.Property;
import at.ac.tuwien.ifs.model.ValueType;
import com.jayway.jsonpath.Configuration;
import com.jayway.jsonpath.DocumentContext;
import com.jayway.jsonpath.JsonPath;
import com.jayway.jsonpath.spi.mapper.JacksonMappingProvider;
import com.jayway.jsonpath.spi.mapper.MappingProvider;
import org.json.JSONArray;
import org.json.JSONObject;
import org.json.XML;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class JSONToolkit {
    public static int PRETTY_PRINT_INDENT_FACTOR = 4;

    public static String translateXML(String xmlString) {
        JSONObject xmlJSONObj = XML.toJSONObject(xmlString);
        String jsonPrettyPrintString = xmlJSONObj.toString(PRETTY_PRINT_INDENT_FACTOR);
        return jsonPrettyPrintString;
    }

    public static List<CharacterisationResult> getCharacterisationResult(FITSPropertyJsonPath jsonPath,
                                                                         String jsonString) {
        List<CharacterisationResult> result = new ArrayList<>();

        Configuration configuration = Configuration.defaultConfiguration();
        MappingProvider mappingProvider = new JacksonMappingProvider();
        configuration = configuration.mappingProvider(mappingProvider);
        DocumentContext document = JsonPath.parse(jsonString, configuration);
        if (jsonPath.equals(FITSPropertyJsonPath.IDENTIFICATION)) {
            result.addAll(parseIdentification(jsonPath, document));
        }
        try {
            GenericProperty read = document.read(jsonPath.getFitsProperty(), GenericProperty.class);
            CharacterisationResult tmpResult = new CharacterisationResult();
            tmpResult.setProperty(Property.FILENAME);
            tmpResult.setValueType(ValueType.STRING);
            tmpResult.setValue(read.getContent());
            tmpResult.setSource(read.getToolname() + ":" + read.getToolversion());
            result.add(tmpResult);
        } catch (Exception e) {
            e.printStackTrace();
        }

        return result;
    }

    private static List<CharacterisationResult> parseIdentification(FITSPropertyJsonPath jsonPath,
                                                                    DocumentContext document) {

        List<CharacterisationResult> result = new ArrayList<>();
        Map read = document.read(jsonPath.getFitsProperty(), Map.class);
        for (int i = 0; i < read.size(); i++) {
            IdentityProperty read1 = document.read(String.format(jsonPath.getFitsProperty() + "[%d]", i), IdentityProperty.class);
            System.out.println(read1);
        }

        Map<String, Object> map = (Map<String, Object>) read;
        if (map.get("status").equals("CONFLICT")) {

        }

        JSONArray identityArray = (JSONArray) map.get("identity");
        identityArray.forEach(item -> {
            Map<String, Object> identityMap = (Map<String, Object>) item;

        });

        return result;
    }
}
