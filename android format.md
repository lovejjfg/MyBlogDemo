# 安卓编码规范

本规范参考资料，依重要度排序

- Clean Code
- Google Java Style
- Android Open Source Project Style
- Sun Java Style




## 代码格式规范

1. 代码源文件为 UTF-8 编码

2. 所有重载的方法应该放在一起，连续出现，中间不要放入其它任何方法

3. 源码中不允许出现任何 `e.printStack()`, `System.out.println()`, `Log.v()`等输出语句

4. 对于尚未实现的功能使用 `TODO` 进行注释

5. 总是使用大括号，且左大括号不换行

   例：

   ```java
   if (result < 0){
     negative = true;
   }
   ```

6. 使用复杂表达式时即使小括号可选也要添加上

7. 一行一条语句

8. 每行只声明一个变量，不要写成 `int a,b,c = 1;`

9. 变量声明时应尽可能靠近使用其的地方

10. 列限制：100个字符

11. 方法限制：方法体内不超过40行

12. 方法体内，语句的逻辑组之间要空一行，逻辑组内不要空行

13. 声明数组时使用中缀中括号：`String[] args` 而非 `String args[]`

14. Modifiers 按照以下顺序声明 `public protected private abstract static final transient volatile synchronized native strictfp`

15. 包作用域前使用 `/* package */` 进行说明
   例：
```java
   public void foo() {}
   private void foo() {}
   /* package */void foo() {}
```

1.  尽量减少 if 嵌套，可能的话使用 `if-return` 进行替换
    例：
    ```java
    if (flg){
     // do something
    } else {
     // do other thing
    }
    /* 以上代码变为以下形式 */
    if (flg){
     // do something
     return;
    }
    // do other thing
    ```

2.  当连接的字符超过三个时，使用 `String.format()` 或 `StringBuilder` 连接字符串

3.  原则上所有域模型都为贫血模型，需要实现 Getter，Setter 和 `toString()`方法

4.  如果出现三层以上的调用方式，需要使用变量存储中间值
    例：
    ```java
    (x) man.friend.sun.name = "Peter";
    (o) Man sun = man.friend.sun;
    	sun.name = "Peter";
    ```


## 命名规范

1. 包名

   1. 全部使用小写英文字母
   2. 如果是包内的类同属于一个逻辑组，则包名使用复数形式
   3. 如有必要需要添加 `package-info`

2. 变量名

   1. 使用小驼峰式命名规则

3. 常量名/静态常量名

   1. 全部大写，用下划线分割多个单词

4. 方法名/静态方法名

   1. 方法名使用动词或动词短语
   2. 遵守小写驼峰式命名规则
   3. 测试方法名为 `test<MethodUnderTest><Status>`
   4. 如果方法的返回值可以为空时，方法名上加上 `@Nullable` 注解

5. 参数名

   1. 参数名使用小写驼峰式命名规则
   2. 如果参数不能为空，则在参数前加上 `@NonNull`注解
   3. 如果参数为资源ID，则在参数前加上`@AnimRes @IdRes @ColorRes @DrawableRes`等注解
   4. 如果参数为指定范围内的常量，可以使用自定义的常量制约
   5. 如果参数数量大于等于5个时需要使用参数对象，大于等于3个时参数对象可选
   6. 尽可能不要使用输出参数

6. 局部变量名

   1. 局部变量名使用小写驼峰式命名规则

7. 类型变量名

   1. 使用单个大写字符或全部大写字符
   2. 使用单个大写字符且多个变量意义相近时后缀追加数字

8. 类名

   1. 类名使用名词或名词短语
   2. 遵守大写驼峰式命名规则
   3. 测试类为其所测试的类的类名后添加 `Test`

9. 接口名

   1. 接口名使用名词或名词短语
   2. 接口名前可以加上 `I` 表示接口，在其实现类后加上 `impl` 表示实现；但是如果接口主要用于工厂类的时候，则建议不要在前面加上 `I`
      例

      ```java
      interface IUserService {}
      class UserServiceImpl implements IUserService {}

      interface Human {}
      class Man implements Human {}
      class HumanFactory {
        public static Human createHuman(){}
      }
      ```




## 注释规范

1.  只添加必要的注释

2.  每个类前添加注释表明类的作用

       ```
       /**
    * 类的作用
    * 
    * @author 创建者
    * @since 创建时间(yyyy/MM/dd)
    */
       ```


1.  原则上禁止使用行末注释

2.  public 成员使用 javadoc 注释

3.  其它成员可以使用单行或多行注释，也可以使用 javadoc 注释




## 包管理规范

包管理根据采用的开发模式会有对应的变化

