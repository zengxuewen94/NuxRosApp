package com.nudtit.gkitr.greendao.gen;

import java.util.Map;

import org.greenrobot.greendao.AbstractDao;
import org.greenrobot.greendao.AbstractDaoSession;
import org.greenrobot.greendao.database.Database;
import org.greenrobot.greendao.identityscope.IdentityScopeType;
import org.greenrobot.greendao.internal.DaoConfig;

import com.eaibot.running.db.dao.SlamLocationBean;

import com.nudtit.gkitr.greendao.gen.SlamLocationBeanDao;

// THIS CODE IS GENERATED BY greenDAO, DO NOT EDIT.

/**
 * {@inheritDoc}
 * 
 * @see org.greenrobot.greendao.AbstractDaoSession
 */
public class DaoSession extends AbstractDaoSession {

    private final DaoConfig slamLocationBeanDaoConfig;

    private final SlamLocationBeanDao slamLocationBeanDao;

    public DaoSession(Database db, IdentityScopeType type, Map<Class<? extends AbstractDao<?, ?>>, DaoConfig>
            daoConfigMap) {
        super(db);

        slamLocationBeanDaoConfig = daoConfigMap.get(SlamLocationBeanDao.class).clone();
        slamLocationBeanDaoConfig.initIdentityScope(type);

        slamLocationBeanDao = new SlamLocationBeanDao(slamLocationBeanDaoConfig, this);

        registerDao(SlamLocationBean.class, slamLocationBeanDao);
    }
    
    public void clear() {
        slamLocationBeanDaoConfig.clearIdentityScope();
    }

    public SlamLocationBeanDao getSlamLocationBeanDao() {
        return slamLocationBeanDao;
    }

}