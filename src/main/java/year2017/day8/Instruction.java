package year2017.day8;

import java.util.Set;

record Instruction(Register affectedRegister, String instructionType, int affectingNumber, Register comparedRegister,
                   String comparisonType, int comparisonNumber) {

    final static Set<String> instructionTypes = Set.of("inc", "dec");
    final static Set<String> comparisonTypes = Set.of("<", "<=", "==", ">", ">=", "!=");

    // example line: "c inc -20 if c == 10"
    Instruction {
        if (!instructionTypes.contains(instructionType)) {
            throw new IllegalStateException("not recignized instruction type" + instructionType);
        }
        if (!comparisonTypes.contains(comparisonType)) {
            throw new IllegalStateException("not recognized comparison type" + comparisonType);
        }
    }
}
