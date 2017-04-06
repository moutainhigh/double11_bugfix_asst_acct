package com.yuwang.pinju.web.interceptor;

import java.lang.reflect.Field;

import org.apache.commons.lang.StringUtils;

import com.opensymphony.xwork2.ActionInvocation;
import com.opensymphony.xwork2.interceptor.AbstractInterceptor;
import com.opensymphony.xwork2.util.CompoundRoot;
import com.opensymphony.xwork2.util.ValueStack;
import com.yuwang.pinju.common.ReflectionUtils;
import com.yuwang.pinju.web.annotatioin.ChainTransParam;

/**  
 * @Project: pinju-web
 * @Description: Result type 为chain时 可通过注解的方式实现参数传递 此参数为前置Action的成员变量、并提供getter方法此参数并不要求一定要在值栈中
 * @author 石兴 shixing@zba.com
 * @date 2011-8-12 下午06:54:19
 * @update 2011-8-12 下午06:54:19
 * @version V1.0  
 */
public class ChainParameterInterceptor extends AbstractInterceptor {

    private static final long serialVersionUID = -8279316685527646358L;

    @Override
    public String intercept(ActionInvocation invocation) throws Exception {
        ValueStack stack = invocation.getStack();
        CompoundRoot root = stack.getRoot();

        // 值栈不为null 且已经有前置Action
        // 栈最顶层(index = 0)为当前Action、紧接着(index = 1) 为前置Action
        if (root == null || root.size() <= 2) {
            return invocation.invoke();
        }

        // 当前Action对象
        Object target = invocation.getAction();

        Field[] fields = target.getClass().getDeclaredFields();

        // 遍历此Action对象的属性 是否有RecieveData注解
        for (Field field : fields) {
            if (field.isAnnotationPresent(ChainTransParam.class)) {
                ChainTransParam rData = field.getAnnotation(ChainTransParam.class);
                // 取得源数据字段名
                String fromName = rData.fieldName();
                fromName = StringUtils.isEmpty(fromName) ? field.getName() : fromName;

                // 取得最近的前置Action
                Object srcAction = root.get(1);

                // 取得对应字段的值
                Object value = ReflectionUtils.getFieldValue(srcAction, srcAction.getClass(), field.getName());
                // 设定值
                ReflectionUtils.setFieldValue(target, field.getName(), field.getType(), value);
            }
        }

        return invocation.invoke();
    }

    @SuppressWarnings("unused")
    private Object findFieldValue(CompoundRoot root, Field field) {
        Object value = null;

        int size = root.size();

        // 按顺序遍历前置Action
        for (int index = 1; index < size; index++) {
            Object srcAction = root.get(index);

            Object tmp = ReflectionUtils.getFieldValue(srcAction, srcAction.getClass(), field.getName());
            // 取得对应字段的值 则返回
            // 问题：如果前置Action中该字段本身就为null 则无法处理
            if (tmp != null) {
                break;
            }
        }

        return value;
    }
}