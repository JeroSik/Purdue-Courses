import java.util.*;

public class Paintings {
    public static HashMap<String, Integer> stringToInt;
    public static HashMap<Integer, String> intToString;
    public static int count;
    public static String favorite;
    public static boolean foundFavorite;

    public static void backtrack(HashMap<Integer, HashSet<Integer>> hideous, int[] painting, int index, HashSet<Integer> usedColors, int n) {
        for (int color = 0; color < n; color++) {
            if (!usedColors.contains(color) && !isHideous(hideous, painting, index, color)) {
                painting[index] = color;
                usedColors.add(color);

                if (index + 1 == n) {
                    count++;
                    if (!foundFavorite) {
                        for (int x : painting) {
                            favorite += intToString.get(x) + " ";
                        }
                        foundFavorite = true;
                    }
                } else {
                    backtrack(hideous, painting, index + 1, usedColors, n);
                }

                painting[index] = -1;
                usedColors.remove(color);
            }
        }
    }

    public static boolean isHideous(HashMap<Integer, HashSet<Integer>> hideous, int[] painting, int index, int color) {
        return index == 0 ? false : hideous.get(painting[index - 1]).contains(color);
    }

    public static void getPaintings(HashMap<Integer, HashSet<Integer>> hideous, int n) {
        count = 0;
        foundFavorite = false;
        favorite = "";

        backtrack(hideous, new int[n], 0, new HashSet<Integer>(), n);
        System.out.println(count);
        System.out.println(favorite);
    }2020

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
            getPaintings(hideous, N);
        }
    }

    public static void init_converters(String[] colors, int N) {
        stringToInt = new HashMap<String, Integer>();
        intToString = new HashMap<Integer, String>();
        for (int i = 0; i < N; i++) {
            stringToInt.put(colors[i], i);
            intToString.put(i, colors[i]);
        }
    }
}