package com.example.projectY.response;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
// @Data
public class MetaDTO {
    private int page;
    private int pageSize;
    private int pages;
    private Long total;
}
