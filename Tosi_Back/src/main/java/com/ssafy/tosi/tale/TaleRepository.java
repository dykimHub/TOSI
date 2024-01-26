package com.ssafy.tosi.tale;

import jakarta.persistence.EntityManager;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
@RequiredArgsConstructor
public class TaleRepository {

    //TODO interface로 변경

    private final EntityManager em;

    /**
     * 전체 동화 조회
     */
    public List<Tale> findAll() {
        return em.createQuery("select t from tales t", Tale.class)
                .getResultList();
    }

    /**
     * 하나의 동화 조회
     */
    public Tale findOne(int taleId) {
        return em.find(Tale.class, taleId);
    }

    /**
     * 이름으로 검색
     */
    public List<Tale> findByTitle(String title) {
        return em.createQuery("select t from tales t where t.title = :title", Tale.class)
                .setParameter("title", title)
                .getResultList();
    }

    /**
     * 랜덤 정렬
     */
    public List<Tale> orderByRandom() {
        return em.createQuery("select t from tales t order by rand()", Tale.class)
                .getResultList();
    }
}