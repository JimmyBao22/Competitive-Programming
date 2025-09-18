#include <string>
#include <unordered_map>
#include <vector>
#include <queue>

using namespace std;

// https://leetcode.com/problems/design-a-food-rating-system/?envType=daily-question&envId=2025-09-17

class FoodRatings {
private:
    struct Food {
        string name, cuisine;
        int rating;
        bool valid;

        Food(string name, string cuisine, int rating) : name(name), cuisine(cuisine), rating(rating) {
            this->name = name;
            this->cuisine = cuisine;
            this->rating = rating;
            valid = true;
        }
    };

    struct Compare {
        bool operator()(const Food* a, const Food* b) {
            if (a->rating == b->rating) {
                return a->name > b->name;
            } else {
                return a->rating < b->rating;
            }
        }
    };

    // store all food objects
    unordered_map<string, Food*> allFoods;

    // for each cuisine, store a priority queue
    unordered_map<string, priority_queue<Food*, vector<Food*>, Compare>> foodByCuisine;

public:
    // O(n log n)
    FoodRatings(vector<string>& foods, vector<string>& cuisines, vector<int>& ratings) {
        int n = foods.size();
        for (int i = 0; i < n; i++) {
            Food* foodPtr = new Food(foods[i], cuisines[i], ratings[i]);
            allFoods[foods[i]] = foodPtr;
            foodByCuisine[cuisines[i]].push(foodPtr);
        }
    }
    
    // when we want to change ratings, set the old value to false
    // since we're using pqs, directly changing the value won't cause the queue to reheapify
    // so, we have 2 options: remove and readd (but there's no remove function in c++)
    //      or, add a 'valid' bool. 
    // So, set the old 'valid' bool to false, and then create a new object with the new rating
    // then, re-add to the heap in order to ensure the heap order is preserved
    // O(log n)
    void changeRating(string food, int newRating) {
        Food* foodPtr = allFoods[food];
        foodPtr->valid = false;
        Food* newFoodPtr = new Food(foodPtr->name, foodPtr->cuisine, newRating);
        allFoods[food] = newFoodPtr;
        foodByCuisine[foodPtr->cuisine].push(newFoodPtr);
    }
    
    // O(log n)
    string highestRated(string cuisine) {
        while (true) {
            Food* topFood = foodByCuisine[cuisine].top();
            // remove all invalid pointers at the top of the pq
            if (!topFood->valid) {
                foodByCuisine[cuisine].pop();
            } else {
                return topFood->name;
            }
        }
    }
};

/**
 * Your FoodRatings object will be instantiated and called as such:
 * FoodRatings* obj = new FoodRatings(foods, cuisines, ratings);
 * obj->changeRating(food,newRating);
 * string param_2 = obj->highestRated(cuisine);
 */