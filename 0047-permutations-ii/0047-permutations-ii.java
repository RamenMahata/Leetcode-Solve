class Solution {
    public List<List<Integer>> permuteUnique(int[] nums) {
        Arrays.sort(nums);
        List<List<Integer>> result = new ArrayList<>();
        boolean[] used = new boolean[nums.length];
        solve(nums, used, new ArrayList<>(), result);
        return result;
    }
    private void solve(
        int[] nums,
        boolean[] used,
        List<Integer> current,
        List<List<Integer>> result
    ) {
        if(current.size() == nums.length) {
            result.add(new ArrayList<>(current));
            return;
        }
        for(int i = 0; i < nums.length; i++) {
            if(used[i]) continue;
            if(i > 0 && nums[i] == nums[i - 1] && !used[i - 1]) {
                continue;
            }
            current.add(nums[i]);
            used[i] = true;
            solve(nums, used, current, result);
            used[i] = false;
            current.remove(current.size() - 1);
        }
    }
}