package com.springboot.blog.payload;

//import java.util.Set;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Size;

import lombok.Data;

@Data
public class PostDto {
	
	private Long id;
	
	/*	ESEMPIO DELLA VALIDAZIONE DEI CAMPI. Supponiamo infatti che il titolo:
	 *  1) non possa essere nullo o vuoto
	 *  2) deve avere almeno due caratteri
	 *  */
	@NotEmpty
	@Size (min=2, message="Il post deve avere almeno due caratteri!")
	private String title;
	
	@NotEmpty
	@Size (min=10, message="La descrizione deve avere almeno 10 caratteri!")
	private String description;
	
	@NotEmpty
	private String content;
	
	
	//private Set<CommentDto> commentDtoSet;
}


/*
 * L'utilizzo delle DTo viene fatto poiche aiuta ad aumentare il disaccoppiamento, soprattutto con "l'esterno".
 * Nel senso che magari vado a storare delle entità sul db. Devo però condividere le informazioni con un altro gruppo
 * esterno ad esempio  e magari non voglio andare a riportare tutte le informazioni delle entita come password/id o altre
 * cose. Vado allora a mappare le mie entita con delle DTO in maniera tale da dare una rappresentazione parziale o anche totale 
 * delle entita. Mappo entita su dto per usare le dto al loro posto.
 * 
 * 
 * */
 