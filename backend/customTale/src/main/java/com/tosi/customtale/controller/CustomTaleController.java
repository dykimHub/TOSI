package com.tosi.customtale.controller;

import com.tosi.customtale.common.exception.SuccessResponse;
import com.tosi.customtale.dto.*;
import com.tosi.customtale.service.CreateCustomTaleService;
import com.tosi.customtale.service.CustomTaleService;
import io.swagger.v3.oas.annotations.Operation;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Slf4j
@RequestMapping("/api/custom-tales")
@RequiredArgsConstructor
@RestController
public class CustomTaleController {
    private final CustomTaleService customTaleService;
    private final CreateCustomTaleService createCustomTaleService;

    @Operation(summary = "커스텀 동화 생성 요청")
    @PostMapping
    public ResponseEntity<CustomTaleResponseDto> createCustomTale(@RequestHeader("Authorization") String accessToken, @RequestBody CustomTaleRequestDto customTaleRequestDto) {
        Long userId = customTaleService.findUserAuthorization(accessToken);
        CustomTaleResponseDto customTaleResponseDto = createCustomTaleService.createCustomTale(userId, customTaleRequestDto);
        return ResponseEntity.ok()
                .body(customTaleResponseDto);
    }

    @Operation(summary = "커스텀 동화 각 페이지 생성")
    @PostMapping("/read")
    public ResponseEntity<List<TalePageResponseDto>> createCustomTalePages(@RequestBody CustomTaleResponseDto customTaleResponseDto) {
        List<TalePageResponseDto> talePageResponseDtoList = customTaleService.createCustomTalePages(customTaleResponseDto);
        return ResponseEntity.ok()
                .body(talePageResponseDtoList);
    }

    @Operation(summary = "내가 만든 동화 목록")
    @GetMapping
    public ResponseEntity<List<CustomTaleDto>> findCustomTales(@RequestHeader("Authorization") String accessToken,
                                                               @PageableDefault(size = 9, sort = "regDate", direction = Sort.Direction.DESC) Pageable pageable) {
        Long userId = customTaleService.findUserAuthorization(accessToken);
        List<CustomTaleDto> customTaleDtoList = customTaleService.findCustomTaleList(userId, pageable);
        return ResponseEntity.ok()
                .body(customTaleDtoList);
    }

    @Operation(summary = "공개중인 커스텀 동화 목록")
    @GetMapping("/public")
    public ResponseEntity<List<CustomTaleDto>> findPublicCustomTales(@PageableDefault(size = 9, sort = "regDate", direction = Sort.Direction.DESC) Pageable pageable) {
        List<CustomTaleDto> customTaleDtoList = customTaleService.findPublicCustomTaleList(pageable);
        return ResponseEntity.ok()
                .body(customTaleDtoList);
    }

    @Operation(summary = "커스텀 동화 저장")
    @PostMapping("/save")
    public ResponseEntity<SuccessResponse> addCustomTale(@RequestHeader("Authorization") String accessToken, @RequestBody CustomTaleDetailDto customTaleDetailDto) {
        Long userId = customTaleService.findUserAuthorization(accessToken);
        SuccessResponse successResponse = customTaleService.addCustomTale(userId, customTaleDetailDto);
        return ResponseEntity.ok()
                .body(successResponse);
    }


//    @Operation(summary="내가 만든 동화 삭제")
//    @DeleteMapping("/customtale/{customTaleId}")
//    public ResponseEntity<?> deleteCustomTale(HttpServletRequest request, @PathVariable Integer customTaleId) {
//        customTaleService.deleteCustomTale(customTaleId);
//        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
//    }

}