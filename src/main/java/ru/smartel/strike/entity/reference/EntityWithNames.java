package ru.smartel.strike.entity.reference;

import org.springframework.data.annotation.AccessType;
import ru.smartel.strike.entity.interfaces.Names;
import ru.smartel.strike.service.Locale;

import javax.persistence.Column;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.MappedSuperclass;
import java.util.Objects;

@MappedSuperclass
abstract public class EntityWithNames implements Names {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    @AccessType(AccessType.Type.PROPERTY) //чтобы доставать id из прокси (без загрузки объекта из базы)
    long id;
    @Column(name = "name_ru")
    private String nameRu;
    @Column(name = "name_en")
    private String nameEn;
    @Column(name = "name_es")
    private String nameEs;
    @Column(name = "name_de")
    private String nameDe;

    EntityWithNames() {}

    public EntityWithNames(String nameRu, String nameEn, String nameEs, String nameDe) {
        this.nameRu = nameRu;
        this.nameEn = nameEn;
        this.nameEs = nameEs;
        this.nameDe = nameDe;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    @Override
    public String getNameRu() {
        return nameRu;
    }

    @Override
    public void setNameRu(String nameRu) {
        this.nameRu = nameRu;
    }

    @Override
    public String getNameEn() {
        return nameEn;
    }

    @Override
    public void setNameEn(String nameEn) {
        this.nameEn = nameEn;
    }

    @Override
    public String getNameEs() {
        return nameEs;
    }

    @Override
    public void setNameEs(String nameEs) {
        this.nameEs = nameEs;
    }

    @Override
    public String getNameDe() {
        return nameDe;
    }

    @Override
    public void setNameDe(String nameDe) {
        this.nameDe = nameDe;
    }

    @Override
    public String getNameByLocale(Locale locale) {
        switch (locale) {
            case RU:
                return getNameRu();
            case EN:
                return getNameEn();
            case ES:
                return getNameEs();
            case DE:
                return getNameDe();
            default:
                return "";
        }
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        EntityWithNames that = (EntityWithNames) o;
        return id == that.id &&
                Objects.equals(nameRu, that.nameRu) &&
                Objects.equals(nameEn, that.nameEn) &&
                Objects.equals(nameEs, that.nameEs);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, nameRu, nameEn, nameEs);
    }
}
