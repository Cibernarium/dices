package com.bonet.dices.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.bonet.dices.entity.Game;

@Repository
public interface GamesRepository extends JpaRepository<Game, Integer> {

	Optional<List<Game>> findByPlayerId(Integer playerId);

	void deleteAllByPlayerId(Integer playerId);

}
