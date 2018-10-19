package com.maxrocky.gundam.common.util;

/**
 * Created by hhs on 2015/9/3.
 * id工具类
 */
public class AppConfig {

    public static String AppID = "";//【必】微信分配的公众账号ID（企业号corpid即为此appId）
    public static String AppID_APP = "";//wx6beeb23e47112fcf
    public static String AppSecrt_APP= "";//cd4d922bc02ad7784f17379ef5b20201

    public static String AppSecret = "";//

    public static String notifyUrl = "";//【必】接收微信支付异步通知回调地址
    public static String notifyUrl_APP = "";//http://api.uhuojia.com/payment/receiveWeixinPayNotify

    public static String mchId = "";//【必】微信支付分配的商户号
    public static String mchId_APP = "";//1283920101

    public static String Key = "";//【必】商户Key
    public static String Key_APP = "";//Yourhomeplusyouhuojia20151106104

    public static String spbillCreateIp = "127.0.0.1";//【必】 设备Ip

    public static String tradeType = "JSAPI";//【必】交易类型
    public static String tradeType_APP = "APP";
    public static String CERT_LOCAL_PATH = "";//HTTPS证书的本地路径

    //微信获取signature 所需参数    getWeChatInfo
    public static String noncestr = "Wm3WZYTPz0wzccnW";//随机字符串，不长于32位。推荐随机数生成算法

    /*阿里云ACCESS_ID*/
    public static String AccessId = "";

    /*阿里云ACCESS_KEY*/
    public static String AccessKey = "";

    /*阿里云OSS_ENDPOINT*/
    public static String OSSEndpoint = "";

    /*阿里云BUCKET_NAME  OSS*/
    public static String BucketName = "";

    /*阿里云图片访问地址*/
    public static String OSSAccessPath = "";

    /* 下载微信图片请求路径 */
    public static String WeChatDownload_ImageRequestPath = "";

    /* 微信下载图片保存本地路径 */
    public static String WeChatDownload_ImageDiskPath = "";

    /* 微信物业报修OSS上传地址 */
    public static String WeChatUpload_OSSRepair = "";

    /* 用户头像OSS上传地址 */
    public static String UserLogo_Upload_OSS = "";

    /* 用户默认头像地址 */
    public static String UserDefaultLogo = "";

    /*后台广告图片保存本地路径*/
    public static String Admin_AdImage_Save_DiskPath = "";

    /*后台广告图片上传OSS路径*/
    public static String Admin_Upload_OSS_Ad = "";

    /* 前台BBS图片上传OSS路径 */
    public static String WeChat_Upload_BBS = "";

    /* 后台BBS图片上传OSS路径 */
    public static String Admin_Upload_BBS = "";

    //二维码
    public static String QRPATH = "";//生成的二维码的路径

    public static  String QROSSPath ="";

    //给前端反馈的cookie domain
    public static String CookieDomain = "";

    /* 短信账号 */
    public static String SMS_Reg = "";

    /* 短信密码 */
    public static String SMS_Password = "";

    /* 短信子通道 */
    public static String SMS_SourceAdd = "";

    /* 短信地址 */
    public static String SMS_URL = "";

    /* 短信有效时间（以秒计算） */
    public static String SMS_ValidTime = "";

    /* 短信内容头 */
    public static String SMS_Content_Head = "";

    /* 短信内容尾 */
    public static String SMS_Content_End = "";

    /* 短信发送间隔 */
    public static String SMS_Interval = "";

    /* 短信启用 */
    public static String SMS_CheckFlag = "";

    /* 产品图片oss path */
    public static String ProductDetailPath;
    /* 产品图片local path */
    public static String ProductDetailLocalPath;

    public static String ORDER_VALID_TIME;//未确认订单的失效（单位：分钟）

    /* 新建报修详情内容 */
    public static String New_Repair_Detail_Content = "";

    /* 广告商品跳转连接地址 */
    public static String AdPositionId_JumpUrl_For_Goods = "";

    /* 广告促销跳转链接地址 */
    public static String AdPositionId_JumpUrl_For_Promo = "";

    /* 广告商户跳转链接地址 */
    public static String AdPositionId_JumpUrl_For_Seller = "";

}
