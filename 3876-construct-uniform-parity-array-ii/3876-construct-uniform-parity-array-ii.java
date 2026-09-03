class Solution {
    public boolean uniformArray(int[] nums1) {
        int min = nums1[0];
        boolean hasOdd = false;
        boolean hasEven = false;
        for(int num: nums1) {
            min = Math.min(min, num);

            if(num % 2 == 0) hasEven = true;
            else hasOdd = true;
        }

        if(!hasOdd || !hasEven) return true;
        return min % 2 == 1;
    }
}