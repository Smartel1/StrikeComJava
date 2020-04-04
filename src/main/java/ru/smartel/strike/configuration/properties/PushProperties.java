package ru.smartel.strike.configuration.properties;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

@Component
@ConfigurationProperties(prefix = "push")
public class PushProperties {
    private Boolean enabled;
    private Topics topics;

    public Boolean getEnabled() {
        return enabled;
    }

    public void setEnabled(Boolean enabled) {
        this.enabled = enabled;
    }

    public Topics getTopics() {
        return topics;
    }

    public void setTopics(Topics topics) {
        this.topics = topics;
    }

    public static class Topics {
        private String admin;
        private LocaleSet news;
        private LocaleSet events;

        public String getAdmin() {
            return admin;
        }

        public void setAdmin(String admin) {
            this.admin = admin;
        }

        public LocaleSet getNews() {
            return news;
        }

        public void setNews(LocaleSet news) {
            this.news = news;
        }

        public LocaleSet getEvents() {
            return events;
        }

        public void setEvents(LocaleSet events) {
            this.events = events;
        }
    }

    public static class LocaleSet {
        private String ru;
        private String en;
        private String es;
        private String de;

        public String getRu() {
            return ru;
        }

        public void setRu(String ru) {
            this.ru = ru;
        }

        public String getEn() {
            return en;
        }

        public void setEn(String en) {
            this.en = en;
        }

        public String getEs() {
            return es;
        }

        public void setEs(String es) {
            this.es = es;
        }

        public String getDe() {
            return de;
        }

        public void setDe(String de) {
            this.de = de;
        }
    }
}
