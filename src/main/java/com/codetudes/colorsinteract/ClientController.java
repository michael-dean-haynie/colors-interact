package com.codetudes.colorsinteract;

import com.codetudes.colorsinteract.models.ChangeDirectionCommand;
import com.codetudes.colorsinteract.models.GameState;
import com.codetudes.colorsinteract.models.NewPlayerCommand;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.stereotype.Controller;

@Log4j2
@Controller
public class ClientController {

    @Autowired
    private GameStateService gameStateService;

    @Autowired
    private GameLoop gameLoop;

    @MessageMapping("/game/start")
    public void startGame() throws Exception {
        log.info("Received start game message from client");
        gameLoop.run();
    }

    @MessageMapping("/player/new")
    public void newPlayer(NewPlayerCommand newPlayerCommand) throws Exception {
        log.info("Received NewPlayerCommand from client: {}", newPlayerCommand);
        gameStateService.newPlayer(newPlayerCommand);
    }

    @MessageMapping("/player/change-direction")
    public void changeDirection(ChangeDirectionCommand changeDirectionCommand) throws Exception {
        log.info("Received ChangeDirectionCommand from client: {}", changeDirectionCommand);
        gameStateService.changeDirection(changeDirectionCommand);
    }


}
