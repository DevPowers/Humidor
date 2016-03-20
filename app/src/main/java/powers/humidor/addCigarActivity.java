package powers.humidor;

import android.app.Activity;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

public class addCigarActivity extends Activity {

    SQLiteDatabase cigarsDB = null;

    EditText cigarNameEditText, cigarQuantityEditText, cigarDateEditText;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.add_cigar_layout);

        cigarNameEditText = (EditText) findViewById(R.id.addCigarNameEditText);
        cigarQuantityEditText = (EditText) findViewById(R.id.addCigarQuantityEditText);
        cigarDateEditText = (EditText) findViewById(R.id.addCigarDateEditText);

    }

    public void addToHumidorButton(View view){

        Toast.makeText(addCigarActivity.this, "Button Pressed", Toast.LENGTH_SHORT).show();

        String cigarName = cigarNameEditText.getText().toString();
        String cigarQuantity = cigarQuantityEditText.getText().toString();
        String cigarDate =  cigarDateEditText.getText().toString();


        SQLiteDatabase cigarsDB = this.openOrCreateDatabase("MyHumidor", MODE_PRIVATE, null);
        cigarsDB.execSQL("INSERT INTO humidor (name, quantity) VALUES ('" +
                cigarName + "', '" + cigarQuantity + "');");

        cigarsDB.close();
        finish();

    }

    @Override
    protected void onStart() {
        super.onStart();
    }
}
