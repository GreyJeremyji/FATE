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
package com.osx.broker.interceptor;
import com.osx.broker.router.FateRouterService;
import com.osx.core.context.Context;
import com.osx.core.router.RouterInfo;
import com.osx.core.service.InboundPackage;
import com.osx.core.service.Interceptor;
import com.osx.core.service.OutboundPackage;
import org.ppc.ptp.Osx;
public class RouterInterceptor implements Interceptor {
    public RouterInterceptor(FateRouterService fateRouterService){
        this.fateRouterService = fateRouterService;
    }
    FateRouterService  fateRouterService;
    public void doPreProcess(Context context, InboundPackage<Osx.Inbound> inboundPackage, OutboundPackage<Osx.Outbound> outboundPackage) throws Exception {
        String sourcePartyId = context.getSrcPartyId();
        String desPartyId = context.getDesPartyId();
        String sourceComponentName  = context.getSrcComponent();
        String desComponentName = context.getDesComponent();
        RouterInfo routerInfo = fateRouterService.route(sourcePartyId,sourceComponentName,desPartyId,desComponentName);
        context.setRouterInfo(routerInfo);
    }

}
