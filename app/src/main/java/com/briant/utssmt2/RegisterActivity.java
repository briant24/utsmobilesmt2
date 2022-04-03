package com.briant.utssmt2;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.app.DatePickerDialog;
import android.content.ContentResolver;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.net.Uri;
import android.os.Bundle;
import android.view.Gravity;
import android.view.View;
import android.webkit.MimeTypeMap;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.textfield.TextInputEditText;
import com.google.android.material.textfield.TextInputLayout;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.OnProgressListener;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

import java.util.Calendar;

public class RegisterActivity extends AppCompatActivity {
    private TextInputLayout tvnama, tvemail, tvnohp, tvtgl, tvalamat, tvbank, tvcatatan, tvnorek;
    TextInputEditText tanggal;
    ImageView imageView;
    ProgressBar progressBar;
    Button browse,save;
    private String nama,email,nohp,tgl,alamat,bank,catatan,norek;
    StorageReference storageReference = FirebaseStorage.getInstance().getReference();
    DatabaseReference databaseReference =FirebaseDatabase.getInstance().getReference();
    Uri imageUri;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
        tvnama = findViewById(R.id.txt_nama);
        tvemail = findViewById(R.id.txt_email);
        tvnohp = findViewById(R.id.txt_nohp);
        tvtgl = findViewById(R.id.txt_tgl);
        tanggal = findViewById(R.id.inputtgl);
        tvalamat = findViewById(R.id.txt_alamat);
        tvbank = findViewById(R.id.txt_bank);
        tvnorek = findViewById(R.id.txt_norek);
        tvcatatan = findViewById(R.id.txt_catatan);
        imageView = findViewById(R.id.imageView);
        progressBar = findViewById(R.id.progressBar);
        browse = findViewById(R.id.btn_browse);
        save = findViewById(R.id.btn_save);
        Calendar calendar = Calendar.getInstance();
        final int year = calendar.get(Calendar.YEAR);
        final int month = calendar.get(Calendar.MONTH);
        final int day = calendar.get(Calendar.DAY_OF_MONTH);

        tanggal.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DatePickerDialog datePickerDialog = new DatePickerDialog(RegisterActivity.this, new DatePickerDialog.OnDateSetListener() {
                    @Override
                    public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
                        month = month+1;
                        String date = dayOfMonth+"/"+month+"/"+year;
                        tanggal.setText(date);
                    }
                },year,month,day);
                datePickerDialog.show();
            }
        });

        save.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                nama = tvnama.getEditText().getText().toString();
                email = tvemail.getEditText().getText().toString();
                nohp = tvnohp.getEditText().getText().toString();
                tgl = tvtgl.getEditText().getText().toString();
                alamat = tvalamat.getEditText().getText().toString();
                bank = tvbank.getEditText().getText().toString();
                norek = tvnorek.getEditText().getText().toString();
                catatan = tvcatatan.getEditText().getText().toString();
                if (nama.isEmpty()){
                    tvnama.requestFocus();
                    return;
                }
                else if (nohp.isEmpty()){
                    tvnohp.requestFocus();
                    return;
                }
                else if (alamat.isEmpty()){
                    tvalamat.requestFocus();
                    return;
                }
                else if (email.isEmpty()){
                    tvemail.requestFocus();
                    return;
                }
                else if (tgl.isEmpty()){
                    tvtgl.requestFocus();
                    return;
                }
                else if (bank.isEmpty()){
                    tvbank.requestFocus();
                    return;
                }
                else if (norek.isEmpty()){
                    tvnohp.requestFocus();
                    return;
                }
                else if (catatan.isEmpty()){
                    tvcatatan.requestFocus();
                    return;
                }
                else if (imageUri == null){
                    Toast toast = Toast.makeText(getApplicationContext(),"Gambar Kosong",Toast.LENGTH_SHORT);
                    toast.setGravity(Gravity.TOP|Gravity.CENTER_HORIZONTAL,0,0);
                    toast.show();
                }
                else{
                    upload(imageUri);
                }
            }
        });

        browse.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                pilihgambar();
            }
        });
    }

    private void upload(Uri imageUri) {
        nama = tvnama.getEditText().getText().toString();
        email = tvemail.getEditText().getText().toString();
        nohp = tvnohp.getEditText().getText().toString();
        tgl = tvtgl.getEditText().getText().toString();
        alamat = tvalamat.getEditText().getText().toString();
        bank = tvbank.getEditText().getText().toString();
        norek = tvnorek.getEditText().getText().toString();
        catatan = tvcatatan.getEditText().getText().toString();
        StorageReference fileref = storageReference.child(System.currentTimeMillis()+"."+getFileExtension(this.imageUri));
        fileref.putFile(this.imageUri).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
            @Override
            public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                fileref.getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
                    @Override
                    public void onSuccess(Uri uri) {
                        UploadUser uploadUser = new UploadUser(email,tgl);
                        UploadPelanggan uploadPelanggan = new UploadPelanggan(nama,nohp,alamat,email,tgl,bank,norek,catatan,uri.toString());
                        String modelId = databaseReference.push().getKey();
                        databaseReference.child("Users").child(modelId).setValue(uploadUser);
                        databaseReference.child("MasterPelanggan").child(modelId).setValue(uploadPelanggan);
                        Toast.makeText(getApplicationContext(), "Sukses Upload", Toast.LENGTH_SHORT).show();
                        progressBar.setVisibility(View.GONE);
                        Intent intent = new Intent(getApplicationContext(), LoginActivity.class);
                        intent.putExtra("email",email);
                        startActivity(intent);
                        bersih();
                    }
                });
            }
        }).addOnProgressListener(new OnProgressListener<UploadTask.TaskSnapshot>() {
            @Override
            public void onProgress(@NonNull UploadTask.TaskSnapshot snapshot) {
                progressBar.setVisibility(View.VISIBLE);
            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                progressBar.setVisibility(View.GONE);
                Toast.makeText(getApplicationContext(), "Gagal", Toast.LENGTH_SHORT).show();
            }
        });
    }


    private String getFileExtension(Uri mUri){
        ContentResolver cr = getContentResolver();
        MimeTypeMap mime = MimeTypeMap.getSingleton();
        return mime.getExtensionFromMimeType(cr.getType(mUri));
    }

    private void pilihgambar() {
        Intent gallerryIntent = new Intent();
        gallerryIntent.setAction(Intent.ACTION_GET_CONTENT);
        gallerryIntent.setType("image/*");
        startActivityForResult(gallerryIntent,2);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == 2 && resultCode == RESULT_OK && data != null){
            imageUri = data.getData();
            imageView.setImageURI(imageUri);
        }
    }

    private void bersih(){
        tvnama.getEditText().setText("");
        tvnohp.getEditText().setText("");
        tvalamat.getEditText().setText("");
        tvemail.getEditText().setText("");
        tvtgl.getEditText().setText("");
        tvbank.getEditText().setText("");
        tvnorek.getEditText().setText("");
        tvcatatan.getEditText().setText("");
        imageView.setImageURI(null);
    }
}