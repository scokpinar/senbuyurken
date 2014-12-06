/**
 *
 */
package senbuyurken.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import senbuyurken.entities.User;

/**
 * @author Siva
 */
public interface UserRepository extends JpaRepository<User, Integer> {

}
