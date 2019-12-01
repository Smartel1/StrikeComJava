package ru.smartel.strike.entity.reference;

import javax.persistence.Entity;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import java.util.List;

@Entity
@Table(name = "countries")
public class Country extends EntityWithNames {
    public Country() {
    }

    public Country(String nameRu, String nameEn, String nameEs, String nameDe, List<Region> regions) {
        super(nameRu, nameEn, nameEs, nameDe);
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
