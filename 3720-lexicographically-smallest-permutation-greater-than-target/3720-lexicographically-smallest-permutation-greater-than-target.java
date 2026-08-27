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
/*
===========================================================
PROBLEM:
Find the lexicographically smallest permutation of `s`
that is strictly greater than `target`.

-----------------------------------------------------------
ALGORITHM / APPROACH:
-----------------------------------------------------------

1. Count the frequency of every character in `s`.
   Since there are only 26 lowercase letters, use int[26].

2. We want to keep the prefix of the answer equal to
   `target` for as long as possible.

3. At every position `i`, check whether we can make the
   answer greater than `target` at this position.

   To do that:
      - Prefix [0 ... i-1] must remain equal to target.
      - Choose the SMALLEST available character > target[i].

4. Do not immediately return when we find a possible position.
   Continue scanning to the right.

   Why?
   A later first difference gives a smaller lexicographical
   answer because it preserves a longer prefix of target.

   Example:
       target = "bba"

       "cab"  -> difference at index 0
       "bca"  -> difference at index 1

       "bca" < "cab"

5. Remember the RIGHTMOST position where we can make the
   answer greater and the smallest character that can be
   placed there.

6. Once the best position is found:
      answer =
          target[0 ... bestIndex-1]
          + chosen character
          + all remaining characters in sorted order

7. Why sort the suffix?
   Once we make the answer greater at `bestIndex`, the suffix
   no longer affects whether the answer is greater.

   Therefore, to make the whole answer as small as possible,
   arrange the remaining characters in ascending order.

8. If no position allows us to make the answer greater,
   return "".

-----------------------------------------------------------
PATTERN TO RECOGNIZE:
-----------------------------------------------------------

This is a:

    "Lexicographically Smallest Greater"
    + "Permutation / Frequency Counting"
    + "Greedy"

pattern.

Whenever you see:

    "smallest string/permutation greater than X"

Think:

    1. Keep the longest possible prefix equal to X.
    2. Make the FIRST difference as late as possible.
    3. At that position, choose the smallest value that
       makes the string greater.
    4. Minimize the remaining suffix.

General template:

    longest matching prefix
            ↓
    smallest necessary increase
            ↓
    minimum possible suffix

This is closely related to the idea behind `next_permutation`.

-----------------------------------------------------------
IMPORTANT OBSERVATION:
-----------------------------------------------------------

Lexicographical comparison is decided by the FIRST position
where two strings differ.

Therefore:

    prefix matters more than suffix.

If:

    A = "abc..."
    B = "abd..."

then A < B regardless of what comes after them.

This is why we try to push the first difference as far RIGHT
as possible.

-----------------------------------------------------------
WHY FREQUENCY ARRAY?
-----------------------------------------------------------

`s` contains only lowercase English letters.

There are only 26 possible characters.

So instead of repeatedly searching/sorting, use:

    int[] freq = new int[26];

Character -> index:

    'a' -> 0
    'b' -> 1
    ...
    'z' -> 25

This gives constant-size character management.

-----------------------------------------------------------
TIME COMPLEXITY:
-----------------------------------------------------------

For every position (n positions), we check at most 26
characters.

    O(26 * n)

Since 26 is constant:

    O(n)

Building the final answer is also O(n).

FINAL TIME:

    O(n)

-----------------------------------------------------------
SPACE COMPLEXITY:
-----------------------------------------------------------

Frequency arrays contain only 26 elements:

    O(26) = O(1)

The answer / remaining characters require O(n).

FINAL SPACE:

    O(n)

-----------------------------------------------------------
BRUTE FORCE:
-----------------------------------------------------------

Generate every permutation of `s`.

Number of permutations:

    O(n!)

For every permutation, compare it with target:

    O(n)

Therefore:

    Time = O(n * n!)

This is impossible for n <= 300.

The important problem-solving lesson is not just
"brute force is slow".

Instead ask:

    "Do I really need to generate every permutation?"

Here the answer is NO because lexicographical ordering
allows us to construct the answer greedily.

-----------------------------------------------------------
COMMON MISTAKES TO AVOID:
-----------------------------------------------------------

1. DO NOT generate all permutations.

   O(n!) is far too slow.

2. DO NOT immediately choose the first position where a
   greater character is possible.

   Example:

       target = "bba"

       Changing index 0 -> "cab"
       Changing index 1 -> "bca"

       "bca" is smaller.

   Always try to make the first difference as far right
   as possible.

3. DO NOT choose any character greater than target[i].

   Choose the SMALLEST available character greater than
   target[i].

   Example:

       target[i] = 'b'
       available = c, d, e

       choose c, NOT d/e.

4. DO NOT arrange the suffix arbitrarily.

   Once the answer is already greater, make the suffix
   as small as possible -> ascending order.

5. DO NOT forget the word "STRICTLY".

   If the constructed permutation equals target, it is NOT
   a valid answer.

6. DO NOT assume target is a permutation of s.

   Example:

       s      = "leet"
       target = "code"

   `target` may contain characters that aren't present in `s`.

7. Be careful with duplicate characters.

   Example:

       s = "baba"

   There are only 6 UNIQUE permutations, not 4! different
   strings.

   Frequency counting naturally handles duplicates.

-----------------------------------------------------------
KEY LEARNINGS FROM THIS PROBLEM:
-----------------------------------------------------------

1. Learn to translate the requirement:

       "smallest permutation greater than target"

   into:

       "preserve the longest prefix,
        make the smallest possible increase,
        minimize the suffix."

2. Lexicographical problems often require reasoning about
   the FIRST DIFFERING POSITION.

3. For "smallest greater":

       first difference -> as RIGHT as possible
       character       -> as SMALL as possible
       suffix           -> as SMALL as possible

4. When a problem involves rearranging characters, always
   consider whether a frequency array can replace expensive
   searching/sorting.

5. Small fixed domains are a major optimization opportunity.

       lowercase English letters -> only 26 possibilities.

6. A good greedy solution usually comes from asking:

       "What decision determines the answer first?"

   Here it is the first differing character.

7. Brute force is useful as a starting point. The goal is
   not to avoid brute force thinking, but to use it to
   discover what structure the optimal solution can exploit.

8. The key optimization is not "make permutation generation
   faster".

   It is:

       "Don't generate permutations at all."

-----------------------------------------------------------
MENTAL TEMPLATE TO REMEMBER:
-----------------------------------------------------------

For:

    "Find the smallest arrangement/string > target"

Think:

    MATCH
      ↓
    MATCH
      ↓
    MATCH
      ↓
    make the smallest possible increase
      ↓
    minimize everything after it

And if multiple positions can be increased:

    choose the RIGHTMOST possible position.

===========================================================
*/