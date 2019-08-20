package ru.smartel.strike.model;

import javax.persistence.*;

@Entity
@Table(name="conflict_results")
public class ConflictResult {
    @Id
    @GeneratedValue
    @Column(name = "id")
    private int id;

    @Column(name = "name_ru")
    private String nameRu;

    @Column(name = "name_en")
    private String nameEn;

    @Column(name = "name_es")
    private String nameEs;

    public ConflictResult(String nameRu, String nameEn, String nameEs) {
        this.nameRu = nameRu;
        this.nameEn = nameEn;
        this.nameEs = nameEs;
    }

    public ConflictResult() {
    }

//    @OneToMany(cascade = CascadeType.DETACH, mappedBy = "conflictResult")
//    private List<Conflict> conflicts;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getNameRu() {
        return nameRu;
    }

    public void setNameRu(String nameRu) {
        this.nameRu = nameRu;
    }

    public String getNameEn() {
        return nameEn;
    }

    public void setNameEn(String nameEn) {
        this.nameEn = nameEn;
    }

    public String getNameEs() {
        return nameEs;
    }

    public void setNameEs(String nameEs) {
        this.nameEs = nameEs;
    }
}
