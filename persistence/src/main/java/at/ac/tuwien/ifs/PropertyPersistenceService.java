package at.ac.tuwien.ifs;

import at.ac.tuwien.ifs.model.CharacterisationResult;
import at.ac.tuwien.ifs.model.Property;
import at.ac.tuwien.ifs.model.statistics.PropertyStatistics;
import at.ac.tuwien.ifs.model.statistics.PropertyValueStatistics;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import java.util.List;

@ApplicationScoped
public class PropertyPersistenceService {

    @Inject
    CharacterisationResultRepository characterisationResultRepository;

    PropertyPersistenceService(){}

    PropertyPersistenceService(CharacterisationResultRepository characterisationResultRepository) {
        this.characterisationResultRepository = characterisationResultRepository;
    }

    public void addCharacterisationResult(CharacterisationResult characterisationResult) {
        characterisationResultRepository.save(characterisationResult);
    }

    public Iterable<CharacterisationResult> getAllCharacterisationResults() {
        Iterable<CharacterisationResult> all = characterisationResultRepository.findAll();
        return all;
    }

    public List<PropertyStatistics> getPropertyDistribution() {
        List<PropertyStatistics> propertyDistribution = characterisationResultRepository.getPropertyDistribution();
        System.out.println(propertyDistribution);
        return propertyDistribution;
    }

    public List<PropertyValueStatistics> getValueDistributionByProperty(Property property) {
        List<PropertyValueStatistics> propertyValueDistribution =
                characterisationResultRepository.getPropertyValueDistribution(property);
        System.out.println(propertyValueDistribution);
        return propertyValueDistribution;
    }

    public Iterable<CharacterisationResult> getCharacterisationResultsByFilepath(String filepath) {
        return characterisationResultRepository.findAllByFilePath(filepath);
    }


}
