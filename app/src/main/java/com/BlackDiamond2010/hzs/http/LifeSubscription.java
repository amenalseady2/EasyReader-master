package com.BlackDiamond2010.hzs.http;

import rx.Subscription;

/**
 * Created by quantan.liu on 2017/3/21.
 */

public interface LifeSubscription{
    void bindSubscription(Subscription subscription);
}

