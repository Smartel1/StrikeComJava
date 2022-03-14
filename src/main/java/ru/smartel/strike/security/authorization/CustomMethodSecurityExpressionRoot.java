package ru.smartel.strike.security.authorization;

import org.springframework.security.access.expression.SecurityExpressionRoot;
import org.springframework.security.access.expression.method.MethodSecurityExpressionOperations;
import org.springframework.security.core.Authentication;
import ru.smartel.strike.entity.User;
import ru.smartel.strike.entity.interfaces.PostEntity;
import ru.smartel.strike.repository.event.EventRepository;
import ru.smartel.strike.repository.news.NewsRepository;
import ru.smartel.strike.security.token.UserPrincipal;

public class CustomMethodSecurityExpressionRoot extends SecurityExpressionRoot implements MethodSecurityExpressionOperations {

    private final EventRepository eventRepository;
    private final NewsRepository newsRepository;

    private Object filterObject;
    private Object returnObject;

    /**
     * Creates a new instance
     * @param authentication the {@link Authentication} to use. Cannot be null.
     * @param eventRepository
     * @param newsRepository
     */
    public CustomMethodSecurityExpressionRoot(Authentication authentication,
                                              EventRepository eventRepository,
                                              NewsRepository newsRepository) {
        super(authentication);
        this.eventRepository = eventRepository;
        this.newsRepository = newsRepository;
    }

    public boolean isEventAuthor(Long eventId) {
        if (!(getPrincipal() instanceof UserPrincipal)) return false;
        return eventRepository.findById(eventId)
                .map(PostEntity::getAuthor)
                .map(User::getId)
                .map(authorId -> authorId.equals(((UserPrincipal)getPrincipal()).getId()))
                .orElse(Boolean.FALSE);
    }

    public boolean isNewsAuthor(Long newsId) {
        if (!(getPrincipal() instanceof UserPrincipal)) return false;
        return newsRepository.findById(newsId)
                .map(PostEntity::getAuthor)
                .map(User::getId)
                .map(authorId -> authorId.equals(((UserPrincipal)getPrincipal()).getId()))
                .orElse(Boolean.FALSE);
    }

    @Override
    public void setFilterObject(Object filterObject) {
        this.filterObject = filterObject;
    }

    @Override
    public Object getFilterObject() {
        return filterObject;
    }

    @Override
    public void setReturnObject(Object returnObject) {
        this.returnObject = returnObject;
    }

    @Override
    public Object getReturnObject() {
        return returnObject;
    }

    @Override
    public Object getThis() {
        return this;
    }
}
