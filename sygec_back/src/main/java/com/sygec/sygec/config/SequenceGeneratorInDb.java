package com.sygec.sygec.config;

import com.sygec.sygec.model.SequenceEntity;
import com.sygec.sygec.repository.SequenceRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;
import javax.transaction.Transactional;
@Service
public class SequenceGeneratorInDb {
    @Autowired
    private SequenceRepository sequenceRepository;

    private static final char PREMIER_CARACTERE = 'D';
    private static final char DERNIER_CARACTERE = 'Z';
    private static final int PLAGE = 7;

    private char premiereLettre = PREMIER_CARACTERE;
    private char deuxiemeLettre = PREMIER_CARACTERE;
    private int nombreActuel = 1;

    @PostConstruct
    public void init() {
        loadState();
    }

    @Transactional
    public String valeurSuivante() {
        if (nombreActuel > Math.pow(10, PLAGE - 1)) {
            if (deuxiemeLettre == DERNIER_CARACTERE) {
                deuxiemeLettre = PREMIER_CARACTERE;
                premiereLettre++;
            }
            deuxiemeLettre++;
            nombreActuel = 1;
        }
        String sequence = String.format("%c%c%07d", premiereLettre, deuxiemeLettre, nombreActuel);
        nombreActuel++;

        saveState();

        return sequence;
    }

    private void loadState() {
        SequenceEntity sequence = sequenceRepository.findById("1").orElse(null);
        if (sequence != null) {
            premiereLettre = (char) sequence.getPremiereLettre();
            deuxiemeLettre = (char) sequence.getDeuxiemeLettre();
            nombreActuel = sequence.getNombreActuel();
        }
    }

    private void saveState() {
        SequenceEntity sequence = sequenceRepository.findById("1").orElse(new SequenceEntity());
        sequence.setUuid("1");
        sequence.setPremiereLettre(premiereLettre);
        sequence.setDeuxiemeLettre(deuxiemeLettre);
        sequence.setNombreActuel(nombreActuel);
        sequenceRepository.save(sequence);
    }
}


