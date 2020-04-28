package com.bonet.dices.entity;

import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

@Entity
@Table(name = "games")
public class Game {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY) // auto_increment SQL
	private Integer id;
	private Integer dice1;
	private Integer dice2;
	private Boolean success;
	

	@JoinColumn(name = "idPlayers", nullable = false)
	@ManyToOne(fetch = FetchType.EAGER, optional = false)
	private Player player;

	public Game() {

	}

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public Integer getDice1() {
		return dice1;
	}

	public void setDice1(Integer dice1) {
		this.dice1 = dice1;
	}

	public Integer getDice2() {
		return dice2;
	}

	public void setDice2(Integer dice2) {
		this.dice2 = dice2;
	}

	public Boolean getSuccess() {
		return success;
	}

	public void setSuccess(Boolean success) {
		this.success = success;
	}

	public Player getPlayer() {
		return player;
	}

	public void setPlayer(Player player) {
		this.player = player;
	}



}