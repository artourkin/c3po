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
import java.util.stream.Collectors;

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

        if (read.size() == 1) {  // There is only 1 identity

            IdentityProperty identityProperty = document.read(jsonPath.getFitsProperty() + ".identity", IdentityProperty.class);

            List<CharacterisationResult> formatData = getFormatData(document, jsonPath.getFitsProperty() + ".identity");
            List<CharacterisationResult> mimetypeData = getMimetypeData(document, jsonPath.getFitsProperty() +
                    ".identity");

            result.addAll(formatData);
            result.addAll(mimetypeData);

            if (identityProperty.getExternalIdentifier() != null) {
                ExternalIdentifierProperty genericProperty = document.read(jsonPath.getFitsProperty() + ".identity.externalIdentifier", ExternalIdentifierProperty.class);
                CharacterisationResult tmpResult = new CharacterisationResult();
                setValues(tmpResult, Property.EXTERNALIDENTIFIER, genericProperty.getContent());
                tmpResult.setSource(genericProperty.getToolname() + ":" + genericProperty.getToolversion());
                result.add(tmpResult);
            }


        } else {  //There are many identities

            for (int i = 0; i < read.size(); i++) {

                IdentityProperty identityProperty = document.read(String.format(jsonPath.getFitsProperty() + ".identity.[%d]", i), IdentityProperty.class);
                List<CharacterisationResult> formatData = getFormatData(document, String.format(jsonPath.getFitsProperty() + ".identity.[%d]", i));
                List<CharacterisationResult> mimetypeData = getMimetypeData(document, String.format(jsonPath.getFitsProperty() + ".identity.[%d]", i));

                result.addAll(formatData);
                result.addAll(mimetypeData);



                if (identityProperty.getExternalIdentifier() != null) {
                    ExternalIdentifierProperty genericProperty = document.read(String.format(jsonPath.getFitsProperty() + ".identity.[%d].externalIdentifier", i), ExternalIdentifierProperty.class);
                    CharacterisationResult tmpResult = new CharacterisationResult();
                    setValues(tmpResult, Property.valueOf("externalIdentifier".toUpperCase()), genericProperty.getContent());
                    tmpResult.setSource(genericProperty.getToolname() + ":" + genericProperty.getToolversion());
                    result.add(tmpResult);
                }


            }
        }
        return result;
    }

    private static List<CharacterisationResult> getFormatData(DocumentContext document, String jsonPath) {
        List<CharacterisationResult> result = new ArrayList<>();
        IdentityProperty identityProperty = document.read(jsonPath, IdentityProperty.class);

        List<ToolIdentity> toolData = getToolData(document, jsonPath);
        List<GenericProperty> versionData = getVersionData(document, jsonPath);

        for (ToolIdentity tool : toolData) {

            List<GenericProperty> matchingVersions =
                    versionData.stream().filter(version -> version.getToolname().equals(tool.getToolname()) && version.getToolversion().equals(tool.getToolversion())).collect(Collectors.toList());

            if (matchingVersions.size() > 0) {
                for (GenericProperty matchingVersion : matchingVersions) {
                    CharacterisationResult tmpResult = new CharacterisationResult();
                    setValues(tmpResult, Property.FORMAT, identityProperty.getFormat() + " " + matchingVersion.getContent());
                    tmpResult.setSource(tool.getToolname() + ":" + tool.getToolversion());
                    result.add(tmpResult);
                }
            } else {
                CharacterisationResult tmpResult = new CharacterisationResult();
                setValues(tmpResult, Property.FORMAT, identityProperty.getFormat());
                tmpResult.setSource(tool.getToolname() + ":" + tool.getToolversion());
                result.add(tmpResult);
            }

        }
        return result;
    }


    private static List<CharacterisationResult> getMimetypeData(DocumentContext document, String jsonPath) {
        List<CharacterisationResult> result = new ArrayList<>();
        IdentityProperty identityProperty = document.read(jsonPath, IdentityProperty.class);

        List<ToolIdentity> toolData = getToolData(document, jsonPath);

        for (ToolIdentity tool : toolData) {

            CharacterisationResult tmpResult = new CharacterisationResult();
            setValues(tmpResult, Property.MIMETYPE, identityProperty.getMimetype());
            tmpResult.setSource(tool.getToolname() + ":" + tool.getToolversion());
            result.add(tmpResult);

        }
        return result;
    }


    private static List<ToolIdentity> getToolData(DocumentContext document,
                                                  String jsonPath) {

        IdentityProperty identityProperty = document.read(jsonPath, IdentityProperty.class);

        Object tool = identityProperty.getTool();

        List<ToolIdentity> tools = new ArrayList<>();
        if (tool != null) {
            if (tool instanceof List) {
                List toolArray = (List) tool;

                for (int j = 0; j < toolArray.size(); j++) {
                    ToolIdentity toolIdentity = document.read(String.format(jsonPath + ".tool[%d]", j), ToolIdentity.class);
                    tools.add(toolIdentity);
                }
            } else {
                ToolIdentity toolIdentity = document.read(jsonPath + ".tool", ToolIdentity.class);
                tools.add(toolIdentity);
            }
        }
        return tools;
    }

    private static List<GenericProperty> getVersionData(DocumentContext document,
                                                        String jsonPath) {

        IdentityProperty identityProperty = document.read(jsonPath, IdentityProperty.class);

        Object version = identityProperty.getVersion();

        List<GenericProperty> versions = new ArrayList<>();
        if (version != null) {
            if (version instanceof List) {
                List versionArray = (List) version;

                for (int j = 0; j < versionArray.size(); j++) {
                    GenericProperty read = document.read(String.format(jsonPath + ".version[%d]", j),
                            GenericProperty.class);
                    versions.add(read);
                }
            } else {
                GenericProperty read = document.read(jsonPath + ".version", GenericProperty.class);
                versions.add(read);
            }
        }
        return versions;
    }


    private static void setValues(CharacterisationResult tmpResult, Property format, String value) {
        tmpResult.setProperty(format);
        tmpResult.setValueType(ValueType.STRING);
        tmpResult.setValue(value);
    }
}
