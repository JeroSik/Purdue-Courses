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
		// Initialize plan 1
		Plan prod1 = new ProductPlan(p1, p2);
		prod1 = new SelectPlan(prod1, pred);
		prod1 = new ProjectPlan(prod1, fields);
		
		// Initialize plan 2
		Plan prod2 = new ProductPlan(p2, p1);
		prod2 = new SelectPlan(prod2, pred);
		prod2 = new ProjectPlan(prod2, fields);

		// Initialize plan 3
		Plan prod3 = new ProductPlan2(p1, p2, pred, fields);
		
		// Calculate block accessed for all three plans
		int b1 = prod1.blocksAccessed();
		int b2 = prod2.blocksAccessed();
		int b3 = prod3.blocksAccessed();

		// Select best plan by comparing values
		if (b1 < b2 && b1 < b3) {
			bestplan = prod1;
		} else if (b2 < b3) {
			bestplan = prod2;
		} else {
			bestplan = prod3;
		}
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
