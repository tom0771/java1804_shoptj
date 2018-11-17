package com.qf.dao;

import com.qf.entity.Cart;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface ICartDao {


    List<Cart> queryAllByUid(Integer uid);

    int addCart(Cart cart);

    int deleteByid(Integer id);

    int deleteCartByUid(Integer uid);

    List<Cart> getCartBygsidAnduid(@Param("gsid") int[] gsid, @Param("uid") Integer uid);


    Cart queryIsGoods(Cart cart);

    void updateGoodsnums(@Param("id") Integer id, @Param("goodsnums")Integer goodsnums);

    List<Cart> queryByCids(@Param("cids") Integer[] cids);
}
