package com.xpown.controller;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.Date;
import java.util.Map;

import javax.annotation.Resource;

import org.json.JSONArray;
import org.json.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.xpown.model.Goods;
import com.xpown.service.GoodsService;
import com.xpown.utils.MapResult;

@Controller
@RequestMapping("/goods")
public class GoodsController {

	private static Logger logger = LoggerFactory
			.getLogger(GoodsController.class);

	@Resource
	private GoodsService goodsService;

	@RequestMapping(value = "getGoodsList", produces = "plain/text;charset=UTF-8")
	@ResponseBody
	public Map<String, Object> getGoodsList(
			@RequestParam(required = true) String appkey,
			@RequestParam(required = false) String category,
			@RequestParam(required = false) String goodsId,
			@RequestParam(defaultValue = "20") int pageSize) {
		try {
			Map<String, Object> ret = MapResult.initMap();
			ret.put("result", goodsService.getGoodsList(appkey,category,goodsId, pageSize));
			return ret;
		} catch (Exception e) {
			logger.error("", e);
			return MapResult.failMap();
		}
	}
	
	/**
	 * 定时器
	 */
	@RequestMapping(value = "insertData")
	@ResponseBody
	public void insertData() {
		importGoodsFromTao("5nkulide4r");
		importGoodsFromTao("021hjz3v3k");
	}

	/**
	 * 从大淘客后台导入数据
	 * @param appkey
	 * @return
	 */
	public void importGoodsFromTao(String appkey) {
		try {
			String result = getTaoData("http://api.dataoke.com/index.php",
					appkey);
			JSONObject jsonObj = new JSONObject(result);
			JSONObject data = jsonObj.getJSONObject("data");
			JSONArray goodsArray = data.getJSONArray("result");
			for (int i=0; i<goodsArray.length();i++) {
				JSONObject goodsObj = goodsArray.getJSONObject(i);
				Goods goods = new Goods();
				goods.setGoodsId(goodsObj.getString("GoodsID"));
				goods.setTitle(goodsObj.getString("Title"));
				goods.setPic(goodsObj.getString("Pic"));
				goods.setIntroduce(goodsObj.getString("Introduce"));
				goods.setPrice(goodsObj.getString("Org_Price"));
				goods.setCategory(goodsObj.getInt("Cid"));
				goods.setLink(goodsObj.getString("ali_click"));
				goods.setAppkey(appkey);
				goods.setCreateTime(new Date());
				goodsService.addGoods(goods);
			}
		} catch (Exception e) {
			logger.error("", e);
		}
	}

	/**
	 * 使用get方式登陆
	 * 
	 * @param username
	 * @param password
	 * @return
	 */
	public static String getTaoData(String url, String param) {
		HttpURLConnection conn = null;

		String data = "r=goodsLink/www&type=www_quan&appkey=" + param + "&v=2";
		url = url + "?" + data;
		try {
			URL mURL = new URL(url);
			conn = (HttpURLConnection) mURL.openConnection();

			conn.setRequestMethod("GET");
			conn.setReadTimeout(5000);
			conn.setConnectTimeout(10000);

			int responseCode = conn.getResponseCode();
			if (responseCode == 200) {
				InputStream is = conn.getInputStream();
				String result = getStringFromInputStream(is);
				return result;
			}
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			if (conn != null) {
				conn.disconnect();
			}
		}
		return null;
	}

	/**
	 * 根据流返回一个字符串信息 *
	 * 
	 * @param is
	 * @return
	 * @throws IOException
	 */
	private static String getStringFromInputStream(InputStream is)
			throws IOException {
		ByteArrayOutputStream os = new ByteArrayOutputStream();
		byte[] buffer = new byte[1024];
		int len = -1;
		// 一定要写len=is.read(buffer)
		// 如果while((is.read(buffer))!=-1)则无法将数据写入buffer中
		while ((len = is.read(buffer)) != -1) {
			os.write(buffer, 0, len);
		}
		is.close();
		// 把流中的数据转换成字符串,采用的编码是utf-8(模拟器默认编码)
		String state = os.toString();
		os.close();
		return state;
	}
}
