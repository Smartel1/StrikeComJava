package ru.smartel.strike.model.reference;

import javax.persistence.*;

@Entity
@Table(name = "video_types")
public class VideoType {
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
}
