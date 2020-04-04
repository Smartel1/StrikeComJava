package ru.smartel.strike.service.notifications;

import com.google.firebase.messaging.FirebaseMessaging;
import com.google.firebase.messaging.FirebaseMessagingException;
import com.google.firebase.messaging.Message;
import com.google.firebase.messaging.Notification;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import ru.smartel.strike.configuration.properties.PushProperties;
import ru.smartel.strike.service.Locale;

import java.util.Map;
import java.util.concurrent.CompletableFuture;
import java.util.function.Supplier;

@Service
public class PushServiceImpl implements PushService {
    private static Logger logger = LoggerFactory.getLogger(PushServiceImpl.class);

    private FirebaseMessaging firebaseMessaging;
    private PushProperties pushProperties;

    public PushServiceImpl(FirebaseMessaging firebaseMessaging, PushProperties pushProperties) {
        this.firebaseMessaging = firebaseMessaging;
        this.pushProperties = pushProperties;
    }

    @Override
    public void newsCreatedByUser(Long newsId, Long authorId, String authorName) {

        sendAsyncIfEnabled(() -> {
            logger.info("sending notification about news {} proposed by user {}", newsId, authorName);
            return Message.builder()
                    .setTopic(pushProperties.getTopics().getAdmin())
                    .setNotification(new Notification("ЗабастКом", "На модерации новость от " + authorName))
                    .putData("id", String.valueOf(newsId))
                    .putData("creatorName", authorName)
                    .putData("creatorId", String.valueOf(authorId))
                    .putData("type", "admin") // wth is that?
                    .build();
        });
    }

    @Override
    public void eventCreatedByUser(Long eventId, Long authorId, String authorName) {

        sendAsyncIfEnabled(() -> {
            logger.info("sending notification about event {} proposed by user {}", eventId, authorName);
            return Message.builder()
                    .setTopic(pushProperties.getTopics().getAdmin())
                    .setNotification(new Notification("ЗабастКом", "На модерации событие от " + authorName))
                    .putData("id", String.valueOf(eventId))
                    .putData("creatorName", authorName)
                    .putData("creatorId", String.valueOf(authorId))
                    .putData("type", "admin") // wth is that?
                    .build();
        });
    }

    @Override
    public void newsPublished(Long newsId, Long authorId, Map<Locale, String> titlesByLocales, String authorFCM, boolean notifyAuthor) {
        for (Map.Entry<Locale, String> titleByLocale : titlesByLocales.entrySet()) {
            newsPublishedByLocale(newsId, authorId, titleByLocale.getValue(), titleByLocale.getKey());
        }

        if (notifyAuthor) {
            sendYourNewsModerated(newsId, authorId, authorFCM);
        }
    }

