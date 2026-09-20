class Solution {
    public List<String> generateParenthesis(int n) {
        List<String> result = new ArrayList<>();
        solve(n, 0, 0, new StringBuilder(), result);
        return result;
    }
    private void solve(
        int n,
        int open,
        int close,
        StringBuilder current,
        List<String> result
    ) {
        if(open == n && close == n) {
            result.add(current.toString());
            return;
        }
        if(open < n) {
            solve(n, open + 1, close, current.append('('), result);
            current.deleteCharAt(current.length() - 1);
        }
        if(close < open) {
            solve(n, open, close + 1, current.append(')'), result);
            current.deleteCharAt(current.length() - 1);
        }
    }
}