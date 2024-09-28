package com.xrosstools.xbehavior.idea.editor.model;

import java.util.concurrent.TimeUnit;

public interface PropertyConstants {
    String PROP_NAME = "Name";
    String PROP_DESCRIPTION = "Description";

    String PROP_COUNT = "Count";
    String PROP_EXPRESSION = "Expression";
    String PROP_MODE = "Mode";
    String PROP_REPEAT_UNTIL_FAILURE = "Repeat until failure";
    String PROP_REACTIVE = "Reactive";
    String PROP_TIMEOUT = "Timeout";
    String PROP_TIME_UNIT = "Time unit";
    String PROP_IMPLEMENTATION = "Implementation";
    String PROP_SUBTREE = "Subtree";
    String PROP_ASYNCHRONOUS = "Asynchronous";
    String PROP_STATUS = "Status";
    String PROP_EVALUATOR = "Evaluator";

    int DEFAULT_HEIGHT = 50;
    ProcessMode DEFAULT_PROCESS_MODE = ProcessMode.MAX_ATTEMPT;
    Boolean DEFAULT_REPEAT_UNTIL_FAILURE = Boolean.TRUE;
    Boolean DEFAULT_REACTIVE = Boolean.FALSE;

    TimeUnit DEFAULT_TIME_UNIT = TimeUnit.SECONDS;
    ConditionNode.Mode DEFAULT_CONDITION_MODE = ConditionNode.Mode.EXPRESSION;
    ParallelNode.Mode DEFAULT_PARALLEL_MODE = ParallelNode.Mode.ALL;
    String DEFAULT_COUNT_STR = String.valueOf(1);
}
