class Solution {
    public int stoneGameVIII(int[] stones) {
        int n = stones.length;
        int[] prefix = new int[n];

        prefix[0] = stones[0];
        for(int i = 1; i < n; i++) {
            prefix[i] = prefix[i-1] + stones[i];
        }

        // If alice choose all the stones
        int best = prefix[n - 1];

        // process from right to left
        for(int i = n - 2; i > 0; i--) {
            best = Math.max(best, prefix[i] - best);
        }
        return best;
    }
}

/*
Algorithm:
1. Calculate prefix sums, where prefix[i] = sum of stones[0...i].
2. Start with the total sum as the initial best score difference
   (Alice can take all stones in the first move).
3. Traverse the prefix sums from right to left.
4. For each prefix:
      best = max(best, prefix[i] - best)
   Here, prefix[i] is the current player's score and best is
   the opponent's best possible score difference.
5. Return best.

Time Complexity: O(n)
Space Complexity: O(n)
*/

 