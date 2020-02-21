package at.ac.tuwien.ifs;

import at.ac.tuwien.ifs.model.CharacterisationResult;

import javax.enterprise.context.Dependent;

@Dependent
public class CharacterisationResultService {


    private final CharacterisationResultRepository characterisationResultRepository;


    public CharacterisationResultService(CharacterisationResultRepository characterisationResultRepository) {
        this.characterisationResultRepository = characterisationResultRepository;
    }

    public void addCharacterisationResult(CharacterisationResult characterisationResult) {
        characterisationResultRepository.save(characterisationResult);
    }

    public Iterable<CharacterisationResult> getAllStudents() {
        Iterable<CharacterisationResult> all = characterisationResultRepository.findAll();
        return all;
    }

}
