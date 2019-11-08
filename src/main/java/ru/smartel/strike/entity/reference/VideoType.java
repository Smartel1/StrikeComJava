package ru.smartel.strike.entity.reference;

import ru.smartel.strike.entity.interfaces.ReferenceWithCode;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import java.util.Objects;

@Entity
@Table(name = "video_types")
public class VideoType implements ReferenceWithCode {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private int id;

    @Column
    private String code;

    public int getId() {
        return id;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        VideoType videoType = (VideoType) o;
        return id == videoType.id &&
                code.equals(videoType.code);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, code);
    }
}
