package year2017.day23;

class Register {
    private final String name;
    private Long value;

    Register(String name) {
        this.name = name;
        this.value = 0L;
    }

    String getName() {
        return name;
    }

    public Long getValueOrZero() {
        return value != null ? value : 0L;
    }

    void setValue(Long value) {
        this.value = value;
    }
}
