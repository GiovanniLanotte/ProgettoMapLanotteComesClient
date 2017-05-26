package mining;
import java.io.Serializable;

import data.Attribute;

/*Definiamo la classe astratta Item che modella un generico item (coppia attributo-valore, per esempio Outlook=”Sunny”) 
*/
public abstract class Item implements Serializable {
private Attribute attribute; //attributo coinvolto nell'item 
private Object value; //valore assegnato all'attributo

public Item(Attribute attribute, Object value){
	this.attribute=attribute;
	this.value=value;
}

public Attribute getAttribute(){
	return attribute;
}


public Object getValue(){
	return value;
}

public abstract boolean checkItemCondition(Object value);

public String toString(){
	return attribute+"="+value;
}

}
