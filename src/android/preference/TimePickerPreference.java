package android.preference;
 
import android.content.Context;
import android.content.DialogInterface;
import android.content.res.TypedArray;
import android.text.format.DateFormat;
import android.util.AttributeSet;
import android.view.View;
import android.widget.TimePicker;
 
/**
 * A preference type that allows a user to choose a time
 */
public class TimePickerPreference extends DialogPreference implements
		TimePicker.OnTimeChangedListener {
 
	/**
	 * The validation expression for this preference
	 */
	private static final String VALIDATION_EXPRESSION = "[0-2]*[0-9]:[0-5]*[0-9]";
 
	/**
	 * The default value for this preference
	 */
	private String defaultValue;
 
	private int mHour = 0, mMinute = 0;

	/**
	 * @param context
	 * @param attrs
	 */
	public TimePickerPreference(Context context, AttributeSet attrs) {
		super(context, attrs);
		initialize();
	}
 
	/**
	 * @param context
	 * @param attrs
	 * @param defStyle
	 */
	public TimePickerPreference(Context context, AttributeSet attrs,
			int defStyle) {
		super(context, attrs, defStyle);
		initialize();
	}
 
	/**
	 * Initialize this preference
	 */
	private void initialize() {
		setPersistent(true);
	}
 
	/*
	 * (non-Javadoc)
	 * 
	 * @see android.preference.DialogPreference#onCreateDialogView()
	 */
	@Override
	protected View onCreateDialogView() {
 
		TimePicker tp = new TimePicker(getContext());
		tp.setOnTimeChangedListener(this);
 
		int h = getHour();
		int m = getMinute();
		if (h >= 0 && m >= 0) {
			tp.setCurrentHour(h);
			tp.setCurrentMinute(m);
		}
 
		return tp;
	}
	@Override
	public void onTimeChanged(TimePicker view, int hour, int minute) {
		mHour = hour;
		mMinute = minute;
	}
	
	


	@Override
	protected Object onGetDefaultValue(TypedArray a, int index) {
		final String value = a.getString(index);
 
		//Removed as I set the default value 
//		if (value == null || !VALIDATION_EXPRESSION.matches(value)) {
//			return null;
//		}
 
		this.defaultValue = value;
		return value;
	}
 
 
	/**
	 * Get the hour value (in 24 hour time)
	 * 
	 * @return The hour value, will be 0 to 23 (inclusive)
	 */
	private int getHour() {
		String time = getPersistedString(this.defaultValue);
		if (time == null || !time.matches(VALIDATION_EXPRESSION)) {
			return -1;
		}
 
		return Integer.valueOf(time.split(":")[0]);
	}
	
	@Override
	public void onDialogClosed(boolean positiveResult) {
		String result = mHour + ":" + mMinute;
		if( positiveResult ) {
			if( isPersistent() ) {
				persistString(result);
			}
			callChangeListener(result);
		}
	}
	
	public String getText(){
		int tmpHour = getHour();
		int tmpMinute = getMinute();
		
		if (DateFormat.is24HourFormat(getContext())){
			return tmpHour + ":" + (tmpMinute<10?("0" + tmpMinute):tmpMinute);
		}
		else {
			return tmpHour%12 + ":" + (tmpMinute<10?("0" + tmpMinute):tmpMinute) + (tmpHour>12?" PM":" AM");
		}
	
	}
	
	@Override
	public void onClick(DialogInterface dialog, int which) {
		super.getDialog().getCurrentFocus().clearFocus();
		super.onClick(dialog, which);
	}
 
	/**
	 * Get the minute value
	 * 
	 * @return the minute value, will be 0 to 59 (inclusive)
	 */
	private int getMinute() {
		String time = getPersistedString(this.defaultValue);
		if (time == null || !time.matches(VALIDATION_EXPRESSION)) {
			return -1;
		}
 
		return Integer.valueOf(time.split(":")[1]);
	}

}