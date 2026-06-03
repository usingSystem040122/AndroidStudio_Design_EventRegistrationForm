package com.example.crud;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.Menu;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.Toast;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity implements AdapterView.OnItemSelectedListener {

    DatabaseHelper dbHelper;
    EditText txtName, txtEmail;
    Spinner cboSelection;
    ArrayList<Person> personList;
    PersonAdapter personAdapter;
    Button updateBtn, deleteBtn;
    Person selectedPerson;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        this.txtName = (EditText) this.findViewById(R.id.txtName);
        this.txtEmail = (EditText) this.findViewById(R.id.txtEmail);
        this.cboSelection = (Spinner) this.findViewById(R.id.spinner);
        this.updateBtn =(Button) findViewById(R.id.updateBtn);
        this.deleteBtn =(Button) findViewById(R.id.deleteBtn);
        ListView listView = findViewById(R.id.listView);

        personList = new ArrayList<>();
        personAdapter = new PersonAdapter(this, personList);
        listView.setAdapter(personAdapter);
        listView.setOnItemLongClickListener((parent, view, position, id) -> {
            selectedPerson = personList.get(position);
            updateBtn.setVisibility(View.VISIBLE);
            deleteBtn.setVisibility(View.VISIBLE);
            txtName.setText(selectedPerson.getName());
            txtEmail.setText(selectedPerson.getEmail());
            return true;
        });

        ArrayAdapter<CharSequence> spinnerAdapter = ArrayAdapter.createFromResource(this, R.array.status, android.R.layout.simple_spinner_item);
        spinnerAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        cboSelection.setAdapter(spinnerAdapter);
        cboSelection.setOnItemSelectedListener(this);

        Button saveBtn = (Button) findViewById(R.id.saveBtn);
        saveBtn.setOnClickListener(v -> {
            String newName = txtName.getText().toString().trim();
            String newEmail = txtEmail.getText().toString().trim();
            if (!newName.isEmpty() && !newEmail.isEmpty()) {
                Person person = new Person(newName, newEmail);
                personList.add(person);
                personAdapter.notifyDataSetChanged();
                Toast.makeText(getBaseContext(), "Person added", Toast.LENGTH_SHORT).show();
                txtName.setText("");
                txtEmail.setText("");
            }
            else {
                Toast.makeText(getBaseContext(), "Please enter name and email", Toast.LENGTH_SHORT).show();
            }
        });

        listView.setOnItemClickListener((parent, view, position, id) -> {
            Person selectedPerson = personList.get(position);
            showOptionsDialog(selectedPerson, position);
            Toast.makeText(this, "Selected: " + selectedPerson.getName() + " - " + selectedPerson.getEmail(), Toast.LENGTH_SHORT).show();
        });

    }

    private void showOptionsDialog(Person person, int position) {
        String[] options = {"Update", "Delete"};
        new AlertDialog.Builder(this)
                .setTitle("Choose an action")
                .setItems(options, (dialog, which) -> {
                    if (which == 0) {
                        // Update
                        showUpdateDialog(person, position);
                    } else if (which == 1) {
                        // Delete
                        personList.remove(position);
                        personAdapter.notifyDataSetChanged();
                        Toast.makeText(this, "Person deleted", Toast.LENGTH_SHORT).show();
                    }
                })
                .show();
    }

    private void showUpdateDialog(Person person, int position) {
        View dialogView = getLayoutInflater().inflate(R.layout.dialog_update, null);
        EditText edtName = dialogView.findViewById(R.id.edtName);
        EditText edtEmail = dialogView.findViewById(R.id.edtEmail);

        edtName.setText(person.getName());
        edtEmail.setText(person.getEmail());

        new AlertDialog.Builder(this)
                .setTitle("Update Person")
                .setView(dialogView)
                .setPositiveButton("Update", (dialog, which) -> {
                    String newName = edtName.getText().toString().trim();
                    String newEmail = edtEmail.getText().toString().trim();

                    if (!newName.isEmpty() && !newEmail.isEmpty()) {
                        person.setName(newName);
                        person.setEmail(newEmail);
                        personAdapter.notifyDataSetChanged();
                        Toast.makeText(this, "Person updated", Toast.LENGTH_SHORT).show();
                    } else {
                        Toast.makeText(this, "Fields cannot be empty", Toast.LENGTH_SHORT).show();
                    }
                })
                .setNegativeButton("Cancel", null)
                .show();
    }

    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
        String text = parent.getItemAtPosition(position).toString();
        Toast.makeText(parent.getContext(), text, Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onNothingSelected(AdapterView<?> parent) {

    }

}