package com.foodoon.news.manager.main.impl;

import java.io.IOException;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.foodoon.common.hibernate3.Updater;
import com.foodoon.core.manager.FtpMng;
import com.foodoon.news.dao.main.CmsSiteDao;
import com.foodoon.news.entity.main.CmsSite;
import com.foodoon.news.entity.main.CmsSiteCompany;
import com.foodoon.news.entity.main.CmsUser;
import com.foodoon.news.manager.assist.CmsResourceMng;
import com.foodoon.news.manager.main.CmsSiteCompanyMng;
import com.foodoon.news.manager.main.CmsSiteMng;
import com.foodoon.news.manager.main.CmsUserMng;
import com.foodoon.news.manager.main.CmsUserSiteMng;

@Service
@Transactional
public class CmsSiteMngImpl implements CmsSiteMng {
	private static final Logger log = LoggerFactory
			.getLogger(CmsSiteMngImpl.class);

	@Transactional(readOnly = true)
	public List<CmsSite> getList() {
		return dao.getList(false);
	}

	@Transactional(readOnly = true)
	public List<CmsSite> getListFromCache() {
		return dao.getList(true);
	}

	@Transactional(readOnly = true)
	public CmsSite findByDomain(String domain, boolean cacheable) {
		return dao.findByDomain(domain, cacheable);
	}

	@Transactional(readOnly = true)
	public CmsSite findById(Integer id) {
		CmsSite entity = dao.findById(id);
		return entity;
	}

	public CmsSite save(CmsSite currSite, CmsUser currUser, CmsSite bean,
			Integer uploadFtpId) throws IOException {
		if (uploadFtpId != null) {
			bean.setUploadFtp(ftpMng.findById(uploadFtpId));
		}
		bean.init();
		dao.save(bean);
		// 复制本站模板
		cmsResourceMng.copyTplAndRes(currSite, bean);
		// 处理管理员
		cmsUserMng.addSiteToUser(currUser, bean, bean.getFinalStep());
		//保存站点相关公司信息
		CmsSiteCompany company=new CmsSiteCompany();
		company.setName(bean.getName());
		siteCompanyMng.save(bean,company);
		return bean;
	}

	public CmsSite update(CmsSite bean, Integer uploadFtpId) {
		CmsSite entity = findById(bean.getId());
		if (uploadFtpId != null) {
			entity.setUploadFtp(ftpMng.findById(uploadFtpId));
		} else {
			entity.setUploadFtp(null);
		}
		Updater<CmsSite> updater = new Updater<CmsSite>(bean);
		entity = dao.updateByUpdater(updater);
		return entity;
	}

	public void updateTplSolution(Integer siteId, String solution) {
		CmsSite site = findById(siteId);
		site.setTplSolution(solution);
	}

	public CmsSite deleteById(Integer id) {
		// 删除用户、站点关联
		cmsUserSiteMng.deleteBySiteId(id);
		CmsSite bean = dao.deleteById(id);
		// 删除模板
		try {
			cmsResourceMng.delTplAndRes(bean);
		} catch (IOException e) {
			log.error("delete Template and Resource fail!", e);
		}
		return bean;
	}

	public CmsSite[] deleteByIds(Integer[] ids) {
		CmsSite[] beans = new CmsSite[ids.length];
		for (int i = 0, len = ids.length; i < len; i++) {
			beans[i] = deleteById(ids[i]);
		}
		return beans;
	}

	@Autowired
	private CmsUserMng cmsUserMng;
	@Autowired
	private CmsUserSiteMng cmsUserSiteMng;
	@Autowired
	private CmsResourceMng cmsResourceMng;
	@Autowired
	private FtpMng ftpMng;
	@Autowired
	private CmsSiteCompanyMng siteCompanyMng;
	@Autowired
	private CmsSiteDao dao;
}