package com.Project.demo.Model.Response;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class PageDto {
    private Object data;
    private Integer totalPages;
    private Long totalElements;
    private Integer pageNo;
}
