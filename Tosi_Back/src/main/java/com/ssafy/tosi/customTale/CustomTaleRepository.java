package com.ssafy.tosi.customTale;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface CustomTaleRepository extends JpaRepository<CustomTale, Integer> {

    // userId로 CustomTale 조회
    List<CustomTale> findByUserId(Integer userId);

    // isPublic이 true인 CustomTale 조회
    List<CustomTale> findByIsPublic(boolean isPublic);

}
