package com.acme.listener;


import java.util.Calendar;
import java.util.Locale;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.joda.time.DateTime;
import org.nuxeo.ecm.core.api.DocumentModel;
import org.nuxeo.ecm.core.event.Event;
import org.nuxeo.ecm.core.event.EventContext;
import org.nuxeo.ecm.core.event.EventListener;
import org.nuxeo.ecm.platform.usermanager.UserManager;
import org.nuxeo.runtime.api.Framework;

public class LogUserLastLoginListener implements EventListener {

    private static final Log LOG = LogFactory.getLog(LogUserLastLoginListener.class);

    @Override
    public void handleEvent(Event event) {
        boolean enabled = Boolean.parseBoolean(Framework.getProperty("nuxeo.user.logLastLogin.listener.enabled", "false"));
        if (!enabled) {
            return;
        }
        if (LOG.isDebugEnabled()) {
            LOG.debug("<handleEvent> " + event.getName());
        }
        EventContext ctx = event.getContext();
        String userName = ctx.getPrincipal().getName();
        UserManager um = Framework.getService(UserManager.class);
        DocumentModel userModel = Framework.doPrivileged(() -> um.getUserModel(userName));
        if (userModel == null) {
            LOG.error("Document model for user " + userName + " not found.");
            return;
        }
        DateTime dateTime = DateTime.now();
        Calendar calendar = dateTime.toCalendar(Locale.getDefault());
        userModel.setPropertyValue("user:lastlogin", calendar);
        Framework.doPrivileged(() -> um.updateUser(userModel));
    }
}
