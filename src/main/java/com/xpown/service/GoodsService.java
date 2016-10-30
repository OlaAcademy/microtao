package com.xpown.service;

import java.util.List;

import com.xpown.baseservice.BaseServiceMybatis;
import com.xpown.model.Goods;

public interface GoodsService extends BaseServiceMybatis<Goods, String> {

	List<Goods> getGoodsList(String keyword, String goodsId, String category, int pageSize);

	void addGoods(Goods goods);

}
