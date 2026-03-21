package com.example.collection_framework;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

public class ShoppingCart {
    private List<Item> items;

    public ShoppingCart() {
        this.items = new ArrayList<>();
    }

    public void addItem(Item item) {
        if (item != null) {
            items.add(item);
        }
    }

    public boolean removeItem(String itemId) {
        return items.removeIf(item -> item.getId().equals(itemId));
    }

    public double getTotalPrice() {
        return items.stream()
                .mapToDouble(Item::getPrice)
                .sum();
    }

    public List<Item> getItems() {
        return Collections.unmodifiableList(items);
    }

    public Optional<Item> findMostExpensiveItem() {
        return items.stream()
                .max((item1, item2) -> Double.compare(item1.getPrice(), item2.getPrice()));
    }

    public void clearCart() {
        items.clear();
    }
}
