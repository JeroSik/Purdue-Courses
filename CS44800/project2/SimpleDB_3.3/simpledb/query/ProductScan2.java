package simpledb.query;

import java.util.List;

import simpledb.record.TableScan;

/**
 * Write your own version of join algorithm here
 */
public class ProductScan2 implements Scan {
	private Scan s1, s2;
	private Predicate pred;
	private List<String> fields;


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


	public void beforeFirst() {
		s1.beforeFirst();
		s1.next();
		s2.beforeFirst();
	}


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
	
	public boolean isSatisfied() {
		for (Term t : pred.getTerms()) {
			Expression lhs = t.getLHS();
			Expression rhs = t.getRHS();
			
			Constant lhsval;
			Constant rhsval;
			
			// Only lhs is field name
			if (lhs.isFieldName() && !rhs.isFieldName()) {
				if (s1.hasField(lhs.asFieldName())) {
					lhsval = lhs.evaluate(s1);
				} else {
					lhsval = lhs.evaluate(s2);
				}
				
				rhsval = rhs.asConstant();
			// Only rhs is field name
			} else if (!lhs.isFieldName() && rhs.isFieldName()) {
				lhsval = lhs.asConstant();
				
				if (s1.hasField(rhs.asFieldName())) {
					rhsval = rhs.evaluate(s1);
				} else {
					rhsval = rhs.evaluate(s2);
				}
			// Both sides are field names
			} else {
//				if (s1.hasField(lhs.asFieldName())) {
//					lhsval = lhs.evaluate(s1);
//					rhsval = rhs.evaluate(s2);
//				} else {
//					lhsval = lhs.evaluate(s2);
//					rhsval = rhs.evaluate(s1);
//				}
				if (s1.hasField(lhs.asFieldName()) && s2.hasField(rhs.asFieldName())) {
					lhsval = lhs.evaluate(s1);
					rhsval = rhs.evaluate(s2);
				} else if ((s1.hasField(rhs.asFieldName()) && s2.hasField(lhs.asFieldName()))) {
					lhsval = lhs.evaluate(s2);
					rhsval = rhs.evaluate(s1);
				} else {
//					return false;
					continue;
				}
			}

			if (!lhsval.equals(rhsval)) {
				return false;
			}
		}
		return true;
	}


	public int getInt(String fldname) {
		if (s1.hasField(fldname)) {
			return s1.getInt(fldname);
		} else {
			return s2.getInt(fldname);
		}
	}

	public String getString(String fldname) {
		if (s1.hasField(fldname)) {
			return s1.getString(fldname);
		} else {
			return s2.getString(fldname);
		}
	}

	public Constant getVal(String fldname) {
		if (s1.hasField(fldname)) {
			return s1.getVal(fldname);
		} else {
			return s2.getVal(fldname);
		} 
	}


	public boolean hasField(String fldname) {
		return s1.hasField(fldname) || s2.hasField(fldname);
	}

	public void close() {
		s1.close();
		s2.close();
	}
}
