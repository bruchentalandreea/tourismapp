package com.example.springdemo.repositories;

import com.example.springdemo.entities.Bookings;
import com.example.springdemo.entities.Post;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface BookingsRepository  extends JpaRepository<Bookings, Long> {

    @Query(nativeQuery =true, value = "SELECT * FROM Bookings u WHERE u.booked_by = (:booked_by) AND u.holiday_id=-1")
    List<Bookings> findByUserSimplePost(@Param("booked_by") String booked_by);

    @Query(nativeQuery =true, value = "SELECT * FROM Bookings u WHERE u.booked_by = (:booked_by) AND u.post_id=-1")
    List<Bookings> findByUserSimpleHoliday(@Param("booked_by") String booked_by);


    @Query(nativeQuery =true, value = "SELECT avg_rating FROM Bookings u WHERE u.id = (:id )")
    Double getRatingbyID(@Param("id") Long id );

    @Modifying
    @Query(nativeQuery =true, value = "UPDATE Bookings u SET u.avg_rating = (:value) WHERE u.id = (:id)")
    void updateRating(@Param("value") Double value,@Param("id") Long id);

    @Modifying
    @Query(nativeQuery =true, value = "DELETE FROM Bookings WHERE id = (:id )")
    void deleteBookings(@Param("id") Long id);

    @Query(nativeQuery =true, value = "SELECT * FROM Bookings ORDER BY id DESC LIMIT 1")
    Bookings findLastBooking();


}
