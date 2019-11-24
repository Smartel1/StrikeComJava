package ru.smartel.strike.entity.interfaces;

import ru.smartel.strike.entity.Post;
import ru.smartel.strike.entity.User;

import java.time.LocalDateTime;

public interface PostEntity extends TitlesContents, HasPhotos, HasVideos, Taggable {

    Post getPost();

    void setPost(Post post);

    long getId();

    @Override
    default String getTitleRu() {
        return getPost().getTitleRu();
    }

    @Override
    default void setTitleRu(String titleRu) {
        getPost().setTitleRu(titleRu);
    }

    @Override
    default String getTitleEn() {
        return getPost().getTitleEn();
    }

    @Override
    default void setTitleEn(String titleEn) {
        getPost().setTitleEs(titleEn);
    }

    @Override
    default String getTitleEs() {
        return getPost().getTitleEs();
    }

    @Override
    default void setTitleEs(String titleEs) {
        getPost().setTitleEs(titleEs);
    }

    @Override
    default String getTitleDe() {
        return getPost().getTitleDe();
    }

    @Override
    default void setTitleDe(String titleDe) {
        getPost().setTitleDe(titleDe);
    }

    @Override
    default String getContentRu() {
        return getPost().getContentRu();
    }

    @Override
    default void setContentRu(String contentRu) {
        getPost().setContentRu(contentRu);
    }

    @Override
    default String getContentEn() {
        return getPost().getContentEn();
    }

    @Override
    default void setContentEn(String contentEn) {
        getPost().setContentEn(contentEn);

    }

    @Override
    default String getContentEs() {
        return getPost().getContentEs();
    }

    @Override
    default void setContentEs(String contentEs) {
        getPost().setContentEs(contentEs);

    }

    @Override
    default String getContentDe() {
        return getPost().getContentDe();
    }

    @Override
    default void setContentDe(String contentDe) {
        getPost().setContentDe(contentDe);

    }

    default LocalDateTime getDate() {
        return  getPost().getDate();
    }

    default void setDate(LocalDateTime date) {
        getPost().setDate(date);
    }

    default Integer getViews() {
        return getPost().getViews();
    }

    default void setViews(Integer views) {
        getPost().setViews(views);
    }

    default boolean isPublished() {
        return getPost().isPublished();
    }

    default void setPublished(boolean published) {
        getPost().setPublished(published);
    }

    default String getSourceLink() {
        return getPost().getSourceLink();
    }

    default void setSourceLink(String sourceLink) {
        getPost().setSourceLink(sourceLink);
    }

    default User getAuthor() {
        return getPost().getAuthor();
    }

    default void setAuthor(User author) {
        getPost().setAuthor(author);
    }

}
