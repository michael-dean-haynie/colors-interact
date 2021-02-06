package com.codetudes.colorsinteract.models.old;

import com.codetudes.colorsinteract.enums.DirectionEnum;
import lombok.Data;

import java.util.ArrayDeque;
import java.util.Queue;

@Data
public class GameStatePlayer {
    private Player playerInfo;
    private DirectionEnum direction;
    // this to queue up commands the user sent to change direction.
    // will be processed 1 per game loop tick
    private Queue<DirectionEnum> directionQueue = new ArrayDeque<>();
    private Integer x;
    private Integer y;
    private Integer r;
    private Integer g;
    private Integer b;
}
