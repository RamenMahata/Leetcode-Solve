import java.util.*;

class Solution {
    public int[] lexicographicallySmallestArray(int[] nums, int limit) {

        int n = nums.length;

        // Store {value, originalIndex}
        int[][] arr = new int[n][2];

        for (int i = 0; i < n; i++) {
            arr[i][0] = nums[i];
            arr[i][1] = i;
        }

        // Sort by value
        Arrays.sort(arr, (a, b) -> Integer.compare(a[0], b[0]));

        int start = 0;

        while (start < n) {

            int end = start;

            // Find one connected component
            while (end + 1 < n &&
                   arr[end + 1][0] - arr[end][0] <= limit) {
                end++;
            }

            // Collect original indices
            List<Integer> indices = new ArrayList<>();

            for (int i = start; i <= end; i++) {
                indices.add(arr[i][1]);
            }

            // Sort indices
            Collections.sort(indices);

            // Values are already sorted because arr is sorted by value
            for (int i = 0; i < indices.size(); i++) {
                nums[indices.get(i)] = arr[start + i][0];
            }

            start = end + 1;
        }

        return nums;
    }
}
/*
PATTERN:
Allowed swaps + transitive relationships
        -> Connected Components
        -> Sort values within each component

ALGORITHM:
1. Store each element as (value, originalIndex).
2. Sort these pairs by value.
3. Scan sorted values.
4. Consecutive values belong to the same component
   if their difference <= limit.
5. A gap > limit starts a new component.
6. For each component:
      - collect original indices
      - sort the indices
      - values are already sorted
      - assign smallest values to smallest indices
7. Return the modified array.

KEY INSIGHT:
If a-b <= limit and b-c <= limit, then a and c
can interact indirectly through b. Therefore, direct
swappability is not enough; we need connected components.

WHY SORTING WORKS:
After sorting values, a component is exactly a maximal
contiguous group where every adjacent difference <= limit.

LEXICOGRAPHIC MINIMIZATION:
Since values within a component can be rearranged freely,
put the smallest value at the smallest index, the second
smallest at the second smallest index, etc.

BRUTE FORCE:
Build graph by checking every pair.
Time:  O(n^2)
Space: O(n^2)

OPTIMAL:
Sort (value, index) pairs and find components using
adjacent differences.
Time:  O(n log n)
Space: O(n)

PREREQUISITES:
- Sorting
- Comparator
- (value, index) pairs
- Connected components
- Greedy / lexicographical ordering
- Basic graph / DSU understanding

COMMON MISTAKES:
- Checking only direct swaps
- Grouping by original index
- Checking every pair
- Sorting the entire array
- Losing original indices

MAIN LEARNING:
Don't simulate swaps when you can characterize all
reachable states using connected components.


"Swap if difference <= limit"
             ↓
"Can use intermediate elements?"
             ↓
YES
             ↓
"So this is connectivity."
             ↓
"Need connected components."
             ↓
"Condition depends on value difference."
             ↓
"Sort by value."
             ↓
"Adjacent gap > limit separates components."
             ↓
"Within each component, arbitrary rearrangement."
             ↓
"To minimize lexicographically:
 smallest value → smallest index."
             ↓
O(n log n)
*/