/* Definire la classe eccezione EmptySetException nel package data per modellare l’eccezione 
 * che occorre qualora l'insieme di training fosse vuoto (non contiene transazioni/esempi). 
 * Tale eccezione è sollevata in public static LinkList frequentPatternDiscovery() e 
 * gestita in  MainTest*/
package data;

public class EmptySetException extends Exception{
public EmptySetException() {
	super("insieme di training vuoto");
}
}



