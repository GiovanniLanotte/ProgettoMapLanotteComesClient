package utility;
/*Definiamo la classe Queue che modella una struttura coda che è poi  usata come contenitore a modalità FIFO per i 
 * pattern frequenti scoperti a livello k da usare per generare i pattern candidati a livello k+1*/
public class Queue <T> implements Cloneable {

	private Record<T> begin = null;

	private Record<T> end = null;
	
	private class Record<R> {

 		public Object elem;

 		public Record<R> next;

		public Record(Object e) {
			this.elem = e; 
			this.next = null;
		}
	}
	

	public boolean isEmpty() {
		return this.begin == null;
	}

	public void enqueue(Object e) {
		if (this.isEmpty())
			this.begin = this.end = new Record<T>(e);
		else {
			this.end.next = new Record<T>(e);
			this.end = this.end.next;
		}
	}


	public Object first() throws EmptyQueueException{
		if(this.isEmpty()){
			throw new EmptyQueueException();
		}
		return this.begin.elem;
	}

	public void dequeue() throws EmptyQueueException{
		if(this.begin==this.end){
			if(this.begin==null)
				throw new EmptyQueueException();
			else
				this.begin=this.end=null;
		}
		else{
			begin=begin.next;
		}
		
	}
	
	public Object clone(){
		Object o = null;
		try {
			o= super.clone();
		} catch (CloneNotSupportedException e) {
		
			System.err.println("La clonazione non è avvenuta correttamente");
		}
		return o;
	}

}