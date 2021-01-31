
package be.fooda.backend.auth.dao;

import be.fooda.backend.auth.model.entity.UserEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.UUID;

@Repository
public interface UserRepository extends JpaRepository<UserEntity, UUID> {

    boolean existsByLogin(String login);

    UserEntity getOneByLogin(String login);

}