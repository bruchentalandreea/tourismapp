package com.example.springdemo.repositories;

import com.example.springdemo.entities.Feedback;
import com.example.springdemo.entities.Post;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Arrays;
import java.util.List;

@Repository
public interface FeedbackRepository extends JpaRepository<Feedback, Long> {

    @Query(nativeQuery =true, value = "SELECT * FROM Feedback u WHERE u.username = (:username) ")
    List<Feedback> filterByUser(@Param("username") String username);

    @Query(nativeQuery =true, value = "SELECT * FROM Feedback u WHERE u.post_id = (:post_id) AND u.holiday_id = -1")
    List<Feedback> filterByPost(@Param("post_id") Long post_id);

    @Query(nativeQuery =true, value = "SELECT * FROM Feedback u WHERE u.holiday_Id = (:holiday_id) AND u.post_id = -1")
    List<Feedback> filterByHoliday(@Param("holiday_id") Long holiday_Id);

    @Query(nativeQuery =true, value = "SELECT * FROM Feedback u WHERE u.bookingsId = (:bookingsId) ")
    List<Feedback> filterByBookings(@Param("bookingsId") Long bookingsId);

    @Query(nativeQuery =true, value = "SELECT rating FROM Feedback u WHERE u.username = (:username) AND u.holiday_id = -1")
    List<Double> filterByUser2(@Param("username") String username);

    @Query(nativeQuery =true, value = "SELECT rating FROM Feedback u WHERE u.username = (:username) AND u.post_id = -1 ")
    List<Double> filterByUserHolidays(@Param("username") String username);

    @Query(nativeQuery =true, value = "SELECT rating FROM Feedback u WHERE u.username = (:username) AND u.post_id = (:post_id) AND u.holiday_id = -1")
    Double findRating(@Param("username") String username,@Param("post_id") Long post_id);

    @Query(nativeQuery =true, value = "SELECT rating FROM Feedback u WHERE u.username = (:username) AND u.holiday_id = (:u.holiday_id) AND u.post_id = -1")
    Double findRatingHoliday(@Param("username") String username,@Param("u.holiday_id") Long holiday_id);

    @Modifying
    @Query(nativeQuery =true, value = "DELETE FROM Feedback WHERE post_id = (:post_id) ")
    void deleteRatingByPost(@Param("post_id") Long post_id);

}
