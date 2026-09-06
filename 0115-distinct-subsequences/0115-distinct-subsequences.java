class Solution {

    public int numDistinct(String s, String t) {

        int n = s.length();
        int m = t.length();

        /*
         * dp[i][j] =
         * number of ways to form t[j...]
         * using characters from s[i...]
         */

        int[][] dp = new int[n + 1][m + 1];

        /*
         * If t is completely formed,
         * there is exactly 1 valid way to finish.
         *
         * Therefore:
         * dp[i][m] = 1
         */
        for (int i = 0; i <= n; i++) {
            dp[i][m] = 1;
        }

        /*
         * Fill the table from bottom to top.
         *
         * We need dp[i + 1][j] and
         * dp[i + 1][j + 1].
         */
        for (int i = n - 1; i >= 0; i--) {

            for (int j = m - 1; j >= 0; j--) {

                if (s.charAt(i) == t.charAt(j)) {

                    // Two choices:
                    // 1. Take s[i]
                    // 2. Skip s[i]
                    dp[i][j] =
                            dp[i + 1][j + 1]
                            + dp[i + 1][j];

                } else {

                    // Characters don't match.
                    // We must skip s[i].
                    dp[i][j] = dp[i + 1][j];
                }
            }
        }

        return dp[0][0];
    }
}