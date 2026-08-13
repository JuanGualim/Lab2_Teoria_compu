/** Representa un estado del AFN. */
public class Estado {
    private final int id;

    public Estado(int id) {
        if (id < 0) {
            throw new IllegalArgumentException("El id del estado no puede ser negativo.");
        }
        this.id = id;
    }

    public int getId() {
        return id;
    }

    @Override
    public String toString() {
        return "q" + id;
    }
}
