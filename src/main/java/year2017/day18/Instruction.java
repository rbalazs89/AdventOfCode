package year2017.day18;

class Instruction {

    final Long longAttributeOne;
    final Register regAttributeOne;
    final Long longAttributeTwo;
    final Register regAttributeTwo;
    final String type;
    // set
    // add
    // mul
    // mod
    // snd
    // set
    // rcv
    // jgz

    Instruction(Long longAttributeOne, Register regAttributeOne, Long longAttributeTwo, Register regAttributeTwo, String type) {
        this.longAttributeOne = longAttributeOne;
        this.regAttributeOne = regAttributeOne;
        this.longAttributeTwo = longAttributeTwo;
        this.regAttributeTwo = regAttributeTwo;
        this.type = type;
    }
}
