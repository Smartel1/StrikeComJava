package ru.smartel.strike.service.notifications;

import com.google.firebase.messaging.FirebaseMessaging;
import com.google.firebase.messaging.FirebaseMessagingException;
import com.google.firebase.messaging.Message;
import com.google.firebase.messaging.Notification;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.stereotype.Service;
import ru.smartel.strike.service.Locale;

import java.util.Map;
import java.util.concurrent.CompletableFuture;

@Service
@ConditionalOnProperty(value = "push.enabled", havingValue = "true")
public class PushServiceImpl implements PushService {
    private static Logger logger = LoggerFactory.getLogger(PushServiceImpl.class);

    private static final String TOPIC_ADMIN = "admin";
    private static final String TOPIC_NEWS_RU = "news_ru";
    private static final String TOPIC_NEWS_EN = "news_en";
    private static final String TOPIC_NEWS_ES = "news_es";
    private static final String TOPIC_NEWS_DE = "news_de";
    private static final String TOPIC_EVENTS_RU = "events_ru";
    private static final String TOPIC_EVENTS_EN = "events_en";
    private static final String TOPIC_EVENTS_ES = "events_es";
    private static final String TOPIC_EVENTS_DE = "events_de";

    private FirebaseMessaging firebaseMessaging;

    public PushServiceImpl(FirebaseMessaging firebaseMessaging) {
        this.firebaseMessaging = firebaseMessaging;
    }

    @Override
    public void newsCreatedByUser(Long newsId, Long authorId, String authorName) {
        logger.info("sending notification about news {} proposed by user {}", newsId, authorName);
        Message message = Message.builder()
                .setTopic(TOPIC_ADMIN)
                .setNotification(new Notification("ЗабастКом", "На модерации новость от " + authorName))
                .putData("id", String.valueOf(newsId))
                .putData("creator_name", authorName)
                .putData("creator_id", String.valueOf(authorId))
                .putData("type", "admin") // wth is that?
                .build();

        sendAsync(message);
    }

    @Override
    public void eventCreatedByUser(Long eventId, Long authorId, String authorName) {
        logger.info("sending notification about event {} proposed by user {}", eventId, authorName);
        Message message = Message.builder()
                .setTopic(TOPIC_ADMIN)
                .setNotification(new Notification("ЗабастКом", "На модерации событие от " + authorName))
                .putData("id", String.valueOf(eventId))
                .putData("creator_name", authorName)
                .putData("creator_id", String.valueOf(authorId))
                .putData("type", "admin") // wth is that?
                .build();

        sendAsync(message);
    }

    @Override
    public void newsPublished(Long newsId, Long authorId, Map<String, Locale> titlesByLocales, String authorFCM, boolean notifyAuthor) {
        for (Map.Entry<String, Locale> titleByLocale : titlesByLocales.entrySet()) {
            newsPublishedByLocale(newsId, authorId, titleByLocale.getKey(), titleByLocale.getValue());
        }

        if (notifyAuthor) {
            sendYourNewsModerated(newsId, authorId, authorFCM);
        }
    }

    @Override
    public void eventPublished(Long eventId, Long authorId, Float lng, Float lat, Map<String, Locale> titlesByLocales, String authorFCM, boolean notifyAuthor) {
        for (Map.Entry<String, Locale> titleByLocale : titlesByLocales.entrySet()) {
            eventPublishedByLocale(eventId, authorId, lng, lat, titleByLocale.getKey(), titleByLocale.getValue());
        }

        if (notifyAuthor) {
            sendYourEventModerated(eventId, authorId, lng, lat, authorFCM);
        }
    }

    @Override
    public void newsDeclined(String authorFCM, Long newsId, Long authorId) {
        if (authorFCM == null) {
            return;
        }

        logger.info("sending notification about news {} rejection to user {} with FCM token {}", newsId, authorId, authorFCM);
        Message message = Message.builder()
                .setToken(authorFCM)
                //idk why always Russian
                .setNotification(new Notification("Забастком", "Ваша новость не прошла модерацию и была удалена"))
                .putData("id", String.valueOf(newsId))
                .putData("message_ru", "Предложенный Вами пост не прошел модерацию и был удалён")
                .putData("message_en", "The news you proposed was removed")
                .putData("message_es", "Las noticias que propusiste fueron eliminadas")
                .putData("message_de", "Der von Ihnen vorgeschlagene Beitrag hat die Moderation nicht bestanden und wurde gelöscht")
                .putData("creator_id", String.valueOf(authorId))
                .putData("type", "moderated") // wth is that?
                .build();
        sendAsync(message);
    }

    @Override
    public void eventDeclined(String authorFCM, Long eventId, Long authorId) {
        if (authorFCM == null) {
            return;
        }
        logger.info("sending notification about event {} rejection to user {} with FCM token {}", eventId, authorId, authorFCM);
        Message message = Message.builder()
                .setToken(authorFCM)
                //idk why always Russian
                .setNotification(new Notification("Забастком", "Ваше событие не прошло модерацию и было удалено"))
                .putData("id", String.valueOf(eventId))
                .putData("message", "Ваше событие не прошло модерацию и было удаленон")
                .putData("creator_id", String.valueOf(authorId))
                .putData("type", "moderated") // wth is that?
                .build();
        sendAsync(message);
    }

