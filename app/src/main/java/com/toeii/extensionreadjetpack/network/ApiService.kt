package com.toeii.extensionreadjetpack.network

import com.toeii.extensionreadjetpack.entity.*
import retrofit2.http.*

interface ApiService {

    // 首页推荐
    @GET("/api/xiandu/data/id/vice/count/20/page/{page}")
    suspend fun getHomeRecommendList(@Path("page") page: String): XianDuViceEntity

    // 首页banner
    @Headers("urls:baseUrlReport")
    @GET("/api/v2/feed?num=2&udid=26868b32e808498db32fd51fb422d00175e179df&vc=83")
    suspend fun getHomeRecommendBannerList(): HomeRecommendBannerEntity

//@Deprecated
//======================================================================================================================

    // 首页日报
    @GET("/api/v5/index/tab/feed?udid=55b862f0d6714f609bd6e45947f8789f0ff90f48&date={time}&num={page}")
    suspend fun getHomeDailyList(@Query("time") time: String,@Query("page") page: Int): HomeDailyEntity

    // 社区分类
    @GET("/api/v6/community/tab/rec?udid=55b862f0d6714f609bd6e45947f8789f0ff90f48&vc=461&deviceModel=PBAT00")
    suspend fun getCommunityItemList(): CommunityItemEntity

    // 社区分类下内容
    @GET("/api/v6/tag/dynamics?id={id}&start={start}&num={page}")
    suspend fun getCommunityContentList(@Query("id") id: String,@Query("start") start: Int,@Query("page") page: Int): CommunityContentEntity


//    //每日精选  一个banner  然后就是时间顺序的数据（不输入date就是banner，输入就是按照日期）
//    "http://baobab.wandoujia.com/api/v2/feed?num=2&udid=26868b32e808498db32fd51fb422d00175e179df&vc=83";
//    //分类
//    http://baobab.kaiyanapp.com/api/v4/categories/
//    //热门排行
//    "http://baobab.wandoujia.com/api/v3/ranklist?num=10&strategy=%s&udid=26868b32e808498db32fd51fb422d00175e179df&vc=83";
//    val STRATEGY = arrayOf("weekly", "monthly", "historical")//周排行 日排行 月排行
//    http://baobab.kaiyanapp.com/api/v4/categories/detail/tab?id=36//分类详情
//    http://baobab.kaiyanapp.com/api/v4/categories/videoList?id=36 //分类里的全部视频
//    http://baobab.kaiyanapp.com/api/v4/categories/detail/index?id=36 //分类里的最近更新之类的（该项目中未使用）
//    http://baobab.kaiyanapp.com/api/v4/categories/detail/pgcs?id=36//分类的作者
//    //搜索关键字
//    http://baobab.kaiyanapp.com/api/v1/search?num=10&query=xxx&start=10
//    //首页请求
//    //首页  、 排行  分类
//    作者信息
//    http://baobab.kaiyanapp.com/api/v4/pgcs/detail/tab?id=571
//    详情页从上页拿到数据，然后显示一部分，然后相关这块又显示一部分
//    有作者显示作者 无作者不显示
//    详情页的相关
//    http://baobab.kaiyanapp.com/api/v4/video/related?id=45897
//    详情页：
//    第一个item 上页数据 有
//    第二个item 上页 可无
//    第三个item 请求相关接口


}