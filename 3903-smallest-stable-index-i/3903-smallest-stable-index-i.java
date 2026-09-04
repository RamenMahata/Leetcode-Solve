class Solution {
    public int firstStableIndex(int[] nums, int k) {

        // for(int i = 0; i < nums.length; i++) {

        //     int min = Integer.MAX_VALUE;
        //     int max = Integer.MIN_VALUE;

        //     for(int j = 0; j <= i; j++) max = Math.max(max, nums[j]);
        //     for(int j = i; j < nums.length; j++) min = Math.min(min, nums[j]);

        //     if((max-min) <= k) return i; 
        // }
        // return -1;

        int n = nums.length;

        /*
         * ALGORITHM:
         *
         * For every index i, we need:
         *
         * instability = max(nums[0..i]) - min(nums[i..n-1])
         *
         * To avoid calculating max and min repeatedly:
         *
         * 1. Build a suffixMin array where:
         *      suffixMin[i] = minimum element from i to n-1
         *
         * 2. Traverse the array from left to right while maintaining:
         *      prefixMax = maximum element from 0 to i
         *
         * 3. At every index i:
         *      instability = prefixMax - suffixMin[i]
         *
         * 4. If instability <= k, i is stable.
         *    Since we traverse from left to right, the first one
         *    we find is the smallest stable index.
         *
         * 5. If no index satisfies the condition, return -1.
         *
         * Time Complexity: O(n)
         * Space Complexity: O(n)
         */

        // suffixMin[i] stores the minimum value from i to n-1
        int[] suffixMin = new int[n];

        // Last element is the minimum of the suffix containing only itself
        suffixMin[n - 1] = nums[n - 1];

        // Build suffix minimum from right to left
        for (int i = n - 2; i >= 0; i--) {
            suffixMin[i] = Math.min(nums[i], suffixMin[i + 1]);
        }

        // Stores maximum value from nums[0] to nums[i]
        int prefixMax = Integer.MIN_VALUE;

        // Traverse from left to right to find the first stable index
        for (int i = 0; i < n; i++) {

            // Update prefix maximum
            prefixMax = Math.max(prefixMax, nums[i]);

            // Calculate instability score
            int instability = prefixMax - suffixMin[i];

            // Check if current index is stable
            if (instability <= k) {
                return i;
            }
        }

        // No stable index found
        return -1;

    }
}