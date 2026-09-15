class Solution {

    private void reverse(char[] s, int left, int right) {

        if (left >= right) {
            return;
        }

        char temp = s[left];
        s[left] = s[right];
        s[right] = temp;

        reverse(s, left + 1, right - 1);
    }

    private void process(char[] s, int index) {

        // All words processed
        if (index >= s.length) {
            return;
        }

        // Find end of current word
        int end = index;

        while (end < s.length && s[end] != ' ') {
            end++;
        }

        // Reverse current word
        reverse(s, index, end - 1);

        // Process next word
        process(s, end + 1);
    }

    public String reverseWords(String s) {

        char[] chars = s.toCharArray();

        process(chars, 0);

        return new String(chars);
    }
}