package junit;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.BeforeAll;

import textdb.Attribute;
import textdb.EquiJoinPredicate;
import textdb.MergeJoin;
import textdb.MergeSort;
import textdb.Operator;
import textdb.Relation;
import textdb.SortComparator;
import textdb.TextFileScan;

/**
 * Tests merge join implementation.
 */
public class TestMergeJoinBonus {

	// Change this if needed to indicate where the data and output directories are.
	public static String DATA_DIR = "bin/data/";
	public static String OUTPUT_DIR = "bin/output/";

	private static Relation r;

	@BeforeAll
	public static void init() throws Exception {
		Attribute[] attrs = new Attribute[5];

		attrs[0] = new Attribute("key", Attribute.TYPE_INT, 0);
		attrs[1] = new Attribute("seq", Attribute.TYPE_INT, 0);
		attrs[2] = new Attribute("v1", Attribute.TYPE_INT, 0);
		attrs[3] = new Attribute("v2", Attribute.TYPE_INT, 0);
		attrs[4] = new Attribute("text", Attribute.TYPE_STRING, 100);

		r = new Relation(attrs);
	}

	@Test
	public void testMediumJoin() {
		System.out.println("\n\nTesting medium merge join.");
		TextFileScan r1Scan = new TextFileScan(DATA_DIR + "mediumInputLeft.txt", r);
		TextFileScan r2Scan = new TextFileScan(DATA_DIR + "mediumInputRight.txt", r);
		SortComparator sorter = new SortComparator(new int[] { 0 }, new boolean[] { true });
		EquiJoinPredicate ep = new EquiJoinPredicate(new int[] { 0 }, new int[] { 0 }, EquiJoinPredicate.INT_KEY);
		MergeSort r1Sort = new MergeSort(r1Scan, 2000, 10, sorter);
		MergeSort r2Sort = new MergeSort(r2Scan, 2000, 10, sorter);
		MergeJoin mjoin = new MergeJoin(new Operator[] { r1Sort, r2Sort }, ep);

		int count = TestScan.compareOperatorWithOutput(mjoin, OUTPUT_DIR + "mergeOutputMedium.txt");
		assertEquals(127, count);
	}

	@Test
	public void testLargeJoin() {
		System.out.println("\n\nTesting large merge join.");
		TextFileScan r1Scan = new TextFileScan(DATA_DIR + "largeInputLeft.txt", r);
		TextFileScan r2Scan = new TextFileScan(DATA_DIR + "largeInputRight.txt", r);
		SortComparator sorter = new SortComparator(new int[] { 0 }, new boolean[] { true });
		EquiJoinPredicate ep = new EquiJoinPredicate(new int[] { 0 }, new int[] { 0 }, EquiJoinPredicate.INT_KEY);
		MergeSort r1Sort = new MergeSort(r1Scan, 2000, 10, sorter);
		MergeSort r2Sort = new MergeSort(r2Scan, 2000, 10, sorter);
		MergeJoin mjoin = new MergeJoin(new Operator[] { r1Sort, r2Sort }, ep);

		int count = TestScan.compareOperatorWithOutput(mjoin, OUTPUT_DIR + "mergeOutputLarge.txt");
		assertEquals(191, count);
	}
}
