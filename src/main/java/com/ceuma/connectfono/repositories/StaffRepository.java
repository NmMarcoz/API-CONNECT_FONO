package com.ceuma.connectfono.repositories;

import com.ceuma.connectfono.models.Staff;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface StaffRepository extends JpaRepository <Staff, Long> {
    @Query(value = "SELECT * from staff WHERE email = :email AND password = :password ", nativeQuery = true)
    Staff login(@Param("email")String email, @Param("password") String password);
}
