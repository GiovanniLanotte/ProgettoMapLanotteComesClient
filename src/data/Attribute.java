package data;

import java.io.Serializable;

/*Definiamo la classe astratta Attribute che modella un generico attributo discreto o continuo.*/
public class Attribute implements Serializable{
private String name;  //nome simbolico dell'attributo
private int index;  //identificativo numerico dell'attributo

//name e index sono private perchè variabili dell'oggetto

public Attribute(String name, int index){ 
	//Questo metodo inizializza i valori dei membri name e index
	//assume come input valori per nome simbolico e identificativo numerico dell'attributo
	this.name=name;
	this.index=index;
}

public String getName(){ //metodo che restituisce il valore del membro name
	return name; //nome dell'attributo
}

public int getIndex(){ //metodo che restituisce il valore del membro index
	return index;  //identificativo numerico dell'attributo
}

public String toString(){ //metodo che restituisce il valore del membro name
	return name;
}
}