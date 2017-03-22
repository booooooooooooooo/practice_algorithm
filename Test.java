import java.util.*;

public class Test{
  public static void main(String args[]){
    // int[][] envelopes = new int[][]{{4, 5}, {4,6}, {6, 7}, {2, 3}, {1, 1}};
    int[][] envelopes = new int[][]{
      {1, 2},
      {2, 3},
      {3, 4},
      {3, 5},
      {4, 5},
      {5, 5},
      {5, 6},
      {6, 7},
      {7, 8}
    };
    System.out.println((new  Solution()).maxEnvelopes(envelopes));
  }
}

 class Solution {
    public int maxEnvelopes(int[][] envelopes) {
      List<Pair> evlpList = new ArrayList<Pair>();
      for(int i = 0; i < envelopes.length; i++){
        int x = envelopes[i][0];
        int y = envelopes[i][1];
        evlpList.add(new Pair(x, y));
      }
      //sort evlpList according to x, at the same time, for equal x, make y increasing
      Collections.sort(evlpList, new Comparator<Pair>(){
        @Override
        public int compare(Pair p1, Pair p2){
          if(p1.x != p2.x) return p1.x - p2.x;
          else return -(p1.y - p2.y);
        }
      });
      for(int i = 0; i < evlpList.size(); i++){
        System.out.printf("(%d, %d)\n", evlpList.get(i).x, evlpList.get(i).y);
      }
      //find maxinum increasing subsequece depending on y.
      int[] dp = new int[envelopes.length];
      int pointerToAppendForDp = 0;
      for(Pair p : evlpList){
        System.out.println(Arrays.toString(dp));
        int y = p.y;
        int lowerBound = getLowerBound(dp, pointerToAppendForDp, y);
        if(lowerBound == pointerToAppendForDp){
          dp[pointerToAppendForDp] = y;
          pointerToAppendForDp++;
        }else{
          dp[lowerBound] = y;
        }
      }
      //return result

      return pointerToAppendForDp - 1 + 1;
    }

    public int getLowerBound(int[] dp, int pointerToAppendForDp, int y){
      int low = 0;
      int high = pointerToAppendForDp;//[low, high)
      while(low < high){
        int mid = low + (high - low) / 2;
        if(dp[mid] < y){
          low = mid + 1;
        }else{
          high = mid;
        }
      }
      return low;
    }

    class Pair{
      public int x;
      public int y;
      public Pair(int x, int y){
        this.x = x;
        this.y = y;
      }
    }
}