    @Override
    public void eventPublished(Long eventId, Long authorId, Float lng, Float lat, Map<Locale, String> titlesByLocales, String authorFCM, boolean notifyAuthor) {
        for (Map.Entry<Locale, String> titleByLocale : titlesByLocales.entrySet()) {
            eventPublishedByLocale(eventId, authorId, lng, lat, titleByLocale.getValue(), titleByLocale.getKey());
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

        sendAsyncIfEnabled(() -> {
            logger.info("sending notification about news {} rejection to user {} with FCM token {}", newsId, authorId, authorFCM);
            return Message.builder()
                    .setToken(authorFCM)
                    //idk why always Russian
                    .setNotification(new Notification("Забастком", "Ваша новость не прошла модерацию и была удалена"))
                    .putData("id", String.valueOf(newsId))
                    .putData("messageRu", "Предложенный Вами пост не прошел модерацию и был удалён")
                    .putData("messageEn", "The news you proposed was removed")
                    .putData("messageEs", "Las noticias que propusiste fueron eliminadas")
                    .putData("messageDe", "Der von Ihnen vorgeschlagene Beitrag hat die Moderation nicht bestanden und wurde gelöscht")
                    .putData("creatorId", String.valueOf(authorId))
                    .putData("type", "moderated") // wth is that?
                    .build();
        });
    }

    @Override
    public void eventDeclined(String authorFCM, Long eventId, Long authorId) {
        if (authorFCM == null) {
            return;
        }
        sendAsyncIfEnabled(() -> {
            logger.info("sending notification about event {} rejection to user {} with FCM token {}", eventId, authorId, authorFCM);
            return Message.builder()
                    .setToken(authorFCM)
                    //idk why always Russian
                    .setNotification(new Notification("Забастком", "Ваше событие не прошло модерацию и было удалено"))
                    .putData("id", String.valueOf(eventId))
                    .putData("message", "Ваше событие не прошло модерацию и было удаленон")
                    .putData("creatorId", String.valueOf(authorId))
                    .putData("type", "moderated") // wth is that?
                    .build();
        });
    }

    /**
     * Notify users of topic about news publication
     * For each locale we have independent topic
     *
     * @param newsId   id of published news
     * @param authorId id of news author
     * @param title    news title by locale
     * @param locale   locale
     */
    private void newsPublishedByLocale(Long newsId, Long authorId, String title, Locale locale) {
        sendAsyncIfEnabled(() -> {
            logger.info("sending notification about news publishing to {} topic. Title: {}", locale, title);

            String topic; //topic to send message to
            String notificationTitle; //title of notification
            String notificationBody; //body of notification
            switch (locale) {
                case RU: {
                    topic = pushProperties.getTopics().getNews().getRu();
                    notificationTitle = "ЗабастКом";
                    notificationBody = title != null ? title : "В приложении опубликована новость";
                    break;
                }
                case EN: {
                    topic = pushProperties.getTopics().getNews().getEn();
                    notificationTitle = "ZabastCom";
                    notificationBody = title != null ? title : "News was published";
                    break;
                }
                case ES: {
                    topic = pushProperties.getTopics().getNews().getEs();
                    notificationTitle = "ZabastCom";
                    notificationBody = title != null ? title : "La noticia ha sido publicada";
                    break;
                }
                case DE: {
                    topic = pushProperties.getTopics().getNews().getDe();
                    notificationTitle = "ZabastCom";
                    notificationBody = title != null ? title : "Die Anwendung hat Nachrichten veröffentlicht";
                    break;
                }
                default:
                    throw new IllegalStateException("unknown topic for locale " + locale);
            }

            return Message.builder()
                    .setTopic(topic)
                    .setNotification(new Notification(notificationTitle, notificationBody))
                    .putData("id", String.valueOf(newsId))
                    .putData("creatorId", String.valueOf(authorId))
                    .putData("title", title)
                    .putData("type", "news") // wth is that?
                    .build();
        });
    }

    /**
     * Notify users of topic about event publication
     * For each locale we have independent topic
     *
     * @param eventId  event id
     * @param authorId author id
     * @param title    event title by locale
     * @param locale   locale
     */
    private void eventPublishedByLocale(Long eventId, Long authorId, Float lng, Float lat, String title, Locale locale) {
        sendAsyncIfEnabled(() -> {
            logger.info("sending notification about event publishing to {} topic. Title: {}", locale, title);

            String topic; //topic to send message to
            String notificationTitle; //title of notification
            String notificationBody; //body of notification
            switch (locale) {
                case RU: {
                    topic = pushProperties.getTopics().getEvents().getRu();
                    notificationTitle = "ЗабастКом";
                    notificationBody = title != null ? title : "В приложении опубликовано событие";
                    break;
                }
                case EN: {
                    topic = pushProperties.getTopics().getEvents().getEn();
                    notificationTitle = "ZabastCom";
                    notificationBody = title != null ? title : "Event was published";
                    break;
                }
                case ES: {
                    topic = pushProperties.getTopics().getEvents().getEs();
                    notificationTitle = "ZabastCom";
                    notificationBody = title != null ? title : "El evento ha sido publicado";
                    break;
                }
                case DE: {
                    topic = pushProperties.getTopics().getEvents().getDe();
                    notificationTitle = "ZabastCom";
                    notificationBody = title != null ? title : "Eine Veranstaltung wird in der Anwendung veröffentlicht";
                    break;
                }
                default:
                    throw new IllegalStateException("unknown topic for locale " + locale);
            }

            return Message.builder()
                    .setTopic(topic)
                    .setNotification(new Notification(notificationTitle, notificationBody))
                    .putData("id", String.valueOf(eventId))
                    .putData("lat", String.valueOf(lat))
                    .putData("lng", String.valueOf(lng))
                    .putData("creatorId", String.valueOf(authorId))
                    .putData("title", title)
                    .putData("type", "news") // wth is that?
                    .build();
        });
    }

    /**
     * Notify news' author about publication
     *
     * @param newsId    news id
     * @param authorId  author id
     * @param authorFCM author FCM registration token
     */
    private void sendYourNewsModerated(Long newsId, Long authorId, String authorFCM) {
        if (authorFCM == null) {
            return;
        }
        sendAsyncIfEnabled(() -> {
            logger.info("sending notification about event {} publication to user {} with FCM token {}", newsId, authorId, authorFCM);
            return Message.builder()
                    .setToken(authorFCM)
                    //idk why always Russian
                    .setNotification(new Notification("Забастком", "Предложенный Вами пост прошел модерацию"))
                    .putData("id", String.valueOf(newsId))
                    .putData("messageRu", "Предложенный Вами пост прошел модерацию")
                    .putData("messageEn", "The news you proposed was published")
                    .putData("messageEs", "Las noticias que propusiste fueron publicadas")
                    .putData("messageDe", "Ihr Beitrag wurde moderiert")
                    .putData("creatorId", String.valueOf(authorId))
                    .putData("type", "moderated") // wth is that?
                    .build();
        });
    }

    /**
     * Notify event's author about publication
     *
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
        sendAsyncIfEnabled(() -> {
            logger.info("sending notification about event {} publication to user {} with FCM token {}", eventId, authorId, authorFCM);
            return Message.builder()
                    .setToken(authorFCM)
                    //idk why always Russian
                    .setNotification(new Notification("Забастком", "Предложенный Вами пост прошел модерацию"))
                    .putData("id", String.valueOf(eventId))
                    .putData("messageRu", "Предложенный Вами пост прошел модерацию")
                    .putData("messageEn", "The news you proposed was published")
                    .putData("messageEs", "Las noticias que propusiste fueron publicadas")
                    .putData("messageDe", "Ihr Beitrag wurde moderiert")
                    .putData("creatorId", String.valueOf(authorId))
                    .putData("lat", String.valueOf(lat))
                    .putData("lng", String.valueOf(lng))
                    .putData("type", "moderated") // wth is that?
                    .build();
        });
    }

    /**
     * Send push to Firebase if push notifications are enabled
     *
     * @param messageSupplier message supplier
     */
    private void sendAsyncIfEnabled(Supplier<Message> messageSupplier) {
        if (!pushProperties.getEnabled()) {
            return;
        }
        CompletableFuture.runAsync(() -> {
            try {
                firebaseMessaging.send(messageSupplier.get());
            } catch (FirebaseMessagingException e) {
                logger.error("Push notification send error", e);
            }
        });
    }
}
