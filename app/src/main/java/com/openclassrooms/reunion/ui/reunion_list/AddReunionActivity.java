package com.openclassrooms.reunion.ui.reunion_list;

import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.fragment.app.FragmentActivity;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.google.android.material.button.MaterialButton;
import com.google.android.material.textfield.TextInputLayout;
import com.openclassrooms.reunion.R;
import com.openclassrooms.reunion.di.DI;
import com.openclassrooms.reunion.model.Reunion;
import com.openclassrooms.reunion.service.ReunionApiService;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class AddReunionActivity<nameParticipantInput> extends AppCompatActivity {


    @BindView(R.id.inom_Reunion)
    public TextInputLayout nameInput;
    @BindView(R.id.intitule_Reunion)
    public TextInputLayout intituleInput;
    @BindView(R.id.idate_Reunion)
    public TextInputLayout dateInput;
    @BindView(R.id.inom_salle_Reunion)
    public TextInputLayout nomSalleInput;
    @BindView(R.id.iparticipant_Reunion)
    public TextInputLayout nameParticipantInput;
    @BindView(R.id.create)
    public MaterialButton addButton;


    String nameR= String.valueOf(nameInput.getEditText().getText());
    String intituleR= String.valueOf(intituleInput.getEditText().getText());
    String dateR= String.valueOf(dateInput.getEditText().getText());
    String nomSalleR= String.valueOf(nomSalleInput.getEditText().getText());
    List <String> participantR= (List<String>) nameParticipantInput.getEditText().getText();

    Intent bundle= getIntent();
    int id=bundle.getIntExtra("id",-1);

    private ReunionApiService mApiService;
  //  private String mNeighbourImage;

    Reunion mReunion= new Reunion(id, nameR, dateR, nomSalleR, participantR);

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_reunion);
        ButterKnife.bind(this);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        mApiService = DI.getReunionApiService();
        init();
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home : {
                finish();
                return true;
            }
        }
        return super.onOptionsItemSelected(item);
    }

    private void init() {
     //   mNeighbourImage = randomImage();
     //   Glide.with(this).load(mNeighbourImage).placeholder(R.drawable.ic_account)
     //           .apply(RequestOptions.circleCropTransform()).into(avatar);
         nameInput.getEditText().addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) { }
            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) { }
            @Override
            public void afterTextChanged(Editable s) {
                addButton.setEnabled(s.length() > 0);
            }
        });

    addButton.setOnClickListener(new View.OnClickListener() {
        @Override
        public void onClick(View view) {
            mApiService.createReunion(mReunion);
            finish();
        }
    });


    }

    /**
     * Generate a random image. Useful to mock image picker
     * @return String
     */
    String randomImage() {
        return "https://i.pravatar.cc/150?u="+ System.currentTimeMillis();
    }

    /**
     * Used to navigate to this activity
     * @param activity
     */
    public static void navigate(FragmentActivity activity) {
        Intent intent = new Intent(activity, AddReunionActivity.class);
        ActivityCompat.startActivity(activity, intent, null);
    }
}
