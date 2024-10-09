# xBehavior
A behavior tree editor and runtime

# 安装
打开IDEA插件管理器，搜索xbehavior，选择并安装。xBehavior依赖IDEA GEF插件，如果有提示，请一并安装

# 使用

## 创建模型
选择想要创建模型的resource目录，从File New里面选择Xross Behavior Model，在对话框中修改模型文件名。

## 放置节点
在左侧菜单选择想要放置的节点，单击该节点，再单击编辑器主窗口相应位置

### 节点类型
节点类型分为三种大类:
* 复合节点。包括Sequence，Selector和Parallel
* 装饰节点。包括Inverter，Repeat，Retry，Wait，Force success，Force failure
* 基础节点。包括Condition，Action，Fixed status，Sleep和Subtree

## 节点连接

### 新建连接
单击Connection，再依次单击想要连接的两个节点即可

### 节点重连
通过鼠标选中连接后，拖拽连接两端的方形锚点到新的位置

### 注意事项
* 组合节点类型允许有多个节点
* 装饰节点只允许有一个子节点
* 基础节点不允许有子节点

进行节点连接或重连接时，如果不满足上述约束，连接将无法建立


## 修改属性
点击节点，下方属性窗口会显示该节点类型对应的属性，根据需要进行修改

## 调整顺序
行为树的节点顺序直接影响运行逻辑，为了调整顺序，可以简单的进行拖动，系统会自动进行重排。
* 复合节点的子节点可以通过左右拖动到新的位置完成调整
* 模型中各行为树可以通过上下拖动顶层节点来调整顺序

## 生成帮助文件
点击Helper可以生成针对当前模型的factory代码。所有子树都会根据name属性进行生成，如果子树顶层节点没有name属性，将忽略该子树。

# 模型属性
## 行为树模型本身属性
* Description。模型描述
* Evaluator。用于解析属性和Condition包含的表达式的求值器。缺省为com.xrosstools.xbehavior.XpressionEvaluator。依赖xpression。

## 复合节点属性
### Sequence
Sequence节点会顺序执行每个子节点，当所有子节点返回StatusEnum.SUCCESS时，将返回StatusEnum.SUCCESS；如果任何一个节点返回FAILURE，则返回FAILURE；如果某节点返回RUNNING，则返回RUNNING。其属性为：
* Name。节点名字
* Description。节点描述
* Reactive。是否采用Reactive模式，如果为true，则当子节点返回StatusEnum.RUNNING时，下一次tick从第一个子节点开始依次tick，否则从上一次返回RUNNING的子节点开始tick
注意Name和Description是所有行为节点的通用属性，下面不再重复介绍

### Selector
Selector节点会顺序执行每个子节点，当任意子节点返回StatusEnum.SUCCESS时，将返回StatusEnum.SUCCESS；如果所有节点返回FAILURE，则返回FAILURE；如果某节点返回RUNNING，则返回RUNNING。其属性为：
* Reactive。是否采用Reactive模式，如果为true，则当子节点返回StatusEnum.RUNNING时，下一次tick从第一个子节点开始依次tick，否则从上一次返回RUNNING的子节点开始tick

### Parallel
Parallel节点会“并发”（内部实现为顺序）执行所有子节点，根据Mode的选择，当满足数量的节点返回SUCCESS时，将返回SUCCESS，否则返回FAILURE；如果数量不满足，且子节点返回RUNNING，则返回RUNNING
* Mode。满足条件的模式，共有三种：ALL，所有节点都返回SUCCESS；ANY，任一节点返回SUCCESS；SOME，给定Count数量的节点都返回SUCCESS
* Count。当Mode为SOME时，会显示Count属性，其值为需要返回SUCCESS的子节点的最小数值

## 装饰节点
### Inverter
对子节点的返回值取反，子节点返回SUCCESS时，返回FAILURE；子节点返回FAILURE时，返回SUCCESS；子节点返回RUNNING时。返回RUNNING

### Repeat
Repeat节点会根据所选Mode重复调用子节点，直到节点返回FAILURE或满足一定条件
* Mode。节点调用模式，有两种。MAX_ATTEMPT，重复调用直到给定的最大重复次数；TIMEOUT，重复调用直到给定的时间用完，此选项将额外显示Time unit属性
* Repeat until failure。是否重复调用直到子节点返回FAILURE。为true时，子节点返回FAILURE时结束调用并返回FAILURE；为false时，忽略子节点状态并重复调用
* Count。重复的次数或者时间长度。如果Mode为MAX_ATTEMPT，则子节点返回RUNNING时，不会导致数量增加
* Time unit。重复时长对应的时间单位，选项从NANOSECONDS一直到DAYS

注意虽然Time unit提供了NANOSECONDS和MICROSECONDS，但实际测量这两种精度似乎都不太准，建议从MILLISECOND开始选取。

### Retry
Retry节点会根据所选Mode重复调用子节点，直到节点返回SUCCESS或满足一定条件
* Mode。节点调用模式，有两种。MAX_ATTEMPT，重复调用直到给定的最大重复次数；TIMEOUT，重复调用直到给定的时间用完，此选项将额外显示Time unit属性
* Count。重复的次数或者时间长度。如果Mode为MAX_ATTEMPT，则子节点返回RUNNING时，不会导致数量增加
* Time unit。重复时长对应的时间单位，选项从NANOSECONDS一直到DAYS

### Wait
Wait节点会在等待给定时间后再调用子节点
* Timeout。等待的时间长度
* Time unit。等待时长对应的时间单位，选项从NANOSECONDS一直到DAYS

### Force success
无论子节点返回SUCCESS或FAILURE，都返回SUCCESS；子节点返回RUNNING时将返回RUNNING

### Force failure
无论子节点返回SUCCESS或FAILURE，都返回FAILURE；子节点返回RUNNING时将返回RUNNING

## 基础节点

### Condition
只返回true/false
* Mode。节点调用模式，有两种。MAX_ATTEMPT，重复调用直到给定的最大重复次数；TIMEOUT，重复调用直到给定的时间用完，此选项将额外显示Time unit属性

### Action


### Fixed status

### Sleep和Subtree





























