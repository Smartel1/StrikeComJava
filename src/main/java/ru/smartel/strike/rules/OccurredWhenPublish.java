package ru.smartel.strike.rules;

import ru.smartel.strike.entity.interfaces.PostEntity;

import java.time.LocalDateTime;

public class OccurredWhenPublish extends BusinessRule {
    private PostEntity entity;

    public OccurredWhenPublish(PostEntity entity) {
        this.entity = entity;
    }

    @Override
    public boolean passes() {
        if (!entity.isPublished()) {
            return true;
        }
        if (entity.getDate() == null) {
            throw new IllegalStateException("Post date is mandatory");
        }
        return LocalDateTime.now().compareTo(entity.getDate()) >= 0;
    }

    @Override
    public String message() {
        return "Published post must happen in the past";
    }
}
