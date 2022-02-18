package com.springboot.blog.entity;

import java.util.HashSet;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.UniqueConstraint;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data 											//OFFERTA DA LOMBOK (guarda sotto spiegazione)
@AllArgsConstructor 							//OFFERTA DA LOMBOK (guarda sotto spiegazione)
@NoArgsConstructor 								//OFFERTA DA LOMBOK (guarda sotto spiegazione)

@Entity 										//OFFERTA DA JAVAX.PERSISTENCE (guarda sotto spiegazione)
@Table( 										//OFFERTA DA JAVAX.PERSISTENCE (guarda sotto spiegazione)
		name = "posts", uniqueConstraints = {@UniqueConstraint(columnNames = {"title"})}
)
public class Post {
	
	@Id 										//OFFERTA DA JAVAX.PERSISTENCE (guarda sotto spiegazione)
	@GeneratedValue (							//OFFERTA DA JAVAX.PERSISTENCE (guarda sotto spiegazione)
		strategy = GenerationType.IDENTITY
	)							
	private long id;
	
	@Column(name="title" , nullable=false)		//OFFERTA DA JAVAX.PERSISTENCE (guarda sotto spiegazione)
	private String title;
	
	@Column(name="description" , nullable=false)
	private String description;
	
	@Column(name="content" , nullable=false)
	private String content;
	
	@OneToMany(mappedBy = "post", cascade = CascadeType.ALL, orphanRemoval = true)
    private Set<Comment> comments = new HashSet<>();

}




/*APPUNTI
 * 
 * 
 * 
 * 
 * Annotazione DATA: 
 * serve a generare i Getter di tutti i campi + un toString qualitativo
 * + l'hashcode con tanto di equal method (per il check di unicità), e inoltre genera anche 
 * dei metodi SETTER per tutti quei campi che NON risultano essere campi dichiarati come FINAL
 *  + genera inoltre anche un costruttore  --->> tramite questa annotazione si riesce a risparmiare
 *  l'utilizzo delle altre seguenti annotazioni: @Getter, @Setter, @RequiredArgsCostrunctor,
 *  @toString, @EwqualsAndHashcode
 *  
 *  
 *  
 * Annotazione @AllArgsConstructor: 
 * Genera un costruttore contenente tutti i campi
 * 
 * 
 * 
 * 
 * Annotazione @NoArgsConstructor: 
 * Genera un costruttore senza nessun campo
 *  
 *  
 * Annotazione @Entity: 
 * Specifica che questa classe è un'entità
 * 
 * 
 * 
 * Annotazione @Table: 
 * Serve ad indicare la tabella PRIMARIA da andare ad utilizzare. Il name dentro serve ovviamente
 * per indicare il nome della tabella. @UniqueConstraint serve ad indicare il vincolo da andare
 * ad imporre su tale tabella, in questo caso il nome della colonna relativa alla tabella indicata nel name
 * 
 * 
 * 
 * Annotazione @Id: 
 * Specifica la chiave primaria di un'entità
 * 
 * 
 * Annotazione @GeneratedValue: 
 * Specifica la tipologia di generazion edella chiave primaria. Può essere utilizzata ovviamente unicamente
 * per chiavi primarie
 * La strategia utilizzata in questo caso è --> IDENTITY= Indica che il provider di persistenza deve assegnare 
 * chiavi primarie per l'entità utilizzando una colonna di identità del database.
 * 
 * 
 * Annotazione @Column(name= [nome colonna], nullable= true/false): 
 * Specifica a quale colonna della tabella del db corrisponde un entita che va storata. Il nullable indica se questo
 * valore puo essere nullo o meno.
 * 
 * */
 