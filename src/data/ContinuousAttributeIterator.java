package data;


import java.util.Iterator;
/*Definire la classe ContinuousAttributeIterator che implementa 
 * l’interfaccia Iterator<Float>. Tale classe realizza l’iteratore 
 * che itera sugli elementi della sequenza composta da numValues valori 
 * reali equidistanti tra di loro (cut points) compresi tra min e max 
 * ottenuti per mezzo di discretizzazione. La classe implementa i 
 * metodi della interfaccia generica Iterator<T> tipizzata con   Float 
*/
public class ContinuousAttributeIterator implements Iterator<Float> {
	private float min; //minimo
	private float max;
	private int j=0;
	private int numValues;
	private float cut_point;
	
	public ContinuousAttributeIterator(float min, float max,int numValues) {
		this.min=min;
		this.max=max;
		this.numValues=numValues;
	}	
	@Override
	public boolean hasNext() {
		return j<=numValues;
	}

	@Override
	public Float next() {
			cut_point=min+(j*((max-min)/numValues));
			j++;
	
		
		return cut_point;
	}
	
	public void romove(){
		
	}

	

}
