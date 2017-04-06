## 是否处于开发模式
development=${development}

## 平台默认字符编码
default.charset=${default_charset}

#文件服务器地址
file.server=${file_server}
#图片服务器地址
image.server=${image_server}
#文件临时路径
file.temp.path=${file_temp_path}
#静态资源服务器地址
static.server=${static_server}
#读取图片服务器地址
view.image.server=${view_image_server}

#品聚服务器地址
pinju.server=${pinju_server}

############################
## SNDA api
############################

## SNDA 开放 API 应用 KEY
snda.api.app.key=${snda_api_app_key}

## SNDA 开放 API 应用安全 KEY
snda.api.app.secret.key=${snda_api_app_secret_key}

## SNDA 开放 API System Token 获取 URL
## 文档地址：http://code.snda.com/index/oauth#systemtoken
snda.api.system.token.url=${snda_api_system_token_url}

## 根据盛大通行证数据账号查询用户其他账号 SNDA 开放 API 调用的方法名
## 测试地址（需要登录）：http://open.snda.com/apitools/index/?appkey=37adf553b771507c9d723f1462ee111c&group=sdo.account.account
snda.api.query.user.info=${snda_api_query_user_info}

## SNDA 开放 API 调用 URL
snda.api.invoke.url=${snda_api_invoke_url}

## 查询用户账号的参数 key 值，各段用“,”分隔，“,”需要转义
## 1 -- PT 账号
## 3 -- 手机账号
## 4 -- 邮箱账号
## 5 -- SNDA 邮箱账号 
snda.api.query.user.info.key=${snda_api_query_user_info_key}

## 盛大开放 API 调用时 connect 和 read 超时时间（毫秒）
snda.api.invoke.timeout=${snda_api_invoke_timeout}

## 盛大通行证定制注册后通知接口进行签名的 KEY
api.snda.register.notify.sign.key=${api_snda_register_notify_sign_key}

## 用于社区的 API 的数据签名 KEY
api.sns.sign.key=${api_sns_sign_key}


############################
## 搜索
############################

## 搜索接口
search.httpclient.serverUrl =${search_httpclient_serverUrl}

## 连接超时时间
search.httpclient.connectTimeout=${search_httpclient_connectTimeout}

## 数据等待时间
search.httpclient.soTimeout=${search_httpclient_soTimeout}

## 每个主机的最大并行链接数
search.httpclient.maxConnectionsPerHost=${search_httpclient_maxConnectionsPerHost}

## 客户端总并行链接最大数
search.httpclient.maxTotalConnections=${search_httpclient_maxTotalConnections}
search.httpclient.updateServerUrl=${search_httpclient_updateServerUrl}

#########################
## 验证码相关
#########################

## 验证码缓存有效时间（秒）
captcha.memcached.timeout=${captcha_memcached_timeout}


#########################
## 登录相关配置
#########################

## 登录密码错误超过多少次时出现验证码
pinju.login.error.count.captcha=${pinju_login_error_count_captcha}

## 登录密码错误超过多少次时出现验证码的超时时间（秒）
pinju.login.error.count.timeout=${pinju_login_error_count_timeout}

#品聚保证金WebService服务地址
pinju.margin.service.url=${pinju_margin_service_url}

#品聚保证金WebService服务命名空间
pinju.margin.service.namespace=${pinju_margin_service_namespace}

###############################
## sina weibo 第三方登录相关配置
###############################

sina.weibo.clientid=${sina_weibo_clientid}
sina.weibo.client.sercret=${sina_weibo_client_sercret}
sina.weibo.redirect.uri=${sina_weibo_redirect_uri}
sina.weibo.getuid.url=${sina_weibo_getuid_url}
sina.weibo.getuser.url=${sina_weibo_getuser_url}
sina.weibo.accesstoken.url=${sina_weibo_accesstoken_url}
sina.weibo.authorize.url=${sina_weibo_authorize_url}
sina.weibo.connecttimeout=${sina_weibo_connecttimeout}
sina.weibo.readtimeout=${sina_weibo_readtimeout}

#########################
## 盛付通即时到账相关配置
#########################

#品聚在盛付通的商户号
shengpay.directpay.merchantno=${shengpay_directpay_merchantno}

#盛付通即时到账网关地址
shengpay.directpay.payurl=${shengpay_directpay_payurl}

#客户端回调地址,支付成功后,将附带回调数据跳转到此页面
shengpay.directpay.postbackurl=${shengpay_directpay_postbackurl}

