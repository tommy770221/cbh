package com.cbh.facebook.util;

import com.restfb.DefaultFacebookClient;
import com.restfb.FacebookClient;
import com.restfb.Parameter;
import com.restfb.Version;
import com.restfb.json.JsonObject;
import com.restfb.types.send.*;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Tommy on 2017/5/14.
 */
public class TestFacebookManage {
    public static void main(String[] args) {

        String pageAccessToken = "EAAFY2anAUssBACgau7xzE7YUM4jSRLwUCSXLw7ykmonCu3gPGC1TzJsBRpG24brtuUDbZCYWoTWwcDevanfaeQkvyjDhrHdOY2yzYzIpP5fRAJvRGO1SiPMb3N455WZBo44FNRLKZAuxcaEAtadCzJXfeTPwIi8ETuIYAlPmwZDZD";

        // create a version 2.8 client
        FacebookClient pageClient = new DefaultFacebookClient(pageAccessToken, Version.VERSION_2_8);
        List<PersistentMenuModify> persistentMenuList=new ArrayList<PersistentMenuModify>();
        PersistentMenuModify persistentMenu=new PersistentMenuModify("default");
        WebButton menuItem=new WebButton("test Title","https://www.google.com");
        menuItem.setMessengerExtensions(false,"");
        menuItem.setWebviewHeightRatio(WebviewHeightEnum.tall);
        NestedButtonModify nested = new NestedButtonModify("My Account");
        nested.addCallToAction(new PostbackButton("Pay Bill", "PAYBILL_PAYLOAD"));
        nested.addCallToAction(new PostbackButton("History", "HISTORY_PAYLOAD"));
        nested.addCallToAction(new PostbackButton("Contact Info", "CONTACT_INFO_PAYLOAD"));

        persistentMenu.setComposerInputDisabled(false);
        persistentMenu.addCallToAction(nested);
     //   persistentMenu.addCallToAction(menuItem);
        persistentMenuList.add(persistentMenu);

        JsonObject resp = pageClient.publish("me/messenger_profile", JsonObject.class,
                Parameter.with("persistent_menu", persistentMenuList));
        System.out.println(resp.toString());

    }
}
