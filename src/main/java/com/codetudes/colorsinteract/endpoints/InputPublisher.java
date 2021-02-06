package com.codetudes.colorsinteract.endpoints;

import com.codetudes.colorsinteract.contracts.InputDTO;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Component;

@Log4j2
@Component
public class InputPublisher {
    @Autowired
    private SimpMessagingTemplate template;

    public void publishInput(Long gameId, InputDTO input){
        log.info("Publishing input [gameId={}, input={}]", gameId, input);
        String destination = String.format("/topic/game/%s/input", gameId);
        this.template.convertAndSend(destination, input);
    }
}
