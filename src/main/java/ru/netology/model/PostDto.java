package ru.netology.model;

import java.util.Optional;

public class PostDto {
    private long id;
    private String content;

    public PostDto(Post post) {
        this.id = post.getId();
        this.content = post.getContent();
    }

    public PostDto (Optional<Post> postOptional) {
        this.id = postOptional.get().getId();
        this.content = postOptional.get().getContent();
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }
}
