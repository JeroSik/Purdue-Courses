import java.sql.SQLOutput;
import java.util.*;
import java.io.*;

/**
 * Project 04
 * Family Tree data structure to encode family relations
 *
 * @author Youngsik Yoon, yoon161@purdue.edu
 * @version 11/21/2018
 *
 */
public class FamilyTree {
    /**
     * Person Class - Creates a person
     * @parameters
     */
    private class Person {
        private String name;
        private ArrayList<String> parent;

        private Person(String name) {
            this.name = name;
            this.parent = new ArrayList<String>();
        }
    }

    /**
     * Declare necessary variables to describe your Tree
     * Each Node in the Tree represents a person
     * You can declare other classes if necessary
     */
    private HashMap<String, Person> tree;
    public FamilyTree() {
        this.tree = new HashMap<>();
    }

    public void addPerson(Person person) {
        if (!tree.containsKey(person.name) && !person.name.equals("")) {
            tree.put(person.name, person);
        }
    }

    /**
     * BFS through family tree to find relation between person1 and person2
     * @param person1 - first name in query
     * @param person2 - second name in query
     * @return - output String of relation between person1 and person2
     */
    public String findRelation(String person1, String person2) {
        // Marks father and mother of current queues
        String father1 = "";
        String mother1 = "";
        String father2 = "";
        String mother2 = "";

        // Data structures to find common ancestors
        HashSet<String> set1 = new HashSet<String>();
        HashSet<String> set2 = new HashSet<String>();
        ArrayList<String> common = new ArrayList<String>();

        // Queues for BFS
        Queue<String> queue1 = new LinkedList<String>();
        Queue<String> queue2 = new LinkedList<String>();

        // Add initial people to queues
        queue1.add(person1);
        queue2.add(person2);

        // Go through entire family tree with BFS
        while (!queue1.isEmpty() || !queue2.isEmpty()) {
            // Check Ancestors
            Iterator<String> i = set1.iterator();
            while (i.hasNext()) {
                String s = i.next();
                if (set2.contains(s)) {
                    common.add(s);
                }
            }
            if (common.size() != 0) {
                return orderAncestor(common);
            }

            // Get first queue for each and check for empty queue
            Person curr1 = null;
            if (!queue1.isEmpty()) {
                curr1 = tree.get(queue1.poll());
            }
            Person curr2 = null;
            if (!queue2.isEmpty()) {
                curr2 = tree.get(queue2.poll());
            }

            // Set father and mother for both people
            if (curr1 != null && curr1.parent.size() != 0) {
                father1 = curr1.parent.get(0);
                queue1.add(father1);
                set1.add(father1);
                mother1 = curr1.parent.get(1);
                queue1.add(mother1);
                set1.add(mother1);
            }
            if (curr2 != null && curr2.parent.size() != 0) {
                father2 = curr2.parent.get(0);
                queue2.add(father2);
                set2.add(father2);
                mother2 = curr2.parent.get(1);
                queue2.add(mother2);
                set2.add(mother2);
            }

            // Check Descendants
            if (father1.equals(person2) || mother1.equals(person2)) {
                return person1 + " is a descendant of " + person2;
            } else if (father2.equals(person1) || mother2.equals(person1)) {
                return person2 + " is a descendant of " + person1;
            }
        }

        // Unrelated is not a descendant or ancestor
        return "unrelated";
    }

    /**
     * Create proper output string from names of all ancestors
     * @param ancestors - arraylist of Strings containing all names of ancestors
     * @return ouput string for ordered ancestors
     */
    public String orderAncestor(ArrayList<String> ancestors) {
        String list = "";
        Collections.sort(ancestors);
        for (int i = 0; i < ancestors.size() - 1; i++) {
            list += ancestors.get(i) + " ";
        }
        list += ancestors.get(ancestors.size() - 1);
        return list;
    }

    /**
     * @input directory or filename of input file. This file contains the information necessary to build the child
     * parent relation. Throws exception if file is not found
     * @param familyFile
     * @throws Exception
     */
    public void buildFamilyTree(String familyFile) throws Exception {
        // Create necessary readers
        FileReader fr = new FileReader(familyFile);
        BufferedReader bfr = new BufferedReader(fr);

        // Read family line until gone through entire file
        String read = bfr.readLine();
        while(read != null) {
            // Split the family into two parents and any children
            String[] family = read.trim().split("\\s+");
            Person husband = new Person(family[0]);
            Person wife = new Person(family[1]);
            addPerson(husband);
            addPerson(wife);
            for (int i = 2; i < family.length; i++) {
                Person child = new Person(family[i]);
                child.parent.add(family[0]);
                child.parent.add(family[1]);
                addPerson(child);
            }

            // Move to next line
            read = bfr.readLine();
        }

        bfr.close();
    }

    /**
     * @input directory or filename of Query and Output.
     * queryFile contains the queries about the tree.
     * The output of this query should be written in file outputfile.
     * @param queryFile
     * @param outputFile
     * @throws Exception
     */
    public void evaluate(String queryFile,String outputFile) throws Exception {
        // Create necessary readers and writers
        FileReader fr = new FileReader(queryFile);
        BufferedReader bfr = new BufferedReader(fr);
        FileWriter fw = new FileWriter(outputFile);

        // Read lines until no more lines in query
        String read = bfr.readLine();
        while(read != null) {
            // Split the list into two people
            String[] list = read.trim().split("\\s+");
            String person1 = list[0];
            String person2 = list[1];

            // Write into new file relation and move to next line
            fw.write(findRelation(person1, person2) + "\n");
            read = bfr.readLine();
        }

        fw.close();
        bfr.close();
    }
}