package com.codetudes.colorsinteract;

import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.event.ContextRefreshedEvent;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Component;

@Log4j2
@Component
public class GameLoop {

    @Autowired
    GameStateService gameStateService;
    @Autowired
    GameStatePublisher gameStatePublisher;

    // ticks per second
    private static final int FRAME_RATE = 60;
    // ms in frame
    private static final int FRAME_LENGTH = 1000 / FRAME_RATE;

    @EventListener
    public void onContextRefreshed(ContextRefreshedEvent event){
        // temp disable
        // run();
    }

    public void run() {
        boolean quit = false;
//        log.info("FRAME_RATE: {}", FRAME_RATE);
//        log.info("FRAME_LENGTH: {}", FRAME_LENGTH);

        this.gameStateService.initializeGameState();

        while(!quit) {
            long beginTime = System.currentTimeMillis();
//            log.info("beginTime: {}", beginTime);

            // update
            int hashBefore = gameStateService.getGameState().hashCode();
            gameStateService.tickGameState();
            int hashAfter = gameStateService.getGameState().hashCode();

            // render
            if (hashBefore != hashAfter) {
                gameStatePublisher.publishGameState(gameStateService.getGameState());
            }

            long timeTaken = System.currentTimeMillis() - beginTime;
//            log.info("timeTaken: {}", timeTaken);
            long sleepTime = FRAME_LENGTH - timeTaken;
//            log.info("sleepTime: {}", sleepTime);

            if (sleepTime >= 0) {
                try {
                    Thread.sleep(sleepTime);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }
    }
}
