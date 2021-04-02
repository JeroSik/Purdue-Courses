package simpledb.buffer;

import java.sql.*;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Random;

import simpledb.jdbc.embedded.EmbeddedDriver;

public class QueryTest {

	public static void main(String[] args) {
		int n = 1000;
		
		long start = System.currentTimeMillis();
		
		createTable(n);
		
		long end = System.currentTimeMillis();
		
//		System.out.println("--- Write ---");
//		System.out.printf("Entires: %d\tTime: %d\n", n, end - start);
		
		String url = "jdbc:simpledb:querytestdb";
		String qry = "select nid, nn "
	            		+ "from numbers "
            			+ "where nid = nn ";

		Driver d = new EmbeddedDriver();
		
		start = System.currentTimeMillis();

		try (Connection conn = d.connect(url, null);
				Statement stmt = conn.createStatement();
				ResultSet rs = stmt.executeQuery(qry)) {
			while (rs.next()) {	
				continue;
			}
		}
		catch(Exception e) {
			e.printStackTrace();
		}

		end = System.currentTimeMillis();

//		System.out.println("--- Read ---");
//		System.out.printf("Entires: %d\tTime: %d\n", n, end - start);		
	}
	
	private static void createTable(int n) {
		Driver d = new EmbeddedDriver();
		String url = "jdbc:simpledb:querytestdb";
		
		try (Connection conn = d.connect(url, null);
				Statement stmt = conn.createStatement()) {
			String s = "create table NUMBERS(nid int, nn int)";
			stmt.executeUpdate(s);

			s = "insert into NUMBERS(nid, nn) values ";
			String[] v = new String[n];
			ArrayList<Integer> l = new ArrayList<Integer>();
			Random r = new Random(12345);
			for (int i = 0; i < n; i++) {
				l.add(i);
			}
			Collections.shuffle(l, r);
			for (int i = 0; i < n; i++) {
				v[i] = "(" + i + ", " + l.get(i) + ")";
			}

			for (int i=0; i<v.length; i++)
				stmt.executeUpdate(s + v[i]);
		} catch(SQLException e) {
			e.printStackTrace();
		}
	}
}
