package com.codetudes.colorsinteract.models;

import lombok.Data;

import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.List;
import java.util.Queue;

@Data
public class GameState {
    private List<GameStatePlayer> players = new ArrayList<>();
    private Queue<Player> newPlayerQueue = new ArrayDeque<>();
}
