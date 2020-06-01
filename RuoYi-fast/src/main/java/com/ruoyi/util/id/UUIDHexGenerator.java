package com.ruoyi.util.id;

public class UUIDHexGenerator extends AbstractUUIDGenerator
{
    private String sep;
    
    public UUIDHexGenerator() {
        this.sep = "";
    }
    
    protected String format(final int intval) {
        final String formatted = Integer.toHexString(intval);
        final StringBuffer buf = new StringBuffer("00000000");
        buf.replace(8 - formatted.length(), 8, formatted);
        return buf.toString();
    }
    
    protected String format(final short shortval) {
        final String formatted = Integer.toHexString(shortval);
        final StringBuffer buf = new StringBuffer("0000");
        buf.replace(4 - formatted.length(), 4, formatted);
        return buf.toString();
    }
    
    public String generate() {
        return new StringBuffer(36).append(this.format(this.getIP())).append(this.sep).append(this.format(this.getJVM())).append(this.sep).append(this.format(this.getHiTime())).append(this.sep).append(this.format(this.getLoTime())).append(this.sep).append(this.format(this.getCount())).toString();
    }
    
    

   
}
