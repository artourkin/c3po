package at.ac.tuwien.ifs.utils;

import at.ac.tuwien.ifs.model.CharacterisationResult;
import at.ac.tuwien.ifs.model.ValueType;
import com.jayway.jsonpath.Configuration;
import com.jayway.jsonpath.JsonPath;
import org.json.JSONObject;
import org.json.XML;

import java.sql.Timestamp;

public class JSONToolkit {
    public static int PRETTY_PRINT_INDENT_FACTOR = 4;

    public static String translateXML(String xmlString) {
        JSONObject xmlJSONObj = XML.toJSONObject(xmlString);
        String jsonPrettyPrintString = xmlJSONObj.toString(PRETTY_PRINT_INDENT_FACTOR);
        return jsonPrettyPrintString;
    }

    public static CharacterisationResult getCharacterisationResult(String jsonPath, String jsonString) {
        CharacterisationResult result = new CharacterisationResult();


        Object document = Configuration.defaultConfiguration().jsonProvider().parse(jsonString);

        Object read = JsonPath.read(document, jsonPath);

        JSONObject jsonObject = new JSONObject(jsonString);
        Object object = jsonObject.get(jsonPath);

        if (object instanceof String) {
            result.setValueType(ValueType.STRING);
        } else if (object instanceof Integer) {
            result.setValueType(ValueType.INTEGER);
        } else if (object instanceof Timestamp) {
            result.setValueType(ValueType.TIMESTAMP);
        } else if (object instanceof Double || object instanceof Float) {
            result.setValueType(ValueType.FLOAT);
        }

        return result;
    }
}
