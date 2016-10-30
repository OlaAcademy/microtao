package com.xpown.serviceImpl;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.apache.commons.lang.StringUtils;
import org.apache.http.util.TextUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.xpown.baseservice.BaseServiceMybatisImpl;
import com.xpown.dao.GoodsDao;
import com.xpown.model.Goods;
import com.xpown.mybatis.EntityDao;
import com.xpown.service.GoodsService;

@Service
@Transactional
public class GoodsServiceImpl extends BaseServiceMybatisImpl<Goods, String>
		implements GoodsService {

	@Resource
	private GoodsDao<?> goodsDao;

	@Override
	protected EntityDao<Goods, String> getEntityDao() {
		return goodsDao;
	}

	public List<Goods> getGoodsList(String appkey,String category, String goodsId,
			int pageSize) {
		Map<String, Object> params = new HashMap<String, Object>();
		if(!StringUtils.isEmpty(goodsId)){
			Goods goods = goodsDao.getById(goodsId);
			if(goods!=null){
				params.put("createTime", goods.getCreateTime());
			}else{
				params.put("createTime", new Date());
			}
		}else{
			params.put("createTime", new Date());
		}
		if(!TextUtils.isEmpty(category)){
			params.put("category", category);
		}
		params.put("appkey", appkey);
		params.put("pageSize", pageSize);
		
		return goodsDao.getGoodsList(params);
	}

	public void addGoods(Goods goods) {
		goodsDao.save(goods);
	}

}
