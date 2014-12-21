/**
 *
 */
package senbuyurken.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import senbuyurken.entities.User;

/**
 * @author Siva
 */
public interface UserRepository extends JpaRepository<User, Integer> {

    @Query("SELECT u FROM User u WHERE u.email = :email")
    User findByEmailAddress(@Param("email") String email);

}
