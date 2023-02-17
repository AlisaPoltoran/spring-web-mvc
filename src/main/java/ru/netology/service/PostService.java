package ru.netology.service;

import org.springframework.stereotype.Service;
import ru.netology.exception.NotFoundException;
import ru.netology.model.Post;
import ru.netology.model.PostDto;
import ru.netology.repository.PostRepository;
import ru.netology.repository.PostRepositoryImpl;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class PostService {
    private final PostRepository repository;

    public PostService(PostRepository repository) {
        this.repository = repository;
    }

    public List<PostDto> all() {
        if (repository.all().isEmpty()) {
            throw new NotFoundException();
        }
        return repository.all().stream()
                .filter(post -> !post.isDeleted())
                .map(PostDto::new)
                .collect(Collectors.toList());
    }

    public PostDto getById(long id) {
        Post tempPost = repository.getById(id).get();
        if (tempPost.isDeleted()) {
            throw new NotFoundException();
        }
        return new PostDto(tempPost);
    }

    public Post save(Post post) {
        return repository.save(post);
    }

    public void removeById(long id) {
        repository.removeById(id);
    }
}

