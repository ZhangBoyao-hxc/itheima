package com.ithtima.考试.在线购物车系统;

import java.util.List;

public class OnlineShoppingCartSystem {
    public static void main(String[] args) {
        ShoppingCart cart = new ShoppingCart();

        // 添加商品到库存
        cart.addProductToInventory(new Product("1", "手机", 5000, 10, "电子产品"));
        cart.addProductToInventory(new Product("2", "书", 30, 50, "书籍"));
        cart.addProductToInventory(new Product("3", "耳机", 200, 20, "电子产品"));

        // 测试功能
        cart.addProductToCart("1", 2); // 添加2个手机到购物车
        cart.addProductToCart("2", 3); // 添加3本书到购物车
        cart.printCart();

        System.out.println("购物车总价：" + cart.calculateTotalPrice());

        cart.updateProductQuantity("1", 1); // 将手机数量更新为1
        cart.printCart();

        List<Product> electronics = cart.findProductsByCategory("电子产品");
        System.out.println("电子产品列表：");
        for (Product p : electronics) {
            System.out.println(p);
        }

        cart.removeProductFromCart("2"); // 移除书
        cart.printCart();

        cart.clearCart(); // 清空购物车
        cart.printCart();
    }
}