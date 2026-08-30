class Solution {
    public int findMin (int[] nums) {
        int n = nums.length;
        int minIndex = 0;
        for(int i =1; i < n; i++) {
            if(nums[i] < nums[minIndex]) minIndex = i; 
        }
        return minIndex;
    }
    public int findMax(int[] nums) {
        int n = nums.length;
        int maxIndex = 0;

        for(int i = 1; i < n; i++) {
            if(nums[i] > nums[maxIndex]) maxIndex = i;
        }
        return maxIndex;
    }
    public int minimumDeletions(int[] nums) {
        int n = nums.length;
        int minIndex = findMin(nums);
        int maxIndex = findMax(nums);

        int left = Math.min(minIndex, maxIndex);
        int right = Math.max(minIndex, maxIndex);

        return Math.min(
            right + 1,
            Math.min(
                n - left,
                (left + 1) + (n - right)
            )
        );
        
    }
}