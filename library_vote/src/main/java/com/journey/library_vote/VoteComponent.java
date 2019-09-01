package com.journey.library_vote;

import com.billy.cc.core.component.CC;
import com.billy.cc.core.component.CCResult;
import com.billy.cc.core.component.IComponent;
import com.journey.library_vote.fragment.VoteTypeFragment;

public class VoteComponent implements IComponent {
    @Override
    public String getName() {
        return "Vote";
    }

    @Override
    public boolean onCall(CC cc) {
        String actionName = cc.getActionName();
        switch (actionName){
            case "getVoteFragment":
                CCResult result = new CCResult();
                result.addData("fragment",new VoteTypeFragment());
                CC.sendCCResult(cc.getCallId(),result);
                break;
            default:
                //其他actionName当前组件暂时不能响应可以通过如下方式返回状态码为-12的ccResult 给调用方
                CC.sendCCResult(cc.getCallId(), CCResult.errorUnsupportedActionName());
                return false;
        }
        return false;
    }
}
