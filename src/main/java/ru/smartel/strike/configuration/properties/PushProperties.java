package ru.smartel.strike.configuration.properties;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;
import org.springframework.validation.annotation.Validated;

import javax.validation.Valid;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

@Component
@ConfigurationProperties(prefix = "push")
@Validated
public class PushProperties {
    @NotNull
    private Boolean enabled;
    @Valid
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
        @NotBlank
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
        @NotBlank
        private String ru;
        @NotBlank
        private String en;
        @NotBlank
        private String es;
        @NotBlank
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
