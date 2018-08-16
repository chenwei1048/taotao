package com.taotao.service.impl;

import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.taotao.common.pojo.DataGridResult;
import com.taotao.common.utils.IDUtils;
import com.taotao.common.utils.TaotaoResult;
import com.taotao.mapper.TbItemDescMapper;
import com.taotao.mapper.TbItemMapper;
import com.taotao.pojo.TbItem;
import com.taotao.pojo.TbItemDesc;
import com.taotao.pojo.TbItemExample;
import com.taotao.pojo.TbItemExample.Criteria;
import com.taotao.service.ItemService;
@Service
public class ItemServiceImpl implements ItemService {

	@Autowired 
	private TbItemMapper itemMapper;
	
	@Autowired
	private TbItemDescMapper tbItemDescMapper;
	
	@Override
	public TbItem getItemById(Long itemID) {
		//TbItem tbItem = itemMapper.selectByPrimaryKey(itemID);
		TbItemExample example = new TbItemExample();
		Criteria criteria = example.createCriteria();
		criteria.andIdEqualTo(itemID);
		List<TbItem> list = itemMapper.selectByExample(example);
		if(list!=null &&list.size()>0){
			TbItem item = list.get(0);
			return item;
		}
		return null;
	}
	/**
	 * 商品列表查询
	 */
	@Override
	public DataGridResult getItemList(int page, int rows) {
		TbItemExample example = new TbItemExample();
		  //在查询之前传入当前页，然后多少记录
		PageHelper.startPage(page, rows);
		List<TbItem> itemList = itemMapper.selectByExample(example);
		DataGridResult result = new DataGridResult(); 
		result.setRows(itemList);
		PageInfo<TbItem> pageInfo = new PageInfo(itemList);
		result.setTotal(pageInfo.getTotal());
		return result;
	}
	/**
	 * 新增商品
	 * @throws Exception 
	 */
	@Override
	public TaotaoResult createItem(TbItem tbItem,String desc) throws Exception {
		//生成商品id
		long itemId = IDUtils.genItemId();
		tbItem.setId(itemId);
		//商品状态 1-正常 2-下架 3-删除
		tbItem.setStatus((byte) 1);
		tbItem.setCreated(new Date());
		tbItem.setUpdated(new Date());
		//单表插入
		itemMapper.insert(tbItem);
		TaotaoResult descResult = insertItemDesc(desc,itemId);
		if(descResult.getStatus()!=200){
			throw new Exception();
		}
		return TaotaoResult.ok();
	}
	private TaotaoResult insertItemDesc(String desc,Long itemId){
		TbItemDesc tbItemDesc = new TbItemDesc();
		tbItemDesc.setItemDesc(desc);
		tbItemDesc.setItemId(itemId);
		tbItemDesc.setCreated(new Date());
		tbItemDesc.setUpdated(new Date());
		tbItemDescMapper.insert(tbItemDesc);
		return TaotaoResult.ok();
	}

}
 