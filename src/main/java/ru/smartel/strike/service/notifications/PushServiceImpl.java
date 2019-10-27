package ru.smartel.strike.service.notifications;

import com.google.firebase.messaging.FirebaseMessaging;
import org.springframework.stereotype.Service;
import ru.smartel.strike.service.Locale;

import java.util.Map;

@Service
public class PushServiceImpl implements PushService {
    String TOPIC_ADMIN = "admin";
    String TOPIC_NEWS_RU = "news_ru";
    String TOPIC_NEWS_EN = "news_en";
    String TOPIC_NEWS_ES = "news_es";
    String TOPIC_EVENTS_RU = "events_ru";
    String TOPIC_EVENTS_EN = "events_en";
    String TOPIC_EVENTS_ES = "events_es";

    private FirebaseMessaging firebaseMessaging;

    @Override
    public void newsCreatedByUser(Integer newsId, Integer authorId, String authorName) {

    }

    @Override
    public void eventCreatedByUser(Integer eventId, Integer authorId, String authorName) {

    }

    @Override
    public void newsPublished(Integer newsId, Integer authorId, Map<String, Locale> titlesByLocales, String authorFCM, boolean notifyAuthor) {

    }

    @Override
    public void eventPublished(Integer eventId, Integer authorId, Float lng, Float lat, Map<String, Locale> titlesByLocales, String authorFCM, boolean notifyAuthor) {

    }

    @Override
    public void newsDeclined(String authorFCM, Integer newsId, Integer authorId) {

    }

    @Override
    public void eventDeclined(String authorFCM, Integer eventId, Integer authorId) {

    }
}
