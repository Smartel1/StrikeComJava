package ru.smartel.strike.security.authorization;

import org.springframework.security.access.expression.SecurityExpressionRoot;
import org.springframework.security.access.expression.method.MethodSecurityExpressionOperations;
import org.springframework.security.core.Authentication;
import ru.smartel.strike.entity.Event;
import ru.smartel.strike.entity.User;
import ru.smartel.strike.repository.EventRepository;

public class CustomMethodSecurityExpressionRoot extends SecurityExpressionRoot implements MethodSecurityExpressionOperations {

    private EventRepository eventRepository;

    private Object filterObject;
    private Object returnObject;

    /**
     * Creates a new instance
     *
     * @param authentication the {@link Authentication} to use. Cannot be null.
     * @param eventRepository
     */
    public CustomMethodSecurityExpressionRoot(Authentication authentication, EventRepository eventRepository) {
        super(authentication);
        this.eventRepository = eventRepository;
    }

    public boolean isEventAuthor(Integer eventId) {
        Event event = eventRepository.findOrThrow(eventId);
        if (null == event) return false;
        if (null == event.getAuthor()) return false;
        return event.getAuthor().getId() == ((User)getAuthentication().getPrincipal()).getId();
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
