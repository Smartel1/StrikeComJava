package ru.smartel.strike.entity.interfaces;


import ru.smartel.strike.entity.Comment;

import java.util.List;

public interface Commentable {

    List<Comment> getComments();

    void setComments(List<Comment> comments);
}
