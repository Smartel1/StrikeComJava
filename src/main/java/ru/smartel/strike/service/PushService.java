package ru.smartel.strike.service;

import java.util.Map;

public interface PushService {

    /**
     * News is created by user. Notifies moderators about it
     * todo generify this method and merge with eventCreatedByUser()
     */
    void newsCreatedByUser(Integer newsId, Integer authorId, String authorName);

    /**
     * Event is created by user. Notifies moderators about it
     */
    void eventCreatedByUser(Integer eventId, Integer authorId, String authorName);

    /**
     * News has been published. Notifies all clients and news' author (if notifyAuthor eq true and fcm is not null)
     * todo generify this method and merge with eventPublished()
     */
    void newsPublished(
            Integer newsId,
            Integer authorId,
            Map<String, Locale> titlesByLocales,
            String authorFCM,
            boolean notifyAuthor
    );

    /**
     * Event has been published. Notifies all clients and event's author (if notifyAuthor eq true and fcm is not null)
     */
    void eventPublished(
            Integer eventId,
            Integer authorId,
            Float lng, Float lat,
            Map<String, Locale> titlesByLocales,
            String authorFCM,
            boolean notifyAuthor
    );

    /**
     * Notify author of news that moderator declined the latter
     */
    void newsDeclined(String authorFCM, Integer newsId, Integer authorId);

    /**
     * Notify author of event that moderator declined the latter
     */
    void eventDeclined(String authorFCM, Integer eventId, Integer authorId);
}
