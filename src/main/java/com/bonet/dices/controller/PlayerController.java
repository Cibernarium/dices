package com.bonet.dices.controller;

import java.util.Date;
import java.util.List;
import org.hibernate.service.spi.ServiceException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.bonet.dices.entity.Player;
import com.bonet.dices.repository.PlayersRepository;

@RestController
public class PlayerController {

	@Autowired
	private PlayersRepository playersRepository;

	@GetMapping("players/ranking")
	List<Player> orderPlayerbyPercentage() {
		return playersRepository.findAllByOrderByPercentageDesc();

	}

	@GetMapping("players/ranking/loser")
	Player worstPlayerPercentage() {
		return playersRepository.findFirstByOrderByPercentageAsc();
	}

	@GetMapping("players/ranking/winner")
	Player besttPlayerPercentage() {
		return playersRepository.findFirstByOrderByPercentageDesc();
	}

	@PostMapping("/players")
	Player newPlayer(@RequestBody Player newPlayer) throws RuntimeException {
		boolean flag = false;
		if (newPlayer.getName().isBlank()) {
			newPlayer.setName("ANONYM");
		}

		Iterable<Player> listPlayers = playersRepository.findAll();
		for (Player player : listPlayers) {
			if (newPlayer.getName().equals(player.getName()) && (!newPlayer.getName().equals("ANONYM"))) {
				flag = true;
			}
		}
		if (flag) {
			throw new ServiceException("Player already exists!!!");
		} else {
			newPlayer.setPercentage(0.0);
			newPlayer.setDate(new Date());
			playersRepository.save(newPlayer);
			return newPlayer;
		}

	}

	@PutMapping("/players")

	Player renamePlayer(@RequestBody Player playerToEdit) throws RuntimeException {
		boolean flag = false;

		Iterable<Player> listPlayers = playersRepository.findAll();
		for (Player player : listPlayers) {
			if (playerToEdit.getName().equals(player.getName()) && (!playerToEdit.getName().equals("ANONYM"))) {
				flag = true;
			}
		}
		if (flag) {
			throw new ServiceException("Player already exists!!!");
		} else {
			playerToEdit.setDate(new Date());
			playersRepository.save(playerToEdit);
			return playerToEdit;
		}

	}

}
