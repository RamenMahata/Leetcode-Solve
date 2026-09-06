class Solution {

    public int numDistinct(String s, String t) {

        int n = s.length();
        int m = t.length();

        /*
         * dp[j] =
         * number of ways to form t[j...]
         * using the current suffix of s.
         *
         * dp[m] = 1 because an empty target
         * can always be formed in exactly 1 way:
         * choose nothing.
         */
        int[] dp = new int[m + 1];

        dp[m] = 1;

        /*
         * Process s from right to left.
         */
        for (int i = n - 1; i >= 0; i--) {

            /*
             * IMPORTANT:
             *
             * We go LEFT -> RIGHT.
             *
             * dp[j]     = old dp[i + 1][j]
             * dp[j + 1] = old dp[i + 1][j + 1]
             *
             * We need dp[j + 1] to remain unchanged.
             */
            for (int j = 0; j < m; j++) {

                if (s.charAt(i) == t.charAt(j)) {

                    /*
                     * Two choices:
                     *
                     * 1. Skip s[i]
                     *    -> dp[j]
                     *
                     * 2. Take s[i]
                     *    -> dp[j + 1]
                     *
                     * Therefore:
                     *
                     * dp[j] = dp[j] + dp[j + 1]
                     */
                    dp[j] = dp[j] + dp[j + 1];
                }
            }
        }

        return dp[0];
    }
}