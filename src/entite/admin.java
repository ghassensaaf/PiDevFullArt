package entite;

import java.util.Objects;

public class admin extends user{

    private int id_admin;
    private String image;

    public int getId_admin() {
        return id_admin;
    }

    public void setId_admin(int id_admin) {
        this.id_admin = id_admin;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        admin admin = (admin) o;
        return id_admin == admin.id_admin;
    }

    @Override
    public int hashCode() {
        return Objects.hash(id_admin);
    }
}
