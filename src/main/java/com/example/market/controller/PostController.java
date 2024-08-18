package com.example.market.controller;

import com.example.market.com.Search;
import com.example.market.dto.PostDto;
import com.example.market.service.PostService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.List;

@Controller
@RequestMapping("/post")
@RequiredArgsConstructor
public class PostController {
    private final PostService postService;
    //private final CommentService commentService;

    @GetMapping("/search")
    public String searchPosts(@RequestParam(value = "keyword", defaultValue = "") String keyword,
                              @RequestParam(value = "page", defaultValue = "1") int page,
                              @RequestParam(value = "size", defaultValue = "10") int size,
                              Model model) {
        Search search = new Search(size, 5); // 페이지당 10개, 페이지 리스트 5개
        search.setPage(page);
        search.setKeyword(keyword);

        int totalDataCount = postService.getTotalPostCountByKeyword(keyword); // Implement this method in your service
        search.calcPage(totalDataCount);

        Page<PostDto> postPage = postService.searchPosts(search);
        model.addAttribute("posts", postPage.getContent());
        model.addAttribute("search", search);
        System.out.println("Search object: " + search); // 디버깅용 로그
        return "posts/overview";
    }

    // 게시글 목록 페이지
    @GetMapping
    public String overview(@RequestParam(value = "page", defaultValue = "1") int page,
                           @RequestParam(value = "size", defaultValue = "10") int size,
                           Model model) {
        Search search = new Search(size, 5);
        search.setPage(page);

        int totalDataCount = postService.getTotalPostCountByKeyword("");  // 전체 게시글 수
        search.calcPage(totalDataCount);

        List<PostDto> posts = postService.getAllPosts();
        model.addAttribute("posts", posts);
        model.addAttribute("search", search);
        return "posts/overview";
    }

    // 게시글 상세 페이지
    @GetMapping("/{id}")
    public String details(@PathVariable Long id, Model model) {
        PostDto post = postService.getPostById(id);
        if (post == null) {
            return "redirect:/post";
        }
        model.addAttribute("post", post);
        return "posts/details";
    }

    // 게시글 작성 폼 페이지
    @GetMapping("/create")
    public String createForm(Model model) {
//        if (user == null) {
//            return "redirect:/login";
//        }
        model.addAttribute("postDto", new PostDto());
        return "posts/create";
    }

    // 게시글 등록 처리
    @PostMapping("/create")
    public String create(@ModelAttribute PostDto postDto, RedirectAttributes redirectAttributes) {
        // Set a dummy author for now or remove this logic if not needed
//        if (user == null) {
//            return "redirect:/login";
//        }
//        postDto.setAuthor(user.getUsername());
        postService.createPost(postDto);
        redirectAttributes.addFlashAttribute("message", "게시글이 등록되었습니다.");
        return "redirect:/post";
    }

    // 게시글 수정 폼 페이지
    @GetMapping("/update/{id}")
    public String updateForm(@PathVariable Long id, Model model) {
        PostDto post = postService.getPostById(id);
        if (post == null) {
            // Optional: Handle post not found
            return "redirect:/post";
        }
        model.addAttribute("postDto", post);
        return "posts/update";
    }

    // 게시글 수정 처리
    @PostMapping("/update")
    public String update(@ModelAttribute PostDto postDto, RedirectAttributes redirectAttributes) {
        // Update the post
        postService.updatePost(postDto);
        redirectAttributes.addFlashAttribute("message", "게시글이 수정되었습니다.");
        return "redirect:/post/" + postDto.getId();
    }

    // 게시글 삭제 처리
    @PostMapping("/delete/{id}")
    public String deletePost(@PathVariable Long id, RedirectAttributes redirectAttributes) {
        postService.deletePost(id);
        redirectAttributes.addFlashAttribute("message", "게시글이 삭제되었습니다.");
        return "redirect:/post";
    }

    // 게시글 조회수 증가 처리
    @PostMapping("/increment-hit/{id}")
    public String incrementHitCount(@PathVariable Long id) {
        postService.incrementHitCount(id);
        return "redirect:/post/" + id;
    }
}
