package com.example.hreminder.Activities;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;
import androidx.room.TypeConverters;

import java.io.Serializable;
import java.util.Date;

import io.reactivex.annotations.NonNull;

@Entity(tableName = "HReminder")

public class HReminder implements Serializable {

    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = "id")
    private int id;

    @ColumnInfo(name = "username")
    private String username;

    @ColumnInfo(name = "pin")
    private int pin;

    @ColumnInfo(name = "fingerprint")
    private boolean fingerprint;

    @ColumnInfo(name = "pro_gender")
    private String gender;

    @TypeConverters(DateConverter.class)
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


    public HReminder(String username, int pin, boolean fingerprint, String gender, long birthdate, float weight,
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

    public void setUsername(String username) {
        this.username = username;
    }

    public int getPin() {
        return pin;
    }

    public void setPin(int pin) {
        this.pin = pin;
    }

    public boolean isFingerprint() {
        return fingerprint;
    }

    public void setFingerprint(boolean fingerprint) {
        this.fingerprint = fingerprint;
    }

    public String getGender() {
        return gender;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }

    public long getBirthdate() {
        return birthdate;
    }

    public void setBirthdate(long birthdate) {
        this.birthdate = birthdate;
    }

    public float getWeight() {
        return weight;
    }

    public void setWeight(float weight) {
        this.weight = weight;
    }

    public boolean isHeart() {
        return heart;
    }

    public void setHeart(boolean heart) {
        this.heart = heart;
    }

    public boolean isNeuro() {
        return neuro;
    }

    public void setNeuro(boolean neuro) {
        this.neuro = neuro;
    }

    public boolean isOrtho() {
        return ortho;
    }

    public void setOrtho(boolean ortho) {
        this.ortho = ortho;
    }

    public boolean isDerma() {
        return derma;
    }

    public void setDerma(boolean derma) {
        this.derma = derma;
    }

    public boolean isEyes() {
        return eyes;
    }

    public void setEyes(boolean eyes) {
        this.eyes = eyes;
    }

    public boolean isEars() {
        return ears;
    }

    public void setEars(boolean ears) {
        this.ears = ears;
    }

    public boolean isSmoke() {
        return smoke;
    }

    public void setSmoke(boolean smoke) {
        this.smoke = smoke;
    }

    public boolean isAllergy() {
        return allergy;
    }

    public void setAllergy(boolean allergy) {
        this.allergy = allergy;
    }


    @Override
    public String toString() {
        return new StringBuilder(username).append("\n").append(pin).toString();
    }
}
