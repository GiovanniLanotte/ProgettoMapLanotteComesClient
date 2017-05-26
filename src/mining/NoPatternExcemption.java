package mining;

public class NoPatternExcemption extends Exception {
public NoPatternExcemption(FrequentPattern fp){
	super("nessuna regola confidente è generata da un "+fp);
}
}
