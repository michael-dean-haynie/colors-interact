package com.codetudes.colorsinteract;

import com.codetudes.colorsinteract.models.old.ChangeDirectionCommand;
import com.codetudes.colorsinteract.models.old.NewPlayerCommand;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Controller;

@Log4j2
@Controller
public class ClientController {

    @Autowired
    private GameStateService gameStateService;

    @Autowired
    private GameLoop gameLoop;

    @Autowired
    private SimpMessagingTemplate template;

//    @MessageMapping("game/new/{type}")
//    public void startGame(@DestinationVariable String type) throws Exception {
//        log.info("startGame({}) endpoint hit", type);
//        if (type == "sandbox"){
//
//        }
//
//    }

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
