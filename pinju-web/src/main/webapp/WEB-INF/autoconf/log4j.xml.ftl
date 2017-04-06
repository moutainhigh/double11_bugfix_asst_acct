<?xml version="1.0" encoding="UTF-8"?>
<!DOCTYPE log4j:configuration SYSTEM "log4j.dtd">

<log4j:configuration xmlns:log4j="http://jakarta.apache.org/log4j/">

	<appender name="CONSOLE.OUT" class="org.apache.log4j.ConsoleAppender">
	    <param name="target" value="System.out" />
	    <layout class="org.apache.log4j.PatternLayout">
			<param name="ConversionPattern" value="%d [%-5p] [%t] [%c{1}] %m%n" />
	    </layout>
	     <filter class="org.apache.log4j.varia.LevelRangeFilter">
            <param name="LevelMin" value="debug"/>
            <param name="LevelMax" value="info"/>
            <param name="AcceptOnMatch" value="false"/>
        </filter>
	</appender>
	
  <appender name="CONSOLE.ERR" class="org.apache.log4j.ConsoleAppender">
        <param name="target" value="System.err"/>
        <layout class="org.apache.log4j.PatternLayout">
            <param name="ConversionPattern" value="%d [%-5p] [%t] [%c{1}] %m%n"/>
        </layout>
        <filter class="org.apache.log4j.varia.LevelRangeFilter">
            <param name="LevelMin" value="warn"/>
            <param name="LevelMax" value="fatal"/>
            <param name="AcceptOnMatch" value="false"/>
        </filter>
    </appender>
    
  <appender name="PROJECT" class="org.apache.log4j.DailyRollingFileAppender">
        <param name="File" value="${log_root_path}/pinju.log" />
         <param name="append" value="true"/>
        <param name="encoding" value="UTF-8"/>
        <param name="threshold" value="${log_root_level}"/>
        <layout class="org.apache.log4j.PatternLayout">
            <param name="ConversionPattern" value="%d [%-5p] [%t] [%c{2}] - %m%n"/>
        </layout>
    </appender>
  
  	<!--担保交易支付日志-->
	<appender name="TENPAY-PAY" class="org.apache.log4j.DailyRollingFileAppender">
        <param name="file" value="${log_root_path}/tenpay-pay.log"/>
        <param name="append" value="true"/>
        <param name="encoding" value="UTF-8"/>
        <layout class="org.apache.log4j.PatternLayout">
            <param name="ConversionPattern" value="%d [%-5p] [%t] [%c{2}] - %m%n"/>
        </layout>
    </appender>
	<logger name="tenpay-pay" additivity="false">
        <level value="debug"/>
        <appender-ref ref="TENPAY-PAY"/>
    </logger>
    
  
	<!--类目及类目属性缓存总操作日志-->
	<appender name="CATE-CACHE-MANAGER" class="org.apache.log4j.RollingFileAppender">
        <param name="file" value="${log_root_path}/cate-cache.log"/>
        <param name="append" value="true"/>
        <param name="encoding" value="UTF-8"/>
        <layout class="org.apache.log4j.PatternLayout">
            <param name="ConversionPattern" value="%d [%-5p] [%t] [%c{2}] - %m%n"/>
        </layout>
    </appender>
	<logger name="cate-cache-manager" additivity="false">
        <level value="debug"/>
        <appender-ref ref="CATE-CACHE-MANAGER"/>
    </logger>
    
	<!--本地类目缓存日志-->
	<appender name="CACHE-CATEGORY" class="org.apache.log4j.RollingFileAppender">
        <param name="file" value="${log_root_path}/local-cache.log"/>
        <param name="append" value="true"/>
        <param name="encoding" value="UTF-8"/>
        <layout class="org.apache.log4j.PatternLayout">
            <param name="ConversionPattern" value="%d [%-5p] [%t] [%c{2}] - %m%n"/>
        </layout>
    </appender>
    
	<logger name="cache-category" additivity="false">
        <level value="debug"/>
        <appender-ref ref="CACHE-CATEGORY"/>
    </logger>
    
    <!--开放API调用日志-->
	<appender name="OPEN-API" class="org.apache.log4j.RollingFileAppender">
        <param name="file" value="${log_root_path}/open-api.log"/>
        <param name="append" value="true"/>
        <param name="encoding" value="UTF-8"/>
        <param name="maxBackupIndex" value="10" />
        <layout class="org.apache.log4j.PatternLayout">
            <param name="ConversionPattern" value="%d [%-5p] [%t] [%c{2}] - %m%n"/>
        </layout>
    </appender>
	<logger name="open-api" additivity="false">
        <level value="info"/>
        <appender-ref ref="OPEN-API"/>
    </logger>
    
    
	<logger name="java.sql">
		<level value="${log_sql_level}" />
	</logger>

	<logger name="com.yuwang">
		<level value="${log_yuwang_level}" />
	</logger>

	<root>
		<level value="${log_root_level}" />
		<appender-ref ref="PROJECT"/>
		<appender-ref ref="CONSOLE.OUT" />
		<appender-ref ref="CONSOLE.ERR" />
	
	</root>
</log4j:configuration>

