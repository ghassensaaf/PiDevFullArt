package entite;

import java.util.Objects;

public class artiste extends user{

    private int id_artiste;
    private String description;
    private String couverture;

    public int getId_artiste() {
        return id_artiste;
    }

    public void setId_artiste(int id_artiste) {
        this.id_artiste = id_artiste;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getCouverture() {
        return couverture;
    }

    public void setCouverture(String couverture) {
        this.couverture = couverture;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        artiste artiste = (artiste) o;
        return id_artiste == artiste.id_artiste;
    }

    @Override
    public int hashCode() {
        return Objects.hash(id_artiste);
    }
}
