package com.xrosstools.xbehavior.idea.editor;

import com.intellij.openapi.util.IconLoader;

import javax.swing.*;

public interface XbehaviorIcons {
    Icon TREE = IconLoader.getIcon("/icons/tree.png", XbehaviorIcons.class);
    Icon CONNECTION = IconLoader.getIcon("/icons/connection.png", XbehaviorIcons.class);
    Icon NODE = IconLoader.getIcon("/icons/Action.png", XbehaviorIcons.class);

    Icon SEQUENCE_ICON = IconLoader.getIcon("/icons/Sequence.png", XbehaviorIcons.class);
    Icon SELECTOR_ICON = IconLoader.getIcon("/icons/Selector.png", XbehaviorIcons.class);
    Icon PARALLEL_ICON = IconLoader.getIcon("/icons/Parallel.png", XbehaviorIcons.class);

    Icon INVERTER_ICON = IconLoader.getIcon("/icons/Inverter.png", XbehaviorIcons.class);
    Icon REPEAT_ICON = IconLoader.getIcon("/icons/Repeat.png", XbehaviorIcons.class);
    Icon RETRY_ICON = IconLoader.getIcon("/icons/Retry.png", XbehaviorIcons.class);
    Icon WAIT_ICON = IconLoader.getIcon("/icons/Wait.png", XbehaviorIcons.class);
    Icon FORCE_SUCCESS_ICON = IconLoader.getIcon("/icons/ForceSuccess.png", XbehaviorIcons.class);
    Icon FORCE_FAILURE_ICON = IconLoader.getIcon("/icons/ForceFailure.png", XbehaviorIcons.class);

    Icon ACTION_ICON = IconLoader.getIcon("/icons/Action.png", XbehaviorIcons.class);
    Icon CONDITION_ICON = IconLoader.getIcon("/icons/Condition.png", XbehaviorIcons.class);
    Icon FIXED_STATUS_ICON = IconLoader.getIcon("/icons/FixedStatus.png", XbehaviorIcons.class);
    Icon SLEEP_ICON = IconLoader.getIcon("/icons/Sleep.png", XbehaviorIcons.class);
}
