package edu.wccnet.people;

import android.app.Activity;
import android.database.Cursor;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

public class MainActivity extends Activity implements OnClickListener {
    private String TAG=MainActivity.class.toString();
    /**
     * Called when the activity is first created.
     */
    private EditText name, age, height;
    private TextView results;
    ClientDB peopleDB = new ClientDB(this);

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        int[] resIds = {R.id.insert, R.id.delete};
        setContentView(R.layout.activity_main);
        name = (EditText) findViewById(R.id.name);
        age = (EditText) findViewById(R.id.age);
        height = (EditText) findViewById(R.id.height);
        results = (TextView) findViewById(R.id.results);
        for (int i = 0; i < resIds.length; i++) {
            Button b = (Button) findViewById(resIds[i]);
            b.setOnClickListener(this);
        }
        showPeople();
    }

    void showPeople() {
        Cursor cursor = peopleDB.getPeople();
        StringBuilder sb = new StringBuilder(" People Information\n");
        // If you wanted to know what index a particular column name was in,
        // you could issue something like:
        int nameIndex = cursor.getColumnIndexOrThrow(Constants.NAME);

        while (cursor.moveToNext()) {
            String output = cursor.getString(0) + " " + cursor.getString(1) + " " + cursor.getString(2) + "\n";
            sb.append(output);
        }
        cursor.close();
        Toast.makeText( getApplicationContext(), sb, Toast.LENGTH_SHORT).show();

        results.setText(sb);
    }

    public void onClick(View v) {
        String nameStr = name.getText().toString().trim();
        if (nameStr.length() == 0) nameStr = null;
        switch (v.getId()) {
            case R.id.insert:
                try {
                    peopleDB.insert(nameStr, Integer.parseInt(age.getText().toString()), Double.parseDouble(height.getText().toString()));
                } catch (NumberFormatException e) {
                    Toast.makeText( getApplicationContext(), "Age and height must be an integer", Toast.LENGTH_SHORT).show();
                    Log.d(TAG, "Bad user input");
                }
                break;
            case R.id.delete:
                peopleDB.delete(nameStr);
                break;
        }

        showPeople();
    }
}

