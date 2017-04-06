package com.yuwang.pinju.core.util.filter;

import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;

/**
 * 敏感词存放节点类
 * 
 * @author zwm
 * 
 */
public class Node implements Serializable {

    private Map<String, Node> children = new HashMap<String, Node>(0);
    private boolean isEnd = false;

    public Node() {
	super();
    }

    public Node(Map<String, Node> children) {
	super();
	this.children = children;
    }

    public Node addChar(char c) {
	String cStr = String.valueOf(c);
	Node node = (Node) this.children.get(cStr);
	if (node == null) {
	    node = new Node();
	    this.children.put(cStr, node);
	}
	return node;
    }

    @Deprecated
    public Node attachChild(Node node) {
	// TODO 调试...
	Map<String, Node> resultMap = new HashMap<String, Node>();
	resultMap.putAll(this.children);
	resultMap.putAll(node.children);
	return new Node(resultMap);
    }
    
    public Node findChar(char c) {
	String cStr = String.valueOf(c);
	return (Node) this.children.get(cStr);
    }

    public boolean isEnd() {
	return this.isEnd;
    }

    public void setEnd(boolean isEnd) {
	this.isEnd = isEnd;
    }
}
