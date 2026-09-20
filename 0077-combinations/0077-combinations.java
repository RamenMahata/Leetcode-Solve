class Solution {
    public List<List<Integer>> combine(int n, int k) {
        List<List<Integer>> result = new ArrayList<>();
        solve(1, n, k, new ArrayList<>(), result);
        return result; 
        
    }
    private void solve(int index, int n, int k, List<Integer> current, List<List<Integer>> result) {
        if(current.size() == k) {
            result.add(new ArrayList<>(current));
            return;
        }
        for(int i = index; i <= n; i++) {
            current.add(i);
            solve(i+1, n, k, current, result);
            current.remove(current.size() - 1);
        }
    }
}