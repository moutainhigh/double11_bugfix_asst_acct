package com.yuwang.pinju.web.combo.cache;

import java.util.Collection;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Set;
import java.util.Map.Entry;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReadWriteLock;
import java.util.concurrent.locks.ReentrantReadWriteLock;

public class ConcurrentLRUMap<K, V> extends LinkedHashMap<K, V> {

	private static final long serialVersionUID = 1L;

	private final Lock readLock;
	private final Lock writeLock;
	private final int maxSize;

	public ConcurrentLRUMap(int maxSize) {
		ReadWriteLock rw = new ReentrantReadWriteLock();
		this.readLock = rw.readLock();
		this.writeLock = rw.writeLock();
		this.maxSize = maxSize;
	}

	@Override
	protected boolean removeEldestEntry(Entry<K, V> eldest) {
		return size() > maxSize;
	}

	@Override
	public int size() {
		readLock.lock();
		try {
			return super.size();
		} finally {
			readLock.unlock();
		}
	}

	@Override
	public boolean isEmpty() {
		return size() == 0;
	}

	@Override
	public boolean containsKey(Object key) {
		readLock.lock();
		try {
			return super.containsKey(key);
		} finally {
			readLock.unlock();
		}
	}

	@Override
	public boolean containsValue(Object value) {
		readLock.lock();
		try {
			return super.containsKey(value);
		} finally {
			readLock.unlock();
		}
	}

	@Override
	public V get(Object key) {
		readLock.lock();
		try {
			return super.get(key);
		} finally {
			readLock.unlock();
		}
	}

	@Override
	public V put(K key, V value) {
		writeLock.lock();
		try {
			return super.put(key, value);
		} finally {
			writeLock.unlock();
		}
	}

	@Override
	public V remove(Object key) {
		writeLock.lock();
		try {
			return super.remove(key);
		} finally {
			writeLock.unlock();
		}
	}

	@Override
	public void putAll(Map<? extends K, ? extends V> m) {
		writeLock.lock();
		try {
			super.putAll(m);
		} finally {
			writeLock.unlock();
		}
	}

	@Override
	public void clear() {
		writeLock.lock();
		try {
			super.clear();
		} finally {
			writeLock.unlock();
		}
	}

	@Override
	public Set<K> keySet() {
		throw new UnsupportedOperationException();
	}

	@Override
	public Collection<V> values() {
		throw new UnsupportedOperationException();
	}

	@Override
	public Set<Map.Entry<K, V>> entrySet() {
		throw new UnsupportedOperationException();
	}
}
