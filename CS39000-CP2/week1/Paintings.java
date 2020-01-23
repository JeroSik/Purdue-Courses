import java.util.*;

public class Paintings {
    public static HashMap<String, Integer> stringToInt;
    public static HashMap<Integer, String> intToString;
    public static int count;
    public static String favorite;
    public static boolean foundFavorite;

    public static void backtrack(HashMap<String, HashSet<String>> hideous) {
        for (int color = 0; color < n; color++) {
            if (remaining.contains(color) && !isHideous(painting, color, hideous)) {
                // Add new color
                painting.push(color);
                remaining.remove(color);
                remaining.removeAll(hideous.get(color));

                // if all colors are already used
                // if not proceed to pick the rest
                if (remaining.size() == 0) {
                    count++;
                    if (!foundFavorite) {
                        favorite = ;
                        foundFavorite = true;
                    }
                } else {
                    backtrack(hideous);
                }

                // Remove color
                painting.pop();
                remaining.add(color);
                remaining.add(hideous.get(color));
            }
        }
    }

    public static boolean isHideous(ArrayList<Integer> nums, HashMap<Integer, HashSet<Integer>> hideous, int n) {
        for (int i = 0; i < n - 1; i++) {
            int curr = nums.get(i);
            int next = nums.get(i+1);
            if (hideous.get(curr).contains(next)) {
                return true;
            }
        }

        return false;
    }

    public static void getPaintings(int n, HashMap<Integer, HashSet<Integer>> hideous) {
        ArrayList<Integer> nums = new ArrayList<Integer>();
        for (int i = 0; i < n; i++) {
            nums.add(i);
        }

        count = 0;
        favoriteExist = false;
        favorite = "";

        backtrack(n, nums, 0, hideous);

        System.out.println(count);
        System.out.println(favorite);
    }

    public static void init_converters(String[] colors, int N) {
        stringToInt = new HashMap<String, Integer>();
        intToString = new HashMap<Integer, String>();
        for (int i = 0; i < N; i++) {
            stringToInt.put(colors[i], i);
            intToString.put(i, colors[i]);
        }
    }

    public static void main(String[] args) {
        Scanner scan = new Scanner(System.in);
        int T = scan.nextInt();

        for (int i = 0; i < T; i++) {
            int N = scan.nextInt();
            scan.nextLine();

            String[] colors = new String[N];
            HashMap<Integer, HashSet<Integer>> hideous = new HashMap<Integer, HashSet<Integer>>();

            for (int j = 0; j < N; j++) {
                colors[j]= scan.next();
                hideous.put(j, new HashSet<Integer>());
            }
            init_converters(colors, N);

            int M = scan.nextInt();
            scan.nextLine();
            
            for (int j = 0; j < M; j++) {
                int color1 = stringToInt.get(scan.next());
                int color2 = stringToInt.get(scan.next());

                hideous.get(color1).add(color2);
                hideous.get(color2).add(color1);
            }

            getPaintings(N, hideous);
        }
    }

    // public static void backtrack(int n, ArrayList<Integer> nums, int first, HashMap<Integer, HashSet<Integer>> hideous) {
    //     if (first == n) {
    //         if (!isHideous(nums, hideous, n)) {
    //             if (!favoriteExist) {
    //                 for (int x : nums) {
    //                     favorite += intToString.get(x) + " ";
    //                 }

    //                 favoriteExist = true;
    //             }
    //             count++;
    //         }
    //     }

    //     for (int i = first; i < n; i++) {
    //         Collections.swap(nums, first, i);
    //         backtrack(n, nums, first + 1, hideous);
    //         Collections.swap(nums, first, i);
    //     }
    // }

    // public static int backtrack(String[] painting, int index, int count, String[] colors, HashMap<String, HashSet<String>> hideous) {
    //     for (int col = 0; col < n; col++) {
    //         if (isNotHideous(row, col, n, colors hideous)) {
    //             // place_queen
    //             rows[col] = 1;
    //             hills[row - col + 2 * n] = 1;  // "hill" diagonals
    //             dales[row + col] = 1;   //"dale" diagonals    

    //             // if all colors are already used
    //             // if not proceed to pick the rest
    //             if (index + 1 == colors.length) {
    //                 count++;
    //             } else {
    //                 count = backtrack(row + 1, count, n, colors, hideous);
    //             }

    //             // Remove color
    //             painting[index] = 0;
    //         }
    //     }

    //     return count;
    // }
}