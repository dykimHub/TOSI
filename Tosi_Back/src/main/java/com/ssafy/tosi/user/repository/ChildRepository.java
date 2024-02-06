package com.ssafy.tosi.user.repository;

import com.ssafy.tosi.user.entity.Child;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface ChildRepository extends JpaRepository<Child, Integer> {

    List<Child> findByUserId(Integer userId);

    @Modifying
    @Query("delete from Child c where c.userId = :userId")
    void deleteByUserId(@Param("userId") Integer userId);
}
