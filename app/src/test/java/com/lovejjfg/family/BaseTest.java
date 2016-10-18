package com.lovejjfg.family;

import android.content.Context;
import android.os.Handler;

import org.mockito.Mockito;
import org.mockito.invocation.InvocationOnMock;
import org.mockito.stubbing.Answer;
import org.robolectric.RuntimeEnvironment;

import static org.mockito.Mockito.any;
import static org.mockito.Mockito.mock;

class BaseTest {


    private Handler handler;
    protected Context context;

    public void setUp() {
        context = RuntimeEnvironment.application;
        handler = mock(Handler.class);
        Mockito.when(handler.post(any(Runnable.class)))
                .thenAnswer(new Answer<Object>() {
                    @Override
                    public Object answer(InvocationOnMock invocation) throws Throwable {
                        Runnable runnable = invocation.getArgumentAt(0, Runnable.class);
                        runnable.run();
                        return null;
                    }
                });
    }
}
