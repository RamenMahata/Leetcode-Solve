class Solution {
    public String shortestBeautifulSubstring(String s, int k) {
        int n = s.length();
        List<Integer> ones = new ArrayList<>();
        for(int i = 0; i < n; i++) {
            if(s.charAt(i) == '1') ones.add(i);
        }
        if(ones.size() < k) return "";

        String answer = "";
        int bestLength = Integer.MAX_VALUE;

        for(int i = 0; i <= ones.size() - k; i++) {
            int start = ones.get(i);
            int end = ones.get(i + k - 1);

            int length = end - start + 1;
            String candidate = s.substring(start, end+1);

            if(length < bestLength) {
                bestLength = length;
                answer = candidate;
            }
            else if(length == bestLength && candidate.compareTo(answer) < 0) {
                answer = candidate;
            } 
        }
        return answer;


        
    }
}