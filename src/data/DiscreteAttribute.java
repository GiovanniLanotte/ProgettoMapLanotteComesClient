package data;
import java.util.Iterator;
/*Definiamo la classe DiscreteAttribute che estende la classe 
 * Attribute e modella un attributo discreto rappresentando 
 * l’insieme di valori distinti del relativo dominio. */
public class DiscreteAttribute extends Attribute {
private String values[]; //array di stringhe per ciascun valore discreto

/*la classe DiscreteAtribute invoca il costruttore della classe madre e  
avvalora l'array values[ ] con i valori discreti in input.*/
public DiscreteAttribute(String name, int index, String values[]){
	super(name,index);  //assume come input valori per nome simbolico per attributo e valori disreti che ne costituiscono il dominio
	this.values=values;//passo un riferimento
}

public int getNumberOfDistinctValues(){ //metodo che restituisce la cardinalità del membro values
	return values.length; //numero di valori discreti dell'attributo
}

public String getValue(int index){//metodo che restituisce in posizione i del membro values
	return values[index]; //restituisce un valore nel dominio dell'attributo
}

}
