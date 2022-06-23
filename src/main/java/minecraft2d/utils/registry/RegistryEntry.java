package minecraft2d.utils.registry;

public class RegistryEntry {
    private Identifier identifier;
    private Object registryObject;

    public RegistryEntry(Identifier identifier, Object registryObject) {
        this.identifier = identifier;
        this.registryObject = registryObject;
    }

    public void setIdentifier(Identifier identifier) {
        this.identifier = identifier;
    }

    public void setRegistryObject(Object registryObject) {
        this.registryObject = registryObject;
    }

    public Identifier getIdentifier() {
        return identifier;
    }

    public Object getRegistryObject() {
        return registryObject;
    }
}
