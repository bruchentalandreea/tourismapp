package com.example.springdemo.repositories;

import com.example.springdemo.entities.Image;
import com.example.springdemo.entities.Post;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface ImageRepository extends JpaRepository<Image, Long> {

    @Query(nativeQuery =true, value = "SELECT link FROM Image u WHERE u.id = (:id)")
    String findImage(@Param("id") Long id);

    @Query(nativeQuery =true, value = "SELECT link FROM Image u WHERE u.id = (:id)")
    String findImageByPhotoId(@Param("id") String id);

    @Query(nativeQuery =true, value = "SELECT id FROM Image u")
    List<String> getAllIds();



}
