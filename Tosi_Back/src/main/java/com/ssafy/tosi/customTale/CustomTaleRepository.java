package com.ssafy.tosi.customTale;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface CustomTaleRepository extends JpaRepository<CustomTale, Long> {

    // userId로 CustomTale 조회
    List<CustomTale> findByUserId(Long userId);

    // isPublic이 true인 CustomTale 조회
    List<CustomTale> findByIsPublic(boolean isPublic);

}
