package com.example.market.service;

import com.example.market.com.Search;
import com.example.market.dao.PostDao;
import com.example.market.dto.PostDto;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class PostServiceImpl implements PostService {
    private final PostDao postDao;

    @Override
    public List<PostDto> getAllPosts() {
        return postDao.selectAllPosts();
    }

    @Override
    public PostDto getPostById(Long id) {
        PostDto post = postDao.selectPostById(id);
        if (post != null) {
            postDao.incrementHitCount(id);
        }
        return post;
    }

    @Override
    public void createPost(PostDto postDto) {
        postDao.insertPost(postDto);
    }

    @Override
    public void updatePost(PostDto postDto) {
        postDao.updatePost(postDto);
    }

    @Override
    public void deletePost(Long id) {
        postDao.deletePost(id);
    }

    @Override
    public void incrementHitCount(Long id) {
        postDao.incrementHitCount(id);
    }

    @Override
    public Page<PostDto> searchPosts(Search search) {
        int limit = search.getRecordSize();
        int offset = search.getOffset();
        List<PostDto> posts = postDao.searchPosts(search.getKeyword(), limit, offset);
        int totalPosts = postDao.countPosts(search.getKeyword());
        search.calcPage(totalPosts);
        return new PageImpl<>(posts, PageRequest.of(search.getPage() - 1, search.getRecordSize()), totalPosts);
    }

    @Override
    public int getTotalPostCountByKeyword(String keyword) {
        return postDao.getTotalPostCountByKeyword(keyword);
    }
}
