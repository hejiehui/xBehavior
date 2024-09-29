package com.xrosstools.xbehavior.sample;

import com.xrosstools.xbehavior.Action;
import com.xrosstools.xbehavior.Blackboard;
import com.xrosstools.xbehavior.StatusEnum;

public class Action5FailureSuccess implements Action {
    int count = 5;
    @Override
    public StatusEnum tick(Blackboard blackboard) {
        return count == 0 ? StatusEnum.SUCCESS : StatusEnum.FAILURE;
    }

    @Override
    public void reset() {
        if(count-- > 0)
            return;
        count = 5;
    }
}
