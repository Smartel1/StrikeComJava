package ru.smartel.strike.service.url;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import ru.smartel.strike.entity.Event;
import ru.smartel.strike.entity.News;
import ru.smartel.strike.entity.interfaces.PostEntity;

@Service
public class UrlService {
   private final String siteUrl;

    public UrlService(@Value("site-url") String siteUrl) {
        this.siteUrl = siteUrl;
    }

    public String getUrl(PostEntity postEntity) {
        if (postEntity.getClass() == Event.class) {
            return siteUrl + "events/" + postEntity.getId();
        }
        if (postEntity.getClass() == News.class) {
            return siteUrl + "news/" + postEntity.getId();
        }
        throw new IllegalArgumentException("Unknown post type");
    }
}
