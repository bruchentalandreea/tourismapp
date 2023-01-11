package com.example.springdemo.repositories;

import com.example.springdemo.entities.Post;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;

@Repository
public interface PostRepository extends JpaRepository<Post, Long> {

    @Query(nativeQuery =true, value = "SELECT * FROM Post u WHERE u.username = (:name)")
    List<Post> findByUserSimple(@Param("name") String name);

    @Query(nativeQuery =true, value = "SELECT * FROM Post u WHERE u.price < (:priceMax) AND u.price > (:priceMin)")
    List<Post> filterByPrice(@Param("priceMax") Double priceMax,@Param("priceMin") Double priceMin);


    @Query(nativeQuery =true, value = "SELECT * FROM Post u WHERE u.check_in_date = (:check_in_date) AND u.check_out_date = (:check_out_date)")
    List<Post> filterByDate(@Param("check_in_date") LocalDate check_in_date,@Param("check_out_date") LocalDate check_out_date);

    @Query(nativeQuery =true, value = "SELECT * FROM Post u WHERE u.country = (:country ) AND u.state = (:state) AND u.city = (:city)")
    List<Post> filterByLocation(@Param("country") String country,@Param("state") String state, @Param("city") String city );

    @Query(nativeQuery =true, value = "SELECT * FROM Post u WHERE u.type_of_vacation = (:type_of_vacation)")
    List<Post> filterByType(@Param("type_of_vacation") String type_of_vacation);

    @Query(nativeQuery =true, value = "SELECT * FROM Post u WHERE u.nr_of_persons = (:nr_of_persons)")
    List<Post> filterByNrOfPersons(@Param("nr_of_persons") Integer nr_of_persons);

    @Query(nativeQuery =true, value = "SELECT avg_rating FROM Post u WHERE u.id = (:id )")
    Double getRatingbyID(@Param("id") Long id );

    @Modifying
    @Query(nativeQuery =true, value = "UPDATE Post u SET u.avg_rating = (:value) WHERE u.id = (:id)")
    void updateRating(@Param("value") Double value,@Param("id") Long id);

    @Query(nativeQuery =true, value = "SELECT list_photo_id FROM Post u WHERE u.id = (:id )")
    String getListPhoto(@Param("id") Long id );

    @Query(nativeQuery =true, value = "SELECT DISTINCT post_id FROM Feedback u WHERE u.username = (:username ) AND u.rating !=-1 AND u.holiday_id = -1")
    List<Long> getIdPostsReviewedByUser(@Param("username") String username);

    @Query(nativeQuery =true, value = "SELECT id FROM Post u")
    List<Long> getAllIdPosts();

    @Query(nativeQuery =true, value = "SELECT * FROM Post u WHERE u.nr_of_persons= (:nr_of_persons) AND u.city = (:city) AND u.type_of_vacation = (:type_of_vacation)")
    List<Post> search(@Param("nr_of_persons") Integer nr_of_persons, @Param("city") String city,@Param("type_of_vacation") String type_of_vacation);

    @Query(nativeQuery =true, value = "SELECT * FROM Post u WHERE u.id = (:id )")
    Post  getPostById(@Param("id") Long id );

    @Modifying
    @Query(nativeQuery =true, value = "DELETE FROM Post WHERE id = (:id )")
    void deletePost(@Param("id") Long id);

    @Query(nativeQuery =true, value = "SELECT photo_id FROM Post WHERE id = (:id )")
    String getPhotoId(@Param("id") Long id);

    @Query(nativeQuery =true, value = "SELECT price FROM Post u WHERE u.id = (:id )")
    Double getPriceByID(@Param("id") Long id );

}
