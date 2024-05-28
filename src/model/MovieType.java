package model;

public enum MovieType {
    REGULAR("regular"),
    CHILDRENS("childerns"),
    NEW("new");

    private final String value;


    MovieType(String value) {
        this.value = value;
    }

    public String getValue() {
        return this.value;
    }

}
