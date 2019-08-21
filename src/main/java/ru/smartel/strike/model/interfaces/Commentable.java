package ru.smartel.strike.model.interfaces;


import ru.smartel.strike.model.Comment;

import java.util.List;

public interface Commentable {

    List<Comment> getComments();

    void setComments(List<Comment> comments);
}
