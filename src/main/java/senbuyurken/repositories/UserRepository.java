/**
 *
 */
package senbuyurken.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import senbuyurken.entities.User;

/**
 * User: SametCokpinar
 * Date: 20/12/14
 * Time: 21:00
 */
public interface UserRepository extends JpaRepository<User, Integer> {

    @Query("SELECT u FROM User u WHERE u.email = :email")
    User findByEmailAddress(@Param("email") String email);

    @Query("SELECT count(u.id) FROM User u")
    void findUserCount4HerokuWake();

}
