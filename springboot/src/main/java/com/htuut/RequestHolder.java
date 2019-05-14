package com.htuut;

public class RequestHolder {

    private final static ThreadLocal<Long> requestHolder = new ThreadLocal<>();

    /**
     * 实际是把 id 放进一个 map 中，map 对应的 key 为当前线程 id，value 为 传入的 value
     *
     * @param value
     */
    public static void add(Long value) {
        requestHolder.set(value);
    }

    /**
     * 从 ThreadLocal 中取值
     *
     * @return
     */
    public static Long get() {
        return requestHolder.get();
    }

    /**
     * 从 ThreadLocal 中移除当前元素
     */
    public static void remove() {
        requestHolder.remove();
    }


}
