package ru.smartel.strike.service.notifications;

import ru.smartel.strike.service.Locale;

import java.util.Map;

public interface PushService {

    /**
     * News is created by user. Notifies moderators about it
     * todo generify this method and merge with eventCreatedByUser()
     */
    void newsCreatedByUser(Long newsId, Long authorId, String authorName);

    /**
     * Event is created by user. Notifies moderators about it
     */
    void eventCreatedByUser(Long eventId, Long authorId, String authorName);

    /**
     * News has been published. Notifies all clients and news' author (if notifyAuthor eq true and fcm is not null)
     * todo generify this method and merge with eventPublished()
     */
    void newsPublished(
            Long newsId,
            Long authorId,
            Map<Locale, String> titlesByLocales,
            String authorFCM,
            boolean notifyAuthor
    );

    /**
     * Event has been published. Notifies all clients and event's author (if notifyAuthor eq true and fcm is not null)
     */
    void eventPublished(
            Long eventId,
            Long authorId,
            Float lng, Float lat,
            Map<Locale, String> titlesByLocales,
            String authorFCM,
            boolean notifyAuthor
    );

    /**
     * Notify author of news that moderator declined the latter
     */
    void newsDeclined(String authorFCM, Long newsId, Long authorId);

    /**
     * Notify author of event that moderator declined the latter
     */
    void eventDeclined(String authorFCM, Long eventId, Long authorId);
}
