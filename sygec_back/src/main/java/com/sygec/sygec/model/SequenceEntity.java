package com.sygec.sygec.model;


import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;
import java.util.Objects;

@Entity
@Table(name = "sequence")
public class SequenceEntity extends AbstractEntity{
    @Column(name = "premiere_lettre")
    private char premiereLettre;
    @Column(name = "deuxieme_lettre")
    private char deuxiemeLettre;
    @Column(name = "nombre_actuel")
    private int nombreActuel;
    @Column(name = "plage")
    private int plage;
    public SequenceEntity(){}

    public SequenceEntity(char premiereLettre, char deuxiemeLettre, int nombreActuel, int plage) {
        this.premiereLettre = premiereLettre;
        this.deuxiemeLettre = deuxiemeLettre;
        this.nombreActuel = nombreActuel;
        this.plage = plage;
    }

    public int getPlage() {
        return plage;
    }

    public void setPlage(int plage) {
        this.plage = plage;
    }

    public char getPremiereLettre() {
        return premiereLettre;
    }

    public void setPremiereLettre(char premiereLettre) {
        this.premiereLettre = premiereLettre;
    }

    public char getDeuxiemeLettre() {
        return deuxiemeLettre;
    }

    public void setDeuxiemeLettre(char deuxiemeLettre) {
        this.deuxiemeLettre = deuxiemeLettre;
    }

    public int getNombreActuel() {
        return nombreActuel;
    }

    public void setNombreActuel(int nombreActuel) {
        this.nombreActuel = nombreActuel;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof SequenceEntity)) return false;
        if (!super.equals(o)) return false;
        SequenceEntity that = (SequenceEntity) o;
        return getPremiereLettre() == that.getPremiereLettre() && getDeuxiemeLettre() == that.getDeuxiemeLettre() && getNombreActuel() == that.getNombreActuel();
    }

    @Override
    public int hashCode() {
        return Objects.hash(getPremiereLettre(), getDeuxiemeLettre(), getNombreActuel());
    }

    @Override
    public String toString() {
        return "SequenceEntity{" +
                "premiereLettre=" + premiereLettre +
                ", deuxiemeLettre=" + deuxiemeLettre +
                ", nombreActuel=" + nombreActuel +
                '}';
    }
}
