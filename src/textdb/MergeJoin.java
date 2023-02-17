package textdb;

import java.io.*;

/**
 * Contains code for performing an external merge join in iterator format.
 */
public class MergeJoin extends Operator {
	private int MERGE_BUFFER_SIZE = 10000; // The number of tuples that can be buffered with the same key.
	private EquiJoinPredicate pred; // A equi-join comparison class that can handle 1 or more attributes
	// Iterator state variables

	public MergeJoin(Operator[] in, EquiJoinPredicate p) {
		super(in, 0, 0);
		pred = p;
	}

	public void init() throws IOException {
		input[0].init();
		input[1].init();

		// Create output relation - keep all attributes of both tuples
		Relation out = new Relation(input[0].getOutputRelation());
		out.mergeRelation(input[1].getOutputRelation());
		setOutputRelation(out);

		// TODO: YOUR SETUP CODE HERE
		Tuple leftBuff = input[0].next();
		Tuple rightBuff = input[1].next();	
	}

	public Tuple next() throws IOException {

		// TODO: YOUR SETUP CODE HERE
		Tuple leftBuff = input[0].next();
		Tuple rightBuff = input[1].next();
		// check if we have moved through all the tuples in a relation
		while(leftBuff != null && rightBuff !=null){
			
		if(pred.isEqual(leftBuff, rightBuff)){
		outputJoinTuple(leftBuff, rightBuff);
		return outputJoinTuple(leftBuff, rightBuff);
		}
		else{
			return null;
		}
	}
		return rightBuff;// to fix error should not return right tuple
		
	}

	public void close() throws IOException {
		super.close();
	}

	private Tuple outputJoinTuple(Tuple left, Tuple right) {
		Tuple t = new Tuple(left, right, getOutputRelation());
		incrementTuplesOutput();
		return t;
	}
}
