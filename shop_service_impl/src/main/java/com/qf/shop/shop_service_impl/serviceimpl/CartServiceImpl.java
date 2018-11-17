package com.qf.shop.shop_service_impl.serviceimpl;

import com.alibaba.dubbo.config.annotation.Service;
import com.google.common.reflect.TypeToken;
import com.google.gson.Gson;
import com.qf.dao.ICartDao;
import com.qf.entity.Cart;
import com.qf.entity.Goods;
import com.qf.entity.User;
import com.qf.service.ICartService;
import com.qf.service.IGoodsService;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;

@Service
public class CartServiceImpl implements ICartService {

    @Autowired
    private ICartDao cartDao;

    @Autowired
    private IGoodsService goodsService;


    @Override
    public List<Cart> queryAllByUid(Integer uid) {
        return cartDao.queryAllByUid(uid);
    }

    @Override
    public int addCart(Cart cart) {
        return cartDao.addCart(cart);
    }

    @Override
    public int deleteByid(Integer id) {
        return cartDao.deleteByid(id);
    }

    @Override
    public int deleteCartByUid(Integer uid) {
        return cartDao.deleteCartByUid(uid);
    }

    @Override
    public List<Cart> getCarts(User user, String carts) {

        List<Cart> cartList = null;

        if(user !=null){
            //从数据库拿到购物车数据
            cartList = queryAllByUid(user.getId());
        }else{
            //从Cookie中拿到购物车数据
            TypeToken<List<Cart>> token = new TypeToken<List<Cart>>() {};
            cartList = new Gson().fromJson(carts,token.getType());

        }

        if(cartList !=null){
            for(int i =0;i<cartList.size();i++){
                Goods goods =goodsService.queryGoodsById(cartList.get(i).getGsid());
                cartList.get(i).setGoods(goods);
            }
        }


        return cartList;
    }

    @Override
    public List<Cart> getCartBygsidAnduid(int[] gsid, Integer uid) {
        List<Cart> cartList = cartDao.getCartBygsidAnduid(gsid, uid);
        for (int i = 0;i<cartList.size();i++){
            Goods goods = goodsService.queryGoodsById(cartList.get(i).getGsid());
            cartList.get(i).setGoods(goods);
        }

        return cartList;
    }

    @Override
    public Cart queryIsGoods(Cart cart) {
        return cartDao.queryIsGoods(cart);
    }

    @Override
    public void updataGoodsnums(Integer id, Integer goodsnums) {
            cartDao.updateGoodsnums(id,goodsnums);
    }
}
