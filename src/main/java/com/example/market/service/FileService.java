package com.example.market.service;

import com.example.market.dao.FileDao;
import com.example.market.dao.ImageDao;
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
public class FileService {
    private final FileDao fileDao;
    private final ImageDao imageDao;

    public void addFile(FileDto fileDto) {
        // Save file metadata
        fileDao.insertFile(fileDto);

        // 파일 ID를 가져오기 위해 메타데이터를 다시 조회
//        Long fileId = fileDto.getId();

        // 이미지 파일 저장
        MultipartFile image = fileDto.getImage();
        if (image != null && !image.isEmpty()) {
            try {
                String imageUrl = saveImageFile(image, fileDto.getId());
                fileDto.setImageUrl(imageUrl);
                fileDao.updateFile(fileDto);
            } catch (IOException e) {
                throw new RuntimeException("파일 저장 중 오류 발생", e);
            }
        }
    }

    // 파일 업데이트
    public void updateFile(FileDto fileDto) {
        fileDao.updateFile(fileDto);

        // 새 이미지가 있는 경우, 새 이미지 저장
        MultipartFile image = fileDto.getImage();
        if (image != null && !image.isEmpty()) {
            try {
                String imageUrl = saveImageFile(image, fileDto.getId());
                fileDto.setImageUrl(imageUrl);
                fileDao.updateFile(fileDto);
            } catch (IOException e) {
                throw new RuntimeException("파일 저장 중 오류 발생", e);
            }
        }
    }

    private String saveImageFile(MultipartFile file, Long fileId)  throws IOException {
        // 파일이 저장될 디렉토리
        String UPLOAD_DIR = "src/main/resources/static/img";
        // 파일 이름 생성
        String fileName = fileId + "_" + file.getOriginalFilename();
        // 저장할 파일 경로
        Path path = Paths.get(UPLOAD_DIR + fileName);

        // 이미지 파일을 서버에 저장
        Files.write(path, file.getBytes());

        // 저장된 파일의 URL을 반환
        return "/img/" + fileName;
    }

    public List<FileDto> getAllFiles() {
        return fileDao.getAllFiles();
    }

    public FileDto getFileById(Long id) {
        return fileDao.getFileById(id);
    }
    
}
