##spring配置中心,读入spring中的配置
##
##add by @liuboen
##
##使用注意事项 :value 以下划线 "_" 替换在auto-config.xml中设置的key的 "."
##
##系统会自动将值匹配进工程中,可直接在spring中使用

test.do.ths=${test_do_ths}

# =============
# 消息队列配置
# =============

# 订单评价 MQ 名称
rate.order.comment.mq.name=${rate_order_comment_mq_name}

# 订单评价 MQ 服务器 URL
rate.order.comment.mq.url=${rate_order_comment_mq_url}

# 私信MQ 名称
pmessage.order.comment.mq.name=${pmessage_order_comment_mq_name}

# 私信 MQ 服务器 URL
pmessage.order.comment.mq.url=${pmessage_order_comment_mq_url}

#缓存相关配置
category.cache.storedir=${category_cache_storedir}
memcache.item.cache.indexkey=${memcache_item_cache_indexkey}
##memcache.category.property.list.cache.indexkey=${memcache_category_property_list_cache_indexkey}
##memcache.category.property.cache.indexkey=${memcache_category_property_cache_indexkey}
##memcache.category.basevalue.cache.indexkey=${memcache_category_basevalue_cache_indexkey}
##memcache.category.spu.cache.indexkey=${memcache_category_spu_cache_indexkey}
memcache.ip=${memcache_ip}
memcache.fall.ip=${memcache_fall_ip}
memcache.port=${memcache_port}

# 店铺资质及 DSR 缓存
memcached.indexkey.pinju.qualitity=${memcached_indexkey_pinju_qualitity}
memcached.ip.pinju.qualitity=${memcached_ip_pinju_qualitity}
memcached.port.pinju.qualitity=${memcached_port_pinju_qualitity}
memcached.fall.pinju.qualitity=${memcached_fall_pinju_qualitity}
memcached.expires.pinju.qualitity=${memcached_expires_pinju_qualitity}

# DSR 商品评论缓存
memcached.indexkey.pinju.dsr.comments=${memcached_indexkey_pinju_dsr_comments}
memcached.ip.pinju.dsr.comments=${memcached_ip_pinju_dsr_comments}
memcached.port.pinju.dsr.comments=${memcached_port_pinju_dsr_comments}
memcached.fall.pinju.dsr.comments=${memcached_fall_pinju_dsr_comments}
memcached.expires.pinju.dsr.comments=${memcached_expires_pinju_dsr_comments}

##商品购买数量缓存配置
memcached.indexkey.pinju.itembuynum=${memcached_indexkey_pinju_itembuynum}
## 商品购买数量 memcached ip或域名
memcached.ip.pinju.itembuynum=${memcached_ip_pinju_itembuynum}
## 商品购买数量 memcached 端口号
memcached.port.pinju.itembuynum=${memcached_port_pinju_itembuynum}
## 商品购买数量 memcached 备用服务器
memcached.fall.pinju.itembuynum=${memcached_fall_pinju_itembuynum}

## 验证码值
memcached.indexkey.pinju.captcha=${memcached_indexkey_pinju_captcha}
## 验证码 memcached ip或域名
memcached.ip.pinju.captcha=${memcached_ip_pinju_captcha}
## 验证码 memcached 端口号
memcached.port.pinju.captcha=${memcached_port_pinju_captcha}
## 验证码 memcached 备用服务器
memcached.fall.pinju.captcha=${memcached_fall_pinju_captcha}

## 登录错误次数记录
memcached.indexkey.pinju.login=${memcached_indexkey_pinju_login}
## login memcached ip或域名
memcached.ip.pinju.login=${memcached_ip_pinju_login}
## login memcached 端口号
memcached.port.pinju.login=${memcached_port_pinju_login}
## 登录错误次数 memcached 备用服务器
memcached.fall.pinju.login=${memcached_fall_pinju_login}

