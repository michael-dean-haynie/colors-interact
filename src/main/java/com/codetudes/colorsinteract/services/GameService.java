package com.codetudes.colorsinteract.services;

import com.codetudes.colorsinteract.contracts.InputDTO;
import com.codetudes.colorsinteract.contracts.PlayerDTO;
import com.codetudes.colorsinteract.contracts.RemovePlayerDTO;
import com.codetudes.colorsinteract.db.domain.Game;
import com.codetudes.colorsinteract.db.domain.Input;
import com.codetudes.colorsinteract.db.domain.Player;
import com.codetudes.colorsinteract.db.domain.PlayerState;
import com.codetudes.colorsinteract.db.repository.GameRepository;
import com.codetudes.colorsinteract.endpoints.InputPublisher;
import com.codetudes.colorsinteract.enums.InputTypeEnum;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;
import java.util.Random;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
public class GameService {
    @Autowired
    GameRepository gameRepository;
    @Autowired
    ModelMapper mapper;
    @Autowired
    InputPublisher inputPublisher;

    public String addPlayer(Long gameId, PlayerDTO playerDTO) {
        Game game = gameRepository.findById(gameId).orElse(null);

        throw404IfNull(game, gameId);
        throw409IfNameTaken(playerDTO.getName(), game);

        Player player = mapper.map(playerDTO, Player.class);
        player.setSecret(UUID.randomUUID().toString());

        PlayerState playerState = new PlayerState();
        playerState.setX(getRandomNumberInRange(0, 500));
        playerState.setY(getRandomNumberInRange(0, 500));
        playerState.setR(getRandomNumberInRange(0, 255));
        playerState.setG(getRandomNumberInRange(0, 255));
        playerState.setB(getRandomNumberInRange(0, 255));
        playerState.setA(0.5);

        Input input = new Input();
        input.setLogicalFrame(calcNextLogicalFrame(game));
        input.setType(InputTypeEnum.ADD_PLAYER);
        input.setTargetPlayer(player);
        input.setTargetPlayerState(playerState);

        game.getInputs().add(input);
        inputPublisher.publishInput(game.getId(), mapper.map(input, InputDTO.class));

        gameRepository.save(game);

        return player.getSecret();

    }

    public void removePlayer(Long gameId, RemovePlayerDTO removePlayerDTO) {
        Game game = gameRepository.findById(gameId).orElse(null);

        throw404IfNull(game, gameId);
        throw404IfNameNotTaken(removePlayerDTO.getPlayer().getName(), game);
        throw403IfPlayerSecretDoesNotMatch(removePlayerDTO.getPlayer(), game);

        Input input = new Input();
        input.setLogicalFrame(removePlayerDTO.getLogicalFrame());
        input.setType(InputTypeEnum.REMOVE_PLAYER);
        input.setTargetPlayer(mapper.map(removePlayerDTO.getPlayer(), Player.class));
        input.setTargetPlayerState(mapper.map(removePlayerDTO.getPlayerState(), PlayerState.class));

        game.getInputs().add(input);
        inputPublisher.publishInput(game.getId(), mapper.map(input, InputDTO.class));

        gameRepository.save(game);
    }

    // use current millis and game start millis and logical frame rate to calculate the next logical frame
    // get the next frame where this change will take place.
    private Long calcNextLogicalFrame(Game game) {
        long now = System.currentTimeMillis();
        long start = game.getGameStart();
        int lfr = game.getLogicalFrameRate();

        long delta = now - start;
        // round up to next logical frame
        return (delta + lfr - 1) / lfr;
    }

    // check if the name is already taken in this game
    private boolean nameTaken(String playerName, Game game) {
        if (game.getInputs().size() > 0){
            List<Input> addOrRemoveInputs = game.getInputs().stream().filter(input -> {
                return input.getTargetPlayer().getName().equals(playerName) &&
                        (input.getType().equals(InputTypeEnum.ADD_PLAYER) || input.getType().equals(InputTypeEnum.REMOVE_PLAYER));
            }).collect(Collectors.toList());
            if (addOrRemoveInputs.size() > 0){
                // return true if the last "add" or "remove" input for this player was "add".
                return addOrRemoveInputs.get(addOrRemoveInputs.size() - 1).getType().equals(InputTypeEnum.ADD_PLAYER);
            } else {
                return false;
            }
        } else {
            return false;
        }

    }

    private void throw404IfNull(Game game, Long gameId) {
        if (game == null) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND,
                    String.format("Game with id '%s' not found.", gameId));
        }
    }

    private void throw409IfNameTaken(String newName, Game game) {
        if (nameTaken(newName, game)) {
            throw new ResponseStatusException(HttpStatus.CONFLICT,
                    String.format("The name '%s' is currently taken for game with id '%s'", newName, game.getId()));
        }
    }

    private void throw404IfNameNotTaken(String playerName, Game game) {
        if (!nameTaken(playerName, game)) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND,
                    String.format("Cannot remove player by name '%s' from game by id '%s'. Player not part of game.", playerName, game.getId()));
        }
    }

    private void throw403IfPlayerSecretDoesNotMatch(PlayerDTO player, Game game) {
        List<Input> addPlayerInputs = game.getInputs().stream().filter(input -> {
            return input.getType().equals(InputTypeEnum.ADD_PLAYER) && input.getTargetPlayer().getName().equals(player.getName());
        }).collect(Collectors.toList());
        Input lastAdded = addPlayerInputs.get(addPlayerInputs.size() - 1);
        if (!lastAdded.getTargetPlayer().getSecret().equals(player.getSecret())) {
            throw new ResponseStatusException(HttpStatus.FORBIDDEN, String.format("Player '%s' secret does not match", player.getName()));
        }

    }

    private static int getRandomNumberInRange(int min, int max) {

        if (min >= max) {
            throw new IllegalArgumentException("max must be greater than min");
        }

        Random r = new Random();
        return r.nextInt((max - min) + 1) + min;
    }
}
