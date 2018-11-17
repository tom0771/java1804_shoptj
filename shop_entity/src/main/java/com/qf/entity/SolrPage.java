package com.qf.entity;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.util.List;

@Getter
@Setter
@ToString
@EqualsAndHashCode
public class SolrPage<T> {
    private Integer indexPage=1;
    private  Integer pageCount=4;
    private  Integer totalPage;
    private  Integer totalCount;
    private List<T> data;


}
