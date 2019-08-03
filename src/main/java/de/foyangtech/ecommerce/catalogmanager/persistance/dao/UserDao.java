package de.foyangtech.ecommerce.catalogmanager.persistance.dao;


import de.foyangtech.ecommerce.catalogmanager.persistance.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserDao extends JpaRepository<User, Long> {

       User findUserByUsername(String username);

}
