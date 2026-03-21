package com.example.collection_framework;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

class ShoppingCartTest {

    private ShoppingCart cart;

    @BeforeEach
    void setUp() {
        cart = new ShoppingCart();
    }

    @Test
    void addItem_shouldIncreaseCartSize() {
        Item item = new Item("1", "Laptop", 1200.0);
        cart.addItem(item);
        assertEquals(1, cart.getItems().size());
        assertTrue(cart.getItems().contains(item));
    }

    @Test
    void removeItem_existingItem_shouldRemoveIt() {
        Item item = new Item("1", "Mouse", 25.0);
        cart.addItem(item);
        
        boolean removed = cart.removeItem("1");
        assertTrue(removed);
        assertEquals(0, cart.getItems().size());
    }

    @Test
    void removeItem_nonExistingItem_shouldReturnFalse() {
        Item item = new Item("1", "Keyboard", 100.0);
        cart.addItem(item);
        
        boolean removed = cart.removeItem("2");
        assertFalse(removed);
        assertEquals(1, cart.getItems().size());
    }

    @Test
    void getTotalPrice_shouldReturnSumOfAllItems() {
        cart.addItem(new Item("1", "Book", 15.0));
        cart.addItem(new Item("2", "Pen", 5.0));
        
        assertEquals(20.0, cart.getTotalPrice());
    }

    @Test
    void findMostExpensiveItem_shouldReturnCorrectItem() {
        Item cheapItem = new Item("1", "Pencil", 2.0);
        Item expensiveItem = new Item("2", "Tablet", 500.0);
        
        cart.addItem(cheapItem);
        cart.addItem(expensiveItem);
        
        Optional<Item> result = cart.findMostExpensiveItem();
        assertTrue(result.isPresent());
        assertEquals(expensiveItem, result.get());
    }

    @Test
    void clearCart_shouldRemoveAllItems() {
        cart.addItem(new Item("1", "Monitor", 300.0));
        cart.clearCart();
        
        assertEquals(0, cart.getItems().size());
        assertEquals(0.0, cart.getTotalPrice());
    }

    @Test
    void getItems_shouldReturnUnmodifiableList() {
        cart.addItem(new Item("1", "Mousepad", 10.0));
        List<Item> items = cart.getItems();
        
        assertThrows(UnsupportedOperationException.class, () -> {
            items.add(new Item("2", "Webcam", 50.0));
        });
    }
}
