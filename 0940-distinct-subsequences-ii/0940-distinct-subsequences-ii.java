class Solution {
    public int distinctSubseqII(String s) {
        int n = s.length();
        final long MOD = 1_000_000_007L;

        long[] dp = new long[n + 1];
        dp[0] = 1;
        int[] last = new int[26];

        Arrays.fill(last, -1);

        for(int i = 0; i < n; i++) {
            char c = s.charAt(i);
            int idx = c - 'a';


            // When there is no duplicate exist
            // Take or not take
            dp[i+1] = (2 * dp[i]) % MOD;

            int previous = last[idx];
            if(previous != -1) {
                dp[i + 1] = (dp[i + 1] - dp[previous] + MOD) % MOD;
            }

            last[idx] = i;
        }
        return (int)((dp[n] - 1 + MOD) % MOD);


    }
}