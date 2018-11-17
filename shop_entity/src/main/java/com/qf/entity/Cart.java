package com.qf.entity;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.io.Serializable;

@Setter
@Getter
@EqualsAndHashCode
@ToString
public class Cart implements Serializable {
    private Integer id;
    private Integer gsid;
    private Integer goodsnums;
    private Integer uid;
    private Goods goods;

}
