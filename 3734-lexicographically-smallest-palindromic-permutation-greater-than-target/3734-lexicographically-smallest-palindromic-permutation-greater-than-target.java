class Solution {

    public String lexPalindromicPermutation(String s, String target) {

        int n = s.length();

        // -----------------------------------------
        // 1. Count frequency of every character
        // -----------------------------------------
        int[] freq = new int[26];

        for (char ch : s.toCharArray()) {
            freq[ch - 'a']++;
        }

        // -----------------------------------------
        // 2. Check whether a palindrome is possible
        // -----------------------------------------
        int oddCount = 0;
        int middle = -1;

        for (int i = 0; i < 26; i++) {

            if (freq[i] % 2 == 1) {
                oddCount++;
                middle = i;
            }
        }

        if (oddCount > 1) {
            return "";
        }

        // -----------------------------------------
        // 3. Build frequency of first half
        // -----------------------------------------
        int[] halfCount = new int[26];

        for (int i = 0; i < 26; i++) {
            halfCount[i] = freq[i] / 2;
        }

        // -----------------------------------------
        // 4. Only first half matters
        // -----------------------------------------
        String targetHalf = target.substring(0, n / 2);

        String answer = solve(
                0,
                new StringBuilder(),
                halfCount,
                targetHalf,
                target,
                middle,
                n
        );

        // solve() uses null internally to mean
        // "no answer from this path".
        // But the problem wants "".
        return answer == null ? "" : answer;
    }


    private String solve(
            int pos,
            StringBuilder half,
            int[] count,
            String targetHalf,
            String target,
            int middle,
            int n) {

        // -----------------------------------------
        // Base case
        // -----------------------------------------
        if (pos == targetHalf.length()) {

            String palindrome =
                    makePalindrome(half, middle, n);

            if (palindrome.compareTo(target) > 0) {
                return palindrome;
            }

            return null;
        }

        int targetChar =
                targetHalf.charAt(pos) - 'a';

        // -----------------------------------------
        // Try characters from smallest to largest
        // -----------------------------------------
        for (int c = targetChar; c < 26; c++) {

            // Character unavailable
            if (count[c] == 0) {
                continue;
            }

            char ch = (char) ('a' + c);

            // -------------------------------------
            // Choose
            // -------------------------------------
            half.append(ch);
            count[c]--;

            String answer = null;

            // -------------------------------------
            // Case 1: Same as target
            // -------------------------------------
            if (c == targetChar) {

                answer = solve(
                        pos + 1,
                        half,
                        count,
                        targetHalf,
                        target,
                        middle,
                        n
                );

            }

            // -------------------------------------
            // Case 2: Greater than target
            // -------------------------------------
            else {

                // We are already greater than target.
                // Therefore make the rest as small
                // as possible.

                StringBuilder candidateHalf =
                        new StringBuilder(half);

                for (int x = 0; x < 26; x++) {

                    for (int k = 0; k < count[x]; k++) {

                        candidateHalf.append(
                                (char) ('a' + x)
                        );
                    }
                }

                answer = makePalindrome(
                        candidateHalf,
                        middle,
                        n
                );
            }

            // -------------------------------------
            // Undo
            // -------------------------------------
            count[c]++;
            half.deleteCharAt(half.length() - 1);

            // If we found an answer, return it.
            if (answer != null) {
                return answer;
            }
        }

        // No valid answer from this path
        return null;
    }


    private String makePalindrome(
            StringBuilder half,
            int middle,
            int n) {

        StringBuilder result =
                new StringBuilder();

        // Left half
        result.append(half);

        // Middle character
        if (n % 2 == 1) {
            result.append(
                    (char) ('a' + middle)
            );
        }

        // Right half
        for (int i = half.length() - 1; i >= 0; i--) {
            result.append(half.charAt(i));
        }

        return result.toString();
    }
}