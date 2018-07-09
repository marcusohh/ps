package sg.edu.rp.c346.ps;

import android.content.SharedPreferences;
import android.preference.PreferenceManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import org.w3c.dom.Text;

import java.util.Calendar;

public class MainActivity extends AppCompatActivity {

    EditText etweight;
    EditText etheight;
    TextView tvdate;
    TextView tvbmi;
    Button calories;
    Button reset;
    TextView tvresult;
    Calendar now = Calendar.getInstance();  //Create a Calendar object with current date and time
    final String datetime = now.get(Calendar.DAY_OF_MONTH) + "/" +
            (now.get(Calendar.MONTH)+1) + "/" +
            now.get(Calendar.YEAR) + " " +
            now.get(Calendar.HOUR_OF_DAY) + ":" +
            now.get(Calendar.MINUTE);


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        etweight = findViewById(R.id.editTextWeight);
        etheight = findViewById(R.id.editTextHeight);
        tvdate = findViewById(R.id.textViewDate);
        tvbmi = findViewById(R.id.textViewBMI);
        calories = findViewById(R.id.buttonCal);
        reset = findViewById(R.id.buttonRes);
        tvresult = findViewById(R.id.textViewresult);

        calories.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                float weight = Float.parseFloat(etweight.getText().toString());
                float height = Float.parseFloat(etheight.getText().toString());

                float BMI = (weight / (height * height));
                String msg = "";
                if(BMI <= 18.5){
                    msg = "You are underweight";
                }
                if(BMI >=18.5 && BMI < 25){
                    msg = "Your BMI is normal";
                }
                if(BMI >= 25 && BMI < 30){
                    msg = "Your BMI is normal";
                }
                if(BMI >= 30){
                    msg = "You are obese";
                }

                tvdate.setText("Last Calculated date is "+ datetime);
                tvbmi.setText("Last Calculated BMI is " + BMI);
                tvresult.setText(msg);
            }
        });
        final SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(this);
        reset.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                etweight.setText("");
                etheight.setText("");
                tvdate.setText("Last Calculated date is ");
                tvbmi.setText("Last Calculated BMI is ");
                tvresult.setText("");
                SharedPreferences.Editor prefEdit = prefs.edit();

                prefEdit.clear().commit();
            }
        });
    }

    @Override
    protected void onPause() {

        super.onPause();
        if (!etheight.getText().toString().isEmpty() && !etweight.getText().toString().isEmpty()) {

            SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(this);

            SharedPreferences.Editor prefEdit = prefs.edit();

            prefEdit.putString("bmi", tvbmi.getText().toString());

            prefEdit.putString("date", tvdate.getText().toString());

            prefEdit.putString("result", tvresult.getText().toString());

            prefEdit.commit();
        }
    }

    @Override
    protected void onResume() {
        super.onResume();
        SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(this);

        String bmi = prefs.getString("bmi","Last Calculated BMI is ");
        String date = prefs.getString("date", "Last Calculated date is ");
        String result = prefs.getString("result","");

        tvdate.setText(date);
        tvbmi.setText(bmi);
        tvresult.setText(result);

    }
}
