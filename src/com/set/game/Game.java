package com.set.game;


import java.util.ArrayList;
import java.util.Random;
import java.util.List;

import com.set.game.Card;




public class Game {
	
	
	
	public Card[] field;
	public List<Card> deck;
	public int[] players;
	public int[] player_scores;
	public boolean gameover;
	
	public Game(int[] in_players){
		players = in_players;
		player_scores = new int[players.length];
		gameover = false;
		deck = new ArrayList<Card>();
		for (int i = 0; i < 3; i++) {
			for (int j = 0; j < 3; j++) {
				for (int k = 0; k < 3; k++) {
					for (int l = 0; l < 3; l++) {
						deck.add(new Card(i,j,k,l));						
					}}}
		}
		field = new Card[0];
	}
	
	
	// started like this to make sure that the opening field has a set, otherwise getting it to
	// update is tricky
	public void startGame(){
		field = new Card[12];
		Random rand = new Random();
		for (int i = 0; i < field.length; i++){
			int s = deck.size();
			int r = rand.nextInt(s);
			Card new_card = deck.remove(r);
			field[i] = new_card;
		}
		addUntilSetExists();
		
	}
	
	
	public boolean isSet(int[] set) {
		if ((field[set[0]].color+field[set[1]].color+field[set[2]].color)%3 != 0){
			return false;
		} if ((field[set[0]].shape+field[set[1]].shape+field[set[2]].shape)%3 != 0){
			return false;
		} if ((field[set[0]].number+field[set[1]].number+field[set[2]].number)%3 != 0){
			return false;
		} if ((field[set[0]].shading+field[set[1]].shading+field[set[2]].shading)%3 != 0){
			return false;
		} else { return true; }	
	}
	
	// returns the indices of a set in the field
	public int[] findSet() {
		int s = field.length;
		for (int i = 0; i < s-2; i++ ){
			for (int j = i+1; j < s-1; j++ ){
				for (int k = j+1; k < s; k++ ){
					if (isSet(new int[] {i,j,k})){
						int[] result = {i,j,k};
						return result;
					}
		}}}
		int[] result = {-1,-1,-1};
		return result;
	}
	
	public boolean setExists(){
		int[] result = findSet();
		if (result[0] == -1) {
			return false;
		}
		else { return true; }
	}
	
	public Card drawCard(){
		Random rand = new Random(); 
		int s = deck.size();
		int r = rand.nextInt(s);
		Card new_card = deck.remove(r);
		return new_card;
	}
	
	public void addCards(){
		Card[] newField = new Card[field.length+3];
		for (int i = 0; i < field.length; i++) { 
			newField[i] = field[i];			
		}
		for (int i = field.length; i < newField.length; i++) { 
			Card new_card = drawCard();
			newField[i] = new_card;			
		}
		field = newField;
	}
	
	public void addUntilSetExists(){
		while (!setExists() && deck.size() > 0){
			addCards();	
		}

	}
	
	public void replaceSet(int[] set){
		// if are at 12 cards out, then replace the set
		if (field.length == 12){
			for (int i = 0; i <3; i++) {
				Card new_card = drawCard();
				field[set[i]] = new_card;		
			}		
		}
		// if we are somehow at more than 12 cards then remove the set 
		else {
			removeSet(set);
		}
		// after replacement/removal, make sure there is a set on the board unless we are out of cards
		addUntilSetExists();
	}
	
	public void removeSet(int[] set){
		Card[] newField = new Card[field.length-3];
		for (int i = 0; i <3; i++) {
			field[set[i]] = null;		
		}	
		int index = 0;
		for (int i = 0; i < field.length; i++) {
			Card card = field[i];
			if (card != null) {
				newField[index] = card;
				index++;
			}
		}
		field = newField;
	}

	
	public boolean handleSelection(int[] set){
		// if the selected cards are a set
		if (isSet( set )) {
			
			// check if we need to remove or replace the cards
			if (deck.size() == 0 ){  
				removeSet(set);
			} else {
				replaceSet(set);				
			}
			//TODO need to keep track of score
			
			
			//check for gameover
			if (deck.size() == 0 && !setExists()){
				gameover = true;
			}	
			return true;
		}
		return false;
	}
	
}
		
	
	
