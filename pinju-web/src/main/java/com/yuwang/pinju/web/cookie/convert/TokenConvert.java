package com.yuwang.pinju.web.cookie.convert;


/**
 * <p>会话 TOKEN 转换器</p>
 *
 * @author gaobaowen
 * @since 2011-7-14 11:31:08
 */
public class TokenConvert extends BasicConvert<Token> {

	@Override
	public String encode(Token obj) {
		return obj.toCookieString();
	}

	@Override
	public Token decode(String value) {
		if(log.isDebugEnabled()) {
			log.debug("start check TOKEN_ID: " + value);
		}
		if (!Token.checkToken(value)) {
			log.warn("check token failed");
			return null;
		}
		char[] chs = value.toCharArray();
        int offset = 0;
        long mac = Token.decodeNode(chs, offset, 9, 1000000000000000L);
        if (mac < 0) {
        	log.warn("token mac is invalid, mac: [" + new String(chs, 0, 9) + "]token: [" + value + "]");
        	return null;
        }
        long tokenTime = Token.decodeNode(chs, (offset += 9), 7, 100000000000L);
        if (tokenTime < 0) {
        	log.warn("token time is invalid, tokenTime: [" + new String(chs, offset, 7) + "]token: [" + value + "]");
        	return null;
        }
        int pid = (int)Token.decodeNode(chs, (offset += 7), 3, 0);
        if (pid < 0 || pid > 226980) {
        	log.warn("token pid is invalid, pid: [" + new String(chs, offset, 3) + "], token: [" + value + "]");
        	return null;
        }
        int sequence = (int)Token.decodeNode(chs, (offset += 3), 5, 100000000L);
        if (sequence < 0) {
        	log.warn("token sequence is invalid, sequence: [" + new String(chs, offset, 5) + "], token: " + value);
			return null;
        }
        long clientIp = Token.decodeNode(chs, (offset += 5), 6, 10000000000L);
        if(clientIp < 0 || clientIp > 0xffffffffL) {
        	log.warn("token clientIp is invalid, clientIp: [" + new String(chs, offset, 6) + "], number client ip: [" + Long.toHexString(clientIp) + "], token: " + value);
			return null;
        }
        return new Token(mac, tokenTime, pid, sequence, Token.toIpAddr(clientIp));
	}
}
