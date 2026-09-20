class Solution {
    public int solve(int[] memo , int n) {
        if(n <= 1) return n;

        if(memo[n] != -1) return memo[n];
        memo[n] = fib(n-1) + fib(n-2);
        return memo[n];
    }
    public int fib(int n) {
        int[] memo = new int[n+1];
        Arrays.fill(memo, -1);

        return solve(memo, n);
        
    }
}