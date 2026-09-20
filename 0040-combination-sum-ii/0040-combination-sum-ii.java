class Solution {
    public List<List<Integer>> combinationSum2(int[] candidates, int target) {
        Arrays.sort(candidates);
        List<List<Integer>> result = new ArrayList<>();
        int rem = target;
        solve(candidates, 0, rem, new ArrayList<>(), result );
        return result;

    }
    private void solve (
        int[] nums, int start, int rem, List<Integer> current, List<List<Integer>> result
    ) {
        if(rem == 0) {
            result.add(new ArrayList<>(current));
            return;
        }
        if(rem < 0) return;
        for(int i = start; i < nums.length; i++) {
            if(i > start && nums[i] == nums[i-1]) continue;
            if(nums[i] > rem) break;
            current.add(nums[i]);
            solve(nums, i+1, rem - nums[i], current, result);
            current.remove(current.size() - 1);
        }
    }
}