##店铺缓存配置
memcached.indexkey.pinju.shop=${memcached_indexkey_pinju_shop}
## 店铺 memcached ip或域名
memcached.ip.pinju.shop=${memcached_ip_pinju_shop}
## 店铺 memcached 端口号
memcached.port.pinju.shop=${memcached_port_pinju_shop}
## 店铺 memcached 备用服务器
memcached.fall.pinju.shop=${memcached_fall_pinju_shop}

##ip库缓存配置
memcached.indexkey.pinju.ip=${memcached_indexkey_pinju_ip}
## ip库 memcached ip或域名
memcached.ip.pinju.ip=${memcached_ip_pinju_ip}
## ip库 memcached 端口号
memcached.port.pinju.ip=${memcached_port_pinju_ip}
## ip库 memcached 备用服务器
memcached.fall.pinju.ip=${memcached_fall_pinju_ip}

##物流运费缓存配置
memcached.indexkey.pinju.logistics=${memcached_indexkey_pinju_logistics}
## 物流运费 memcached ip或域名
memcached.ip.pinju.logistics=${memcached_ip_pinju_logistics}
## 物流运费 memcached 端口号
memcached.port.pinju.logistics=${memcached_port_pinju_logistics}
## 物流运费 memcached 备用服务器
memcached.fall.pinju.logistics=${memcached_fall_pinju_logistics}

# 手机号码发送频率限制
memcached.indexkey.pinju.mobile.frequency=${memcached_indexkey_pinju_mobile_frequency}
memcached.ip.pinju.mobile.frequency=${memcached_ip_pinju_mobile_frequency}
memcached.port.pinju.mobile.frequency=${memcached_port_pinju_mobile_frequency}
memcached.fall.pinju.mobile.frequency=${memcached_fall_pinju_mobile_frequency}
memcached.expires.pinju.mobile.frequency=${memcached_expires_pinju_mobile_frequency}
    
# 手机号码日发送限制
memcached.indexkey.pinju.mobile.daily=${memcached_indexkey_pinju_mobile_daily}
memcached.ip.pinju.mobile.daily=${memcached_ip_pinju_mobile_daily}
memcached.port.pinju.mobile.daily=${memcached_port_pinju_mobile_daily}
memcached.fall.pinju.mobile.daily=${memcached_fall_pinju_mobile_daily}
memcached.expires.pinju.mobile.daily=${memcached_expires_pinju_mobile_daily}

# 登录页面图片数据
memcached.indexkey.pinju.login.page=${memcached_indexkey_pinju_login_page}
memcached.ip.pinju.login.page=${memcached_ip_pinju_login_page}
memcached.port.pinju.login.page=${memcached_port_pinju_login_page}
memcached.fall.pinju.login.page=${memcached_fall_pinju_login_page}
memcached.expires.pinju.login.page=${memcached_expires_pinju_login_page}

# 用于数据传输密钥缓存
memcached.indexkey.pinju.trans.key=${memcached_indexkey_pinju_trans_key}
memcached.ip.pinju.trans.key=${memcached_ip_pinju_trans_key}
memcached.port.pinju.trans.key=${memcached_port_pinju_trans_key}
memcached.fall.pinju.trans.key=${memcached_fall_pinju_trans_key}
memcached.expires.pinju.trans.key=${memcached_expires_pinju_trans_key}

# 子账号权限数据
memcached.indexkey.pinju.asst.perm=${memcached_indexkey_pinju_asst_perm}
memcached.ip.pinju.asst.perm=${memcached_ip_pinju_asst_perm}
memcached.port.pinju.asst.perm=${memcached_port_pinju_asst_perm}
memcached.fall.pinju.asst.perm=${memcached_fall_pinju_asst_perm}
memcached.expires.pinju.asst.perm=${memcached_expires_pinju_asst_perm}

#数据库相关配置
jdbc.oracle.driver = ${jdbc_oracle_driver}
pinju.oracle.jdbc.url= ${pinju_oracle_jdbc_url}
pinju.oracle.jdbc.port= ${pinju_oracle_jdbc_port}
pinju.oracle.jdbc.username= ${pinju_oracle_jdbc_username}
pinju.oracle.jdbc.password= ${pinju_oracle_jdbc_password}

