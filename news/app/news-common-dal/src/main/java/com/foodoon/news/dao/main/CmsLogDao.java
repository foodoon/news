package com.foodoon.news.dao.main;

import java.util.Date;

import com.foodoon.common.page.Pagination;
import com.foodoon.news.entity.main.CmsLog;

public interface CmsLogDao {
	public Pagination getPage(Integer category, Integer siteId, Integer userId,
			String title, String ip, int pageNo, int pageSize);

	public CmsLog findById(Integer id);

	public CmsLog save(CmsLog bean);

	public CmsLog deleteById(Integer id);

	public int deleteBatch(Integer category, Integer siteId, Date before);
}