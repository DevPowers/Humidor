package powers.humidor;

import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import java.io.File;

public class MainActivity extends AppCompatActivity {

    SQLiteDatabase cigarsDB = null;

    Button addCigarsButton, resetHumidorButton;

    EditText cigarListEditText;


    @Override  //----Initializing all editText boxs and buttons----
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        addCigarsButton = (Button) findViewById(R.id.addCigarsButton);
        resetHumidorButton = (Button) findViewById(R.id.resetHumidorButton);

        cigarListEditText = (EditText) findViewById(R.id.cigarListEditText);


    }

    @Override
    public void onStart() {
        super.onStart();

        createDataBase(); //creates the dataBase
        getCigars();  //lists the cigars in cigarList editTextBox

    }

    //clears Humidor Table of all cigar objects
    public void clearHumidor (View view){

        cigarsDB.execSQL("DELETE FROM humidor;");
        cigarListEditText.setText("");
        Toast.makeText(this, "Humidor cleared", Toast.LENGTH_SHORT).show();
    }

    //Creates an 'intent' to start the addCigarActivity
    public void openAddCigarsActivity (View view){

        Intent openAddCigarActivity = new Intent(this, addCigarActivity.class);
        startActivity(openAddCigarActivity);
    }

    //Creates 'humidor' database and table.
    public void createDataBase (){

        try {
            //Humidor is db name,mode_private means only this app can use db info, use null unless you need db error handler
            cigarsDB = this.openOrCreateDatabase("MyHumidor", MODE_PRIVATE, null);

            cigarsDB.execSQL("CREATE TABLE IF NOT EXISTS humidor "
                    + "(id INTEGER primary key,"
                    + "name VARCHAR,"
                    + "quantity VARCHAR);");

            File database = getApplicationContext().getDatabasePath("MyHumidor");

            if (!database.exists()) {
                Toast.makeText(this, "HumidorDB Creation Failed :[", Toast.LENGTH_LONG).show();
            }
        } catch(Exception e) {
                 Log.e("Humidor Error", "Error creating HumidorDB");
        }
    }

    //Gets all the cigars in 'humidor' table and puts the data in cigarList string
    //Sets the cigarList editTextView to cigarList string
    public void getCigars (){

        //cursor provides read and write access to db for this query
        Cursor cursor = cigarsDB.rawQuery("SELECT * FROM humidor", null);

        int idColumn = cursor.getColumnIndex("id");
        int nameColumn = cursor.getColumnIndex("name");
        int quantityColumn = cursor.getColumnIndex("quantity");

        cursor.moveToFirst();

        String cigarList = "";

        if(cursor != null && (cursor.getCount() > 0)) {
            do{
                String id = cursor.getString(idColumn);
                String name = cursor.getString(nameColumn);
                String quantity = cursor.getString(quantityColumn);

                cigarList = cigarList + id + " " + name + " " + quantity + "\n";
            }while(cursor.moveToNext());

            cigarListEditText.setText(cigarList);
        } else {
            Toast.makeText(this, "No cigars in Humidor",
                    Toast.LENGTH_LONG).show();
            cigarListEditText.setText("");
        }

    }


    @Override    //closes the dataBase connection
    protected void onDestroy() {
        cigarsDB.close();
        super.onDestroy();
    }
}