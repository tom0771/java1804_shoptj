package com.qf.service;

import com.qf.entity.Cart;
import com.qf.entity.User;

import java.util.List;

public interface ICartService {

    List<Cart> queryAllByUid(Integer uid);

    int addCart(Cart cart);

    int deleteByid(Integer id);

    int deleteCartByUid(Integer uid);

    List<Cart> getCarts(User user, String carts);

    List<Cart> getCartBygsidAnduid(int[] gsid, Integer uid);

    Cart queryIsGoods(Cart cart);

    void updataGoodsnums(Integer id,Integer goodsnums);
}
