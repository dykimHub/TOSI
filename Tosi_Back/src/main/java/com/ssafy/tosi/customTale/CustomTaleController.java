package com.ssafy.tosi.customTale;

import com.ssafy.tosi.s3.S3Controller;
import io.swagger.v3.oas.annotations.Operation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@CrossOrigin("*")
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
    public ResponseEntity<?> getCustomTale(@PathVariable Long customTaleId) {
            Optional<CustomTale> customTale = customTaleService.getCustomTale(customTaleId);
            return ResponseEntity.ok(customTale);
    }

    @Operation(summary="내가 만든 동화 목록")
    @GetMapping("/customtale/user/{userId}")
    public ResponseEntity<?> getCustomTalesByUserId(@PathVariable Long userId) {
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
    public ResponseEntity<?> insertCustomTale(@RequestBody CustomTale customTale) {
        System.out.println(customTale.getThumbnail());
            customTale.setThumbnail("https://talebucket.s3.ap-northeast-2.amazonaws.com/"+s3Controller.uploadImageToS3(customTale.getThumbnail()));
        System.out.println(customTale.getThumbnail());
        CustomTale savedCustomTale = customTaleService.postCustomTale(customTale);
            return new ResponseEntity<>(savedCustomTale, HttpStatus.CREATED);

    }

    @Operation(summary="내가 만든 동화 공개여부 수정")
    @PutMapping("/customtale/{customTaleId}")
    public ResponseEntity<?> updateCustomTale(@PathVariable Long customTaleId, @RequestParam boolean isPublic) {
            CustomTale updatedCustomTale = customTaleService.putCustomTale(customTaleId, isPublic);
            return ResponseEntity.ok(updatedCustomTale);

    }

    @Operation(summary="내가 만든 동화 삭제")
    @DeleteMapping("/customtale/{customTaleId}")
    public ResponseEntity<?> deleteCustomTale(@PathVariable Long customTaleId) {
        customTaleService.deleteCustomTale(customTaleId);
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }
}