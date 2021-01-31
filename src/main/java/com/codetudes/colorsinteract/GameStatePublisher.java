package com.codetudes.colorsinteract;

import com.codetudes.colorsinteract.models.GameState;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Component;

@Log4j2
@Component
public class GameStatePublisher {

    @Autowired
    private SimpMessagingTemplate template;

    public void publishGameState(GameState gameState) {
//        log.info("Publishing game state: {}", gameState);
        this.template.convertAndSend("/topic/game-state", gameState);
    }
}
