/*
 * Copyright 2019 The FATE Authors. All Rights Reserved.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package com.osx.broker.grpc;

import com.google.common.base.Preconditions;
import com.osx.broker.constants.Direction;
import com.osx.broker.util.ResourceUtil;
import com.osx.core.context.Context;
import com.webank.ai.eggroll.api.networking.proxy.Proxy;
import io.grpc.stub.StreamObserver;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class ForwardPullRespSO implements StreamObserver<Proxy.Packet> {

    Logger logger = LoggerFactory.getLogger(ForwardPullRespSO.class);

    //TokenApplyService tokenApplyService;

    Context context;

    StreamObserver<Proxy.Packet> backStreamObserver;

    public ForwardPullRespSO(Context context, StreamObserver<Proxy.Packet> backStreamObserver) {
        //,TokenApplyService  tokenApplyService){

        // Preconditions.checkArgument(tokenApplyService!=null);
        Preconditions.checkArgument(backStreamObserver != null);
        Preconditions.checkArgument(context != null);
        this.context = context;
        // this.tokenApplyService = tokenApplyService;
        this.backStreamObserver = backStreamObserver;
    }

    @Override
    public void onNext(Proxy.Packet value) {
        String resource = ResourceUtil.buildResource(context.getRouterInfo(), Direction.DOWN);
        //tokenApplyService.applyToken(context,resource,value.toByteArray().length);
        backStreamObserver.onNext(value);
    }

    @Override
    public void onError(Throwable t) {
        logger.error("error", t);
        t.printStackTrace();
        backStreamObserver.onError(t);
    }

    @Override
    public void onCompleted() {
        backStreamObserver.onCompleted();
    }
}
