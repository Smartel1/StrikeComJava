package ru.smartel.strike.entity.interfaces;

import ru.smartel.strike.entity.Tag;

import java.util.Set;

public interface Taggable {

    Set<Tag> getTags();

    void setTags(Set<Tag> tags);
}
