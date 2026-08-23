class Solution {
    public boolean sumGame(String num) {
        int n = num.length();
        int half = n/2;

        int leftSum = 0;
        int rightSum = 0;

        int leftQ = 0;
        int rightQ = 0;

        // First Half
        for(int i = 0; i < half; i++) {
            if(num.charAt(i) == '?') leftQ++;
            else leftSum += num.charAt(i) - '0';
        }

        // Second Half
        for(int i = half; i < n; i++) {
            if(num.charAt(i) == '?') rightQ++;
            else rightSum += num.charAt(i) - '0';
        }

        int diff = leftSum - rightSum;
        int diffQ = rightQ - leftQ;

        // Odd numbers of Questions Alice got the last move
        if((leftQ + rightQ) % 2 == 1) return true;

        // Bob wins if he exactly balance the differnce
        return leftSum - rightSum != 9 * diffQ/2;
        
    }
}