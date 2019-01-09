package rapid.com.rdframework.retrofit;

import java.util.Map;

import io.reactivex.Observable;
import retrofit2.http.POST;
import retrofit2.http.PartMap;
import retrofit2.http.Query;
import retrofit2.http.QueryMap;

/**
 * @author: FYL
 * @time: 2018/10/18
 * @email:347933430@qq.com
 * @describe: rapid.com.net
 */
public interface APIFunction {
    //?appName=画世界&start=10&num=10&actionState=0&userID=100
    @POST("getHomeData")
    Observable<SearchData> getData(@Query("appName")String name,
                                @Query("start")String start,
                                @Query("num")String num,
                                @Query("actionState")String actionState,
                                @Query("userID")String userID);
    @POST("getHomeData")
    Observable<SearchData> getData(@QueryMap Map<String,String> name);



}
