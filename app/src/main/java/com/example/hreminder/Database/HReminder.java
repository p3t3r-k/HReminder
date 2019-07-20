package com.example.hreminder.Database;

import androidx.annotation.NonNull;
import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;
import androidx.room.TypeConverters;

import com.example.hreminder.BehindTheScenes.DateConverter;

import org.jetbrains.annotations.NotNull;

@Entity(tableName = "HReminder")
@TypeConverters(DateConverter.class)
public class HReminder {

    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = "id")
    private int id;

    @ColumnInfo(name = "username")
    private String username;

    @ColumnInfo(name = "pin")
    private String pin;

    @ColumnInfo(name = "fingerprint")
    private boolean fingerprint;

    @ColumnInfo(name = "pro_gender")
    private String gender;

    @ColumnInfo(name = "pro_birthdate")
    private long birthdate;

    @ColumnInfo(name = "pro_weight")
    private float weight;

    @ColumnInfo(name = "heart")
    private boolean heart;

    @ColumnInfo(name = "neuro")
    private boolean neuro;

    @ColumnInfo(name = "ortho")
    private boolean ortho;

    @ColumnInfo(name = "derma")
    private boolean derma;

    @ColumnInfo(name = "eyes")
    private boolean eyes;

    @ColumnInfo(name = "ears")
    private boolean ears;

    @ColumnInfo(name = "smoke")
    private boolean smoke;

    @ColumnInfo(name = "allergy")
    private boolean allergy;


    public HReminder(String username, String pin, boolean fingerprint, String gender, long birthdate, float weight,
                     boolean heart, boolean neuro, boolean ortho, boolean derma, boolean eyes, boolean ears, boolean smoke, boolean allergy) {
        this.username = username;
        this.pin = pin;
        this.fingerprint = fingerprint;
        this.gender = gender;
        this.birthdate = birthdate;
        this.weight = weight;
        this.heart = heart;
        this.neuro = neuro;
        this.ortho = ortho;
        this.derma = derma;
        this.eyes = eyes;
        this.ears = ears;
        this.smoke = smoke;
        this.allergy = allergy;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getUsername() {
        return username;
    }

    public String getPin() {
        return pin;
    }

    public boolean isFingerprint() {
        return fingerprint;
    }

    public String getGender() {
        return gender;
    }

    public long getBirthdate() {
        return birthdate;
    }

    public float getWeight() {
        return weight;
    }

    public boolean isHeart() {
        return heart;
    }

    public boolean isNeuro() {
        return neuro;
    }

    public boolean isOrtho() {
        return ortho;
    }

    public boolean isDerma() {
        return derma;
    }

    public boolean isEyes() {
        return eyes;
    }

    public boolean isEars() {
        return ears;
    }

    public boolean isSmoke() {
        return smoke;
    }

    public boolean isAllergy() {
        return allergy;
    }



    @NotNull
    @Override
    public String toString() {
        return username + "\n" + pin;
    }
}
