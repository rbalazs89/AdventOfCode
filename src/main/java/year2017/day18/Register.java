package year2017.day18;

class Register {
    private String name;
    private Long value;

    Register(String name) {
        this.name = name;
        this.value = 0L;
    }

    String getName() {
        return name;
    }

    void setName(String name) {
        this.name = name;
    }

    Long getValue() {
        if(value == null){
            System.out.println("???? " + name);
        }
        return value;
    }

    void setValue(Long value) {
        this.value = value;
    }
}
