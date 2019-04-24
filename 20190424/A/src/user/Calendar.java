package user;

import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.ObjectStreamField;
import java.io.Serializable;
import java.text.DateFormatSymbols;
import java.util.Locale;
import java.util.TimeZone;

import libcore.icu.ICU;
import libcore.icu.LocaleData;

public abstract class Calendar implements Serializable, Cloneable, Comparable<Calendar> {
    private static final long serialVersionUID = -1807547505821590642L;
    
    protected boolean areFieldsSet;
   
    protected int[] fields;
    
    protected boolean[] isSet;
    
    protected boolean isTimeSet;
    
    protected long time;
    transient int lastTimeFieldSet;
    transient int lastDateFieldSet;
    private boolean lenient;
    private int firstDayOfWeek;
    private int minimalDaysInFirstWeek;
    private TimeZone zone;
    
    public static final int JANUARY = 0;
    
    public static final int FEBRUARY = 1;
    
    public static final int MARCH = 2;
   
    public static final int APRIL = 3;
    
    public static final int MAY = 4;
    
    public static final int JUNE = 5;
    
    public static final int JULY = 6;
    
    public static final int AUGUST = 7;
    
    public static final int SEPTEMBER = 8;
    
