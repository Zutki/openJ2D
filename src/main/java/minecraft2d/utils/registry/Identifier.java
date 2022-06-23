package minecraft2d.utils.registry;

public class Identifier {
    private String name;
    private String namespace;

    public String getName() {
        return name;
    }

    public String getNamespace() {
        return namespace;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setNamespace(String namespace) {
        this.namespace = namespace;
    }
}
