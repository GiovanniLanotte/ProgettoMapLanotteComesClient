package utility;

public class EmptyQueueException extends Exception {
  public EmptyQueueException() {
	super("Errore lettura/cancellazione da una coda vuota");
}
}
