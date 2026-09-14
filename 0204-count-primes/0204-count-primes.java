class Solution {
    public boolean[] sieve(int n) {
        boolean[] isPrime = new boolean[n + 1];
        Arrays.fill(isPrime, true);
        if(n >= 0) isPrime[0] = false;
        if(n >= 1) isPrime[1] = false;

        for(int i = 2; (long) i * i <= n; i++) {
            if(isPrime[i]) {
                for(int j = i * i; j <= n; j += i) {
                    isPrime[j] = false;
                }
            }
        }
        return isPrime;
    }
    public int countPrimes(int n) {
        boolean[] prime = sieve(n);
        int count = 0;
        for(int i = 2; i < n; i++) {
            if(prime[i]) count++;
        }
        return count; 
    }
}