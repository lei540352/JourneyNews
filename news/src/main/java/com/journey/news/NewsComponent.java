package com.journey.news;

import com.billy.cc.core.component.CC;
import com.billy.cc.core.component.CCResult;
import com.billy.cc.core.component.IComponent;
import com.journey.news.headlinenews.HeadlineNewsFragment;

public class NewsComponent implements IComponent {

    @Override
    public String getName() {
        return "News";
    }

    @Override
    public boolean onCall(CC cc) {
        String actionName = cc.getActionName();//根据名字来判断怎么操作
        switch (actionName){
            case "getHeadlineNewsFragment":
                CCResult result = new CCResult();
                result.addData("fragment",new HeadlineNewsFragment());
                CC.sendCCResult(cc.getCallId(),result);
                return true;
            default:
                //其他actionName当前组件暂时不能响应可以通过如下方式返回状态码为-12的ccResult 给调用方
                CC.sendCCResult(cc.getCallId(),CCResult.errorUnsupportedActionName());
                return false;
    }
    }
}
