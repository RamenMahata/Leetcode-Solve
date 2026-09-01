import java.util.*;

class Solution {

    static class State {
        int r;
        int c;
        int mask;
        int energy;
        int dist;

        State(int r, int c, int mask, int energy, int dist) {
            this.r = r;
            this.c = c;
            this.mask = mask;
            this.energy = energy;
            this.dist = dist;
        }
    }

    public int minMoves(String[] classroom, int energy) {
        int m = classroom.length;
        int n = classroom[0].length();

        // Find starting position and assign IDs to litter cells.
        int startR = -1;
        int startC = -1;

        Map<Integer, Integer> litterId = new HashMap<>();
        int k = 0;

        for (int r = 0; r < m; r++) {
            for (int c = 0; c < n; c++) {

                char cell = classroom[r].charAt(c);

                if (cell == 'S') {
                    startR = r;
                    startC = c;
                } 
                else if (cell == 'L') {
                    // Encode (r, c) as one integer.
                    litterId.put(r * n + c, k);
                    k++;
                }
            }
        }

        // No litter -> already finished.
        if (k == 0) {
            return 0;
        }

        // Example: k = 4 -> 1111
        int allMask = (1 << k) - 1;

        /*
         * best[r][c][mask] =
         * maximum energy with which we have reached
         * (r, c) after collecting the litter represented by mask.
         */
        int[][][] best = new int[m][n][1 << k];

        // -1 means we have never reached this state.
        for (int r = 0; r < m; r++) {
            for (int c = 0; c < n; c++) {
                Arrays.fill(best[r][c], -1);
            }
        }

        Queue<State> queue = new ArrayDeque<>();

        // Initial state.
        best[startR][startC][0] = energy;
        queue.offer(new State(
                startR,
                startC,
                0,
                energy,
                0
        ));

        int[][] directions = {
            {1, 0},   // down
            {-1, 0},  // up
            {0, 1},   // right
            {0, -1}   // left
        };

        while (!queue.isEmpty()) {

            State current = queue.poll();

            int r = current.r;
            int c = current.c;
            int mask = current.mask;
            int e = current.energy;
            int dist = current.dist;

            // All litter collected.
            if (mask == allMask) {
                return dist;
            }

            // No energy -> cannot make another move.
            if (e == 0) {
                continue;
            }

            // Try all four directions.
            for (int[] dir : directions) {

                int nr = r + dir[0];
                int nc = c + dir[1];

                // Outside the grid.
                if (nr < 0 || nr >= m || nc < 0 || nc >= n) {
                    continue;
                }

                // Obstacle.
                if (classroom[nr].charAt(nc) == 'X') {
                    continue;
                }

                // Every move costs 1 energy.
                int newEnergy = e - 1;

                // Initially, nothing changes in the mask.
                int newMask = mask;

                char nextCell = classroom[nr].charAt(nc);

                // Collect litter.
                if (nextCell == 'L') {
                    int id = litterId.get(nr * n + nc);
                    newMask |= (1 << id);
                }

                // Reset energy after entering R.
                if (nextCell == 'R') {
                    newEnergy = energy;
                }

                /*
                 * Dominance:
                 *
                 * If we have already reached this same
                 * (row, col, mask) with >= energy,
                 * this state can never be better.
                 */
                if (newEnergy <= best[nr][nc][newMask]) {
                    continue;
                }

                // This is a better state.
                best[nr][nc][newMask] = newEnergy;

                queue.offer(new State(
                        nr,
                        nc,
                        newMask,
                        newEnergy,
                        dist + 1
                ));
            }
        }

        // Could not collect all litter.
        return -1;
    }
}