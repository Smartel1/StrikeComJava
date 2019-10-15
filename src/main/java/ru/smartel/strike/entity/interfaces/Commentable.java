package ru.smartel.strike.entity.interfaces;


import ru.smartel.strike.entity.Comment;

import java.util.Set;


public interface Commentable {

    Set<Comment> getComments();

    void setComments(Set<Comment> comments);
}
