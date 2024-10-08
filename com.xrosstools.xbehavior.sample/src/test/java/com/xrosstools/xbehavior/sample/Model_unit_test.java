package com.xrosstools.xbehavior.sample;

import com.xrosstools.xbehavior.Behavior;
import com.xrosstools.xbehavior.XbehaviorFactory;

/**
 IMPORTANT NOTE!
 This is generated code based on Xross Behavior Tree model file "xbehavior/model_unit_test.xbehavior".
 In case the model file changes, regenerate this file.
 Do Not Modify It.



 Last generated time:
 2024-09-26T23:39:16.793+08:00[Asia/Shanghai]
 */
public class Model_unit_test {

    public static class SequenceFailure {
        public static Behavior create() {
            return load().create("sequence failure");
        }
    }

    public static class SequenceSuccess {
        public static Behavior create() {
            return load().create("sequence success");
        }
    }

    public static class SequenceRunning {
        public static Behavior create() {
            return load().create("sequence running");
        }
    }

    public static class SelectorSuccess {
        public static Behavior create() {
            return load().create("selector success");
        }
    }

    public static class SelectorFailure {
        public static Behavior create() {
            return load().create("selector failure");
        }
    }

    public static class SelectorRunning {
        public static Behavior create() {
            return load().create("selector running");
        }
    }

    public static class ParallelSuccess {
        public static Behavior create() {
            return load().create("parallel success");
        }
    }

    public static class ParallelFailure {
        public static Behavior create() {
            return load().create("parallel failure");
        }
    }

    public static class ParallelRunning {
        public static Behavior create() {
            return load().create("parallel running");
        }
    }

    public static class InverterSuccess {
        public static Behavior create() {
            return load().create("inverter success");
        }
    }

    public static class InverterFailure {
        public static Behavior create() {
            return load().create("inverter failure");
        }
    }

    public static class InverterRunning {
        public static Behavior create() {
            return load().create("inverter running");
        }
    }

    public static class RepeatSuccess {
        public static Behavior create() {
            return load().create("repeat success");
        }
    }

    public static class RepeatFailure {
        public static Behavior create() {
            return load().create("repeat failure");
        }
    }

    public static class RepeatRunning {
        public static Behavior create() {
            return load().create("repeat running");
        }
    }

    public static class RepeatSuccessTimeout {
        public static Behavior create() {
            return load().create("repeat success timeout");
        }
    }

    public static class RepeatFailureTimeout {
        public static Behavior create() {
            return load().create("repeat failure timeout");
        }
    }

    public static class RepeatRunningTimeout {
        public static Behavior create() {
            return load().create("repeat running timeout");
        }
    }

    public static class RetryFailure {
        public static Behavior create() {
            return load().create("retry failure");
        }
    }

    public static class RetrySuccess {
        public static Behavior create() {
            return load().create("retry success");
        }
    }

    public static class RetryRunning {
        public static Behavior create() {
            return load().create("retry running");
        }
    }

    public static class RetrySuccessTimeout {
        public static Behavior create() {
            return load().create("retry success timeout");
        }
    }

    public static class RetryFailureTimeout {
        public static Behavior create() {
            return load().create("retry failure timeout");
        }
    }

    public static class RetryRunningTimeout {
        public static Behavior create() {
            return load().create("retry running timeout");
        }
    }

    public static class WaitSuccess {
        public static Behavior create() {
            return load().create("wait success");
        }
    }

    public static class WaitFailure {
        public static Behavior create() {
            return load().create("wait failure");
        }
    }

    public static class WaitRunning {
        public static Behavior create() {
            return load().create("wait running");
        }
    }

    public static class ForceSucess {
        public static Behavior create() {
            return load().create("force sucess");
        }
    }

    public static class ForceFailure {
        public static Behavior create() {
            return load().create("force failure");
        }
    }

    public static class ForceSuccessRunning {
        public static Behavior create() {
            return load().create("force success running");
        }
    }

    public static class ForceFailureRunning {
        public static Behavior create() {
            return load().create("force failure running");
        }
    }

    public static class Sleep {
        public static Behavior create() {
            return load().create("sleep");
        }
    }

    public static class Failure {
        public static Behavior create() {
            return load().create("Failure");
        }
    }

    public static class Success {
        public static Behavior create() {
            return load().create("Success");
        }
    }

    public static class SubtreeSucess {
        public static Behavior create() {
            return load().create("subtree sucess");
        }
    }

    public static class CallbackTrue {
        public static Behavior create() {
            return load().create("callback true");
        }
    }

    public static class CallbackFalse {
        public static Behavior create() {
            return load().create("callback false");
        }
    }

    public static class ExpressionTrue {
        public static Behavior create() {
            return load().create("expression true");
        }
    }

    public static class ExpressionFalse {
        public static Behavior create() {
            return load().create("expression false");
        }
    }

    private static volatile XbehaviorFactory factory;
    private static XbehaviorFactory load()  {
        if(factory == null) {
            synchronized(Model_unit_test.class) {
                if(factory == null)
                    factory = XbehaviorFactory.load("model_unit_test.xbehavior");
            }
        }
        return factory;
    }
}
