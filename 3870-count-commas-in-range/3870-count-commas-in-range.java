class Solution {
    public int countCommas(int n) {
        int total = 0;

        for (int i = 1; i <= n; i++) {
            int temp = i;

            while (temp >= 1000) {
                total++;
                temp /= 1000;
            }
        }

        return total;
    }
}