package ru.smartel.strike.model.interfaces;

import ru.smartel.strike.model.User;

public interface Post extends Titles, Commentable {
    int getId();

    User getAuthor();

    void setAuthor(User author);
}
