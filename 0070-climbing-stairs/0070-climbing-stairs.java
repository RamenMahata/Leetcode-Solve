class Solution {
    private int solve(int[] memo, int n) {
        if(n == 0 || n == 1) return 1;
        if(memo[n] != 0) return memo[n];

        memo[n] = solve(memo, n - 1) + solve(memo, n -2);
        return memo[n];
    }
    public int climbStairs(int n) {
        int[] memo = new int[n+1];
        return solve(memo, n);
        
    }
}