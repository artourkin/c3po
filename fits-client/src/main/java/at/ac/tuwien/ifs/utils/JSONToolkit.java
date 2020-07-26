package at.ac.tuwien.ifs.utils;

import at.ac.tuwien.ifs.FITSObjects.ExternalIdentifierProperty;
import at.ac.tuwien.ifs.FITSObjects.FITSPropertyJsonPath;
import at.ac.tuwien.ifs.FITSObjects.GenericProperty;
import at.ac.tuwien.ifs.FITSObjects.IdentityProperty;
import at.ac.tuwien.ifs.FITSObjects.ToolIdentity;
import at.ac.tuwien.ifs.model.CharacterisationResult;
import at.ac.tuwien.ifs.model.Property;
import at.ac.tuwien.ifs.model.ValueType;
import com.jayway.jsonpath.Configuration;
import com.jayway.jsonpath.DocumentContext;
import com.jayway.jsonpath.JsonPath;
import com.jayway.jsonpath.spi.mapper.JacksonMappingProvider;
import com.jayway.jsonpath.spi.mapper.MappingProvider;
import org.json.JSONObject;
import org.json.XML;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Map;
import java.util.Set;

public class JSONToolkit {
    public static int PRETTY_PRINT_INDENT_FACTOR = 4;

    public static String translateXML(String xmlString) {
        JSONObject xmlJSONObj = XML.toJSONObject(xmlString);
        String jsonPrettyPrintString = xmlJSONObj.toString(PRETTY_PRINT_INDENT_FACTOR);
        return jsonPrettyPrintString;
    }

    public static Set<String> getAvailableFitsProperties(String jsonString) {
        Configuration configuration = Configuration.defaultConfiguration();
        MappingProvider mappingProvider = new JacksonMappingProvider();
        configuration = configuration.mappingProvider(mappingProvider);
        DocumentContext document = JsonPath.parse(jsonString, configuration);

        Map mapOfProperties = document.read("$.fits.fileinfo", Map.class);
        Set<String> set = (Set<String>) mapOfProperties.keySet();
        return set;

    }


    public static List<CharacterisationResult> getCharacterisationResults(FITSPropertyJsonPath fitsProperty,
                                                                          String jsonString) {
        List<CharacterisationResult> result = new ArrayList<>();

        Configuration configuration = Configuration.defaultConfiguration();
        MappingProvider mappingProvider = new JacksonMappingProvider();
        configuration = configuration.mappingProvider(mappingProvider);
        DocumentContext document = JsonPath.parse(jsonString, configuration);
        if (fitsProperty.equals(FITSPropertyJsonPath.IDENTIFICATION)) {
            result.addAll(parseIdentification(fitsProperty, document));
        } else {
            result.addAll(parseGenericProperty(fitsProperty, document));
        }
        return result;
    }

