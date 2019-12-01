package ru.smartel.strike.entity.reference;

import javax.persistence.Entity;
import javax.persistence.Table;

@Entity
@Table(name = "video_types")
public class VideoType extends ReferenceWithCode {
    public VideoType() {
    }

    public VideoType(String code) {
        super(code);
    }
}
