class Solution {
    public List<List<Integer>> combinationSum(int[] candidates, int target) {
        List<List<Integer>> result = new ArrayList<>();
        int rem = target;
        solve(candidates, 0, rem, new ArrayList<>(), result);
        return result;
        
    }
    private void solve(
        int[] nums , int index, int rem, List<Integer> current, List<List<Integer>> result
    ) {
        if(rem == 0) {
            result.add(new ArrayList<>(current));
            return;
        }
        if(rem < 0) return;
        for(int i = index; i < nums.length; i++) {
            current.add(nums[i]);
            
            solve(nums, i, rem - nums[i], current, result);
            current.remove(current.size() - 1);
        }
    }
}