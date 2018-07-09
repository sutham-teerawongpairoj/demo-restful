package com.biztech.demo.repository;

import com.biztech.demo.model.MemberModel;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface MemberRepository extends JpaRepository<MemberModel, Integer> {

    @Query("Select m from MemberModel m where m.name like %:name%")
    List<MemberModel> findByName(@Param("name") String name);

    @Query("Select m from MemberModel m where m.surname like %:surname%")
    List<MemberModel> findBySurname(@Param("surname") String surname);
}
