package com.acme.login;

import java.util.Calendar;

import org.apache.commons.lang.BooleanUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.nuxeo.ecm.core.api.DocumentModel;
import org.nuxeo.ecm.core.api.security.SecurityConstants;
import org.nuxeo.ecm.platform.api.login.UserIdentificationInfo;
import org.nuxeo.ecm.platform.login.BaseLoginModule;
import org.nuxeo.ecm.platform.login.LoginPlugin;
import org.nuxeo.ecm.platform.usermanager.UserManager;
import org.nuxeo.runtime.api.Framework;

public class ActiveUserLoginModule extends BaseLoginModule implements LoginPlugin {

    private static final Log LOG = LogFactory.getLog(ActiveUserLoginModule.class);

    /**
     * The name associated to this LoginPlugin.
     */
    public static final String NAME = "ACTIVEUSER_LM";

    @Override
    public Boolean initLoginModule() {
        return Boolean.TRUE;
    }

    @Override
    public String validatedUserIdentity(UserIdentificationInfo userIdent) {
        if (LOG.isDebugEnabled()) {
            LOG.debug("<validatedUserIdentity> " + userIdent);
        }
        UserManager um = Framework.getService(UserManager.class);
        boolean checkUsernamePassword = um.checkUsernamePassword(userIdent.getUserName(),
                userIdent.getPassword());
        if (SecurityConstants.ADMINISTRATOR.equals(userIdent.getUserName())) {
            if (checkUsernamePassword) {
                return userIdent.getUserName();
            }
            return null;
        }
        if (checkUsernamePassword && isUserActive(userIdent.getUserName()) && isUserNotExpired(userIdent.getUserName())) {
            return userIdent.getUserName();
        }
        LOG.warn("user [" + userIdent.getUserName() + "] is inactive");
        return null;
    }
    
    protected boolean isUserActive(String userName) {
        UserManager um = Framework.getService(UserManager.class);
        DocumentModel userModel = Framework.doPrivileged(() -> um.getUserModel(userName));
        if (userModel == null) {
            LOG.error("Document model for user " + userName + " not found.");
            return false;
        }
        Boolean active = (Boolean) userModel.getPropertyValue("user:active");
        if (LOG.isDebugEnabled()) {
            LOG.debug("<isUserActive> " + userName + " => " + active);
        }
        return BooleanUtils.isTrue(active);
    }

    protected boolean isUserNotExpired(String userName) {
        UserManager um = Framework.getService(UserManager.class);
        DocumentModel userModel = Framework.doPrivileged(() -> um.getUserModel(userName));
        if (userModel == null) {
            LOG.error("Document model for user " + userName + " not found.");
            return false;
        }
        Calendar expired = (Calendar) userModel.getPropertyValue("user:expired");
        if (LOG.isDebugEnabled()) {
            LOG.debug("<isUserNotExpired> " + userName + " => " + expired);
        }
       return (expired == null || Calendar.getInstance().before(expired));
    }
}
