package ru.smartel.strike.entity.interfaces;

import ru.smartel.strike.entity.Photo;

import java.util.Set;

public interface HasPhotos {

    Set<Photo> getPhotos();

    void setPhotos(Set<Photo> photos);
}
