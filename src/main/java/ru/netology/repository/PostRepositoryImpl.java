package ru.netology.repository;

import org.springframework.stereotype.Repository;
import ru.netology.exception.NotFoundException;
import ru.netology.model.Post;

import java.util.*;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicLong;
import java.util.stream.Collectors;

// Stub
@Repository
public class PostRepositoryImpl implements PostRepository {

    private final AtomicLong counter = new AtomicLong(0);

    private final Map<Long, Post> posts = new ConcurrentHashMap<>();

    public List<Post> all() {
        return new ArrayList<>(posts.values());
    }

    public Optional<Post> getById(long id) {
        return Optional.of(posts.get(id));
    }

    public Post save(Post post) {
        Post tempPost;
        if (post.getId() == 0) {
            counter.addAndGet(1);
            posts.put(counter.get(), new Post(counter.get(), post.getContent(), false));
            tempPost = posts.get(counter.get());
        } else if (posts.containsKey(post.getId()) && !posts.get(post.getId()).isDeleted()) {
            posts.replace(post.getId(), posts.get(post.getId()), post);
            tempPost = post;
        } else {
            throw new NotFoundException();
        }
        return tempPost;
    }

    public void removeById(long id) {
        if (posts.containsKey(id) && !posts.get(id).isDeleted()) {
            posts.get(id).setDeleted(true);
        } else {
            throw new NotFoundException();
        }
    }
}
