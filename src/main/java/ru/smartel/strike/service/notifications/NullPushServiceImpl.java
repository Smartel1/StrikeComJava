package ru.smartel.strike.service.notifications;

import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.stereotype.Service;
import ru.smartel.strike.service.Locale;

import java.util.Map;

@Service
@ConditionalOnProperty(value = "push.enabled", havingValue = "false", matchIfMissing = true)
public class NullPushServiceImpl implements PushService {

    @Override
    public void newsCreatedByUser(Long newsId, Long authorId, String authorName) {

    }

    @Override
    public void eventCreatedByUser(Long eventId, Long authorId, String authorName) {

    }

    @Override
    public void newsPublished(Long newsId, Long authorId, Map<Locale, String> titlesByLocales, String authorFCM, boolean notifyAuthor) {

    }

    @Override
    public void eventPublished(Long eventId, Long authorId, Float lng, Float lat, Map<Locale, String> titlesByLocales, String authorFCM, boolean notifyAuthor) {

    }

    @Override
    public void newsDeclined(String authorFCM, Long newsId, Long authorId) {

    }

    @Override
    public void eventDeclined(String authorFCM, Long eventId, Long authorId) {

    }
}
