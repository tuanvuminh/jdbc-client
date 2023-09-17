package com.backend.jdbcclient.controller;

import com.backend.jdbcclient.exception.PostException;
import com.backend.jdbcclient.model.Post;
import com.backend.jdbcclient.service.PostService;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/posts")
public class PostController {

    private final PostService postService;

    public PostController(@Qualifier("clientPostService") PostService postService) {
        this.postService = postService;
    }

    @GetMapping
    List<Post> findAll() {
        return postService.findAll();
    }

    @GetMapping("/{id}")
    Post findById(@PathVariable String id) throws PostException {
        Optional<Post> post = postService.findById(id);
        return post.orElseThrow(() -> new PostException("Post [ID: " + id + "] was not found."));
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public void create(@RequestBody Post post) {
        postService.create(post);
    }


    @PutMapping("/{id}")
    void update(@RequestBody Post post, @PathVariable String id) {
        postService.update(post, id);
    }

    @DeleteMapping("/{id}")
    void delete(@PathVariable String id) {
        postService.delete(id);
    }
}
