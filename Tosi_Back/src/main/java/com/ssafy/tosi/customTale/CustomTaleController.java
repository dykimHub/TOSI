package com.ssafy.tosi.customTale;

import com.ssafy.tosi.s3.S3Controller;
import com.ssafy.tosi.taleDetail.Page;
import io.swagger.v3.oas.annotations.Operation;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.util.List;
import java.util.Optional;

@RestController
public class CustomTaleController {

    private final CustomTaleService customTaleService;
    private final S3Controller s3Controller;

    @Autowired
    public CustomTaleController(CustomTaleService customTaleService,S3Controller s3Controller) {
        this.customTaleService = customTaleService;
        this.s3Controller = s3Controller;
    }

    @Operation(summary="커스텀 동화 상세조회")
    @GetMapping("/customtale/{customTaleId}")
    public ResponseEntity<?> getCustomTale(@PathVariable Integer customTaleId) {
            Optional<CustomTale> customTale = customTaleService.getCustomTale(customTaleId);
            return ResponseEntity.ok(customTale);
    }

    @Operation(summary="내가 만든 동화 목록")
    @GetMapping("/customtale/user")
    public ResponseEntity<?> getCustomTalesByUserId(HttpServletRequest request, HttpServletResponse response) {
        Integer userId = (Integer) request.getAttribute("userId");
        List<CustomTale> customTales = customTaleService.getCustomTalesByUserId(userId);
        return ResponseEntity.ok(customTales);
    }

    @Operation(summary="공개중인 커스텀 동화 목록")
    @GetMapping("/customtale")
    public ResponseEntity<?> getCustomTales() {
            List<CustomTale> customTales = customTaleService.getCustomTales();
            return ResponseEntity.ok(customTales);

    }

    @Operation(summary="내가 만든 동화 저장")
    @PostMapping("/customtale")
    public ResponseEntity<?> insertCustomTale(HttpServletRequest request, HttpServletResponse response, @RequestBody CustomTale customTale) {
        Integer userId = (Integer) request.getAttribute("userId");
        customTale.setUserId(userId);
        System.out.println(customTale.getThumbnail());
            customTale.setThumbnail("https://talebucket.s3.ap-northeast-2.amazonaws.com/"+s3Controller.uploadImageToS3(customTale.getThumbnail()));
        System.out.println(customTale.getThumbnail());
        CustomTale savedCustomTale = customTaleService.postCustomTale(customTale);
            return new ResponseEntity<>(savedCustomTale, HttpStatus.CREATED);

    }

    @Operation(summary="내가 만든 동화 공개여부 수정")
    @PutMapping("/customtale/{customTaleId}")
    public ResponseEntity<?> updateCustomTale(@PathVariable Integer customTaleId, @RequestParam boolean isPublic) {
            CustomTale updatedCustomTale = customTaleService.putCustomTale(customTaleId, isPublic);
            return ResponseEntity.ok(updatedCustomTale);

    }

    @Operation(summary="내가 만든 동화 삭제")
    @DeleteMapping("/customtale/{customTaleId}")
    public ResponseEntity<?> deleteCustomTale(@PathVariable Integer customTaleId) {
        customTaleService.deleteCustomTale(customTaleId);
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    @PostMapping("/customtale/read")
    public ResponseEntity<?> read(@RequestBody String string) {
        try {
            String splitted_contents = customTaleService.split_sentences(string); // 문장 분리
            HttpHeaders headers = new HttpHeaders();
            headers.setContentType(MediaType.TEXT_PLAIN);
            headers.set(HttpHeaders.CONTENT_TYPE, "text/plain;charset=UTF-8"); // 인코딩 설정
            return new ResponseEntity<String>(splitted_contents, headers, HttpStatus.OK);
        } catch (IOException e) {
            return new ResponseEntity<String>(e.getMessage(), HttpStatus.BAD_REQUEST);
        } catch (Exception e) {
            return new ResponseEntity<String>(e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        }

    }
}