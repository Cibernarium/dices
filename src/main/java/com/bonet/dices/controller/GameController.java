package com.bonet.dices.controller;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.bonet.dices.entity.Game;
import com.bonet.dices.entity.Player;
import com.bonet.dices.repository.GamesRepository;
import com.bonet.dices.repository.PlayersRepository;
import com.bonet.dices.exception.GenericException;

@RestController
public class GameController {

	@Autowired
	private GamesRepository gamesRepository;

	@Autowired
	private PlayersRepository playersRepository;

	@GetMapping("/player")
	List<Player> returnAllPlayers() {

		return playersRepository.findAll();
	}

	@GetMapping("/game")
	List<Game> returnAllGames() {

		return gamesRepository.findAll();
	}

	@GetMapping("/players/{id}/games")
	List<Game> getAllGamesByPlayerId(@PathVariable(value = "id") Integer playerId) throws Exception {

		try {
			return (List<Game>) gamesRepository.findByPlayerId(playerId).get();
		} catch (Exception e) {
			new GenericException(playerId + " not found");
			return null;
		}
	}

	@GetMapping("/players")

	List<Player> finAllPlayersPercentage() {

		int countSuccess = 0;
		int countNoSuccess = 0;
		int totalGamesPlayer = 0;
		long totalGamesCount = gamesRepository.count();

		for (Player player : playersRepository.findAll()) {
			for (Game game : gamesRepository.findAll()) {
				if (player.getId() == game.getPlayer().getId()) {

					if (game.getSuccess()) {
						countSuccess++;
					} else {
						countNoSuccess++;
					}

					totalGamesPlayer = countSuccess + countNoSuccess;
					player.setPercentage((double) ((countSuccess * 100) / totalGamesPlayer));
					totalGamesCount--;

				} else if ((player.getId() != game.getPlayer().getId())) {
					totalGamesCount--;
					continue;
				}

			}
			if (totalGamesCount == 0) {
				playersRepository.save(player);
				countSuccess = 0;
				countNoSuccess = 0;
				totalGamesPlayer = 0;

			}
			totalGamesCount = gamesRepository.count();

		}

		return playersRepository.findAll();
	}

	@PostMapping("/players/{id}/games")
	Game rollDices(@PathVariable(value = "id") Integer playerId, @RequestBody Game game) {

		return playersRepository.findById(playerId).map(player -> {

			if (game.getDice1() + game.getDice2() == 7) {
				game.setSuccess(true);
			} else {
				game.setSuccess(false);
			}
			game.setPlayer(player);
			return gamesRepository.save(game);
		}).orElseThrow(() -> new GenericException(playerId + " not found"));

	}

	@DeleteMapping("/players/{id}/games")
	void deleteRollDicesByPlayer(@PathVariable(value = "id") Integer playerId) {

		for (Game game : gamesRepository.findByPlayerId(playerId).get()) {
			gamesRepository.delete(game);
		}
	}
}
