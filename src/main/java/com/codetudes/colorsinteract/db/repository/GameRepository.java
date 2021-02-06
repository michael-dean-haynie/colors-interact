package com.codetudes.colorsinteract.db.repository;

import com.codetudes.colorsinteract.db.domain.Game;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface GameRepository extends CrudRepository<Game, Long> { }
