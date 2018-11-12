/**
 * Project 02
 *
 * An infix arithmetic expression evaluator
 *
 * @author Youngsik Yoon, yoon161@purdue.edu
 * @version date of completition
 *
 */

/* Quadratic Probing */
	public class QuadraticProbing<AnyType>
	{
		private static final int DEFAULT_TABLE_SIZE = 13;
		private HashEntry<AnyType> [ ] array; // The array of elements
	
	public static class HashEntry<AnyType>
	{
		/* Initialize the entries here. You can write a constructor for the same */
		public AnyType  element; 
		public boolean isActive;  // For Lazy deletion
		public String toString()
		{
			if(this.element!=null)
				return (String) element;
			else
				return "NULL";
		}
	}


/* Construct the hash table */
	public QuadraticProbing( )
	{
		this( DEFAULT_TABLE_SIZE );
	}

/* Construct the hash table */

	public QuadraticProbing( int size )
	{
		/* allocate memory to hash table */
	}


/* Return true if currentPos exists and is active - Lazy Deletion*/
	public boolean isActive(int position)
	{
		return false;
	}
	

/* Find an item in the hash table. */
	public boolean contains( AnyType x )
	{
		/* Should return the active status of key in hash table */
		return false;
	}


/* Insert into the Hash Table */
	
	public void insert( AnyType x )
	{
		/* Insert an element */
		
	}


/* Remove from the hash table. */
	
	public void remove( AnyType x )	
	{
		/* Lazy Deletion*/
   	}

   
/* Rehashing for quadratic probing hash table */
	private void rehash( )
	{

	}
	

/* Hash Function */
	public int hash( String key, int tableSize )
	{
		/**  Make sure to type cast "AnyType"  to string 
		before calling this method - ex: if "x" is of "AnyType", 
		you should invoke this function as hash((x.toString()), tableSize) */

		int hashVal = 0;
		/* Compute the hash code*/
		return hashVal;
	}

	public int probe(AnyType x)
	{
		/* Return the number of probes encountered for a key */
		int num_of_probes = 0;
		
		return num_of_probes;
	}
	
}

