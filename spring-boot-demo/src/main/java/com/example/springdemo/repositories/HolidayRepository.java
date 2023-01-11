package com.example.springdemo.repositories;

import com.example.springdemo.entities.Holiday;
import com.example.springdemo.entities.Post;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;

@Repository
public interface HolidayRepository extends JpaRepository<Holiday, Long> {

    @Query(nativeQuery =true, value = "SELECT * FROM Holiday u WHERE u.username = (:name)")
    List<Holiday> findByUserSimple(@Param("name") String name);

    @Modifying
    @Query(nativeQuery =true, value = "UPDATE Holiday u SET u.price = (:value) WHERE u.id = (:id)")
    void updatePrice(@Param("value") Double value,@Param("id") Long id);


    @Query(nativeQuery =true, value = "SELECT price FROM Holiday u WHERE u.id = (:id )")
    Double getPriceByID(@Param("id") Long id );

    @Query(nativeQuery =true, value = "SELECT avg_rating FROM Holiday u WHERE u.id = (:id )")
    Double getRatingbyID(@Param("id") Long id );

    @Modifying
    @Query(nativeQuery =true, value = "UPDATE Holiday u SET u.avg_rating = (:value) WHERE u.id = (:id)")
    void updateRating(@Param("value") Double value,@Param("id") Long id);

    @Query(nativeQuery =true, value = "SELECT list_photo_id FROM Holiday u WHERE u.id = (:id )")
    String getListPhoto(@Param("id") Long id );

    @Query(nativeQuery =true, value = "SELECT DISTINCT holiday_id FROM Feedback u WHERE u.username = (:username ) AND u.rating !=-1 ")
    List<Long> getIdHolidaysReviewedByUser(@Param("username") String username);

    @Query(nativeQuery =true, value = "SELECT id FROM Holiday u")
    List<Long> getAllIdHolidays();

    @Query(nativeQuery =true, value = "SELECT * FROM Holiday u WHERE u.nr_of_persons= (:nr_of_persons) AND u.city = (:city) AND u.type_of_vacation = (:type_of_vacation)")
    List<Holiday> search(@Param("nr_of_persons") Integer nr_of_persons, @Param("city") String city,@Param("type_of_vacation") String type_of_vacation);

    @Query(nativeQuery =true, value = "SELECT * FROM Holiday u WHERE u.id = (:id )")
    Holiday  getHolidayById(@Param("id") Long id );

    @Modifying
    @Query(nativeQuery =true, value = "DELETE FROM Holiday WHERE id = (:id )")
    void deleteHoliday(@Param("id") Long id);

    @Query(nativeQuery =true, value = "SELECT photo_id FROM Holiday WHERE id = (:id )")
     String getPhotoId(@Param("id") Long id);


    @Query(nativeQuery =true, value = "SELECT * FROM Holiday u WHERE u.price < (:priceMax) AND u.price > (:priceMin)")
    List<Holiday> filterByPrice(@Param("priceMax") Double priceMax,@Param("priceMin") Double priceMin);

    @Query(nativeQuery =true, value = "SELECT * FROM Holiday u WHERE u.country = (:country ) AND u.state = (:state) AND u.city = (:city)")
    List<Holiday> filterByLocation(@Param("country") String country,@Param("state") String state, @Param("city") String city );

    @Query(nativeQuery =true, value = "SELECT * FROM Holiday u WHERE u.type_of_vacation = (:type_of_vacation)")
    List<Holiday> filterByType(@Param("type_of_vacation") String type_of_vacation);

    @Query(nativeQuery =true, value = "SELECT * FROM Holiday u WHERE u.nr_of_persons = (:nr_of_persons)")
    List<Holiday> filterByNrOfPersons(@Param("nr_of_persons") Integer nr_of_persons);

    @Query(nativeQuery =true, value = "SELECT * FROM Holiday u WHERE u.country = (:country)")
    List<Holiday> getHolidaysByCountry(@Param("country") String country);

    @Query(nativeQuery =true, value = "SELECT DISTINCT post_id FROM Feedback u WHERE  u.rating !=-1 AND u.post_id = -1")
    List<Long> getIdPostsReviewed(@Param("username") String username);


}
