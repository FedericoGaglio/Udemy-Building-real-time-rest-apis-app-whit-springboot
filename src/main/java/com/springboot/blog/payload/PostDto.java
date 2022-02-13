package com.springboot.blog.payload;

import lombok.Data;

@Data
public class PostDto {
	
	private Long id;
	private String title;
	private String description;
	private String content;

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
 