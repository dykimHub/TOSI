package com.ssafy.tosi.s3;

import lombok.RequiredArgsConstructor;
import org.apache.tomcat.util.http.fileupload.FileItem;
import org.apache.tomcat.util.http.fileupload.IOUtils;
import org.apache.tomcat.util.http.fileupload.disk.DiskFileItem;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.*;
import java.nio.file.Files;

@RestController
@RequiredArgsConstructor
//@CrossOrigin("*")
public class S3Controller {
    private final S3Service s3UploadService;

    @GetMapping("/tales/s3/download/{fileName}")
    public ResponseEntity<?> downloadFile(@PathVariable String fileName) {
        try {
            String filePath = s3UploadService.getPath(fileName);
            return new ResponseEntity<String>(filePath, HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<String>(e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        }

    }

//    @PostMapping("/tales/s3/upload")
//    public ResponseEntity<?> uploadFile(@RequestParam("file") MultipartFile file, @RequestParam("fileName") String fileName) {
//        try {
//            String uploadUrl = s3UploadService.uploadFile(file, fileName);
//            return new ResponseEntity<String>(uploadUrl, HttpStatus.OK);
//        } catch (IOException e) {
//            return new ResponseEntity<String>(e.getMessage(),HttpStatus.INTERNAL_SERVER_ERROR);
//        } catch (Exception e) {
//            return new ResponseEntity<String>(e.getMessage(),HttpStatus.INTERNAL_SERVER_ERROR);
//        }
//    }

    @PostMapping("/tales/s3/upload")
    public String uploadImageToS3(@RequestBody String imageUrl) {
        // 이미지 다운로드 및 S3 업로드 로직
        return s3UploadService.uploadImageFromUrl(imageUrl);
        }

}
