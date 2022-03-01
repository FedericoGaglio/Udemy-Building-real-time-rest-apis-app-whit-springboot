package com.springboot.blog.entity;

import java.util.HashSet;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.Table;
import javax.persistence.UniqueConstraint;

import lombok.Data;

@Data
@Entity
@Table( name = "users", 
		uniqueConstraints = {
				@UniqueConstraint(columnNames = {"username"}),
				@UniqueConstraint(columnNames = {"email"})
		}
      )


public class User {
	
	@Id
	@GeneratedValue( strategy = GenerationType.IDENTITY)
	private long id;
	
	private String name;
	private String username;
	private String email;
	private String password;
	
	@ManyToMany( fetch = FetchType.EAGER, cascade = CascadeType.ALL)
	@JoinTable(name = "user_roles",
			   joinColumns = @JoinColumn( name = "user_id", referencedColumnName = "id"),
			   inverseJoinColumns = @JoinColumn( name = "role_id", referencedColumnName = "id"))
	private Set<Role> roles = new HashSet<>();
	
	/**
	 * 
	 * dunque in questo caso stiamo definendo una relazione many to many tra role e user. Per definizione
	 * essendo una many to many, oltre alle due classi legate tra di loro, esisterà anche una tabella di join
	 * che lega le due entita.
	 * 
	 * quindi in questo caso attraverso @JoinTable vado ad indicare il nome della tabella di join
	 * che mi andra a legaree tra di loro le due classi (user e role).
	 * 
	 * tramite JOINCOLUMNS vado con il name ad indicare la foreign key che si riferisce all'id della tabella
	 * della entita user che verrà messa nella tabella di join (in relazione al role ovviamente)
	 * 
	 * tramite inverseJoinColumns indico Le colonne della chiave esterna della tabella di join che
	 *  fanno riferimento alla tabella primaria dell'entità che non possiede l'associazione.(concetto
	 *  identico ma opposto rispetto al join column, quindi definito a role e non a user)
	 * 
	 */

}
