class Solution {
    public String lexGreaterPermutation(String s, String target) {
        int n = s.length();

        // Count characters available in s.
        int[] freq = new int[26];

        for (char c : s.toCharArray()) {
            freq[c - 'a']++;
        }

        // Characters already used to match target's prefix.
        int[] used = new int[26];

        int bestIndex = -1;
        int bestChar = -1;

        for (int i = 0; i < n; i++) {

            int targetChar = target.charAt(i) - 'a';

            // Can we make the answer greater at position i?
            for (int c = targetChar + 1; c < 26; c++) {

                if (freq[c] - used[c] > 0) {

                    // This position can be the first difference.
                    // Since we're moving left -> right,
                    // overwrite the previous candidate.
                    bestIndex = i;
                    bestChar = c;

                    // We want the smallest possible
                    // character greater than target[i].
                    break;
                }
            }

            // Try to continue matching target exactly.
            if (freq[targetChar] - used[targetChar] > 0) {
                used[targetChar]++;
            } else {
                // We cannot make target's prefix any longer.
                break;
            }
        }

        // No position can make us strictly greater.
        if (bestIndex == -1) {
            return "";
        }

        // Build the answer.
        StringBuilder answer = new StringBuilder();

        // 1. Copy the matching prefix.
        for (int i = 0; i < bestIndex; i++) {
            answer.append(target.charAt(i));
        }

        // 2. Put the chosen larger character.
        answer.append((char) ('a' + bestChar));

        // 3. Calculate remaining characters.
        int[] remaining = freq.clone();

        for (int i = 0; i < bestIndex; i++) {
            remaining[target.charAt(i) - 'a']--;
        }

        remaining[bestChar]--;

        // 4. Put remaining characters in sorted order.
        for (int c = 0; c < 26; c++) {
            while (remaining[c] > 0) {
                answer.append((char) ('a' + c));
                remaining[c]--;
            }
        }

        return answer.toString();
    }
}