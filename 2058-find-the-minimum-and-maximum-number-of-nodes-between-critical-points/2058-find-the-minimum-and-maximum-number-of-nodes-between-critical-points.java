/**
 * Definition for singly-linked list.
 * public class ListNode {
 *     int val;
 *     ListNode next;
 *     ListNode() {}
 *     ListNode(int val) { this.val = val; }
 *     ListNode(int val, ListNode next) { this.val = val; this.next = next; }
 * }
 */
class Solution {
    public int[] nodesBetweenCriticalPoints(ListNode head) {
        ListNode prev = head;
        ListNode curr = head.next;

        int position = 1;
        int firstCritical = -1;
        int previousCritical = -1;

        int minDistance = Integer.MAX_VALUE;

        while(curr.next != null) {
            position++;

            boolean isCritical = (prev.val < curr.val && curr.val > curr.next.val) || (prev.val > curr.val && curr.val < curr.next.val);

            if(isCritical) {
                if(firstCritical == -1) {
                    firstCritical = position;
                } else {
                   minDistance =  Math.min(
                        minDistance,
                        position - previousCritical
                    );
                }
                previousCritical = position;
            }
            prev = curr;
            curr = curr.next;
        }

        if(firstCritical == previousCritical) return new int[] {-1,-1};
        int maxDistance = previousCritical - firstCritical;

        return new int[] {minDistance, maxDistance};
        
    }
}