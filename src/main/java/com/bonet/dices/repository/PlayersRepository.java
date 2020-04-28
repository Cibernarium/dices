package com.bonet.dices.repository;

import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.bonet.dices.entity.Player;

@Repository
public interface PlayersRepository extends JpaRepository<Player, Integer> {

	List<Player> findByName(String name);

	List<Player> findAllByOrderByPercentageDesc();

	Player findFirstByOrderByPercentageAsc();

	Player findFirstByOrderByPercentageDesc();

}
