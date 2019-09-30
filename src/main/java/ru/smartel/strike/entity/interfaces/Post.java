package ru.smartel.strike.entity.interfaces;

import ru.smartel.strike.entity.User;

public interface Post extends Titles, Commentable {
    int getId();

    User getAuthor();

    void setAuthor(User author);
}
