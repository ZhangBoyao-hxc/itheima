package com.ithtima.考试.在线购物车系统;

import java.util.*;

public class ShoppingCart {
    private Map<String, Integer> cartItems; // 商品ID -> 数量
    private Map<String, Product> inventory; // 商品ID -> 商品对象

    public ShoppingCart() {
        cartItems = new HashMap<>();
        inventory = new HashMap<>();
    }

    // 添加商品到库存
    public void addProductToInventory(Product product) {
        inventory.put(product.getId(), product);
    }

    // 添加商品到购物车
    public void addProductToCart(String productId, int quantity) {
        if (!inventory.containsKey(productId)) {
            System.out.println("商品ID不存在！");
            return;
        }
        Product product = inventory.get(productId);
        if (product.getStock() < quantity) {
            System.out.println("库存不足！当前库存：" + product.getStock());
            return;
        }
        cartItems.put(productId, cartItems.getOrDefault(productId, 0) + quantity);
        product.setStock(product.getStock() - quantity);
        System.out.println("成功添加商品到购物车！");
    }

    // 从购物车中删除商品
    public void removeProductFromCart(String productId) {
        if (!cartItems.containsKey(productId)) {
            System.out.println("购物车中没有该商品！");
            return;
        }
        Product product = inventory.get(productId);
        int quantity = cartItems.remove(productId);
        product.setStock(product.getStock() + quantity);
        System.out.println("成功从购物车中移除商品！");
    }

    // 更新购物车中的商品数量
    public void updateProductQuantity(String productId, int newQuantity) {
        if (!cartItems.containsKey(productId)) {
            System.out.println("购物车中没有该商品！");
            return;
        }
        Product product = inventory.get(productId);
        int currentQuantity = cartItems.get(productId);
        if (newQuantity > product.getStock() + currentQuantity) {
            System.out.println("更新失败：库存不足！");
            return;
        }
        cartItems.put(productId, newQuantity);
        product.setStock(product.getStock() + (currentQuantity - newQuantity));
        System.out.println("成功更新购物车中的商品数量！");
    }

    // 计算购物车总价
    public double calculateTotalPrice() {
        double total = 0.0;
        for (Map.Entry<String, Integer> entry : cartItems.entrySet()) {
            String productId = entry.getKey();
            int quantity = entry.getValue();
            Product product = inventory.get(productId);
            total += product.getPrice() * quantity;
        }
        return total;
    }

    // 查询特定类型的商品
    public List<Product> findProductsByCategory(String category) {
        List<Product> result = new ArrayList<>();
        for (String productId : cartItems.keySet()) {
            Product product = inventory.get(productId);
            if (product.getCategory().equalsIgnoreCase(category)) {
                result.add(product);
            }
        }
        return result;
    }

    // 清空购物车
    public void clearCart() {
        for (Map.Entry<String, Integer> entry : cartItems.entrySet()) {
            String productId = entry.getKey();
            int quantity = entry.getValue();
            Product product = inventory.get(productId);
            product.setStock(product.getStock() + quantity);
        }
        cartItems.clear();
        System.out.println("购物车已清空！");
    }

    // 打印购物车内容
    public void printCart() {
        System.out.println("购物车内容：");
        for (Map.Entry<String, Integer> entry : cartItems.entrySet()) {
            String productId = entry.getKey();
            int quantity = entry.getValue();
            Product product = inventory.get(productId);
            System.out.println(product.getName() + " x " + quantity);
        }
    }
}

