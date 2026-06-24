class Solution:

    # https://leetcode.com/problems/total-waviness-of-numbers-in-range-ii/?envType=daily-question&envId=2026-06-05

    MAX_NUM_DIGITS = 17

    def totalWaviness(self, num1: int, num2: int) -> int:
        total_waviness_num2 = self.total_waviness_helper(str(num2))
        print(total_waviness_num2)
        total_waviness_num1 = self.total_waviness_helper(str(num1 - 1))
        print(total_waviness_num1)
        return total_waviness_num2 - total_waviness_num1

    def total_waviness_helper(self, max_val: str):
        if len(max_val) < 3:
            return 0
        
        # digit dp - each item holds a tuple
        # Need to store # future ways to form a number so that if we currently form a peak/valley, we know how much to add. Note that sum of all future ways starting at 0 == max_val
        # Need to store future total waviness formed for the memoization. 
            # Initially I made the mistake of only storing the # of future ways, without storing future waviness count. Then, whenever you hit "if memo[...] == None: return memo[...]", you're not returning the future waviness count of that section which leads to significant undercounting

        # dp[index][last last digit][last digit][first nonzero digit index][at max value y/n] = (# of possible ways to form #s at [index...], total waviness formed at [index...])
        self.memo = [[[[[None] * 2 for _ in range(self.MAX_NUM_DIGITS + 1)] for _ in range(10)] for _ in range(10)] for _ in range(self.MAX_NUM_DIGITS)]

        total_ways = 0
        total_sum = 0

        for first_digit in range(int(max_val[0])):
            for second_digit in range(10):
                if first_digit == 0 and second_digit == 0:
                    w, s = self.dp(2, 0, 0, self.MAX_NUM_DIGITS, 0, max_val)
                elif first_digit == 0:
                    w, s = self.dp(2, 0, second_digit, 1, 0, max_val)
                else:
                    w, s = self.dp(2, first_digit, second_digit, 0, 0, max_val)
                total_ways += w
                total_sum += s

        # first_digit == max_val[0]
        for second_digit in range(int(max_val[1])):
            w, s = self.dp(2, int(max_val[0]), second_digit, 0, 0, max_val)
            total_ways += w
            total_sum += s

        # first_digit == max_val[0] and second_digit == max_val[1]
        w, s = self.dp(2, int(max_val[0]), int(max_val[1]), 0, 1, max_val)
        total_ways += w
        total_sum += s

        print(total_ways, max_val)
        assert total_ways == int(max_val)

        return total_sum


    def dp(self, index, last_last_digit, last_digit, nonzero_index, at_max, max_val: str):
        if index == len(max_val):
            return (nonzero_index != self.MAX_NUM_DIGITS, 0)

        if self.memo[index][last_last_digit][last_digit][nonzero_index][at_max] != None:
            return self.memo[index][last_last_digit][last_digit][nonzero_index][at_max]
        
        prev_sum = 0
        current_sum = 0
        num_ways = 0
        if at_max:
            for cur_digit in range(int(max_val[index])):
                w, s = self.dp(index + 1, last_digit, cur_digit, nonzero_index, 0, max_val)
                if last_last_digit < last_digit and cur_digit < last_digit: # peak
                    current_sum += w
                elif last_last_digit > last_digit and cur_digit > last_digit: # valley
                    current_sum += w
                num_ways += w
                prev_sum += s

            # continue at max_val
            cur_digit = int(max_val[index])
            w, s = self.dp(index + 1, last_digit, cur_digit, nonzero_index, 1, max_val)
            if last_last_digit < last_digit and cur_digit < last_digit: # peak
                current_sum += w
            elif last_last_digit > last_digit and cur_digit > last_digit: # valley
                current_sum += w
            num_ways += w
            prev_sum += s
        
        elif nonzero_index >= index - 1:
            # the case where we can't add peak/valleys because the beginning of this number is all 0s
            assert last_last_digit == 0
            
            # cur_digit = 0
            w, s = self.dp(index + 1, last_digit, 0, nonzero_index, 0, max_val)
            num_ways += w
            prev_sum += s

            for cur_digit in range(1, 10):
                w, s = self.dp(index + 1, last_digit, cur_digit, min(nonzero_index, index), 0, max_val)
                num_ways += w
                prev_sum += s

        else:
            for cur_digit in range(10):
                w, s = self.dp(index + 1, last_digit, cur_digit, nonzero_index, 0, max_val)
                if last_last_digit < last_digit and cur_digit < last_digit: # peak
                    current_sum += w
                elif last_last_digit > last_digit and cur_digit > last_digit: # valley
                    current_sum += w
                num_ways += w
                prev_sum += s

        self.memo[index][last_last_digit][last_digit][nonzero_index][at_max] = (num_ways, current_sum + prev_sum)
        return (num_ways, current_sum + prev_sum)
