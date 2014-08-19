/**
 * zoneland.net Inc.
 * Copyright (c) 2002-2012 All Rights Reserved.
 */
package com.foodoon.stream.common.dal.impl;

import com.foodoon.stream.common.dal.BlankDao;

import org.apache.log4j.Logger;

/**
 * 
 * @author gag
 * @version $Id: BlankDaoImpl.java, v 0.1 2012-5-3 ����2:10:04 gag Exp $
 */
public class BlankDaoImpl implements BlankDao {

    private final static Logger logger = Logger.getLogger(BlankDaoImpl.class);

    /** 
     * @see com.foodoon.stream.common.dal.BlankDao#queryUser()
     */
    public String queryUser() {
        if (logger.isInfoEnabled()) {
            logger.info("���ò���dao");
        }

        return "���Ĳ���DAO";
    }

}
