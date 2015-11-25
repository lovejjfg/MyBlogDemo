# Add project specific ProGuard rules here.
# By default, the flags in this file are appended to flags specified
# in D:\Android\sdk/tools/proguard/proguard-android.txt
# You can edit the include path and order by changing the proguardFiles
# directive in build.gradle.
#
# For more details, see
#   http://developer.android.com/guide/developing/tools/proguard.html

# Add any project specific keep options here:

# If your project uses WebView with JS, uncomment the following
# and specify the fully qualified class name to the JavaScript interface
# class:
# Add project specific ProGuard rules here.
# By default, the flags in this file are appended to flags specified
# in E:\SDK\android-sdk-windows/tools/proguard/proguard-android.txt
# You can edit the include path and order by changing the proguardFiles
# directive in build.gradle.
#
# For more details, see
#   http://developer.android.com/guide/developing/tools/proguard.html

# Add any project specific keep options here:

# If your project uses WebView with JS, uncomment the following
# and specify the fully qualified class name to the JavaScript interface
# class:
#-keepclassmembers class fqcn.of.javascript.interface.for.webview {
#   public *;
#}

-optimizationpasses 5
-dontusemixedcaseclassnames
-dontskipnonpubliclibraryclasses
-dontskipnonpubliclibraryclassmembers
-dontpreverify
-verbose
-printmapping mapping.txt
-optimizations !code/simplification/arithmetic,!field/*,!class/merging/*



#不混淆实体类
-keep class com.pinganfang.ananzu.entity.**  {* ;}
-keep class com.pinganfang.ananzu.customer.entity.**  {* ;}
-keep class com.pinganfang.ananzu.landlord.entity.**  {* ;}

#不混淆注解框架
-keepattributes *Annotation*

#不混淆本地JS实现类
#-keepclassmembers class fqcn.of.javascript.interface.for.webview {
#   public class com.pinganfang.ananzu.pub.OnJavascriptInterface;
#}
#友盟统计
-keepclassmembers class * {
   public <init>(org.json.JSONObject);
}
#让混淆器检测到android官方包引用到不适用的API的时候不警告
-dontwarn android.support.**

-dontwarn  com.squareup.picasso.**

-dontwarn org.springframework.**

-dontwarn com.unity3d.**

-keep class * implements android.os.Parcelable {
  public static final android.os.Parcelable$Creator *;
}

-keepclasseswithmembernames class * {  # 保持 native 方法不被混淆
    native <methods>;
}

-keepclasseswithmembers class * {   # 保持自定义控件类不被混淆
    public <init>(android.content.Context, android.util.AttributeSet);
}

-keepattributes Signature

-keep class com.gotye.**  {* ;}

-keep class sun.misc.Unsafe { *; }

-keep class com.squareup.picasso.**  {* ;}

-keep class de.greenrobot.**  {* ;}

-keep class com.projectzero.android.library.**  {* ;}

-keep class **.R$* {*;}


-keep class com.baidu.** {*;}
-keep class vi.com.** {*;}
-dontwarn com.baidu.**
#-libraryjars BaiduLibrary/libs/armeabi/libBaiduMapSDK_v3_5_0_11.so
#-libraryjars BaiduLibrary/libs/armeabi/libbdpush_V2_3.so
#-libraryjars BaiduLibrary/libs/armeabi/liblocSDK6a.so
#-libraryjars BaiduLibrary/libs/armeabi-v7a/libBaiduMapSDK_v3_5_0_11.so
#-libraryjars BaiduLibrary/libs/armeabi-v7a/libbdpush_V2_3.so
#-libraryjars BaiduLibrary/libs/armeabi-v7a/liblocSDK6a.so


#EventBus混淆
-keep class de.greenrobot.** {*;}

-keepclassmembers class ** {
    public void onEvent*(**);
    public void wxShare*(**);
}



-keep  class com.squareup.picasso.** {*;}

-keep class com.yixia.** {*;}

-keep class org.springframework.** {*;}

-keep class com.unity3d.** {*;}

-keepattributes Signature

-dontwarn com.alibaba.fastjson.**

-keep class com.alibaba.fastjson.** { *; }

-keep class com.pinganfang.api.entity.** { *; }

-keep class com.pinganfang.palibrary.** { *; }

-keep class com.pinganfang.media.** { *; }

-keep class com.pingan.im.imlibrary.** { *; }

-keep class cn.aigestudio.datepicker.** { *; }

-keep class com.projectzero.android.library.widget.** { *; }

-keep class com.iflytek.** { *; }

#直播的混淆
-dontwarn com.luke.**
-keep class com.luke.**{*;}

-dontwarn com.aphidmobile.**

-dontwarn org.apache.**
-keep class org.apache.**{*;}

-dontwarn android.net.**
-keep class android.net.**{*;}

-dontwarn com.android.internal.http.multipart.**
-keep class com.android.internal.http.multipart.**{*;}

#微信分享的混淆
-dontwarn com.tencent.mm.**
-keep class com.tencent.mm.**{*;}

#公共库的混淆
-dontwarn com.tendcloud.tenddata**
-keep  class com.tendcloud.tenddata.**{*;}
-dontwarn com.umeng.analytics**
-keep class com.umeng.analytics.**{*;}
-keepattributes SourceFile,LineNumberTable






