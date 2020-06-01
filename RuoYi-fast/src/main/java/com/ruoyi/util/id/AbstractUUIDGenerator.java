package com.ruoyi.util.id;

import java.net.*;

public abstract class AbstractUUIDGenerator
{
    private static final int IP;
    private static short counter;
    private static final int JVM;
    
    protected int getJVM() {
        return AbstractUUIDGenerator.JVM;
    }
    
    protected short getCount() {
        synchronized (AbstractUUIDGenerator.class) {
            if (AbstractUUIDGenerator.counter < 0) {
                AbstractUUIDGenerator.counter = 0;
            }
            final short counter = AbstractUUIDGenerator.counter;
            AbstractUUIDGenerator.counter = (short)(counter + 1);
            return counter;
        }
    }
    
    protected int getIP() {
        return AbstractUUIDGenerator.IP;
    }
    
    protected short getHiTime() {
        return (short)(System.currentTimeMillis() >>> 32);
    }
    
    protected int getLoTime() {
        return (int)System.currentTimeMillis();
    }
    
    static {
        int ipadd;
        try {
            ipadd = BytesHelper.toInt(InetAddress.getLocalHost().getAddress());
        }
        catch (Exception e) {
            ipadd = 0;
        }
        IP = ipadd;
        AbstractUUIDGenerator.counter = 0;
        JVM = (int)(System.currentTimeMillis() >>> 8);
    }
}
