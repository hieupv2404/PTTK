package inventory.enums;

public enum EnumForDB {
    USERNAME("root"),
    PASSWORD("ult.zda1");

    private final String value;

    EnumForDB(String value) {
        this.value = value;
    }

    public String getValue() {
        return value;
    }
}
