class Solution {
    public int firstStableIndex(int[] nums, int k) {
        int n = nums.length;
        int[] suffixMin = new int[n];

        suffixMin[n-1] = nums[n-1];
        for(int i = n - 2; i >= 0; i--) {
            suffixMin[i] = Math.min(nums[i], suffixMin[i+1]);
        }
        int prefixMax = nums[0];
        for(int i = 0; i < n; i++) {
            prefixMax = Math.max(prefixMax, nums[i]);

            int score = prefixMax - suffixMin[i];
            if(score <= k) return i;
        }
        return -1;
    }
}
        /*
         * ============================================================
         * PATTERN: PREFIX + SUFFIX PRECOMPUTATION
         * ============================================================
         *
         * Use this pattern when a problem asks about every index i
         * and needs information from BOTH sides of i:
         *
         *      [0 ........ i] | [i ........ n-1]
         *           LEFT      |      RIGHT
         *
         * Common examples:
         * - prefix maximum + suffix minimum
         * - prefix minimum + suffix maximum
         * - prefix sum + suffix sum
         * - left product + right product
         * - count/frequency on left + count/frequency on right
         *
         * Main idea:
         * Instead of recalculating information for every index,
         * calculate it once and reuse it.
         *
         *
         * ============================================================
         * PREREQUISITES / WHAT YOU SHOULD KNOW
         * ============================================================
         *
         * 1. Arrays
         * 2. Loops
         * 3. min() / max()
         * 4. Prefix concept
         * 5. Suffix concept
         * 6. Time complexity: O(n) vs O(n^2)
         * 7. Basic space complexity
         *
         *
         * ============================================================
         * HOW TO RECOGNIZE THIS PATTERN
         * ============================================================
         *
         * Ask:
         *
         * "For every index i, do I need information about elements
         *  before/at i AND information about elements at/after i?"
         *
         * If YES, think:
         *
         *      PREFIX / SUFFIX PRECOMPUTATION
         *
         *
         * ============================================================
         * WHERE ELSE CAN THIS PATTERN BE USED?
         * ============================================================
         *
         * 1. Find an index where:
         *      max(left) <= min(right)
         *
         * 2. Find an index where:
         *      max(left) - min(right) <= k
         *
         * 3. Find equilibrium/partition points.
         *
         * 4. Find elements that are greater/smaller than everything
         *    on one side.
         *
         * 5. Problems involving:
         *      "maximum on the left"
         *      "minimum on the right"
         *      "sum before i"
         *      "sum after i"
         *      "number of elements before/after i"
         *
         * 6. Problems where calculating left/right information
         *    repeatedly would result in O(n^2).
         *
         *
         * ============================================================
         * ALGORITHM
         * ============================================================
         *
         * We need:
         *
         *      max(nums[0..i])
         *      min(nums[i..n-1])
         *
         * Step 1:
         * Create suffixMin[].
         *
         * suffixMin[i] = minimum element from i to n-1.
         *
         * Step 2:
         * Initialize the last element:
         *
         * suffixMin[n - 1] = nums[n - 1]
         *
         * Step 3:
         * Traverse from RIGHT -> LEFT:
         *
         * suffixMin[i] = min(nums[i], suffixMin[i + 1])
         *
         * Step 4:
         * Traverse from LEFT -> RIGHT.
         *
         * Maintain prefixMax using one variable.
         *
         * prefixMax = max(prefixMax, nums[i])
         *
         * Step 5:
         * Calculate:
         *
         * instability = prefixMax - suffixMin[i]
         *
         * Step 6:
         * If:
         *
         * instability <= k
         *
         * return i.
         *
         * Since we are checking from left to right, the first valid
         * index is automatically the SMALLEST stable index.
         *
         * Step 7:
         * If no index satisfies the condition, return -1.
         *
         *
         * ============================================================
         * COMPLEXITY
         * ============================================================
         *
         * First pass:  O(n)
         * Second pass: O(n)
         *
         * Total Time:
         *      O(n)
         *
         * Extra Space:
         *      O(n)
         *
         * because of suffixMin[].
         *
         * We cannot directly calculate suffixMin while scanning
         * left -> right because we don't know the future elements yet.
         *
         * ============================================================
         */
