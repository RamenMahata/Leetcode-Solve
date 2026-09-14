class Solution {
    private int gcd(int a, int b) {
        while(b != 0) {
            int temp = b;
            b = a % b;
            a = temp;
        }
        return a;
    }
    public int gcdOfOddEvenSums(int n) {
        int sumEven = n * (n + 1);
        int sumOdd = n * n;

        return gcd(sumEven, sumOdd);
    }
}