    private static Collection<? extends CharacterisationResult> parseGenericProperty(FITSPropertyJsonPath jsonPath, DocumentContext document) {
        List<CharacterisationResult> result = new ArrayList<>();

        try {
            GenericProperty read = document.read(jsonPath.getFitsProperty(), GenericProperty.class);
            CharacterisationResult tmpResult = new CharacterisationResult();
            setValues(tmpResult, Property.valueOf(jsonPath.name().toUpperCase()), read.getContent());
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

        //String status = document.read(jsonPath.getFitsProperty() + ".status", String.class);

        Map read = document.read(jsonPath.getFitsProperty(), Map.class);

        if (read.size()==1) {


            IdentityProperty identityProperty = document.read(jsonPath.getFitsProperty() + ".identity", IdentityProperty.class);

            Object tool = identityProperty.getTool();
            if (tool instanceof List) {

                List toolArray = (List) tool;

                for (int j = 0; j < toolArray.size(); j++) {

                    CharacterisationResult tmpResult = new CharacterisationResult();
                    setValues(tmpResult, Property.FORMAT, identityProperty.getFormat());
                    ToolIdentity toolIdentity = document.read(String.format(jsonPath.getFitsProperty() + ".identity.tool[%d]", j), ToolIdentity.class);
                    tmpResult.setSource(toolIdentity.getToolname() + ":" + toolIdentity.getToolversion());
                    result.add(tmpResult);

                    tmpResult = new CharacterisationResult();
                    setValues(tmpResult, Property.MIMETYPE, identityProperty.getMimetype());
                    tmpResult.setSource(toolIdentity.getToolname() + ":" + toolIdentity.getToolversion());
                    result.add(tmpResult);


                }
            } else {
                CharacterisationResult tmpResult = new CharacterisationResult();
                setValues(tmpResult, Property.FORMAT, identityProperty.getFormat());
                ToolIdentity toolIdentity = document.read(jsonPath.getFitsProperty() + ".identity.tool",
                        ToolIdentity.class);
                tmpResult.setSource(toolIdentity.getToolname() + ":" + toolIdentity.getToolversion());
                result.add(tmpResult);

                tmpResult = new CharacterisationResult();
                setValues(tmpResult, Property.MIMETYPE, identityProperty.getMimetype());
                tmpResult.setSource(toolIdentity.getToolname() + ":" + toolIdentity.getToolversion());
                result.add(tmpResult);

            }

            if (identityProperty.getExternalIdentifier() != null) {
                ExternalIdentifierProperty genericProperty = document.read(jsonPath.getFitsProperty() + ".identity.externalIdentifier", ExternalIdentifierProperty.class);
                CharacterisationResult tmpResult = new CharacterisationResult();
                setValues(tmpResult, Property.valueOf("externalIdentifier".toUpperCase()), genericProperty.getContent());
                ToolIdentity toolIdentity = document.read(jsonPath.getFitsProperty() + ".identity.tool", ToolIdentity.class);
                tmpResult.setSource(genericProperty.getToolname() + ":" + genericProperty.getToolversion());
                result.add(tmpResult);
            }


        } else {


            for (int i = 0; i < read.size(); i++) {

                IdentityProperty identityProperty = document.read(String.format(jsonPath.getFitsProperty() + ".identity.[%d]", i), IdentityProperty.class);

                Object tool = identityProperty.getTool();
                if (tool instanceof List) {

                    List toolArray = (List) tool;

                    for (int j = 0; j < toolArray.size(); j++) {

                        CharacterisationResult tmpResult = new CharacterisationResult();
                        setValues(tmpResult, Property.FORMAT, identityProperty.getFormat());
                        ToolIdentity toolIdentity = document.read(String.format(jsonPath.getFitsProperty() + ".identity.[%d].tool[%d]", i, j), ToolIdentity.class);
                        tmpResult.setSource(toolIdentity.getToolname() + ":" + toolIdentity.getToolversion());
                        result.add(tmpResult);

                        tmpResult = new CharacterisationResult();
                        setValues(tmpResult, Property.MIMETYPE, identityProperty.getMimetype());
                        tmpResult.setSource(toolIdentity.getToolname() + ":" + toolIdentity.getToolversion());
                        result.add(tmpResult);


                    }
                } else {
                    CharacterisationResult tmpResult = new CharacterisationResult();
                    setValues(tmpResult, Property.FORMAT, identityProperty.getFormat());
                    ToolIdentity toolIdentity = document.read(String.format(jsonPath.getFitsProperty() + ".identity.[%d].tool", i), ToolIdentity.class);
                    tmpResult.setSource(toolIdentity.getToolname() + ":" + toolIdentity.getToolversion());
                    result.add(tmpResult);

                    tmpResult = new CharacterisationResult();
                    setValues(tmpResult, Property.MIMETYPE, identityProperty.getMimetype());
                    tmpResult.setSource(toolIdentity.getToolname() + ":" + toolIdentity.getToolversion());
                    result.add(tmpResult);

                }

                if (identityProperty.getExternalIdentifier() != null) {
                    ExternalIdentifierProperty genericProperty = document.read(String.format(jsonPath.getFitsProperty() + ".identity.[%d].externalIdentifier", i), ExternalIdentifierProperty.class);
                    CharacterisationResult tmpResult = new CharacterisationResult();
                    setValues(tmpResult, Property.valueOf("externalIdentifier".toUpperCase()), genericProperty.getContent());
                    ToolIdentity toolIdentity = document.read(String.format(jsonPath.getFitsProperty() + ".identity.[%d].tool", i), ToolIdentity.class);
                    tmpResult.setSource(genericProperty.getToolname() + ":" + genericProperty.getToolversion());
                    result.add(tmpResult);
                }


            }
        }
        return result;
    }


    private static void setValues(CharacterisationResult tmpResult, Property format, String value) {
        tmpResult.setProperty(format);
        tmpResult.setValueType(ValueType.STRING);
        tmpResult.setValue(value);
    }
}
