package com.springboot.blog.repository;

import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import com.springboot.blog.entity.User;

public interface UserRepository extends JpaRepository<User, Long>{
	
	/*i metodi che metti qui in interfaccia sono quelli "personalizzati" ricorda.
	 * tutti i metodi crud invece non vanno messi in quanto sono "portati" direttamente
	 * dall'estensione che si va a fare  del JpaRepository*/
	
	Optional<User> findByEmail(String email);
    Optional<User> findByUsernameOrEmail(String username, String email);
    Optional<User> findByUsername(String username);
    Boolean existsByUsername(String username);
    Boolean existsByEmail(String email);
	
	
	/**
	 * Oggetto OPTIONAL -> A container object which may or may not contain a non-null value.
	 *  If a value is present, isPresent() will return true and get() will return the value.
	 *  
	 *  OVVERO
	 *  
	 *  Un oggetto contenitore che può contenere o meno un valore non null.
	 *  se l'oggetto è effettivamente presente il metodo isPresent() ritornerà TRUE e quindi andare
	 *  a fare poi il metodo get() ritornerà il valore effettivo (in quanto effettivamente esistente)
	 */

}
