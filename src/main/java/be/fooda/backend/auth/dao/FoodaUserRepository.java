
package be.fooda.backend.auth.dao;

import be.fooda.backend.auth.model.entity.FoodaUser;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface FoodaUserRepository extends JpaRepository<FoodaUser, Long> {

    @Query("SELECT u FROM FoodaUser u WHERE u.login = :login ")
    Optional<FoodaUser> findByLogin(@Param("login") final String login);

}