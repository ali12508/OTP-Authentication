package app.my.otpverification;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

public class ProfileActivity extends AppCompatActivity {
    DatabaseReference mref;
    FirebaseAuth auth;
    FirebaseStorage storage;
    ImageView imageView;
    Uri uri;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);
        mref=FirebaseDatabase.getInstance().getReference().child("Users");
        auth=FirebaseAuth.getInstance();
        storage=FirebaseStorage.getInstance();



      imageView=findViewById(R.id.profile_pic);
        TextView name=findViewById(R.id.name);
        TextView bioo=findViewById(R.id. bio);
        Button button=findViewById(R.id.btnsave);

        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {




              if(uri!=null){
                 StorageReference storageref=storage.getReference().child("Profile").child(auth.getUid());
                  storageref.putFile(uri).addOnCompleteListener(new OnCompleteListener<UploadTask.TaskSnapshot>() {
                      @Override
                      public void onComplete(@NonNull Task<UploadTask.TaskSnapshot> task) {
                          if(task.isSuccessful()){
                              storageref.getDownloadUrl().addOnCompleteListener(new OnCompleteListener<Uri>() {
                                  @Override
                                  public void onComplete(@NonNull Task<Uri> task) {
                                      String img=uri.toString();
                                      String pno =auth.getCurrentUser().getPhoneNumber();
                                      String uname=name.getText().toString();
                                      String ubio=bioo.getText().toString();
                                      User user= new User(uname,img,pno,ubio);
                                     FirebaseDatabase.getInstance().getReference().child("Users").push().setValue(user).addOnSuccessListener(new OnSuccessListener<Void>() {
                                         @Override
                                         public void onSuccess(Void unused) {
                                             startActivity(new Intent(ProfileActivity.this,MainActivity.class));
                                         }
                                     });

                                  }
                              });
                          }
                      }
                  });
              }
            }
        });

        imageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent();
                intent.setAction ( Intent.ACTION_PICK );
                intent.setType (  "image/*" );
                startActivityForResult ( intent, 25);

            }
        });



    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(data.getData()!=null){
            imageView.setImageURI(data.getData());
            uri=data.getData();


        }
    }
}