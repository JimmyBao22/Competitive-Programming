#include <vector>
using namespace std;

class KthLargestElementinanArray {
public:

    // https://leetcode.com/problems/kth-largest-element-in-an-array/description/

    int findKthLargest(vector<int>& nums, int k) {
        int n = nums.size();
        int pivot = findPivot(nums);
        int count_pivot = 0;
        vector<int> smaller;
        vector<int> larger;
        for (int i = 0; i < n; i++) {
            if (nums[i] < pivot) smaller.push_back(nums[i]);
            else if (nums[i] > pivot) larger.push_back(nums[i]);
            else count_pivot++;
        }
        
        if (larger.size() < k && smaller.size() <= n - k) {
            // pivot is the kth largest
            return pivot;
        }
        if (larger.size() >= k) {
            // it is in the larger side
            return findKthLargest(larger, k);
        }
        else {
            // it is in the smaller side
            return findKthLargest(smaller, k - count_pivot - larger.size());
        }
    }
    
    // choose pivot using randomness
    int findPivot(vector<int> nums) {
        int n = nums.size();
        while (true) {
            int rand_index = rand() % n;
            int x = nums[rand_index];
            // central splitter - at least n/4 elements are smaller than x and at least n/4 elements are larger than x
            int count_smaller = 0;
            int count_larger = 0;
            for (int i = 0; i < n; i++) {
                if (nums[i] <= x) count_smaller++;
                if (nums[i] >= x) count_larger++;
            }
            if (count_smaller > n/4 && count_larger > n/4) {
                // this is a central splitter
                return x;
            }
        }
    }
};