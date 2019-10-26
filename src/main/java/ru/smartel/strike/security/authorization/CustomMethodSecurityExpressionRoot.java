package ru.smartel.strike.security.authorization;

import org.springframework.security.access.expression.SecurityExpressionRoot;
import org.springframework.security.access.expression.method.MethodSecurityExpressionOperations;
import org.springframework.security.core.Authentication;
import ru.smartel.strike.entity.Event;
import ru.smartel.strike.entity.News;
import ru.smartel.strike.entity.User;
import ru.smartel.strike.repository.EventRepository;
import ru.smartel.strike.repository.NewsRepository;

public class CustomMethodSecurityExpressionRoot extends SecurityExpressionRoot implements MethodSecurityExpressionOperations {

    private EventRepository eventRepository;
    private NewsRepository newsRepository;

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

    public boolean isEventAuthor(Integer eventId) {
        Event event = eventRepository.findOrThrow(eventId);
        if (null == event) return false;
        if (null == event.getAuthor()) return false;
        return event.getAuthor().getId() == ((User)getAuthentication().getPrincipal()).getId();
    }

    public boolean isNewsAuthor(Integer newsId) {
        News news = newsRepository.findOrThrow(newsId);
        if (null == news) return false;
        if (null == news.getAuthor()) return false;
        return news.getAuthor().getId() == ((User)getAuthentication().getPrincipal()).getId();
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
