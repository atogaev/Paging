package com.example.repository;

import com.example.entity.StudentEntity;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;

@Repository
public interface StudentRepository extends CrudRepository<StudentEntity,Integer>,
        PagingAndSortingRepository<StudentEntity, Integer> {
    List<StudentEntity> getByName(String name);

    List<StudentEntity> getBySurname(String surname);

    List<StudentEntity> getByCreatedDate(LocalDate createdDate);
    List<StudentEntity> findAllByCreatedDateBetween(LocalDate from, LocalDate to);
    Page<StudentEntity> findByLevel(String level,Pageable pageable);
//    Page<StudentEntity> findAllByLevel(@Param("level") String level, Pageable pageable);
//    @Query("select s" +
//            " from StudentEntity s" +
//            " where s.level =: level")
//    Page<StudentEntity> findAllByLevel2(@Param("level") String level, Pageable pageable);
//
//    @Query(value = "select *" +
//            " FROM student" +
//            " where level = :level", nativeQuery = true)
//    Page<StudentEntity> findAllByLevel3(@Param("level") String level, Pageable pageable);
}
