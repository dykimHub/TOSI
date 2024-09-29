package com.tosi.customtale.service;

import com.tosi.customtale.common.exception.CustomException;
import com.tosi.customtale.common.exception.ExceptionCode;
import com.tosi.customtale.common.exception.SuccessResponse;
import com.tosi.customtale.dto.CustomTaleResponseDto;
import com.tosi.customtale.dto.CustomTaleDetailDto;
import com.tosi.customtale.dto.CustomTaleDto;
import com.tosi.customtale.dto.TalePageResponseDto;
import com.tosi.customtale.entity.CustomTale;
import com.tosi.customtale.repository.CustomTaleRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.ArrayList;
import java.util.List;

@RequiredArgsConstructor
@Service
public class CustomTaleServiceImpl implements CustomTaleService {
    private final S3Service s3Service;
    private final CustomTaleRepository customTaleRepository;
    private final RestTemplate restTemplate;
    @Value("${service.user.url}")
    private String userURL;

    /**
     * 해당 회원이 생성한 커스텀 동화 목록을 반환합니다.
     *
     * @param userId 로그인한 회원 번호
     * @param pageable 페이지 번호, 페이지 크기, 정렬 기준 및 방향을 담고 있는 Pageable 객체
     * @return CustomTaleDto 객체 리스트
     */
    @Override
    public List<CustomTaleDto> findCustomTaleList(Long userId, Pageable pageable) {
        return customTaleRepository.findCustomTaleListByUserId(userId, pageable);
    }

    /**
     * 공개중인 커스텀 동화 목록을 반환합니다.
     *
     * @param pageable 페이지 번호, 페이지 크기, 정렬 기준 및 방향을 담고 있는 Pageable 객체
     * @return CustomTaleDto 객체 리스트
     */
    @Override
    public List<CustomTaleDto> findPublicCustomTaleList(Pageable pageable) {
        return customTaleRepository.findPublicCustomTaleList(pageable);
    }

    /**
     * 생성된 커스텀 동화와 관련 정보를 저장합니다.
     *
     * @param userId              회원 번호
     * @param customTaleDetailDto 커스텀 동화, 제목, 공개 여부 등(내용 제외) 담긴 CustomTaleDto 객체
     * @return 커스텀 동화 저장에 성공하면 SuccessResponse 객체 반환
     */
    @Transactional
    @Override
    public SuccessResponse addCustomTale(Long userId, CustomTaleDetailDto customTaleDetailDto) {
        CustomTale customTale = CustomTale.of(customTaleDetailDto);
        customTaleRepository.save(customTale);

        return SuccessResponse.of("커스텀 동화 저장에 성공하였습니다.");
    }

    /**
     * 커스텀 동화 페이지를 생성합니다.
     * 왼쪽 페이지는 삽화, 오른쪽 페이지는 동화 본문을 2문장씩 삽입합니다.
     *
     * @param customTaleResponseDto 커스텀 동화 내용과 이미지 S3 Key가 담긴 객체
     * @return TalePageResponse 객체 리스트
     */
    @Override
    public List<TalePageResponseDto> createCustomTalePages(CustomTaleResponseDto customTaleResponseDto) {
        String[] lines = customTaleResponseDto.getCustomTale().split("\n");
        String imageS3URL = s3Service.findS3URL(customTaleResponseDto.getCustomImageS3Key());

        int pageNum = 1;
        List<TalePageResponseDto> talePageResponseDtoList = new ArrayList<>();

        for (int i = 0; i < lines.length; i += 2) {
            String line1 = lines[i];
            // line1이 마지막 문장이면 다음 문장은 빈 문장
            String line2 = (i + 1 < lines.length) ? lines[i + 1] : "";

            talePageResponseDtoList.add(
                    TalePageResponseDto.builder()
                            .leftNo(pageNum++)
                            .left(imageS3URL)
                            .rightNo(pageNum++)
                            .right(line1 + "\n" + line2)
                            .build()
            );
        }

        return talePageResponseDtoList;
    }

    /**
     * 회원 서비스로 토큰을 보내고 인증이 완료되면 회원 번호를 반환합니다.
     *
     * @param accessToken 로그인한 회원의 토큰
     * @return 회원 번호
     * @throws CustomException 인증에 성공하지 못하면 예외 처리
     */
    @Override
    public Long findUserAuthorization(String accessToken) {
        HttpHeaders headers = new HttpHeaders();
        headers.set("Authorization", accessToken);
        HttpEntity<String> httpEntity = new HttpEntity<>(headers);
        try {
            Long userId = restTemplate.exchange(userURL + "auth",
                    HttpMethod.GET, httpEntity, Long.class).getBody();
            return userId;
        } catch (Exception e) {
            throw new CustomException(ExceptionCode.INVALID_TOKEN);
        }
    }


//
//    //customTaleId에 해당하는 CustomTale 엔터티 (커스텀 동화 상세조회)
//    public Optional<CustomTale> getCustomTale(Integer customTaleId) {
//        return customTaleRepository.findById(customTaleId);
//    }
//
//
//    //opened가 true인 CustomTale 엔터티를 조회 (친구들이 만든 동화보기 목록)
//    public List<CustomTale> getCustomTales() {
//        return customTaleRepository.findByOpened(true);
//    }
//
//    //customTaleId에 해당하는 CustomTale 엔터티의 opened 값 변경 (나의 책장 - 내가 만든 동화)
//    public CustomTale putCustomTale(Integer customTaleId, boolean opened) {
//        Optional<CustomTale> optionalCustomTale = customTaleRepository.findById(customTaleId);
//
//        if (optionalCustomTale.isPresent()) {
//            CustomTale customTale = optionalCustomTale.get();
//            customTale.setOpened(opened);
//            return customTaleRepository.save(customTale);
//        } else {
//            return null;
//        }
//    }
//
//    //customTaleId에 해당하는 CustomTale 엔터티를 삭제
//    public boolean deleteCustomTale(Integer customTaleId) {
//        customTaleRepository.deleteById(customTaleId);
//        return true;
//    }
//

}
