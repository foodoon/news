package com.foodoon.news.action.admin;

import java.util.ArrayList;
import java.util.List;
import java.util.Properties;

import javax.servlet.http.HttpServletRequest;

import com.alibaba.fastjson.JSON;
import com.foodoon.news.entity.main.Channel;
import com.foodoon.news.manager.main.ChannelMng;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;

import com.foodoon.news.entity.main.CmsSite;
import com.foodoon.news.entity.main.CmsUser;
import com.foodoon.news.manager.main.CmsSiteMng;
import com.foodoon.news.statistic.CmsStatisticSvc;
import com.foodoon.news.web.AdminContextInterceptor;
import com.foodoon.news.helper.CmsUtils;

@Controller
public class WelcomeAct {
	@RequestMapping("/index.do")
	public String index(HttpServletRequest request, ModelMap model) {
        List<CmsSite> siteList = cmsSiteMng.getList();
        CmsSite site = CmsUtils.getSite(request);
        CmsUser user = CmsUtils.getUser(request);
        model.addAttribute("siteList", siteList);
        model.addAttribute("site", site);
        model.addAttribute("siteParam", AdminContextInterceptor.SITE_PARAM);
        model.addAttribute("user", user);


        List<Channel> list = channelMng.getTopList(site.getId(), false);
        List<Channel> channelList = new ArrayList<Channel>();
        for(Channel channel:list) {
            List<Channel> tempList = channelMng.getChildList(channel.getId(), false);
            channelList.addAll(tempList);
        }

        model.addAttribute("channelList", channelList);
		return "index";
	}
	
	@RequestMapping("/map.do")
	public String map() {
		return "map";
	}

	@RequestMapping("/top.do")
	public String top(HttpServletRequest request, ModelMap model) {
		// 需要获得站点列表
		List<CmsSite> siteList = cmsSiteMng.getList();
		CmsSite site = CmsUtils.getSite(request);
		CmsUser user = CmsUtils.getUser(request);
		model.addAttribute("siteList", siteList);
		model.addAttribute("site", site);
		model.addAttribute("siteParam", AdminContextInterceptor.SITE_PARAM);
		model.addAttribute("user", user);
		return "top";
	}



	@RequestMapping("/main.do")
	public String main() {
		return "main";
	}

	@RequestMapping("/left.do")
	public String left() {
		return "left";
	}

	@RequestMapping("/right.do")
	public String right(HttpServletRequest request, ModelMap model) {
		CmsSite site = CmsUtils.getSite(request);
		CmsUser user = CmsUtils.getUser(request);
		String version = site.getConfig().getVersion();
		Properties props = System.getProperties();
		Runtime runtime = Runtime.getRuntime();
		long freeMemoery = runtime.freeMemory();
		long totalMemory = runtime.totalMemory();
		long usedMemory = totalMemory - freeMemoery;
		long maxMemory = runtime.maxMemory();
		long useableMemory = maxMemory - totalMemory + freeMemoery;
		model.addAttribute("props", props);
		model.addAttribute("freeMemoery", freeMemoery);
		model.addAttribute("totalMemory", totalMemory);
		model.addAttribute("usedMemory", usedMemory);
		model.addAttribute("maxMemory", maxMemory);
		model.addAttribute("useableMemory", useableMemory);
		model.addAttribute("version", version);
		model.addAttribute("user", user);
		model.addAttribute("flowMap", cmsStatisticSvc.getWelcomeSiteFlowData(site.getId()));
		return "right";
	}

	@Autowired
	private CmsSiteMng cmsSiteMng;
	@Autowired
	private CmsStatisticSvc cmsStatisticSvc;
    @Autowired
    private ChannelMng channelMng;
}
