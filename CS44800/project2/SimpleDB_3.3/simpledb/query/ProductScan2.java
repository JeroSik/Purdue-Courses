package simpledb.query;

import java.util.List;

import simpledb.record.TableScan;

/**
 * Write your own version of join algorithm here
 */
public class ProductScan2 implements Scan {
	// Initialize global variables
	private Scan s1, s2;
	private Predicate pred;
	private List<String> fields;

	/**
	 * Create a product scan having the two underlying scans.
	 * @param s1 the LHS scan
	 * @param s2 the RHS scan
	 * @param pred the predicates used for the selection
	 * @param fields the fields used for the projection
	 */
	public ProductScan2(Scan s1, Scan s2, Predicate pred, List<String> fields) {
		// Get the query data predicates and field list
		this.pred = pred;
		this.fields = fields;

		// Get both scans
		this.s1 = s1;
		this.s2 = s2;

		// Call beforeFirst()
		beforeFirst();
	}

	/**
	 * Position the scan before its first record.
	 * In particular, the LHS scan is positioned at 
	 * its first record, and the RHS scan
	 * is positioned before its first record.
	 */	
	public void beforeFirst() {
		s1.beforeFirst();
		s1.next();
		s2.beforeFirst();
	}

	/**
	 * Move the scan to the next record.
	 * The method moves to the next RHS record, if possible.
	 * Afterwards, check if the record satisfies the predicates. Otherwise, move to the next RHS record.
	 * If no longer possible to move to next RHS record, it moves to the next LHS record and the first RHS record.
	 * If there are no more LHS records, the method returns false.
	 */
	public boolean next() {
		do {
			while (s2.next()) {
				if (isSatisfied()) {
					return true;
				}
			}
			s2.beforeFirst();
		} while (s1.next());

		return false;
	}

	/**]
	 * Helper function which returns true if the current records satisfy the predicates given.
	 * Method will iterate through all predicates. Records must satisfy all predicates to return true.
	 * For each term, the method evaluates the LHS and RHS values and compares the two.
	 */
	public boolean isSatisfied() {
		// Iterate through all terms
		for (Term t : pred.getTerms()) {
			// Get lhs and rhs expressions from term
			Expression lhs = t.getLHS();
			Expression rhs = t.getRHS();

			// Initialize lhs and rhs values
			Constant lhsval;
			Constant rhsval;

			// Check the three cases
			// 1. Only LHS is a field name
			// 2. Only RHS is a field name
			// 3. Both sides are field names
			if (lhs.isFieldName() && !rhs.isFieldName()) {
				// Check if s1 contains the field name and set lhs value
				if (s1.hasField(lhs.asFieldName())) {
					lhsval = lhs.evaluate(s1);
				} else {
					lhsval = lhs.evaluate(s2);
				}

				// Get rhs constant value
				rhsval = rhs.asConstant();
			} else if (!lhs.isFieldName() && rhs.isFieldName()) {
				// Get lhs constant value
				lhsval = lhs.asConstant();

				// Check if s1 contains the field name and set rhs value
				if (s1.hasField(rhs.asFieldName())) {
					rhsval = rhs.evaluate(s1);
				} else {
					rhsval = rhs.evaluate(s2);
				}
			} else {
				// Check which side has the field names and assign accordingly
				if (s1.hasField(lhs.asFieldName()) && s2.hasField(rhs.asFieldName())) {
					lhsval = lhs.evaluate(s1);
					rhsval = rhs.evaluate(s2);
				} else if ((s1.hasField(rhs.asFieldName()) && s2.hasField(lhs.asFieldName()))) {
					lhsval = lhs.evaluate(s2);
					rhsval = rhs.evaluate(s1);
				} else {
					continue;
				}
			}

			// Compare the two values
			if (!lhsval.equals(rhsval)) {
				return false;
			}
		}
		return true;
	}

	/** 
	 * Return the integer value of the specified field.
	 * The value is obtained from whichever scan
	 * contains the field.
	 */
	public int getInt(String fldname) {
		if (s1.hasField(fldname)) {
			return s1.getInt(fldname);
		} else {
			return s2.getInt(fldname);
		}
	}

	/** 
	 * Returns the string value of the specified field.
	 * The value is obtained from whichever scan
	 * contains the field.
	 */
	public String getString(String fldname) {
		if (s1.hasField(fldname)) {
			return s1.getString(fldname);
		} else {
			return s2.getString(fldname);
		}
	}

	/** 
	 * Return the value of the specified field.
	 * The value is obtained from whichever scan
	 * contains the field.
	 */
	public Constant getVal(String fldname) {
		if (s1.hasField(fldname)) {
			return s1.getVal(fldname);
		} else {
			return s2.getVal(fldname);
		} 
	}

	/**
	 * Returns true if the specified field is in
	 * either of the underlying scans.
	 */
	public boolean hasField(String fldname) {
		return s1.hasField(fldname) || s2.hasField(fldname);
	}

	/**
	 * Close both underlying scans.
	 */
	public void close() {
		s1.close();
		s2.close();
	}
}