#服务端通知地址,支付成功后,盛付通发支付成功到此地址
shengpay.directpay.notifyurl=${shengpay_directpay_notifyurl}

#货币类型，仅支持人民币，必须传入RMB
shengpay.directpay.currencytype=${shengpay_directpay_currencytype}

#发货通知方式tcp，http，https等。一般为http
shengpay.directpay.notifyurltype=${shengpay_directpay_notifyurltype}

#盛付通当前版本号,目前为3.0
shengpay.directpay.version=${shengpay_directpay_version}

#盛付通md5加密串
shengpay.directpay.md5key=${shengpay_directpay_md5key}

#盛付通签名方式
shengpay.directpay.signtype=${shengpay_directpay_signtype}

#盛付通消息回传成功后通知盛付通的成功消息
shengpay.directpay.successmsg=${shengpay_directpay_successmsg}

#盛付通支付渠道
shengpay.directpay.paychannel=${shengpay_directpay_paychannel}

#品聚网盛付通账号
shengpay.directpay.pinju.payaccount=${shengpay_directpay_pinju_payaccount}

#品聚网sellerId
shengpay.directpay.pinju.sellerid=${shengpay_directpay_pinju_sellerid}

#品聚网sellerNick
shengpay.directpay.pinju.sellernick=${shengpay_directpay_pinju_sellernick}

#########################
## 财付通即时到账相关配置
#########################
#财付通md5加密串
tenpay.directpay.md5key=${tenpay_directpay_md5key}

#品聚在财付通的测试商户号
tenpay.directpay.partner=${tenpay_directpay_partner}

#财付通即时到账网关地址
tenpay.directpay.payurl=${tenpay_directpay_payurl}

#财付通客户端回调地址
tenpay.directpay.returnurl=${tenpay_directpay_returnurl}

#财付通后台回调地址
tenpay.directpay.notifyurl=${tenpay_directpay_notifyurl}

#财付通货币类型
tenpay.directpay.feetype=${tenpay_directpay_feetype}

#财富通签名加密类型
tenpay.directpay.signtype=${tenpay_directpay_signtype}

#财富通使用字符集
tenpay.directpay.inputcharset=${tenpay_directpay_inputcharset}

#财富通支付渠道
tenpay.directpay.banktype=${tenpay_directpay_banktype}

#财富通交易模式 
tenpay.directpay.trademode=${tenpay_directpay_trademode}

#财富通支付结果
tenpay.directpay.tradestate=${tenpay_directpay_tradestate}

#财富通通知成功后回传消息:处理成功
tenpay.directpay.success=${tenpay_directpay_success}

#财富通通知成功后回传消息:处理不成功
tenpay.directpay.fail=${tenpay_directpay_fail}

#财付通查询订单信息  版本号Add By LiXin 2011.09.13
tenpay.searchorder.version=${tenpay_searchorder_version}

#财付通查询订单业务代码Add By LiXin 2011.09.13
tenpay.searchorder.cmdno=${tenpay_searchorder_cmdno}

#财付通查询订单后台回调地址Add By LiXin 2011.09.13
tenpay.searchorder.returnurl=${tenpay_searchorder_returnurl}

#财付通查询请求地址Add By LiXin 2011.09.13
tenpay.searchorder.searchurl=${tenpay_searchorder_searchurl}

#财富通绑定账号网关地址
tenpay.bind.gateurl=${tenpay_bind_gateurl}


#########################
## 财付通分账相关配置
#########################
#财付通分账网关地址
tenpay.split.patmentgatewayurl=${tenpay_split_patmentgatewayurl}

#担保交易品聚财付通分账账户
tenpay.pay.pinju.account=${tenpay_pay_pinju_account}

#财付通分账客户端回调地址
tenpay.split.returnurl=${tenpay_split_returnurl}

#财付通分账业务代码
tenpay.split.cmdno=${tenpay_split_cmdno}

#财付通分账业务类型
tenpay.split.bustype=${tenpay_split_bustype}

#财付通分账版本号
tenpay.split.version=${tenpay_split_version}

#编码字符，分账和退款使用此编码字符
tenpay.pay.input.charset.gbk = ${tenpay_pay_input_charset_gbk}

#########################
## 财付通平台退款相关配置
#########################
#财付通平台退款网关地址 Add By MaYuanChao @2011-09-13
tenpay.platformrefund.refundgatewayurl=${tenpay_platformrefund_refundgatewayurl}
	
#财付通平台退款返回地址 Add By MaYuanChao @2011-09-13
tenpay.platformrefund.returnurl=${tenpay_platformrefund_returnurl}
	
