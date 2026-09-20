class Solution {
    public List<List<Integer>> subsetsWithDup(int[] nums) {
        List<List<Integer>> result = new ArrayList<>();

        Arrays.sort(nums);
        solve(nums, 0, new ArrayList<>(), result);
        return result;
        
    }
    private void solve(int[] nums, int index, List<Integer> current, List<List<Integer>> result) {
        result.add(new ArrayList(current));
        for(int i = index ; i < nums.length; i++) {
            if(i > index && nums[i - 1] == nums[i]) continue;
            current.add(nums[i]);
            solve(nums, i + 1, current, result);
            current.remove(current.size() - 1);
        }
    }
}