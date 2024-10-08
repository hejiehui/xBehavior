package com.xrosstools.xbehavior.sample.spring;

import com.xrosstools.xbehavior.Behavior;
import com.xrosstools.xbehavior.XbehaviorFactory;

/**
 IMPORTANT NOTE!
 This is generated code based on Xross Behavior Tree model file "spring_support.xbehavior".
 In case the model file changes, regenerate this file.
 Do Not Modify It.



 Last generated time:
 2024-10-08T22:28:21.737+08:00[Asia/Shanghai]
 */
public class Spring_support {

    public static class Sequence {
        public static Behavior create() {
            return load().create("sequence");
        }
    }


    private static volatile XbehaviorFactory factory;
    private static XbehaviorFactory load()  {
        if(factory == null) {
            synchronized(Spring_support.class) {
                if(factory == null)
                    factory = XbehaviorFactory.load("spring_support.xbehavior");
            }
        }
        return factory;
    }
}
