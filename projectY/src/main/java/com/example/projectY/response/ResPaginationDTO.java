package com.example.projectY.response;

import java.util.List;

import lombok.Data;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Data
public class ResPaginationDTO<T, M> {
    private List<T> result;
    // private String page;
    // private String size;
    // private Integer totalPages;
    // private Long totalElements;
    // private Boolean hasNext;
    // private Boolean hasPrevious;
    private M meta;

}
