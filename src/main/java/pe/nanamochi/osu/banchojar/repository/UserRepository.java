package pe.nanamochi.osu.banchojar.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import pe.nanamochi.osu.banchojar.entities.db.User;

@Repository
public interface UserRepository extends JpaRepository<User, Integer> {
    User findByUsernameAndPasswordMd5(String username, String password);
}
