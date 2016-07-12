package com.sprint22.apps.android.hydration;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;

import com.sprint22.apps.android.hydration.calculation.CalculateWaterTask;
import com.sprint22.apps.android.hydration.configuration.SPConstants;
import com.sprint22.apps.android.hydration.dao.WaterFactorsDao;
import com.sprint22.apps.android.hydration.dto.ClimateType;
import com.sprint22.apps.android.hydration.dto.GenderType;
import com.sprint22.apps.android.hydration.dto.WaterFactors;
import com.sprint22.apps.android.hydration.dto.WaterFactorsBuilder;
import com.sprint22.apps.android.hydration.utils.CloseActivityHandler;

public class HydrationFactors extends AbstractActivity {

	private Spinner mGenderSpinner;
	private EditText mWeightText;
	private EditText mExerciseText;
	private Spinner mClimateSpinner;
	private CheckBox mIllnessCheckbox;
	private CheckBox mPregnentCheckbox;
	private EditText mAlcoholText;
	private CheckBox mHealthDietCheckbox;
	private TextView mWeightMeasurement;

	private Button mSaveButton;
	private Button mCancelButton;

	private CloseActivityHandler mCloseHandler = new CloseActivityHandler() {

		@Override
		public void close() {
			finishActivity();
		}

	};;

	private SharedPreferences mSharedPreferences = null;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);

	}

	protected void initialise() {
		setContentView(R.layout.hydration_factors);

		mSharedPreferences = PreferenceManager.getDefaultSharedPreferences(this);

		mGenderSpinner = (Spinner) findViewById(R.id.gender_options);
		mWeightText = (EditText) findViewById(R.id.edit_weight);
		mExerciseText = (EditText) findViewById(R.id.edit_exercise);
		mClimateSpinner = (Spinner) findViewById(R.id.climate_options);
		mIllnessCheckbox = (CheckBox) findViewById(R.id.option_illness);
		mPregnentCheckbox = (CheckBox) findViewById(R.id.option_pregnancy);
		mAlcoholText = (EditText) findViewById(R.id.edit_alcohol);
		mHealthDietCheckbox = (CheckBox) findViewById(R.id.option_healthy_diet);
		mWeightMeasurement = (TextView) findViewById(R.id.text_weight_measurement);

		mSaveButton = (Button) findViewById(R.id.save_button);
		mCancelButton = (Button) findViewById(R.id.cancel_button);

		mSaveButton.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View arg0) {
				saveFactors();
			}

		});

		mCancelButton.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View arg0) {
				HydrationFactors.this.finish();
			}

		});

		populateValues();
	}

	private void finishActivity() {
		this.finish();
	}

	private void populateValues() {

		WaterFactors waterFactors = new WaterFactorsDao(this).retrieve();

		mWeightMeasurement.setText(mSharedPreferences.getString(
				SPConstants.KEY_WEIGHT_MEASUREMENT_TYPE, SPConstants.DEFAULT_WEIGHT_MEASUREMENT));
		mWeightText.setText("" + waterFactors.getWeight());
		mExerciseText.setText("" + waterFactors.getExerciseMinutes());

		// set the gender
		mGenderSpinner.setSelection(waterFactors.getGender().equals(GenderType.MALE) ? 0 : 1);
		mAlcoholText.setText("" + waterFactors.getAlcohol());
		mClimateSpinner.setSelection(lookupPositionInArray(waterFactors.getClimate().getName(),
				getResources().getStringArray(R.array.entries_list_climates)));
		mHealthDietCheckbox.setChecked(waterFactors.hasHealtDiet());
		mPregnentCheckbox.setChecked(waterFactors.isPregnant());
		mIllnessCheckbox.setChecked(waterFactors.isIll());
	}

	private void saveFactors() {

		WaterFactorsBuilder waterFactorsBuilder = WaterFactorsBuilder.newWaterFactorsBuilder();

		populateGender(waterFactorsBuilder);
		populateClimate(waterFactorsBuilder);
		populateWeight(waterFactorsBuilder);
		populateExercise(waterFactorsBuilder);
		populateAlcohol(waterFactorsBuilder);
		waterFactorsBuilder.withIsPregnant(mPregnentCheckbox.isChecked());
		waterFactorsBuilder.withHealthDiet(mHealthDietCheckbox.isChecked());
		waterFactorsBuilder.withIsIll(mIllnessCheckbox.isChecked());

		// save the water factors.
		new WaterFactorsDao(this).save(waterFactorsBuilder.build());

		CalculateWaterTask task = new CalculateWaterTask(this, mCloseHandler,
				CalculateWaterTask.DEFAULT_HYDRATION_FACTOR_CALCULATORS);
		task.execute(waterFactorsBuilder.build());
	}

	private void populateAlcohol(WaterFactorsBuilder waterFactorsModel) {
		String alcoholAsString = mAlcoholText.getText().toString();
		if (alcoholAsString != null && alcoholAsString.length() > 0) {
			waterFactorsModel.withAlcohol(Double.valueOf(alcoholAsString));
		}
	}

	private void populateExercise(WaterFactorsBuilder waterFactorsModel) {
		String exceriseMinsAsString = mExerciseText.getText().toString();
		if (exceriseMinsAsString != null && exceriseMinsAsString.length() > 0) {
			waterFactorsModel.withExerciseMinutes(Double.valueOf(exceriseMinsAsString));
		}
	}

	private void populateWeight(WaterFactorsBuilder waterFactorsBuilder) {
		String strWeight = mWeightText.getText().toString();
		double weight = Double.valueOf(strWeight);
		waterFactorsBuilder.withWeight(weight);
	}

	private void populateClimate(WaterFactorsBuilder waterFactorsModel) {
		String climate = (String) mClimateSpinner.getSelectedItem();
		waterFactorsModel.withClimate(ClimateType.valueOfName(climate));
	}

	private void populateGender(WaterFactorsBuilder waterFactorsBuilder) {
		String gender = (String) mGenderSpinner.getSelectedItem();
		if (SPConstants.MALE.equalsIgnoreCase(gender)) {
			waterFactorsBuilder.withGender(GenderType.MALE);
		} else if (SPConstants.FEMALE.equalsIgnoreCase(gender)) {
			waterFactorsBuilder.withGender(GenderType.FEMALE);
		} else {
			throw new IllegalArgumentException("Gender not understood (" + gender + ")");
		}
	}

	private int lookupPositionInArray(String matching, String[] array) {
		for (int i = 0; i < array.length; i++) {
			if (array[i].equalsIgnoreCase(matching)) {
				return i;
			}
		}
		return -1;
	}

	@Override
	public int getHelpLayout() {
		return R.layout.help_hydration_factors;
	}

}
