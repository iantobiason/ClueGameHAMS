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
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((cardName == null) ? 0 : cardName.hashCode());
		result = prime * result + ((type == null) ? 0 : type.hashCode());
		return result;
	}
	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Card other = (Card) obj;
		if (cardName == null) {
			if (other.cardName != null)
				return false;
		} else if (!cardName.equals(other.cardName))
			return false;
		if (type != other.type)
			return false;
		return true;
	}
	public String getName(){
		return cardName;
	}
	
	public CardType getType(){
		return type;
	}
}
