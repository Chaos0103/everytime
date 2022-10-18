package project.everytime.client.user.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import project.everytime.client.user.User;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

public interface UserRepository extends JpaRepository<User, Long>, UserRepositoryCustom {

    @Query("select u from User u where u.status = 'BAN'")
    List<User> findBanUsers();

    @Query("select u from User u where u.createdDate >= :period")
    List<User> findByPeriod(@Param("period") LocalDateTime period);

    @Query("select u from User u where u.authType = 'NONE'")
    List<User> findNonAuth();

    Optional<User> findByLoginId(String loginId);

    Optional<User> findByPhone(String phone);

    Optional<User> findByEmail(String email);

    @Query("select u from User u where u.nickname.nickname = :nickname")
    Optional<User> findByNickname(@Param("nickname") String nickname);

    Optional<User> findLoginByLoginIdAndPassword(String loginId, String password);

    @Query("select u from User u where u.loginId = :data or u.email = :data")
    Optional<User> findByEmailOrLoginId(@Param("data") String data);
}
