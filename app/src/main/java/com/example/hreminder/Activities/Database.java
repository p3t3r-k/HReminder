package com.example.hreminder.Activities;

import android.arch.persistence.room.ColumnInfo;
import android.arch.persistence.room.Entity;
import android.arch.persistence.room.PrimaryKey;

import java.util.Date;

import io.reactivex.annotations.NonNull;

@Entity(tableName = "HReminder")
public class Database {

    @NonNull
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

    @ColumnInfo(name = "pro_birthdate")
    private Date birthdate;

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

    public Database() {

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

    public Date getBirthdate() {
        return birthdate;
    }

    public void setBirthdate(Date birthdate) {
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
