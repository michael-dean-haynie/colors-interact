package com.codetudes.colorsinteract;

import com.codetudes.colorsinteract.enums.DirectionEnum;
import com.codetudes.colorsinteract.models.ChangeDirectionCommand;
import com.codetudes.colorsinteract.models.GameState;
import com.codetudes.colorsinteract.models.GameStatePlayer;
import com.codetudes.colorsinteract.models.NewPlayerCommand;
import lombok.Getter;
import lombok.Setter;
import org.springframework.stereotype.Component;

import java.util.Random;

@Component
public class GameStateService {
    // distance moved per game tick
    private static final int PLAYER_SPEED = 3;

    @Getter
    @Setter
    private GameState gameState;

    public void initializeGameState() {
        gameState = new GameState();
    }

    public void tickGameState() {
        // process new player queue
        if (gameState.getNewPlayerQueue().size() > 0) {
            GameStatePlayer gsp = new GameStatePlayer();
            gsp.setPlayerInfo(gameState.getNewPlayerQueue().poll());
            gsp.setR(new Random().nextInt(256));
            gsp.setG(new Random().nextInt(256));
            gsp.setB(new Random().nextInt(256));
            gsp.setX(0);
            gsp.setY(0);
            gsp.setDirection(DirectionEnum.STILL);

            gameState.getPlayers().add(gsp);
        }

        // process direction queues
        gameState.getPlayers().forEach((player) -> {
            if (player.getDirectionQueue().size() > 0) {
                player.setDirection(player.getDirectionQueue().poll());
            }
        });

        // update positions
        gameState.getPlayers().forEach((player) -> {
            switch(player.getDirection()) {
                case UP:
                    player.setY(player.getY() + PLAYER_SPEED);
                    break;
                case RIGHT:
                    player.setX(player.getX() + PLAYER_SPEED);
                    break;
                case DOWN:
                    player.setY(player.getY() - PLAYER_SPEED);
                    break;
                case LEFT:
                    player.setX(player.getX() - PLAYER_SPEED);
                    break;
                default:
                    // continue
            }
        });
    }

    public void newPlayer(NewPlayerCommand newPlayerCommand) {
        gameState.getNewPlayerQueue().add(newPlayerCommand.getPlayer());
    }

    public void changeDirection(ChangeDirectionCommand changeDirectionCommand) {
        GameStatePlayer gsp = gameState.getPlayers().stream().filter(gameStatePlayer -> {
            return gameStatePlayer.getPlayerInfo().getId().equals(changeDirectionCommand.getPlayer().getId());
        }).findFirst().orElse(null);

        if (gsp != null) {
            gsp.getDirectionQueue().add(changeDirectionCommand.getDirection());
        }
    }
}
