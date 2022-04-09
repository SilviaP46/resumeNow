package com.app.demo.repository;

import com.app.demo.model.User;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Repository;
import org.springframework.web.context.annotation.ApplicationScope;

import javax.transaction.Transactional;
import java.util.List;

@Repository
@ApplicationScope
public interface UserRepository extends CrudRepository<User, Long> {

    @Query("select u from User u where u.password=:password and u.username=:username")
    User findAndValidate(@Param("password") String password, @Param("username") String username);


    @Query("select u from User u where u.username=:username")
    User findByUserName(@Param("username")String username);

    @Query("select u.username from User u where u.username like CONCAT('%', :username, '%')")
    List<String> getUserNamesLike(@Param("username")String toString);

    @Query("select u.idUser from User u where u.username=:username")
    Long findIdByUsername(@Param("username")String username);

    @Transactional
    @Modifying
    @Query("update User u set u.failed=u.failed+1 where u.username=:username")
    void increaseFailed(@Param("username")String username);

    @Transactional
    @Modifying
    @Query("update User u set u.failed=0 where u.username=:username")
    void resetFailed(@Param("username")String username);

    @Query("select u.failed from User u where u.username=:username")
    Short getFailed(String username);

    @Transactional
    @Modifying
    @Query("update User u set u.status=1 where u.username=:username")
    void forcedDeactivate(String username);
}
