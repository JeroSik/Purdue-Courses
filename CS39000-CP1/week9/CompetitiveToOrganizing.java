import java.io.*;
import java.util.*;

public class Solution {
    public static class Event {
        private int start, finish, profit;
        
        private Event(int start, int finish, int profit) {
            this.start = start;
            this.finish = finish;
            this.profit = profit;
        }
        
        private int getFinish() {
            return this.finish;
        }
    }

    public static void main(String[] args) {
        Scanner scan = new Scanner(System.in);
        int t = scan.nextInt();
        for (int i = 0; i < t; i++) {
            int n = scan.nextInt();
            Event[] events = new Event[n];
            for (int j = 0; j < n; j++) {
                events[j] = new Event(scan.nextInt(), scan.nextInt(), scan.nextInt());
            }
            Arrays.sort(events, new Comparator<Event>() {
                @Override
                public int compare(Event e1, Event e2) {
                    if (e1.getFinish() < e2.getFinish())
                        return -1;  
                    else if (e1.getFinish() > e2.getFinish())
                        return 1;
                    else
                        return 0;
                }
            });
            System.out.println(findMaxProfit(events));
        }
    }
    
    public static int findMaxProfit(Event[] events) {
        int[] dp = new int[events.length];
        
        dp[0] = events[0].profit;
        for (int i = 1; i < events.length; i++){
            dp[i] = Math.max(events[i].profit, dp[i - 1]);
            for (int j = i - 1; j >= 0; j--) {
                if (events[j].finish <= events[i].start){
                    dp[i] = Math.max(dp[i], events[i].profit + dp[j]);
                    break;
                }
            }
        }
        int result = dp[0];
        for (int i : dp) {
            if (result < i) {
                result = i;
            }
        }
        return result;
    }
}
