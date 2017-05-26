package data;
/* Definiamo la classe ContinuousAttribute  che estende la classe Attribute e modella un attributo continuo (numerico),
 * rappresentandone il dominio [min,max] */

import java.util.Iterator;

public class ContinuousAttribute extends Attribute implements Iterable<Float>{
private float max;
private float min;  //max e mi sono gli estremi dell'iintervallo che definisce il dominio

public ContinuousAttribute(String name, int index, float min, float max){
	/*ConitnuousAttribute è un metodo, che invoca il costruttore della classe madre
	 * e avvalora i membri ed ha come input il nome dell'attributo 
	 * e l'dentificativo numerico */
	super(name,index); 
	this.max=max;
	this.min=min;
}

public float getMin(){ //metodo che restituisce il valore del membro min
	return min; //output: estremo inferiore dell'intervallo
}

public float getMax(){ //metodo che restituisce il valore del membro max
	return max;  //output:estremo superiore dell'intervallo
}

@Override 
public Iterator<Float> iterator() {
	int numValue=5;
	return new ContinuousAttributeIterator(min,max,numValue);
}

}
