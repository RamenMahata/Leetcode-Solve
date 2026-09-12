import java.util.*;

class Solution {

    static class Interval {
        int start;
        int end;
        int weight;
        int index;

        Interval(int start, int end, int weight, int index) {
            this.start = start;
            this.end = end;
            this.weight = weight;
            this.index = index;
        }
    }

    static class Result {
        long score;
        List<Integer> indices;

        Result(long score, List<Integer> indices) {
            this.score = score;
            this.indices = indices;
        }
    }

    Interval[] arr;
    int[] next;
    Result[][] dp;

    public int[] maximumWeight(List<List<Integer>> intervals) {

        int n = intervals.size();

        // 1. Store intervals with their original indices
        arr = new Interval[n];

        for (int i = 0; i < n; i++) {

            int start = intervals.get(i).get(0);
            int end = intervals.get(i).get(1);
            int weight = intervals.get(i).get(2);

            arr[i] = new Interval(start, end, weight, i);
        }

        // 2. Sort by start time
        Arrays.sort(arr, (a, b) -> {
            if (a.start != b.start) {
                return Integer.compare(a.start, b.start);
            }

            return Integer.compare(a.end, b.end);
        });

        // 3. Find next compatible interval
        next = new int[n];

        for (int i = 0; i < n; i++) {
            next[i] = findNext(i);
        }

        // 4. DP
        // k = number of intervals we can still select
        dp = new Result[n + 1][5];

        Result answer = solve(0, 4);

        // 5. Convert List<Integer> to int[]
        int[] result = new int[answer.indices.size()];

        for (int i = 0; i < answer.indices.size(); i++) {
            result[i] = answer.indices.get(i);
        }

        return result;
    }

    // Find first interval whose start > current interval's end
    private int findNext(int i) {

        int target = arr[i].end;

        int left = i + 1;
        int right = arr.length;

        while (left < right) {

            int mid = left + (right - left) / 2;

            if (arr[mid].start > target) {
                right = mid;
            } else {
                left = mid + 1;
            }
        }

        return left;
    }

    // DP
    private Result solve(int i, int k) {

        // No intervals left OR cannot select anymore
        if (i == arr.length || k == 0) {
            return new Result(0, new ArrayList<>());
        }

        // Already calculated
        if (dp[i][k] != null) {
            return dp[i][k];
        }

        // ----------------------------------------
        // Option 1: Skip current interval
        // ----------------------------------------

        Result skip = solve(i + 1, k);

        // ----------------------------------------
        // Option 2: Take current interval
        // ----------------------------------------

        Result remaining = solve(next[i], k - 1);

        List<Integer> takeIndices =
            new ArrayList<>(remaining.indices);

        takeIndices.add(arr[i].index);

        // Answer must be in ascending index order
        Collections.sort(takeIndices);

        Result take = new Result(
            arr[i].weight + remaining.score,
            takeIndices
        );

        // ----------------------------------------
        // Choose the better result
        // ----------------------------------------

        dp[i][k] = better(skip, take);

        return dp[i][k];
    }

    // Compare two results
    private Result better(Result a, Result b) {

        // Higher score wins
        if (a.score > b.score) {
            return a;
        }

        if (b.score > a.score) {
            return b;
        }

        // Same score -> lexicographically smaller indices
        if (compare(a.indices, b.indices) <= 0) {
            return a;
        }

        return b;
    }

    // Lexicographical comparison
    private int compare(List<Integer> a, List<Integer> b) {

        int n = Math.min(a.size(), b.size());

        for (int i = 0; i < n; i++) {

            if (!a.get(i).equals(b.get(i))) {
                return Integer.compare(a.get(i), b.get(i));
            }
        }

        // If one is a prefix of the other,
        // shorter one is lexicographically smaller.
        return Integer.compare(a.size(), b.size());
    }
}