import java.util.*;

public class TricolorPaintings {
    public static int count = 0;

    public static void getAllComb(HashMap<Integer, ArrayList<Integer>> same, HashMap<Integer, ArrayList<Integer>> different, int n, int[] painting, int index) {   
        if (index == n) {
            if (isValid(n, same, different, painting)) {
                count++;
            }
            return;
        } 
  
        for (int i = 1; i <= 3; i++) {
            painting[index] = i;
            getAllComb(same, different, n, painting, index + 1); 
        }

        return;
    }

    public static boolean isValid(int n, HashMap<Integer, ArrayList<Integer>> same, HashMap<Integer, ArrayList<Integer>> different, int[] painting) {
        for (Map.Entry mapElement : same.entrySet()) { 
            int x = (int)mapElement.getKey(); 
            ArrayList<Integer> temp = (ArrayList) mapElement.getValue();
            for (int y : temp) {
                if (painting[x] != painting[y]) {
                    return false;
                }
            }
        }

        for (Map.Entry mapElement : different.entrySet()) { 
            int x = (int)mapElement.getKey(); 
            ArrayList<Integer> temp = (ArrayList) mapElement.getValue();
            for (int y : temp) {
                if (painting[x] == painting[y]) {
                    return false;
                }
            }
        }

        return true;
    }

    public static void getPaintings(HashMap<Integer, ArrayList<Integer>> same, HashMap<Integer, ArrayList<Integer>> different, int n) {
        getAllComb(same, different, n, new int[n], 1);

        System.out.println(count);
    }

    public static void main(String[] args) {
        Scanner scan = new Scanner(System.in);
        int N = scan.nextInt();
        int K = scan.nextInt();

        int[] colors = new int[N];
        HashMap<Integer, ArrayList<Integer>> same = new HashMap<Integer, ArrayList<Integer>>();
        HashMap<Integer, ArrayList<Integer>> different = new HashMap<Integer, ArrayList<Integer>>();
        for (int i = 1; i <= N; i++) {
            same.put(i, new ArrayList<Integer>());
            different.put(i, new ArrayList<Integer>());
        }


        for (int i = 0; i < K; i++) {
            char c = scan.next().charAt(0);
            int x = scan.nextInt();
            int y = scan.nextInt();
            x--;
            y--;

            if (c == 'S') {
                same.get(x).add(y);
            } else {
                different.get(x).add(y);
            }
        }

        getPaintings(same, different, N);
    }
}