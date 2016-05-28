package com.lovejjfg.blogdemo.base;





import com.lovejjfg.blogdemo.model.bean.BlogBean;

import retrofit2.http.Body;
import retrofit2.http.POST;
import retrofit2.http.Path;
import rx.Observable;

/**
 * Created by 张俊 on 2016/3/15.
 */
public interface BombApiService {



//    @Multipart
//    @POST("/batch")
//    void updateFile(@Part("photo") RequestBody photo, @Part("description") RequestBody description);

    @POST("files/{fileName}")
    Observable<BlogBean> OupdateFiles(@Path("fileName") String fileName, @Body byte[] bytes);

}
