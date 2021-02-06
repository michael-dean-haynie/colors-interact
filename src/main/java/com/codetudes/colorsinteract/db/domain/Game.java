package com.codetudes.colorsinteract.db.domain;

import lombok.Data;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Data
@Entity
public class Game {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    // game cycles/ticks per second.
    private int logicalFrameRate = 30;

    private Long gameStart = System.currentTimeMillis();

    @OneToMany(cascade = CascadeType.ALL)
    private List<Input> inputs = new ArrayList<>();
}
