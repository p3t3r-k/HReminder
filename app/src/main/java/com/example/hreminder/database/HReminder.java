package com.example.hreminder.database;

/*@Entity(tableName = "HReminder")
public class HReminder {

    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = "id")
    private int id;

    @ColumnInfo(name = "username")
    private final String username;

    @ColumnInfo(name = "pin")
    private final String pin;

    @ColumnInfo(name = "fingerprint")
    private final boolean fingerprint;

    @ColumnInfo(name = "pro_gender")
    private final String gender;

    @ColumnInfo(name = "pro_birthdate")
    private final long birthdate;

    @ColumnInfo(name = "pro_weight")
    private final float weight;

    @ColumnInfo(name = "heart")
    private final boolean heart;

    @ColumnInfo(name = "neuro")
    private final boolean neuro;

    @ColumnInfo(name = "ortho")
    private final boolean ortho;

    @ColumnInfo(name = "derma")
    private final boolean derma;

    @ColumnInfo(name = "eyes")
    private final boolean eyes;

    @ColumnInfo(name = "ears")
    private final boolean ears;

    @ColumnInfo(name = "smoke")
    private final boolean smoke;

    @ColumnInfo(name = "allergy")
    private final boolean allergy;


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
*/