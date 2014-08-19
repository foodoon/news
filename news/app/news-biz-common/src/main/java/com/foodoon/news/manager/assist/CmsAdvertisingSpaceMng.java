package com.foodoon.news.manager.assist;

import java.util.List;

import com.foodoon.news.entity.assist.CmsAdvertisingSpace;

public interface CmsAdvertisingSpaceMng {
	public List<CmsAdvertisingSpace> getList(Integer siteId);

	public CmsAdvertisingSpace findById(Integer id);

	public CmsAdvertisingSpace save(CmsAdvertisingSpace bean);

	public CmsAdvertisingSpace update(CmsAdvertisingSpace bean);

	public CmsAdvertisingSpace deleteById(Integer id);

	public CmsAdvertisingSpace[] deleteByIds(Integer[] ids);
}