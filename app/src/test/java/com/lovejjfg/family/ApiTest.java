package com.lovejjfg.family;

import com.lovejjfg.blogdemo.utils.BaseUtil;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.robolectric.RobolectricTestRunner;
import org.robolectric.annotation.Config;

@RunWith(RobolectricTestRunner.class)
@Config(sdk = 23, manifest = Config.NONE)
public class ApiTest extends BaseTest {



    //    private LoginApi loginApi;
    @Override
    @Before
    public void setUp() {
        super.setUp();
//        activity = Robolectric.buildActivity(MainActivity.class).create().get();
//        userService = BaseDataManager.getUserService();
//        loginApi = BaseDataManager.getLoginApi();
    }

    @After
    public void tearDown() {

    }


    @Test
    public void testVersion() throws Exception {
        int versionCode = BaseUtil.getVersionCode(context);
        String versionName = BaseUtil.getVersionName(context);
        System.out.println(versionCode);
        System.out.println(versionName);

    }


}