#财付通平台退款版本号 Add By MaYuanChao @2011-09-13
tenpay.platformrefund.version=${tenpay_platformrefund_version}
	
# 财付通平台退款业务代号 Add By MaYuanChao @2011-09-13
tenpay.platformrefund.cmdno=${tenpay_platformrefund_cmdno}

#退款Id生成规则:109＋spid+YYYYMMDD+7位流水号 
tenpay.platformrefund.codes=${tenpay_platformrefund_codes}

#########################
## 财付通分账回退相关配置
#########################
tenpay.splitrefund.refundgatewayurl=${tenpay_splitrefund_refundgatewayurl}
tenpay.splitrefund.returnurl=${tenpay_splitrefund_returnurl}
tenpay.splitrefund.version=${tenpay_splitrefund_version}
tenpay.splitrefund.cmdno=${tenpay_splitrefund_cmdno}
tenpay.splitrefund.bustype=${tenpay_splitrefund_bustype}
tenpay.splitrefund.refundid=${tenpay_splitrefund_refundid}

#########################
## 财付通单笔支付，合并支付相关配置
#########################
#品聚在财付通的测试商户号,必填
tenpay.pay.partner=${tenpay_pay_partner}

#品聚在财付通的测试md5加密KEY,必填
tenpay.pay.md5key=${tenpay_pay_md5key}

#财付通单笔支付与合并支付网关地址
tenpay.pay.payurl=${tenpay_pay_payurl}

#客户端回调地址,支付成功后,将附带回调数据跳转到此页面,必填
tenpay.pay.returnurl=${tenpay_pay_returnurl}

#后台回调地址,支付成功后,将附带回调数据跳转到此,必填
tenpay.pay.notifyurl=${tenpay_pay_notifyurl}

#版本号
tenpay.pay.ver=${tenpay_pay_ver}

#业务代码
tenpay.pay.cmdno=${tenpay_pay_cmdno}

#银行类型:财付通支付填0
tenpay.pay.banktype=${tenpay_pay_banktype}

#现金支付币种
tenpay.pay.feetype=${tenpay_pay_feetype}

#desc字符编码名称
tenpay.pay.cs=${tenpay_pay_cs}

#财富通支付结果：0—成功,1—失败
tenpay.pay.tradestate=${tenpay_pay_tradestate}

#财付通委托关系查询接口网关地址
tenpay.inquire.relation.gateurl=${tenpay_inquire_relation_gateurl}

#财付通证书目录
tenpay.certificate.path=${tenpay_certificate_path}

#财付通证书名称
tenpay.cacert.name=${tenpay_cacert_name}

#=====================
# 会员安全配置
#=====================

# 认证加密算法
auth.encrypt.algorithm=${auth_encrypt_algorithm}

# 认证加密算法加密模式（ECB/CBC/...）
auth.encrypt.mode=${auth_encrypt_mode}

# 对称加密算法填充算法
auth.encrypt.padding=${auth_encrypt_padding}

# 认证密钥
auth.encrypt.key=${auth_encrypt_key}

# MAC 签名算法
auth.sign.mac.algorithm=${auth_sign_mac_algorithm}

# MAC 签名密钥
auth.sign.mac.key=${auth_sign_mac_key}

# RSA 私钥
auth.rsa.private.key=${auth_rsa_private_key}

# 邮件链接过期时间（天）
email.link.expire.days=${email_link_expire_days}

# 系统邮件地址
email.system.address=${email_system_address}

# 系统邮件名称
email.system.from.name=${email_system_from_name}

# 短信验证码有效时间（秒）
mobile.code.expire.seconds=${mobile_code_expire_seconds}

# 短信验证码字符数
mobile.code.length=${mobile_code_length}

# 短信验证码每天的发送次数（按功能分）
mobile.code.daily.max.count=${mobile_code_daily_max_count}

# 手机号码正则表达式
mobile.number.pattern=${mobile_number_pattern}

#品聚公司IP地址
pinju.company.ips=${pinju_company_ips}

# ===============
# DSR 评论配置
# ===============

# 商品评论默认每页数量
comments.item.default.page.size=${comments_item_default_page_size}

# 商品评论页：选取多少天以内的评论
comments.item.days=${comments_item_days}

# 卖家评论默认每页数量
comments.seller.default.page.size=${comments_seller_default_page_size}

# 卖家资质评论页：选取多少天以内的评论
comments.seller.days=${comments_seller_days}

# 品牌旗舰店保证金金额
shop.business.margin=${shop_business_margin}
