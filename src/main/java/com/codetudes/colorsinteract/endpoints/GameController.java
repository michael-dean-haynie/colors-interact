package com.codetudes.colorsinteract.endpoints;

import com.codetudes.colorsinteract.contracts.GameDTO;
import com.codetudes.colorsinteract.contracts.PlayerDTO;
import com.codetudes.colorsinteract.contracts.RemovePlayerDTO;
import com.codetudes.colorsinteract.db.domain.Game;
import com.codetudes.colorsinteract.db.repository.GameRepository;
import com.codetudes.colorsinteract.enums.GameTypeEnum;
import com.codetudes.colorsinteract.services.GameService;
import lombok.extern.log4j.Log4j2;
import org.modelmapper.ModelMapper;
import org.modelmapper.TypeToken;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Log4j2
@RestController
@RequestMapping("/game")
public class GameController {

    @Autowired
    GameRepository gameRepository;

    @Autowired
    GameService gameService;

    @Autowired
    ModelMapper mapper;

    @PostMapping("/new/{gameType}")
    public GameDTO newGame(@PathVariable GameTypeEnum gameType) {
        log.info("newGame endpoint hit [gameType={}]", gameType);
        Game game = new Game();
        game = gameRepository.save(game);
        return mapper.map(game, GameDTO.class);
    }

    @PostMapping("/{gameId}/add-player")
    public String addPlayer(@PathVariable Long gameId, @RequestBody PlayerDTO playerDTO) {
        log.info("addPlayer endpoint hit [gameId={}, playerName={}]", gameId, playerDTO.getName());
        return gameService.addPlayer(gameId, playerDTO);
    }

    @PostMapping("/{gameId}/remove-player")
    public ResponseEntity removePlayer(@PathVariable Long gameId, @RequestBody RemovePlayerDTO removePlayerDTO) {
        log.info("removePlayer endpoint hit [gameId={}, playerName={}]", gameId, removePlayerDTO.getPlayer());
        gameService.removePlayer(gameId, removePlayerDTO);
        return ResponseEntity.ok().build();
    }

    @GetMapping
    public List<GameDTO> getGames() {
        return mapper.map(gameRepository.findAll(), new TypeToken<List<GameDTO>>() {}.getType());
    }
}
