package entite;

import java.util.Objects;

public class client extends user{

    private int id_client;


    public int getId_client() {
        return id_client;
    }

    public void setId_client(int id_client) {
        this.id_client = id_client;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        client client = (client) o;
        return id_client == client.id_client;
    }

    @Override
    public int hashCode() {
        return Objects.hash(id_client);
    }
}

