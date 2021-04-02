package simpledb.plan;

import java.util.List;

import simpledb.query.Predicate;
import simpledb.query.Scan;
import simpledb.record.Schema;

/** A Plan class corresponding to the <i>product</i>
 * relational algebra operator that determines the
 * most efficient ordering of its inputs.
 * @author Edward Sciore
 */
public class OptimizedProductPlan implements Plan {
	private Plan bestplan;

	public OptimizedProductPlan(Plan p1, Plan p2, Predicate pred, List<String> fields) {
		Plan prod1 = new ProductPlan(p1, p2);
		prod1 = new SelectPlan(prod1, pred);
		prod1 = new ProjectPlan(prod1, fields);
		
		Plan prod2 = new ProductPlan(p2, p1);
		prod2 = new SelectPlan(prod2, pred);
		prod2 = new ProjectPlan(prod2, fields);

		Plan prod3 = new ProductPlan2(p1, p2, pred, fields);
//		prod3 = new ProjectPlan(prod3, fields);
		
		int b1 = prod1.blocksAccessed();
		int b2 = prod2.blocksAccessed();
		int b3 = prod3.blocksAccessed();

		System.out.printf("[OptimizedProductPlan] b1: %d\tb2: %d\tb3: %d\n", b1, b2, b3);

//		bestplan = (b1 < b2) ? prod1 : prod2;   //find the best plan comparing between b1,b2 and b3	
//		bestplan = (b1 < b2) ? (b1 < b3 ? prod1 : prod3) : (b2 < b3 ? prod2 : prod3); //find the best plan comparing between b1,b2 and b3
		if (b1 < b2 && b1 < b3) {
			bestplan = prod1;
		} else if (b2 < b3) {
			bestplan = prod2;
		} else {
			bestplan = prod3;
		}
		bestplan = prod3;
	}

	public Scan open() {
		return bestplan.open();
	}

	public int blocksAccessed() {
		return bestplan.blocksAccessed();
	}

	public int recordsOutput() {
		return bestplan.recordsOutput();
	}

	public int distinctValues(String fldname) {
		return bestplan.distinctValues(fldname);
	}

	public Schema schema() {
		return bestplan.schema();
	}
}