#dbcp
pinju.dbcp.oracle.initialSize=${pinju_dbcp_oracle_initialSize}
pinju.dbcp.oracle.maxIdle=${pinju_dbcp_oracle_maxIdle}
pinju.dbcp.oracle.minIdle=${pinju_dbcp_oracle_minIdle}
pinju.dbcp.oracle.maxActive=${pinju_dbcp_oracle_maxActive}
pinju.dbcp.oracle.removeAbandoned = ${pinju_dbcp_oracle_removeAbandoned}
pinju.dbcp.oracle.removeAbandonedTimeout = ${pinju_dbcp_oracle_removeAbandonedTimeout}
pinju.dbcp.oracle.maxWait=${pinju_dbcp_oracle_maxWait}

##文件上传相关
file.server=${file_server}
image.server=${image_server}
file.temp.path=${file_temp_path}

#Logistics WebService
pinju.logisticsWebServiceURL=${pinju_logisticsWebServiceURL}
pinju.logisticsWebServiceKEY=${pinju_logisticsWebServiceKEY}

#快递100
pinju.hundred.logisticsWebServiceURL=${pinju_hundred_logisticsWebServiceURL}
pinju.hundred.logisticsWebServiceKEY=${pinju_hundred_logisticsWebServiceKEY}

#search 暂时没有用到,防止报错用的

search.httpclient.url =${search_httpclient_url}
search.httpclient.connectTimeout=${search_httpclient_connectTimeout}
search.httpclient.soTimeout=${search_httpclient_connectTimeout}

#mysql read[c3p0]
read.c3p0.jdbcUrl=${read_c3p0_jdbcUrl}
read.c3p0.driverClass=${c3p0_driverClass}
read.c3p0.user=${c3p0_user}
read.c3p0.password=${c3p0_password}
read.c3p0.initialPoolSize=${c3p0_initialPoolSize}
read.c3p0.minPoolSize=${c3p0_minPoolSize}
read.c3p0.maxPoolSize=${c3p0_maxPoolSize}
read.c3p0.maxIdleTime=${c3p0_maxIdleTime}
read.c3p0.acquireRetryAttempts=${c3p0_acquireRetryAttempts}
read.c3p0.acquireRetryDelay=${c3p0_acquireRetryDelay}
read.c3p0.checkoutTimeout=${c3p0_checkoutTimeout}
read.c3p0.maxStatements=${c3p0_maxStatements}
read.c3p0.numHelperThreads=${c3p0_numHelperThreads}
read.c3p0.testConnectionOnCheckin=${c3p0_testConnectionOnCheckin}
read.c3p0.preferredTestQuery=${c3p0_preferredTestQuery}

#bonecp
bonecp.idleMaxAge=${bonecp_idleMaxAge}
bonecp.partitionCount=${bonecp_partitionCount}
bonecp.acquireIncrement=${bonecp_acquireIncrement}
bonecp.statementCacheSize=${bonecp_statementCacheSize}
bonecp.releaseHelperThreads=${bonecp_releaseHelperThreads}
bonecp.maxConnectionsPerPartition=${bonecp_maxConnectionsPerPartition}
bonecp.minConnectionsPerPartition=${bonecp_minConnectionsPerPartition}
bonecp.idleConnectionTestPeriod=${bonecp_idleConnectionTestPeriod}

bonecp.mysql.write.jdbcUrl=${bonecp_mysql_write_jdbcUrl}

bonecp.mysql.driverClass=${bonecp_mysql_driverClass}
bonecp.mysql.read.jdbcUrl=${bonecp_mysql_read_jdbcUrl}
bonecp.mysql.user=${bonecp_mysql_user}
bonecp.mysql.password=${bonecp_mysql_password}

bonecp.oracle.write.jdbcUrl=${bonecp_oracle_write_jdbcUrl}

bonecp.oracle.driverClass=${bonecp_oracle_driverClass}
bonecp.oracle.read.jdbcUrl=${bonecp_oracle_read_jdbcUrl}
bonecp.oracle.user=${bonecp_oracle_user}
bonecp.oracle.password=${bonecp_oracle_password}
