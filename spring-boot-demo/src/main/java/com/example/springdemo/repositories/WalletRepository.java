package com.example.springdemo.repositories;

import com.example.springdemo.entities.Post;
import com.example.springdemo.entities.Wallet;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface WalletRepository extends JpaRepository<Wallet, Long> {

    @Query(nativeQuery =true, value = "SELECT * FROM Wallet u WHERE u.username = (:name)")
    Wallet findByUserSimple(@Param("name") String name);

    @Modifying
    @Query(nativeQuery =true, value = "UPDATE Wallet u SET u.amount = (:amount) WHERE u.username = (:name)")
    void updateAmount(@Param("amount") Integer amount,@Param("name") String name);
}
