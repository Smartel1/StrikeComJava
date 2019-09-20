package ru.smartel.strike.model.reference;

import ru.smartel.strike.model.interfaces.Reference;

import javax.persistence.Entity;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import java.util.List;

@Entity
@Table(name = "countries")
public class Country extends EntityWithNames implements Reference {
    public Country() {
    }

    public Country(String nameRu, String nameEn, String nameEs, List<Region> regions) {
        super(nameRu, nameEn, nameEs);
        this.regions = regions;
    }

    @OneToMany(mappedBy = "country")
    private List<Region> regions;

    public List<Region> getRegions() {
        return regions;
    }

    public void setRegions(List<Region> regions) {
        this.regions = regions;
    }
}
