package com.springboot.blog.payload;

import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/*classe fatta per customizzare il pagin and sorting*/

@Data
@AllArgsConstructor
@NoArgsConstructor
public class PostResponse {
	
	private List<PostDto> content;
	private int pageNo;
	private int pageSize;
	private long  totalElements;
	private int totalPages;
	private boolean last;
	
	
}