    /**
     * Notify users of topic about news publication
     * For each locale we have independent topic
     * @param newsId   id of published news
     * @param authorId id of news author
     * @param title    news title by locale
     * @param locale   locale
     */
    private void newsPublishedByLocale(Long newsId, Long authorId, String title, Locale locale) {
        logger.info("sending notification about news publishing to {} topic. Title: {}", locale, title);

        String topic; //topic to send message to
        String notificationTitle; //title of notification
        String notificationBody; //body of notification
        switch (locale) {
            case RU: {
                topic = TOPIC_NEWS_RU;
                notificationTitle = "ЗабастКом";
                notificationBody = title != null ? title : "В приложении опубликована новость";
                break;
            }
            case EN: {
                topic = TOPIC_NEWS_EN;
                notificationTitle = "ZabastCom";
                notificationBody = title != null ? title : "News was published";
                break;
            }
            case ES: {
                topic = TOPIC_NEWS_ES;
                notificationTitle = "ZabastCom";
                notificationBody = title != null ? title : "La noticia ha sido publicada";
                break;
            }
            case DE: {
                topic = TOPIC_NEWS_DE;
                notificationTitle = "ZabastCom";
                notificationBody = title != null ? title : "Die Anwendung hat Nachrichten veröffentlicht";
                break;
            }
            default:
                throw new IllegalStateException("unknown topic for locale " + locale);
        }

        Message message = Message.builder()
                .setTopic(topic)
                .setNotification(new Notification(notificationTitle, notificationBody))
                .putData("id", String.valueOf(newsId))
                .putData("creator_id", String.valueOf(authorId))
                .putData("creator_id", title)
                .putData("type", "news") // wth is that?
                .build();
        sendAsync(message);
    }

    /**
     * Notify users of topic about event publication
     * For each locale we have independent topic
     * @param eventId  event id
     * @param authorId author id
     * @param title    event title by locale
     * @param locale   locale
     */
    private void eventPublishedByLocale(Long eventId, Long authorId, Float lng, Float lat, String title, Locale locale) {
        logger.info("sending notification about event publishing to {} topic. Title: {}", locale, title);

        String topic; //topic to send message to
        String notificationTitle; //title of notification
        String notificationBody; //body of notification
        switch (locale) {
            case RU: {
                topic = TOPIC_EVENTS_RU;
                notificationTitle = "ЗабастКом";
                notificationBody = title != null ? title : "В приложении опубликовано событие";
                break;
            }
            case EN: {
                topic = TOPIC_EVENTS_EN;
                notificationTitle = "ZabastCom";
                notificationBody = title != null ? title : "Event was published";
                break;
            }
            case ES: {
                topic = TOPIC_EVENTS_ES;
                notificationTitle = "ZabastCom";
                notificationBody = title != null ? title : "El evento ha sido publicado";
                break;
            }
            case DE: {
                topic = TOPIC_EVENTS_DE;
                notificationTitle = "ZabastCom";
                notificationBody = title != null ? title : "Eine Veranstaltung wird in der Anwendung veröffentlicht";
                break;
            }
            default:
                throw new IllegalStateException("unknown topic for locale " + locale);
        }

        Message message = Message.builder()
                .setTopic(topic)
                .setNotification(new Notification(notificationTitle, notificationBody))
                .putData("id", String.valueOf(eventId))
                .putData("lat", String.valueOf(lat))
                .putData("lng", String.valueOf(lng))
                .putData("creator_id", String.valueOf(authorId))
                .putData("creator_id", title)
                .putData("type", "news") // wth is that?
                .build();
        sendAsync(message);
    }

    /**
     * Notify news' author about publication
     * @param newsId    news id
     * @param authorId  author id
     * @param authorFCM author FCM registration token
     */
    private void sendYourNewsModerated(Long newsId, Long authorId, String authorFCM) {
        if (authorFCM == null) {
            return;
        }
        logger.info("sending notification about event {} publication to user {} with FCM token {}", newsId, authorId, authorFCM);
        Message message = Message.builder()
                .setToken(authorFCM)
                //idk why always Russian
                .setNotification(new Notification("Забастком", "Предложенный Вами пост прошел модерацию"))
                .putData("id", String.valueOf(newsId))
                .putData("message_ru", "Предложенный Вами пост прошел модерацию")
                .putData("message_en", "The news you proposed was published")
                .putData("message_es", "Las noticias que propusiste fueron publicadas")
                .putData("message_de", "Ihr Beitrag wurde moderiert")
                .putData("creator_id", String.valueOf(authorId))
                .putData("type", "moderated") // wth is that?
                .build();
        sendAsync(message);
    }

    /**
     * Notify event's author about publication
     * @param eventId   event id
     * @param authorId  author id
     * @param lng       event lng
     * @param lat       event lat
     * @param authorFCM author FCM registration token
     */
    private void sendYourEventModerated(Long eventId, Long authorId, Float lng, Float lat, String authorFCM) {
        if (authorFCM == null) {
            return;
        }
        logger.info("sending notification about event {} publication to user {} with FCM token {}", eventId, authorId, authorFCM);
        Message message = Message.builder()
                .setToken(authorFCM)
                //idk why always Russian
                .setNotification(new Notification("Забастком", "Предложенный Вами пост прошел модерацию"))
                .putData("id", String.valueOf(eventId))
                .putData("message_ru", "Предложенный Вами пост прошел модерацию")
                .putData("message_en", "The news you proposed was published")
                .putData("message_es", "Las noticias que propusiste fueron publicadas")
                .putData("message_es", "Ihr Beitrag wurde moderiert")
                .putData("creator_id", String.valueOf(authorId))
                .putData("lat", String.valueOf(lat))
                .putData("lng", String.valueOf(lng))
                .putData("type", "moderated") // wth is that?
                .build();
        sendAsync(message);
    }

    /**
     * Send push to Firebase
     * @param message message to send
     */
    private void sendAsync(Message message) {
        CompletableFuture.runAsync(() -> {
            try {
                firebaseMessaging.send(message);
            } catch (FirebaseMessagingException e) {
                logger.error("Push notification send error", e);
            }
        });
    }
}
