package clueGame;

public class Card {

	private String cardName;
	private CardType type;
	
	public Card(String name, CardType cardType) {
		cardName = name;
		type = cardType;
	}

	public boolean equals(Card card){
		return cardName.equals(card.getName());
	}
	
	public String getName(){
		return cardName;
	}
	
	public CardType getType(){
		return type;
	}
}