```
|- app/src/main/java    应用目录
    |- **/xxx		项目名小写
        |- xxx    自定义的 Application，为项目名+Application
        |- common   存放一些通用配置
            |- Constants    存放相关常量，比如远程服务器地址，超时时间等
        |- data
            |- local    存放各种访问本地资源的服务，如对 Prefs，本地文件操作的封装
            |- remote   存放各种访问远程资源的服务，如 http, socket 等请求
            |- models  存放各种域模型
            |- entities 当域模型和实体不匹配或域模型无法充当实体时，存放对应的实体类
        |- interfaces   存放各种公用的 interfaces, callbacks, listeners，只有画面本身可用的就直接定义为内部类即可
        |- ui
            |- activities 存放 Activity，所有 Activity 应有统一的父类
            |- fragments 存放 Fragment，所有 Fragment 应有统一的父类
            |- adapters 存放 Adapter
            |- views 存放各种自定义 View
        |- controllers
        	|- receivers 存放各种 BroadcastReceiver
        	|- services 存放各种 Service
        	|- providers 存放各种 ContentProvider
        |- utils    存放各种工具类，助手类，工厂类
```



## Android 特殊规范

### 传值常量规范

对于 Android 中表示传值的常量，将常量定义在需要使用这些常量的类中

以下为特殊常量命名规范

|      类型       |       格式       |
| :-----------: | :------------: |
| Intent Action |   ACTION_XXX   |
|  Permission   | PERMISSION_XXX |
|    Bundle     |   EXTRA_XXX    |
|    Request    |  REQUEST_XXX   |
|  Preferences  |   PREFS_XXX    |
|    Dialog     |   DIALOG_XXX   |



### Android内置类命名规范

|        类型         |     格式      |
| :---------------: | :---------: |
|     Activity      | xxxActivity |
|     Fragment      | xxxFragment |
|      Service      | xxxService  |
| BroadcastReceiver | xxxReceiver |
|  ContentProvider  | xxxProvider |
|      Dialog       |  xxxDialog  |
|      Adapter      | xxxAdapter  |



### 资源文件命名规范

1. 所有资源文件全小写字符，使用下划线分隔多个单词

2. 布局命名

   |    使用的场景    |      格式      |
   | :---------: | :----------: |
   |  Activity   | activity_xxx |
   |  Fragment   | fragment_xxx |
   |   Dialog    |  dialog_xxx  |
   | PopupWindow |  popup_xxx   |
   |   Adapter   |   item_xxx   |
   |     子布局     |   item_xxx   |

3. Drawable 命名：用途_附近说明（如：bg_corner_white）

4. 动画命名：类型_方向（如：fade_in)

5. Strings 命名：

   1. 通用名词采用直译
   2. 消息提示，句子则为 `msg_xxx`
   3. 错误消息格式为 `err_msg_xxx`

6. Colors 命名：使用 `color_16进制数` 表示

7. Styles：布局文件中重复出现的样式需要提炼为Style




### 控件缩写参考

1. 控件命名使用小驼峰式，格式为：缩写+功能（如：btnLogin），缩写可以参考以下列表
2. 对于可重用的布局文件或控件本身在布局文件中只出现一次可以使用全称，全称+数字的形式进行命名（如：toolbar, webview, text1, text2）
3. 对于各种 Layout 也可以使用 `xxxContainer` 形式进行命名

|          控件类型           |       缩写       |
| :---------------------: | :------------: |
|     RelativeLayout      |       rl       |
|      LinearLayout       |       ll       |
|       FrameLayout       |       fl       |
|        TextView         |       tv       |
|        EditText         |       et       |
|         Button          |      btn       |
|       ImageButton       |  imgBtn 或 ib   |
|        ImageView        |  imgView 或 iv  |
|        CheckBox         |    chk 或 cb    |
|       RadioButton       |  rdoBtn 或 rb   |
|       AnalogClock       |     anaClk     |
|      DigitalClock       |     dgtClk     |
|       DatePicker        |   dtPk 或 dp    |
|       TimePicker        |   tmPk 或 tp    |
|      ToggleButton       |     tglBtn     |
|       ProgressBar       |     proBar     |
|         SeekBar         |     skBar      |
|  AutoCompleteTextView   |    autoTxt     |
|       ZoomControl       |     zmCtl      |
|        VideoView        |     vdoVi      |
|         WebView         |     webVi      |
|         Spinner         |      spn       |
|       Chronometer       |      cmt       |
|       ScrollView        | sclVi 或 scroll |
|       TextSwitch        |     txtSwt     |
|       ImageSwitch       |     imgSwt     |
|        ListView         |       lv       |
|        GridView         |       gv       |
|     ExpandableList      |     epdLt      |
|         MapView         |    mapView     |
|         Toolbar         |    toolbar     |
|      RecyclerView       | rv 或 recycler  |
|        CardView         |       cv       |
|      AppBarLayout       |     appbar     |
| CollapsingToolbarLayout | collapsToolbar |