package ru.smartel.strike.entity.interfaces;

import ru.smartel.strike.entity.Video;

import java.util.Set;

public interface HasVideos {

    Set<Video> getVideos();

    void setVideos(Set<Video> videos);
}
