package com.example.tipcalculator;

import java.text.NumberFormat;

import android.app.Activity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.text.Editable;
import android.text.TextWatcher;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.CompoundButton.OnCheckedChangeListener;
import android.widget.EditText;
import android.widget.SeekBar;
import android.widget.SeekBar.OnSeekBarChangeListener;
import android.widget.TextView;

public class MainActivity extends Activity {

	private static final NumberFormat currencyFormat = NumberFormat
			.getCurrencyInstance();
	private static final NumberFormat percentFormat = NumberFormat
			.getPercentInstance();

	private double billAmount = 0.0;
	private double customPercent = 0.18;
	private TextView amountDisplayTextView;
	private TextView percentCustomTextView;
	private TextView tip15TextView;
	private TextView total15TextView;
	private TextView tipCustomTextView;
	private TextView totalCustomTextView;
	private TextView numPeopleEditText;
	private TextView person15TextView;
	private TextView personCustomTextView;
	private CheckBox includeTaxCheckBox;
	private int numPeople = 0;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState); 
		setContentView(R.layout.activity_main);

		
		amountDisplayTextView = (TextView) findViewById(R.id.amountDisplayTextView);
		percentCustomTextView = (TextView) findViewById(R.id.percentCustomTextView);
		tip15TextView = (TextView) findViewById(R.id.tip15TextView);
		total15TextView = (TextView) findViewById(R.id.total15TextView);
		tipCustomTextView = (TextView) findViewById(R.id.tipCustomTextView);
		totalCustomTextView = (TextView) findViewById(R.id.totalCustomTextView);
		numPeopleEditText = (TextView) findViewById(R.id.numPeopleEditText);
		person15TextView = (TextView) findViewById(R.id.person15TextView);
		personCustomTextView = (TextView) findViewById(R.id.personCustomTextView);
		includeTaxCheckBox = (CheckBox) findViewById(R.id.includeTaxCheckBox);
		

		
		amountDisplayTextView.setText(currencyFormat.format(billAmount));
		updateStandard(); 
		updateCustom(); 

		
		EditText amountEditText = (EditText) findViewById(R.id.amountEditText);
		amountEditText.addTextChangedListener(amountEditTextWatcher);

		
		SeekBar customTipSeekBar = (SeekBar) findViewById(R.id.customTipSeekBar);
		customTipSeekBar.setOnSeekBarChangeListener(customSeekBarListener);
		

		
		
		EditText numPeopEditText = (EditText) findViewById(R.id.numPeopleEditText);
		numPeopEditText.addTextChangedListener(numPeopEditTextWatcher);
		
		includeTaxCheckBox.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {

			@Override
			public void onCheckedChanged(CompoundButton buttonView,
					boolean isChecked) {
				updateStandard(); 
				updateCustom(); 
				updateStandardPeople(); 
				updateCustomPeople(); 
				
			}
			
		});
	}
	
	

	private void updateStandard() {
		boolean checked = includeTaxCheckBox.isChecked();
		double fifteenPercentTip;
		double fifteenPercentTotal;
		if(checked){
			fifteenPercentTip = ((billAmount*0.13)+billAmount) * 0.15;
			fifteenPercentTotal = ((billAmount*0.13)+billAmount) + fifteenPercentTip;
			
		}
		else{
		
			fifteenPercentTip = billAmount * 0.15;
			fifteenPercentTotal = billAmount + fifteenPercentTip;
			
		}
		tip15TextView.setText(currencyFormat.format(fifteenPercentTip));
		total15TextView.setText(currencyFormat.format(fifteenPercentTotal));
	}

	private void updateCustom() {
		
		percentCustomTextView.setText(percentFormat.format(customPercent));
		boolean checked = includeTaxCheckBox.isChecked();
		double customTip;
		double customTotal;
		if(checked){
			customTip = ((billAmount*0.13)+billAmount) * customPercent;
			customTotal = ((billAmount*0.13)+billAmount) + customTip;
			
		}
		else{
			customTip = billAmount * customPercent;
			customTotal = billAmount + customTip;
			
		}
		
				
		tipCustomTextView.setText(currencyFormat.format(customTip));
		totalCustomTextView.setText(currencyFormat.format(customTotal));
	}
	
	private void updateStandardPeople(){
		boolean checked = includeTaxCheckBox.isChecked();
		double fifteenPeopleTotal;
		if(numPeople > 0){
			if(checked){
				fifteenPeopleTotal = ((((billAmount*0.13)+billAmount) * 0.15) + ((billAmount*0.13)+billAmount)) / numPeople;
			}
			else{
				fifteenPeopleTotal = ((billAmount * 0.15) + billAmount) / numPeople;
			}
		
				
			person15TextView.setText(currencyFormat.format(fifteenPeopleTotal));
		}
		
	}
	
	private void updateCustomPeople(){
		boolean checked = includeTaxCheckBox.isChecked();
		double customPeopleTotal;
		if(numPeople > 0){
			if(checked){
				customPeopleTotal = ((((billAmount*0.13)+billAmount) * customPercent) + ((billAmount*0.13)+billAmount)) / numPeople;
			}
			else{
				customPeopleTotal = ((billAmount * customPercent) + billAmount) / numPeople;
			}
		
		
			personCustomTextView.setText(currencyFormat.format(customPeopleTotal));
		}
		
	}

	private OnSeekBarChangeListener customSeekBarListener = new OnSeekBarChangeListener() {
		
		@Override
		public void onProgressChanged(SeekBar seekBar, int progress,
				boolean fromUser) {
			
			customPercent = progress / 100.0;
			updateCustom(); 
		} 

		@Override
		public void onStartTrackingTouch(SeekBar seekBar) {
		} 

		@Override
		public void onStopTrackingTouch(SeekBar seekBar) {
		} 
	};

	private TextWatcher amountEditTextWatcher = new TextWatcher() {
		
		@Override
		public void onTextChanged(CharSequence s, int start, int before,
				int count) {
			
			try {
				billAmount = Double.parseDouble(s.toString()) / 100.0;
			} 
			catch (NumberFormatException e) {
				billAmount = 0.0; 
			} 

			
			amountDisplayTextView.setText(currencyFormat.format(billAmount));
			updateStandard(); 
			updateCustom(); 
		}

		@Override
		public void afterTextChanged(Editable s) {
		} 

		@Override
		public void beforeTextChanged(CharSequence s, int start, int count,
				int after) {
		} 
	};
	
	private TextWatcher numPeopEditTextWatcher = new TextWatcher() {
		
		@Override
		public void onTextChanged(CharSequence s, int start, int before,
				int count) {
			
			try {
				numPeople = Integer.parseInt(s.toString());
			}
			catch (NumberFormatException e) {
				numPeople = 0;
			} 
			
			updateStandardPeople(); 
			updateCustomPeople(); 
		}
		@Override
		public void afterTextChanged(Editable s) {
		} 

		@Override
		public void beforeTextChanged(CharSequence s, int start, int count,
				int after) {
		} 
		
};

	


	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		
		getMenuInflater().inflate(R.menu.main, menu);
		return true;
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		
		int id = item.getItemId();
		if (id == R.id.action_settings) {
			return true;
		}
		return super.onOptionsItemSelected(item);
	}
}
