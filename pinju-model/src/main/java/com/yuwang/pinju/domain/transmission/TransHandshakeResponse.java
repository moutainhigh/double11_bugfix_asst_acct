package com.yuwang.pinju.domain.transmission;

import org.apache.commons.codec.digest.DigestUtils;

import com.yuwang.pinju.common.RandomUUID;

/**
 * <p>安全数据传输握手响应</p>
 *
 * @author gaobaowen
 * @since 2011-11-28 下午02:11:33
 */
public class TransHandshakeResponse {

	/**
	 * 握手中客户端生成的密钥，用于之后数据传输解密用的密钥
	 */
	private String hexKey;

	/**
	 * 数据传输消息编号
	 */
	private String transId;

	/**
	 * 握手密钥与数据传输编号产生的 HASH 值
	 */
	private String hash;

	public TransHandshakeResponse(String hexKey) {
		this.hexKey = hexKey;
		this.transId = RandomUUID.get();
		this.hash = DigestUtils.shaHex(hexKey + transId);
	}

	public String getHexKey() {
		return hexKey;
	}

	public String getTransId() {
		return transId;
	}

	public String getHash() {
		return hash;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((hash == null) ? 0 : hash.hashCode());
		result = prime * result + ((hexKey == null) ? 0 : hexKey.hashCode());
		result = prime * result + ((transId == null) ? 0 : transId.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		TransHandshakeResponse other = (TransHandshakeResponse) obj;
		if (hash == null) {
			if (other.hash != null)
				return false;
		} else if (!hash.equals(other.hash))
			return false;
		if (hexKey == null) {
			if (other.hexKey != null)
				return false;
		} else if (!hexKey.equals(other.hexKey))
			return false;
		if (transId == null) {
			if (other.transId != null)
				return false;
		} else if (!transId.equals(other.transId))
			return false;
		return true;
	}

	@Override
	public String toString() {
		return "TransHandshakeResponse [hexKey=" + hexKey + ", transId=" + transId + ", hash=" + hash + "]";
	}
}
