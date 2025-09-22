import java.util.Map;
import java.util.PriorityQueue;
import java.util.List;
import java.util.ArrayList;
import java.util.HashMap;

// https://leetcode.com/problems/design-movie-rental-system/?envType=daily-question&envId=2025-09-21

class MovieRentingSystem {

    private Map<String, Entry> movieMap;
    private Map<Integer, PriorityQueue<Entry>> cheapestStores;
    private PriorityQueue<Entry> cheapestMovies;

    public MovieRentingSystem(int n, int[][] entries) {
        movieMap = new HashMap<>();
        cheapestStores = new HashMap<>();
        cheapestMovies = new PriorityQueue<>();

        for (int i = 0; i < entries.length; i++) {
            int shop = entries[i][0];
            int movie = entries[i][1];
            Entry curEntry = new Entry(shop, movie, entries[i][2]);
            movieMap.put(convertToKey(shop, movie), curEntry);

            if (!cheapestStores.containsKey(movie)) {
                cheapestStores.put(movie, new PriorityQueue<>());
            }

            cheapestStores.get(movie).add(curEntry);

            cheapestMovies.add(curEntry);
        }
    }
    
    public List<Integer> search(int movie) {
        List<Integer> shops = new ArrayList<>();
        List<Entry> entries = new ArrayList<>();
        if (!cheapestStores.containsKey(movie)) return shops;
        PriorityQueue<Entry> storePQ = cheapestStores.get(movie);
        
        // search for the cheapest 5 shops, skip all rented copies
        while (shops.size() < 5 && !storePQ.isEmpty()) {
            Entry topEntry = storePQ.poll();
            if (topEntry.rented) {
                topEntry.inCheapestStorePQ = false;
                continue;
            }
            shops.add(topEntry.shop);
            entries.add(topEntry);
        }

        // add the removed entries back into the pq
        for (Entry entry : entries) {
            storePQ.add(entry);
        }

        return shops;
    }
    
    public void rent(int shop, int movie) {
        Entry copy = movieMap.get(convertToKey(shop, movie));
        copy.rented = true;
        // add this back into priority queues if necessary
        if (!copy.inCheapestMoviePQ) {
            cheapestMovies.add(copy);
            copy.inCheapestMoviePQ = true;
        }
    }
    
    public void drop(int shop, int movie) {
        Entry copy = movieMap.get(convertToKey(shop, movie));
        copy.rented = false;
        // add this back into priority queues if necessary
        if (!copy.inCheapestStorePQ) {
            cheapestStores.get(movie).add(copy);
            copy.inCheapestStorePQ = true;
        }
    }
    
    public List<List<Integer>> report() {
        List<List<Integer>> res = new ArrayList<>();
        List<Entry> entries = new ArrayList<>();
        
        // search for the cheapest 5 rented movies, skip all unrented copies
        while (res.size() < 5 && !cheapestMovies.isEmpty()) {
            Entry topEntry = cheapestMovies.poll();
            if (!topEntry.rented) {
                topEntry.inCheapestMoviePQ = false;
                continue;
            }

            List<Integer> cur = new ArrayList<>();
            cur.add(topEntry.shop);
            cur.add(topEntry.movie);
            res.add(cur);
            entries.add(topEntry);
        }

        // add the removed entries back into the pq
        for (Entry entry : entries) {
            cheapestMovies.add(entry);
        }

        return res;
    }

    private String convertToKey(int shop, int movie) {
        return shop + "|" + movie;
    }

    private class Entry implements Comparable<Entry> {
        int shop, movie, price;
        boolean rented;
        boolean inCheapestStorePQ;
        boolean inCheapestMoviePQ;

        Entry(int s, int m, int p) {
            shop = s;
            movie = m;
            price = p;
            rented = false;
            inCheapestStorePQ = true;
            inCheapestMoviePQ = true;
        }

        public int compareTo(Entry o) {
            if (price == o.price) {
                if (shop == o.shop) return movie - o.movie;
                return shop - o.shop;
            }
            return price - o.price;
        }
    }
}

/**
 * Your MovieRentingSystem object will be instantiated and called as such:
 * MovieRentingSystem obj = new MovieRentingSystem(n, entries);
 * List<Integer> param_1 = obj.search(movie);
 * obj.rent(shop,movie);
 * obj.drop(shop,movie);
 * List<List<Integer>> param_4 = obj.report();
 */