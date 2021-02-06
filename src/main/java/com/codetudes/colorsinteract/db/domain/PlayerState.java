package com.codetudes.colorsinteract.db.domain;

import lombok.Data;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

@Data
@Entity
public class PlayerState {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    Integer x;
    Integer y;
    Integer r;
    Integer g;
    Integer b;
    Double a;
}
