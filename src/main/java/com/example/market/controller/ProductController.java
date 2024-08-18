package com.example.market.controller;

import com.example.market.dto.FileDto;
import com.example.market.service.ProductService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;

@Controller
@RequestMapping("/product")
@RequiredArgsConstructor
public class ProductController {
    private final ProductService productService;

    // 상품 목록 페이지
    @GetMapping
    public String listPage(Model model) {
        List<FileDto> products = productService.getAllProducts();
        model.addAttribute("products", products);
        return "products/list";
    }

    // 상품 상세 페이지
    @GetMapping("/{id}")
    public String viewPage(@PathVariable Long id, Model model) {
        FileDto product = productService.getProductById(id);
        model.addAttribute("product", product);
        return "products/detail";
    }

    // 상품 등록 페이지
    @GetMapping("/upload")
    public String uploadPage(Model model) {
        model.addAttribute("fileDto", new FileDto());
        return "products/upload";
    }

    @PostMapping("/upload")
    public String upload(@ModelAttribute FileDto fileDto, @RequestParam("image") MultipartFile image) {
        try {
            if (!image.isEmpty()) {
                String imageUrl = saveImageFile(image, fileDto.getId());
                fileDto.setImageUrl(imageUrl);
            }
        } catch (IOException e) {
            throw new RuntimeException("파일 저장 중 오류 발생", e);
        }
//        fileDto.setImage(image);
        productService.addProduct(fileDto);
        return "redirect:/product";
    }

    // 상품 수정 페이지
    @GetMapping("/edit/{id}")
    public String editPage(@PathVariable Long id, Model model) {
        FileDto product = productService.getProductById(id);
        model.addAttribute("product", product);
        return "products/edit";
    }

    @PostMapping("/edit")
    public String edit(@ModelAttribute FileDto fileDto, @RequestParam("image") MultipartFile image) {
        try {
            if (!image.isEmpty()) {
                String imageUrl = saveImageFile(image, fileDto.getId());
                fileDto.setImageUrl(imageUrl);
            }
        } catch (IOException e) {
            throw new RuntimeException("파일 저장 중 오류 발생", e);
        }
//        fileDto.setImage(image);
        productService.updateProduct(fileDto);
        return "redirect:/product/" + fileDto.getId();
    }

    private String saveImageFile(MultipartFile file, Long fileId) throws IOException {
        String UPLOAD_DIR = "src/main/resources/static/img";
        String fileName = fileId + "_" + file.getOriginalFilename();
        Path path = Paths.get(UPLOAD_DIR, fileName);
        Files.write(path, file.getBytes());
        return "/img/" + fileName;
    }

//    private String saveImageFile(MultipartFile file) {
//        // 파일이 저장될 디렉토리
//        String UPLOAD_DIR = "src/main/resources/static/img/";
//        // 파일 이름 생성
//        String fileName = System.currentTimeMillis() + "_" + file.getOriginalFilename();
//        // 저장할 파일 경로
//        Path path = Paths.get(UPLOAD_DIR + fileName);
//
//        try {
//            // 이미지 파일을 서버에 저장
//            Files.write(path, file.getBytes());
//            // 저장된 파일의 URL을 반환
//            return "/img/" + fileName;
//        } catch (IOException e) {
//            e.printStackTrace();
//            throw new RuntimeException("파일 저장 중 오류 발생", e);
//        }
//    }
}
