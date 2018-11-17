package com.qf.entity;


import lombok.*;

import java.io.Serializable;

@Setter
@Getter
@ToString
@EqualsAndHashCode
@AllArgsConstructor
@NoArgsConstructor
public class Goods implements Serializable {
    private  Integer id;
    private  String title;
    private  String ginfo;
    private  Integer gcount;
    private  Integer tid=1;
    private  Double allprice;
    private  Double price;
    private  String gimage;



}
