package com.example.springdemo.repositories;

import com.example.springdemo.entities.UserSimple;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

public interface UserSimpleRepository extends JpaRepository<UserSimple, Long> {

    Optional<UserSimple> findByUserName(String username);

    @Query(nativeQuery =true, value = "SELECT user_name FROM user_simple u WHERE u.user_name != (:user_name ) ")
    List<String> getNames(@Param("user_name") String user_name);

    @Query(nativeQuery =true, value = "SELECT user_name FROM user_simple u")
    List<String> getAllUsers();

    @Query(nativeQuery =true, value = "SELECT user_role FROM user_simple u WHERE u.user_name = (:user_name )")
    String getRole(@Param("user_name") String user_name);

    //not ok
    @Modifying(clearAutomatically = true)
    @Query(nativeQuery =true, value = "UPDATE user_simple u SET u.phone_number = (:phone_number) , u.name = (:name) , u.surname = (:surname) , u.country = (:country) , u.state = (:state) , u.city = (:city) WHERE u.user_name = (:user_name)")
    void updateUserProfile(@Param("phone_number") String phone_number,@Param("name") String name,@Param("surname") String surname,@Param("country") String country,@Param("state") String state,@Param("city") String city,@Param("user_name") String user_name);

    @Modifying(clearAutomatically = true)
    @Query(nativeQuery =true, value = "UPDATE  user_simple u SET u.phone_number = (:phone_number) WHERE u.user_name = (:user_name)")
    void updatePhone(@Param("phone_number") String phone_number,@Param("user_name") String user_name);

    @Modifying(clearAutomatically = true)
    @Query(nativeQuery =true, value = "UPDATE user_simple u SET u.name = (:name)  WHERE u.user_name = (:user_name)")
    void updateName(@Param("name") String name, @Param("user_name") String user_name);

    @Modifying(clearAutomatically = true)
    @Query(nativeQuery =true, value = "UPDATE user_simple u SET  u.surname = (:surname) WHERE u.user_name = (:user_name)")
    void updateSurname(@Param("surname") String surname, @Param("user_name") String user_name);

    @Modifying(clearAutomatically = true)
    @Query(nativeQuery =true, value = "UPDATE user_simple u SET   u.country = (:country) , u.state = (:state) , u.city = (:city) WHERE u.user_name = (:user_name)")
    void updateAddress(@Param("country")  String country,@Param("state") String state,@Param("city") String city,@Param("user_name") String user_name);


}