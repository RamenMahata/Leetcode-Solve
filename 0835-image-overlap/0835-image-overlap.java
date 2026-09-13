class Solution {
    public int largestOverlap(int[][] img1, int[][] img2) {
        int n = img1.length;

        java.util.List<int[]> a = new java.util.ArrayList<>();
        java.util.List<int[]> b = new java.util.ArrayList<>();

        // Collect coordinates of 1s
        for (int r = 0; r < n; r++) {
            for (int c = 0; c < n; c++) {

                if (img1[r][c] == 1) {
                    a.add(new int[]{r, c});
                }

                if (img2[r][c] == 1) {
                    b.add(new int[]{r, c});
                }
            }
        }

        java.util.Map<Integer, Integer> map = new java.util.HashMap<>();

        int answer = 0;

        for (int[] p1 : a) {
            for (int[] p2 : b) {

                int dr = p2[0] - p1[0];
                int dc = p2[1] - p1[1];

                // Encode (dr, dc) into one integer
                int key = (dr + n) * 100 + (dc + n);

                int count = map.getOrDefault(key, 0) + 1;

                map.put(key, count);

                answer = Math.max(answer, count);
            }
        }

        return answer;
    }
}