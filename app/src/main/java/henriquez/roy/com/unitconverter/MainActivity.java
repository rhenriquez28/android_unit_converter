package henriquez.roy.com.unitconverter;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import java.util.ArrayList;
import java.util.Arrays;

public class MainActivity extends AppCompatActivity {

    //Variables globales
    String[] types, units;
    int type, unit1, unit2;
    String unitFinal;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        //Se inicializa types para obtener el contenido del arreglo de los tipos de unidades
        types = getResources().getStringArray(R.array.type_array);
        //Se hace referencia al spinner del tipo
        Spinner typeSpinner = findViewById(R.id.typeSpinner);
        //Se crea el adapter
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this,
                android.R.layout.simple_spinner_item, types);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        //Se asigna el adapter
        typeSpinner.setAdapter(adapter);
        //Referencias a los dos spinners de unidades
        Spinner unit1Spinner = findViewById(R.id.unit1Spinner);
        Spinner unit2Spinner = findViewById(R.id.unit2Spinner);
        //Referencias a los demas Views
        final EditText data = findViewById(R.id.data);
        Button send = findViewById(R.id.send);
        final TextView results = findViewById(R.id.result);
        //TextView para la visualizacion de resultado esta invisible porque no tiene contenido aun
        results.setVisibility(View.INVISIBLE);
        
        //Creacion de listeners
        typeSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                //Se obtiene la posicion del item selecionado
                int index=parent.getSelectedItemPosition();
                //Se guarda para utilizarlo en el listener de send
                type = index;
                //Se ejecuta el cambio de contenido de los spinners de unidades
                SwitchSpinnersContent(type);
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        unit1Spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                //Se obtiene la posicion del item selecionado
                int index=parent.getSelectedItemPosition();
                //Se le asigna la posicion a la variable para uso en los otros metodos
                unit1 = index;
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        unit2Spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                //Se obtiene la posicion del item selecionado
                int index=parent.getSelectedItemPosition();
                //Se le asigna la posicion a la variable para uso en los otros metodos
                unit2 = index;
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });


        send.setOnClickListener(new View.OnClickListener() {
            //Variables para contener el resultado de la conversion y el mensaje final
            String result, message;
            @Override
            public void onClick(View v) {
                //Se obtiene el dato del EditText y se convierte en double
                double dataFinal = Double.parseDouble(data.getText().toString());
                //Se utiliza la posicion guardada en unit2 para buscar el string del mismo
                unitFinal = units[unit2];
                //Dependiendo del tipo de unidad escogido, se ejecuta el metodo de conversion adecuado
                if (type == 0){
                    result = Double.toString(LenghtConverter(dataFinal, unit1, unit2));
                }else if (type == 1){
                    result = Double.toString(WeightConverter(dataFinal, unit1, unit2));
                    }else if (type == 2){
                    result = Double.toString(TemperatureConverter(dataFinal, unit1, unit2));
                    }
                //Se crea el mensaje final
                message = getResources().getString(R.string.resultMessage) + " " + result + " " + unitFinal;
                //Se asigna el mensaje final al TextView
                results.setText(message);
                //Una vez que el TextView tiene contenido, se hace visible
                if (results.getVisibility() == View.INVISIBLE){
                    results.setVisibility(View.VISIBLE);
                }
            }

        });
    }

    //Metodo utilizado para cambiar el contenido de los otros dos spinners
    // dinamicamente dependiendo del item escogido en typeSpinner
    public void SwitchSpinnersContent(int type){
        //Se crean referencias a los spinners
        Spinner unit1Spinner = findViewById(R.id.unit1Spinner);
        Spinner unit2Spinner = findViewById(R.id.unit2Spinner);
        //Se le asigna a units el arrays de longitud porque es valor por defecto en typeSpinner
        units = getResources().getStringArray(R.array.longitud_array);
        //Se crea un ArrayList para poder modificar el contenido
        ArrayList<String> lst = new ArrayList<String>(Arrays.asList(units));
        //Se crea el adapter
        ArrayAdapter<String> unitsAdapter = new ArrayAdapter<>(this,
                android.R.layout.simple_spinner_item, lst);
        unitsAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        //Se asigna el adapter
        unit1Spinner.setAdapter(unitsAdapter);
        unit2Spinner.setAdapter(unitsAdapter);
        //Se modifica el valor inicial para mostrar en el segundo spinner
        // para que no se el mismo que el primero
        unit2Spinner.setSelection(1);
        //Array usado para hacer el cambio de contenido
        String[] temp;
        //Dependiendo del tipo...
        switch (type){
            case 0:
                //A temp se le asigna el array de contenido anterior
                temp = units;
                //A units se le asigna el array de contenido nuevo
                units = getResources().getStringArray(R.array.longitud_array);
                //Se remueven todos los items del array anterior en el adapter de los spinners
                for (int i = 0; i<temp.length; i++){
                    unitsAdapter.remove(temp[i]);
                }
                //Se agregan todos los items del array nuevo en el adapter de los spinners
                for (int i = 0; i<units.length; i++){
                    unitsAdapter.add(units[i]);
                }
                //Se le notifica al adapter que su lista cambio y por lo tanto, actualiza los spinners
                unitsAdapter.notifyDataSetChanged();
                break;
            case 1:
                temp = units;
                units = getResources().getStringArray(R.array.peso_array);
                for (int i = 0; i<temp.length; i++){
                    unitsAdapter.remove(temp[i]);
                }
                for (int i = 0; i<units.length; i++){
                    unitsAdapter.add(units[i]);
                }
                unitsAdapter.notifyDataSetChanged();
                break;
            case 2:
                temp = units;
                units = getResources().getStringArray(R.array.temperatura_array);
                for (int i = 0; i<temp.length; i++){
                    unitsAdapter.remove(temp[i]);
                }
                for (int i = 0; i<units.length; i++){
                    unitsAdapter.add(units[i]);
                }
                unitsAdapter.notifyDataSetChanged();
                break;
        }
    }

    //Metodo usado para la conversion de unidades de longitud
    public double LenghtConverter(double data, int unit1, int unit2){
        /*
        Para referencia:
        0 = cm
        1 = m
        2 = km
        3 = in
        4 = ft
        5 = mi
        */
        double result = 0;
        switch (unit1){
            case 0:
                if (unit2 == 0){
                    result = data;
                }else if (unit2 == 1){
                    result = data * 0.01;
                }else if (unit2 == 2){
                    result = data * 0.00001;
                }else if (unit2 == 3){
                    result = data * 0.393701;
                }else if (unit2 == 4){
                    result = data * 0.0328084;
                }else if (unit2 == 5){
                    result = data * 0.0000062137;
                }
                break;
            case 1:
                if (unit2 == 1){
                    result = data;
                }else if (unit2 == 0){
                    result = data * 100;
                }else if (unit2 == 2){
                    result = data * 0.001;
                }else if (unit2 == 3){
                    result = data * 39.3701;
                }else if (unit2 == 4){
                    result = data * 3.28084;
                }else if (unit2 == 5){
                    result = data * 0.00062137;
                }
                break;
            case 2:
                if (unit2 == 2){
                    result = data;
                }else if (unit2 == 1){
                    result = data * 1000;
                }else if (unit2 == 0){
                    result = data * 100000;
                }else if (unit2 == 3){
                    result = data * 39370.1;
                }else if (unit2 == 4){
                    result = data * 3280.84;
                }else if (unit2 == 5){
                    result = data * 0.62137;
                }
                break;
            case 3:
                if (unit2 == 3){
                    result = data;
                }else if (unit2 == 1){
                    result = data * 0.0254;
                }else if (unit2 == 2){
                    result = data * 0.0000254;
                }else if (unit2 == 0){
                    result = data * 2.54;
                }else if (unit2 == 4){
                    result = data * 0.0833333;
                }else if (unit2 == 5){
                    result = data * 0.00001578282197;
                }
                break;
            case 4:
                if (unit2 == 4){
                    result = data;
                }else if (unit2 == 1){
                    result = data * 0.3048;
                }else if (unit2 == 2){
                    result = data * 0.0003048;
                }else if (unit2 == 3){
                    result = data * 12;
                }else if (unit2 == 0){
                    result = data * 30.48;
                }else if (unit2 == 5){
                    result = data * 0.000189394;
                }
                break;
            case 5:
                if (unit2 == 5){
                    result = data;
                }else if (unit2 == 1){
                    result = data * 1609.34;
                }else if (unit2 == 2){
                    result = data * 1.60934;
                }else if (unit2 == 3){
                    result = data * 63360;
                }else if (unit2 == 4){
                    result = data * 5280;
                }else if (unit2 == 0){
                    result = data * 160934;
                }
                break;
        }
        return result;
    }

    //Metodo usado para la conversion de unidades de peso
    public double WeightConverter(double data, int unit1, int unit2){
        /*
        Para referencia:
        0 = g
        1 = kg
        2 = oz
        3 = lb
        */
        double result = 0;
        switch (unit1){
            case 0:
                if (unit2 == 0){
                    result = data;
                }else if (unit2 == 1){
                    result = data * 0.001;
                }else if (unit2 == 2){
                    result = data * 0.035274;
                }else if (unit2 == 3){
                    result = data * 0.00220462;
                }
                break;
            case 1:
                if (unit2 == 1){
                    result = data;
                }else if (unit2 == 0){
                    result = data * 1000;
                }else if (unit2 == 2){
                    result = data * 35.274;
                }else if (unit2 == 3){
                    result = data * 2.20462;
                }
                break;
            case 2:
                if (unit2 == 2){
                    result = data;
                }else if (unit2 == 1){
                    result = data * 0.0283495;
                }else if (unit2 == 0){
                    result = data * 28.3495;
                }else if (unit2 == 3){
                    result = data * 0.0625;
                }
                break;
            case 3:
                if (unit2 == 3){
                    result = data;
                }else if (unit2 == 0){
                    result = data * 453.592;
                }else if (unit2 == 1){
                    result = data * 0.453592;
                }else if (unit2 == 2){
                    result = data * 16;
                }
                break;
        }
        return result;
    }

    //Metodo usado para la conversion de unidades de temperatura
    public double TemperatureConverter(double data, int unit1, int unit2){
        /*
        Para referencia:
        0 = ºC
        1 = ºF
        */
        double result = 0;
        switch (unit1){
            case 0:
                if (unit2 == 0){
                    result = data;
                }else if (unit2 == 1){
                    result = data * 1.8 + 32;
                }
                break;
            case 1:
                if (unit2 == 1){
                    result = data;
                }else if (unit2 == 0){
                    result = (data - 32) / 1.8;
                }
                break;
        }
        return result;
    }
    
}
