class Solution {
    public String shortestBeautifulSubstring(String s, int k) {
        int n = s.length();
        List<Integer> ones = new ArrayList<>();
        for(int i = 0; i < n; i++) {
            if(s.charAt(i) == '1') ones.add(i);
        }
        if(ones.size() < k) return "";

        String answer = "";
        int bestLength = Integer.MAX_VALUE;

        for(int i = 0; i <= ones.size() - k; i++) {
            int start = ones.get(i);
            int end = ones.get(i + k - 1);

            int length = end - start + 1;
            String candidate = s.substring(start, end+1);

            if(length < bestLength) {
                bestLength = length;
                answer = candidate;
            }
            else if(length == bestLength && candidate.compareTo(answer) < 0) {
                answer = candidate;
            } 
        }
        return answer;
        
    }
}
/*
===========================================================
ALGORITHM: Shortest Beautiful Substring
===========================================================

1. Store the indexes/positions of every '1' in the string.

   Example:
   s = "100011001"
   ones = [0, 4, 5, 8]

2. If the number of 1s is less than k, a beautiful substring
   is impossible, so return "".

3. A shortest substring containing exactly k ones must contain
   k CONSECUTIVE 1s.

   Therefore, for every group of k consecutive positions:
   
       start = ones[i]
       end   = ones[i + k - 1]

   The substring from start to end contains exactly k ones.

4. Calculate its length:

       length = end - start + 1

5. Keep the substring with:
   - Smaller length
   - If lengths are equal, lexicographically smaller string

6. Return the best substring.


===========================================================
WHY DO WE ONLY CHECK k CONSECUTIVE ONES?
===========================================================

If we want exactly k ones, choosing non-consecutive 1s
would only make the range larger.

For example:

    ones = [2, 5, 7, 10]
    k = 3

Possible groups:

    [2, 5, 7]  -> range 2 to 7
    [5, 7, 10] -> range 5 to 10

These give the minimum possible ranges containing 3 ones.

So the problem can be reduced from checking all substrings
to checking groups of k consecutive 1s.


===========================================================
TIME COMPLEXITY
===========================================================

Let:
    n = length of string
    m = number of 1s

1. Scan the string to find positions of 1s:
       O(n)

2. Number of groups of k consecutive 1s:
       O(m)

3. Creating/comparing candidate substrings can take O(n)
   in the worst case.

Therefore, with the straightforward substring + compareTo
implementation:

       O(n + m * n)
       = O(n^2)   because m <= n

Time Complexity:
       O(n^2)


===========================================================
SPACE COMPLEXITY
===========================================================

The list stores the positions of all 1s:

       O(m)

Since m <= n:

       O(n)

Space Complexity:
       O(n)


===========================================================
IMPORTANT JAVA MISTAKES TO AVOID
===========================================================

1. char vs int

WRONG:
    s.charAt(i) == 1

CORRECT:
    s.charAt(i) == '1'

charAt() returns a char, so use '1'.


2. Finding minimum with the wrong initial value

WRONG:
    int bestLength = Integer.MIN_VALUE;

CORRECT:
    int bestLength = Integer.MAX_VALUE;

We are finding the MINIMUM length, so start with a
very large value.


3. Remember substring's ending index is EXCLUSIVE

    s.substring(start, end + 1)

is required when 'end' is the actual last index.


4. Don't confuse substring with subsequence.

A substring must be continuous.


===========================================================
IMPORTANT PROBLEM-SOLVING LEARNINGS
===========================================================

1. First understand the requirements before coding:

    Valid condition:
        exactly k ones

    Optimization:
        shortest length

    Tie-breaker:
        lexicographically smallest


2. Don't immediately jump to a known pattern like
   sliding window.

   First ask:
       "What information actually matters?"

   Here, only the positions of 1s matter.


3. Look for ways to reduce the search space.

   Brute force:
       Check every substring -> O(n^3)

   Better:
       Store positions of 1s and check groups of k
       consecutive 1s -> O(n^2) with direct substring
       creation/comparison.


4. When minimizing a range containing a fixed number of
   ordered elements, think about CONSECUTIVE elements.

   This is a reusable pattern.


5. Separate the conditions:

       First minimize length.

       If length is equal,
       minimize lexicographically.

   Don't mix the two conditions.


6. When analyzing complexity, don't only count loops.

   Also ask:
       "What does each loop iteration cost?"

   A loop running O(n) times is not necessarily O(n)
   if each iteration performs O(n) work.


===========================================================
MENTAL PATTERN TO REMEMBER
===========================================================

When you see:

    "shortest substring/range containing exactly k
     occurrences of something"

Think:

    1. Find positions of important elements.
    2. Consider k consecutive positions.
    3. Calculate the range:
           positions[i+k-1] - positions[i] + 1
    4. Minimize the range.
    5. Handle tie-breaking separately.


===========================================================
INTERVIEW EXPLANATION
===========================================================

"The condition depends only on the number of 1s.
I store the positions of all 1s. A shortest substring
containing exactly k ones must span k consecutive 1s,
so I examine every group of k consecutive positions.
For each group, I calculate the substring length and
keep the shortest one. If two candidates have the same
length, I choose the lexicographically smaller one."


===========================================================
KEY TAKEAWAY
===========================================================

Don't memorize this solution.

Learn the reasoning:

    Brute force
        ↓
    Identify repeated work
        ↓
    Identify important elements
        ↓
    Store their positions
        ↓
    Reduce the search space
        ↓
    Apply optimization + tie-breaker
        ↓
    Analyze the cost of EACH operation

This way of thinking is more important than this
specific problem.
===========================================================
*/