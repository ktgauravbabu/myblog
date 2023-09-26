package com.myblog5.myblog5.payload;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;
@Data
@AllArgsConstructor
@NoArgsConstructor

public class PostResponse {
    private List<PostDto> postDto;
    private int pageNo;

    private int pageSize;
    private long TotalElements;

    private int TotalPages;
    private boolean last;

}
