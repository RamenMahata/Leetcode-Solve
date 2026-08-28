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
/*
====================================================================
 PROBLEM: Smallest Palindromic Permutation Greater Than Target
====================================================================

PREREQUISITES
-------------
Before solving this problem, you should be comfortable with:

1. Character Frequency Counting
   - int[26] for lowercase English letters
   - freq[ch - 'a']

2. Palindrome Properties
   - Even length:
       Every character must occur an even number of times.
   - Odd length:
       At most one character can occur an odd number of times.

3. String / StringBuilder
   - charAt()
   - substring()
   - append()
   - deleteCharAt()
   - reverse()
   - compareTo()

4. Lexicographical Ordering
   - Strings are compared from left to right.
   - At the first different position:
         smaller character -> smaller string
         larger character  -> larger string

5. Permutations
   - Understand that characters can be rearranged.
   - Understand duplicate characters.

6. Greedy Thinking
   - If we want the smallest string greater than target:
         stay equal as long as possible,
         then make the smallest possible increase,
         then minimize everything after it.

7. Backtracking
   - Choose
   - Explore
   - Undo

8. Recursion
   - Understand base case and recursive case.


====================================================================
 KEY OBSERVATIONS
====================================================================

OBSERVATION 1: PALINDROME REDUCES THE SEARCH SPACE
--------------------------------------------------

A palindrome is completely determined by:

    first half + middle character (if n is odd)

Example:

    "abcba"

        abcba
        ↑ ↑ ↑
        first half = "ab"
        middle     = "c"
        right half = reverse("ab") = "ba"

Therefore, we DO NOT need to permute all n characters.

We only need to arrange:

    freq[i] / 2

copies of each character in the first half.


OBSERVATION 2: CHECK WHETHER PALINDROME IS POSSIBLE
---------------------------------------------------

A palindrome can have at most ONE character with odd frequency.

    oddCount > 1
        -> impossible
        -> return ""

If n is odd, the only odd-frequency character becomes
the middle character.


OBSERVATION 3: ONLY THE FIRST HALF NEEDS TO BE SEARCHED
--------------------------------------------------------

Once the first half is fixed:

    palindrome =
        firstHalf + middle + reverse(firstHalf)

Therefore, the lexicographical ordering is primarily
determined by the first half.

Example:

    abcba
    abdba

At the first different position:

    c < d

Therefore:

    abcba < abdba


====================================================================
 MAIN GREEDY IDEA
====================================================================

We need:

    smallest palindrome > target

Think about constructing the first half from LEFT to RIGHT.

At every position:

    1. Try the same character as target.
       -> This keeps the prefix as small as possible.

    2. If the same character is available,
       recursively continue.

    3. If the equal path eventually fails,
       backtrack.

    4. Try the smallest character GREATER than target's
       character.

    5. Once we choose a greater character,
       the entire string is guaranteed to be greater
       than target.

    6. Therefore, after becoming greater, put all remaining
       characters in ASCENDING order to make the suffix
       as small as possible.


The fundamental pattern is:

        EQUAL
          |
        EQUAL
          |
        EQUAL
          |
       CANNOT
          |
        INCREASE
          |
    MINIMIZE SUFFIX


====================================================================
 WHY DO WE NEVER CHOOSE A SMALLER CHARACTER?
====================================================================

Suppose:

    target = "abc"

If we construct:

    "abb"

At the first differing position:

    b < c

Therefore:

    "abb" < "abc"

No later character can fix this.

So:

    character < target[i]
        -> immediately reject


====================================================================
 WHY DO WE TRY THE EQUAL CHARACTER FIRST?
====================================================================

Suppose possible answers are:

    "abd"
    "aec"

Both are greater than:

    "abc"

But:

    "abd" < "aec"

Why?

Because "abd" stays equal to the target for longer.

Therefore:

    Try equal first.
    Increase only when necessary.

This is a very common lexicographical greedy pattern.


====================================================================
 WHY CAN WE MINIMIZE THE REMAINING CHARACTERS AFTER INCREASING?
====================================================================

Suppose:

    target = "abc"

and we construct:

    "abd..."

At the third position:

    d > c

Therefore, the complete string is already guaranteed
to be greater than target.

The remaining characters no longer affect whether the
string is greater.

They only affect which GREATER string is the smallest.

Therefore:

    Sort / place remaining characters in ascending order.


====================================================================
 ALGORITHM
====================================================================

Step 1:
    Count frequency of every character in s.

Step 2:
    Count characters with odd frequency.

Step 3:
    If oddCount > 1:
        return ""

Step 4:
    Build halfCount:

        halfCount[i] = freq[i] / 2

Step 5:
    Find the middle character if n is odd.

Step 6:
    Extract the first half of target:

        targetHalf = target.substring(0, n / 2)

Step 7:
    Recursively construct our first half.

Step 8:
    At position pos:

        targetChar = targetHalf.charAt(pos)

        Try every available character from:

            targetChar -> 'z'

        in ascending order.

Step 9:
    If chosen character == targetChar:

        Choose it.
        Recursively continue.

Step 10:
    If chosen character > targetChar:

        We are already greater than target.

        Copy the current half.

        Add all remaining characters in ascending order.

        Build the palindrome.

        This is the smallest palindrome for this prefix.

Step 11:
    After trying a character:

        Undo the choice.

        Restore its frequency.

        Remove it from StringBuilder.

    This is:

        CHOOSE -> EXPLORE -> UNDO

Step 12:
    When the complete first half is constructed:

        Build the complete palindrome.

        If:

            palindrome.compareTo(target) > 0

        return palindrome.

        Otherwise return null for this path.

Step 13:
    If the entire search fails:

        Internal recursion returns null.

        Public method converts null to:

            ""


====================================================================
 PSEUDOCODE
====================================================================

function solve(pos, half, count):

    if pos == halfLength:

        palindrome = buildPalindrome(half)

        if palindrome > target:
            return palindrome

        return null


    targetChar = targetHalf[pos]

    for c from targetChar to 'z':

        if c is not available:
            continue

        choose c

        if c == targetChar:

            answer = solve(pos + 1, half, count)

        else:

            append all remaining characters
            in ascending order

            answer = buildPalindrome(half)

        undo choice

        if answer exists:
            return answer

    return null


====================================================================
 BACKTRACKING PATTERN
====================================================================

Every recursive decision follows:

    1. CHOOSE
    2. EXPLORE
    3. UNDO

Example:

    choose 'a'
        |
        explore
        |
        fails
        |
    undo 'a'
        |
    choose 'b'
        |
        explore
        |
        succeeds


In code:

    // Choose
    half.append(ch);
    count[c]--;

    // Explore
    answer = solve(...);

    // Undo
    count[c]++;
    half.deleteCharAt(...);


====================================================================
 COMPLEXITY ANALYSIS
====================================================================

Let:

    n = length of s
    m = n / 2

The alphabet contains only 26 lowercase letters.

At every position of the first half, we try at most 26 characters.

There are m positions.

Therefore:

    O(26 * m)

Since 26 is a constant:

    O(m)

Since:

    m = n / 2

we get:

    TIME = O(n)


Building the final palindrome takes:

    O(n)

So overall:

    TIME COMPLEXITY = O(n)


SPACE:

    Frequency arrays:
        O(26) = O(1)

    StringBuilder for first half:
        O(n)

    Recursion stack:
        O(n)

    Final answer:
        O(n)

Therefore:

    SPACE COMPLEXITY = O(n)


FINAL:

    Time  = O(n)
    Space = O(n)


====================================================================
 IMPORTANT: WHY IS THIS NOT O(n!)?
====================================================================

At first, the problem looks like a permutation problem.

Normal brute force:

    n! permutations

But we do NOT explore all permutations.

Why?

Because:

    1. Palindrome -> only n/2 positions are independent.

    2. Lexicographical condition -> we only follow
       the equal prefix.

    3. Once we become greater -> remaining characters
       are placed greedily in sorted order.

So we avoid generating the entire permutation tree.


====================================================================
 COMMON MISTAKES
====================================================================

1. Generating all permutations of s.

   WRONG:

       O(n!)

   Instead:
       Use palindrome structure.

2. Forgetting the odd-frequency condition.

       oddCount > 1 -> impossible

3. Trying to construct both halves independently.

   WRONG.

   Right half is automatically:

       reverse(firstHalf)

4. Choosing a character smaller than target[i].

   This makes the entire string smaller.

5. Choosing a greater character too early.

   Always try equality first.

6. After becoming greater, continuing recursion unnecessarily.

   Once greater:
       minimize the remaining characters directly.

7. Forgetting STRICTLY greater.

       compareTo(target) > 0

   NOT:

       >= 0

8. Forgetting backtracking.

       count[c]++
       half.deleteCharAt(...)

9. Returning null to the judge.

   null is useful internally to represent:

       "No solution from this path."

   But the problem expects:

       ""

   Therefore:

       return answer == null ? "" : answer;


====================================================================
 PATTERN RECOGNITION
====================================================================

When you see:

    "lexicographically smallest"
    +
    "greater than target"
    +
    "permutation"

Think:

    KEEP PREFIX EQUAL
           ↓
    INCREASE WHEN NECESSARY
           ↓
    MINIMIZE SUFFIX


When you see:

    "palindromic permutation"

Think:

    FREQUENCY COUNT
           ↓
    HALF FREQUENCIES
           ↓
    OPTIONAL MIDDLE
           ↓
    MIRROR THE HALF


When you see:

    duplicate characters

Think:

    FREQUENCY ARRAY

instead of blindly using:

    boolean[] used


When you see:

    "smallest valid arrangement"

Think:

    GREEDY + LEXICOGRAPHICAL ORDER


====================================================================
 BIGGEST LEARNINGS FROM THIS PROBLEM
====================================================================

1. Look for STRUCTURE before using brute force.

2. A palindrome reduces n independent positions to n/2.

3. Lexicographical comparison is decided at the FIRST
   DIFFERENT position.

4. To find the smallest string greater than target:

       stay equal as long as possible,
       then make the smallest possible increase,
       then minimize the suffix.

5. "Choose -> Explore -> Undo" is the basic backtracking pattern.

6. Recursive solutions are NOT automatically exponential.
   Always analyze what branches are actually explored.

7. Separate:
       algorithmic failure
   from:
       implementation / return-value bugs.

8. Before coding, explain the algorithm in plain English.
   Then translate each step into code.

====================================================================
 MENTAL TEMPLATE TO REMEMBER
====================================================================

For future problems:

    What is the brute force?
            ↓
    What structure can eliminate unnecessary search?
            ↓
    What does lexicographically smallest mean?
            ↓
    Can I keep the prefix equal to target?
            ↓
    Where can I make the smallest increase?
            ↓
    Once greater, how do I minimize the rest?
            ↓
    How do duplicates affect the implementation?
            ↓
    Can I express the decisions as:
        Choose -> Explore -> Undo?


    Prerequisites in the order I'd recommend learning them

    Before attempting similar problems on your own, make sure you're comfortable with:

    Arrays + frequency counting
    Strings and StringBuilder in Java
    Palindrome checking and palindrome properties
    Permutations and duplicate handling
    Lexicographical string comparison
    Recursion
    Backtracking
    Greedy algorithms
    Next Permutation / lexicographical permutation

====================================================================
*/