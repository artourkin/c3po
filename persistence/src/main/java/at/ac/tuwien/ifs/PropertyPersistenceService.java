package at.ac.tuwien.ifs;

import at.ac.tuwien.ifs.model.CharacterisationResult;
import at.ac.tuwien.ifs.model.Property;
import at.ac.tuwien.ifs.model.statistics.PropertyStatistic;
import at.ac.tuwien.ifs.model.statistics.PropertyValueStatistic;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import java.util.List;
import java.util.stream.Collectors;

@ApplicationScoped
public class PropertyPersistenceService {

    @Inject
    CharacterisationResultRepository characterisationResultRepository;

    PropertyPersistenceService() {
    }

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

    public List<PropertyStatistic> getPropertyDistribution() {
        List<Object[]> propertyDistribution = characterisationResultRepository.getPropertyDistribution();
        List<PropertyStatistic> collect = propertyDistribution.stream()
                .map(stat -> new PropertyStatistic((Long) stat[1], (Property) stat[0]))
                .collect(Collectors.toList());
        System.out.println(collect);
        return collect;
    }

    public List<PropertyValueStatistic> getValueDistributionByProperty(Property property) {
        List<Object[]> propertyValueDistribution =
                characterisationResultRepository.getPropertyValueDistribution(property);
        List<PropertyValueStatistic> collect = propertyValueDistribution.stream()
                .map(stat -> new PropertyValueStatistic((String) stat[0], (Long) stat[1]))
                .collect(Collectors.toList());
        System.out.println(propertyValueDistribution);
        return collect;
    }

    public Iterable<CharacterisationResult> getCharacterisationResultsByFilepath(String filepath) {
        return characterisationResultRepository.findAllByFilePath(filepath);
    }


}
