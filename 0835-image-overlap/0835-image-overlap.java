class Solution {
    public int largestOverlap(int[][] img1, int[][] img2) {
        int n = img1.length;

        int maxOverlap = 0;

        // Store coordinates of all 1s in img1
        java.util.List<int[]> ones1 = new java.util.ArrayList<>();

        // Store coordinates of all 1s in img2
        java.util.List<int[]> ones2 = new java.util.ArrayList<>();

        for (int r = 0; r < n; r++) {
            for (int c = 0; c < n; c++) {

                if (img1[r][c] == 1) {
                    ones1.add(new int[]{r, c});
                }

                if (img2[r][c] == 1) {
                    ones2.add(new int[]{r, c});
                }
            }
        }

        // Count how many pairs produce the same translation
        java.util.Map<String, Integer> frequency = new java.util.HashMap<>();

        for (int[] p1 : ones1) {
            for (int[] p2 : ones2) {

                int dr = p2[0] - p1[0];
                int dc = p2[1] - p1[1];

                String key = dr + "," + dc;

                int count = frequency.getOrDefault(key, 0) + 1;

                frequency.put(key, count);

                maxOverlap = Math.max(maxOverlap, count);
            }
        }

        return maxOverlap;
    }
}