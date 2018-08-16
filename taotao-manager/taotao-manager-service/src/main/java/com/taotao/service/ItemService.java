package com.taotao.service;


import com.taotao.common.pojo.DataGridResult;
import com.taotao.common.utils.TaotaoResult;
import com.taotao.pojo.TbItem;

public interface ItemService {
	TbItem getItemById(Long itemID);
	DataGridResult getItemList(int page,int rows);
	TaotaoResult createItem(TbItem tbItem,String desc) throws Exception ;
}
