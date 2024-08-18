package com.example.market.service;

import com.example.market.dao.FileDao;
import com.example.market.dao.ImageDao;
import com.example.market.dao.ProductDao;
import com.example.market.dto.FileDto;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;

@Service
@RequiredArgsConstructor
public class ProductServiceImpl implements ProductService {
    private final FileDao fileDao;
    private final ImageDao imageDao;
    private final FileService fileService;
    private final ProductDao productDao;

    @Override
    public List<FileDto> getAllProducts() {
        return fileDao.getAllFiles();
    }

    @Override
    public FileDto getProductById(Long id) {
        return fileDao.getFileById(id);
    }

    @Override
    public void addProduct(FileDto fileDto) {

        // 이미지 업로드 및 URL 설정
        MultipartFile image = fileDto.getImage();
        if (image != null && !image.isEmpty()) {
            try {
                String imageUrl = saveImageFile(image, fileDto.getId());
                fileDto.setImageUrl(imageUrl);
//                fileDao.updateFile(fileDto);
            } catch (IOException e) {
                throw new RuntimeException("파일 저장 중 오류 발생", e);
            }
        }
        // 상품 등록
        fileDao.insertFile(fileDto);
//        fileService.addFile(fileDto); // FileService를 사용하여 파일 추가 
    }

    @Override
    public void updateProduct(FileDto fileDto) {

        MultipartFile image = fileDto.getImage();
        if (image != null && !image.isEmpty()) {
            try {
                String imageUrl = saveImageFile(image, fileDto.getId());
                fileDto.setImageUrl(imageUrl);
//                fileDao.updateFile(fileDto);  // 이미지 URL 업데이트
            } catch (IOException e) {
                throw new RuntimeException("파일 저장 중 오류 발생", e);
            }
        }
        fileDao.updateFile(fileDto);
//        fileService.updateFile(fileDto); // FileService를 사용하여 파일 업데이트
    }

    private String saveImageFile(MultipartFile file, Long fileId) throws IOException {
        String UPLOAD_DIR = "src/main/resources/static/img";
        String fileName = fileId + "_" + file.getOriginalFilename();
        Path path = Paths.get(UPLOAD_DIR + fileName);

        Files.write(path, file.getBytes());

        return "/img/" + fileName;
    }

    @Override
    public List<FileDto> getLatestProducts() {
        List<FileDto> latestProducts = imageDao.findTop5ByOrderByCreatedDateDesc();
        System.out.println("Latest Products: " + latestProducts); // 디버깅 로그 추가
        return latestProducts;
    }

//    @Override
//    public List<FileDto> searchProductsByKeyword(String keyword, int size, int offset) {
//        List<ProductDto> products = productDao.searchProducts(keyword, size, offset);
//        return DtoConverter.convertToFileDtoList(products);
//    }
//
//    @Override
//    public int getTotalProductCountByKeyword(String keyword) {
//        return productDao.countProducts(keyword);
//    }
}
