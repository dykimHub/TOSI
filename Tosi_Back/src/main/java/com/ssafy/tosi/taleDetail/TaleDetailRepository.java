package com.ssafy.tosi.taleDetail;

import com.ssafy.tosi.tale.Tale;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

@Repository
public interface TaleDetailRepository extends JpaRepository<Tale, Integer> {
    @Transactional
    @Modifying
    @Query("UPDATE Tale t SET t.likeCnt = t.likeCnt + 1 WHERE t.taleId = :taleId")
    int updateLikeCnt(int taleId);


}
