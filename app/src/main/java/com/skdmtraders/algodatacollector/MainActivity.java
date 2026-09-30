package com.skdmtraders.algodatacollector;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;
import java.io.File;
import java.io.FileOutputStream;
import java.nio.charset.StandardCharsets;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

public class MainActivity extends AppCompatActivity {
    private EditText instrument, timeframe, open, high, low, close, volume, premium, velocity, acceleration, notes;
    private TextView timeText, status;
    private final String fileName = "algo_data.csv";

    @Override protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        instrument=findViewById(R.id.instrument); timeframe=findViewById(R.id.timeframe);
        open=findViewById(R.id.open); high=findViewById(R.id.high); low=findViewById(R.id.low);
        close=findViewById(R.id.close); volume=findViewById(R.id.volume); premium=findViewById(R.id.premium);
        velocity=findViewById(R.id.velocity); acceleration=findViewById(R.id.acceleration);
        notes=findViewById(R.id.notes); timeText=findViewById(R.id.timeText); status=findViewById(R.id.status);
        updateTime();
        ((Button)findViewById(R.id.saveButton)).setOnClickListener(v -> saveRecord());
        ((Button)findViewById(R.id.exportButton)).setOnClickListener(v -> exportCsv());
    }

    private String now() {
        return new SimpleDateFormat("yyyy-MM-dd HH:mm:ss", Locale.getDefault()).format(new Date());
    }
    private void updateTime() { timeText.setText("Time: " + now()); }
    private String value(EditText e) { return e.getText().toString().trim().replace(",", " "); }

    private File dataFile() {
        File dir=getExternalFilesDir(Environment.DIRECTORY_DOCUMENTS);
        if (dir != null && !dir.exists()) dir.mkdirs();
        return new File(dir, fileName);
    }

    private void saveRecord() {
        try {
            File file=dataFile();
            boolean header=!file.exists() || file.length()==0;
            StringBuilder row=new StringBuilder();
            if(header) row.append("timestamp,instrument,timeframe,open,high,low,close,volume,premium,velocity,acceleration,notes
");
            row.append(now()).append(",").append(value(instrument)).append(",").append(value(timeframe)).append(",")
               .append(value(open)).append(",").append(value(high)).append(",").append(value(low)).append(",")
               .append(value(close)).append(",").append(value(volume)).append(",").append(value(premium)).append(",")
               .append(value(velocity)).append(",").append(value(acceleration)).append(",").append(value(notes)).append("
");
            try(FileOutputStream out=new FileOutputStream(file,true)) {
                out.write(row.toString().getBytes(StandardCharsets.UTF_8));
            }
            status.setText("Saved: " + file.getName());
            Toast.makeText(this,"Record saved",Toast.LENGTH_SHORT).show();
            updateTime();
        } catch(Exception e) {
            Toast.makeText(this,"Save failed: "+e.getMessage(),Toast.LENGTH_LONG).show();
        }
    }

    private void exportCsv() {
        File file=dataFile();
        if(!file.exists()) {
            Toast.makeText(this,"No CSV records yet",Toast.LENGTH_SHORT).show();
            return;
        }
        Intent i=new Intent(Intent.ACTION_SEND);
        i.setType("text/csv");
        i.putExtra(Intent.EXTRA_SUBJECT,"Algo Data Collector CSV");
        i.putExtra(Intent.EXTRA_STREAM,Uri.parse(file.toURI().toString()));
        startActivity(Intent.createChooser(i,"Export CSV"));
    }
}
