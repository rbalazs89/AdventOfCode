package year2017.day21;

import java.util.ArrayList;

class EnhancePattern {

    final char[][] inputPattern;
    final char[][] enhancedPattern;

    EnhancePattern(char[][] inputPattern, char[][] enhancedPattern){
        this.inputPattern = inputPattern;
        this.enhancedPattern = enhancedPattern;
    }

    char[][] implementEnhancement(){
        return enhancedPattern;
    }

    boolean isMatching(char[][] currentPattern) {
        // precheck if length match
        if (currentPattern.length != inputPattern.length) {
            throw new IllegalStateException("wrong pattern applied");
        }

        return isExactMatch(currentPattern)
                || isMirroredMatch(currentPattern)
                || isTurnedUpsideDownMatch(currentPattern)
                || isRotatedBy180Match(currentPattern)
                || isRotatedBy90ClockwiseMatch(currentPattern)
                || isRotatedBy270ClockwiseMatch(currentPattern)
                || isMainDiagonalMirrorMatch(currentPattern)
                || isAntiDiagonalMirrorMatch(currentPattern);
    }

    private boolean isExactMatch(char[][] currentPattern){
        for (int i = 0; i < currentPattern.length; i++) {
            for (int j = 0; j < currentPattern[i].length; j++) {
                if(inputPattern[i][j] != currentPattern[i][j]){
                    return false;
                }
            }
        }
        return true;
    }

    private boolean isTurnedUpsideDownMatch(char[][] currentPattern){
        for (int i = 0; i < currentPattern.length; i++) {
            for (int j = 0; j < currentPattern[i].length; j++) {
                if(inputPattern[i][j] != currentPattern[currentPattern.length - 1 - i][j]){ // Fixed
                    return false;
                }
            }
        }
        return true;
    }


    private boolean isMirroredMatch(char[][] currentPattern){
        for (int i = 0; i < currentPattern.length; i++) {
            for (int j = 0; j < currentPattern[i].length; j++) {
                if(inputPattern[i][j] != currentPattern[i][currentPattern[i].length - j - 1]){
                    return false;
                }
            }
        }
        return true;
    }

    private boolean isRotatedBy180Match(char[][] currentPattern){
        for (int i = 0; i < currentPattern.length; i++) {
            for (int j = 0; j < currentPattern[i].length; j++) {
                if(inputPattern[i][j] != currentPattern[currentPattern.length - i - 1][currentPattern[i].length - j - 1]){
                    return false;
                }
            }
        }
        return true;
    }

    private boolean isRotatedBy90ClockwiseMatch(char[][] currentPattern) {
        int n = currentPattern.length;
        for (int i = 0; i < n; i++) {
            for (int j = 0; j < n; j++) {
                // 90 deg clockwise
                if (inputPattern[i][j] != currentPattern[j][n - 1 - i]) {
                    return false;
                }
            }
        }
        return true;
    }

    private boolean isRotatedBy270ClockwiseMatch(char[][] currentPattern) {
        int n = currentPattern.length;
        for (int i = 0; i < n; i++) {
            for (int j = 0; j < n; j++) {
                // 270 deg clockwise
                if (inputPattern[i][j] != currentPattern[n - 1 - j][i]) {
                    return false;
                }
            }
        }
        return true;
    }

    private boolean isMainDiagonalMirrorMatch(char[][] currentPattern) {
        int n = currentPattern.length;
        for (int i = 0; i < n; i++) {
            for (int j = 0; j < n; j++) {
                // Reflection over main diagonal transpose
                if (inputPattern[i][j] != currentPattern[j][i]) {
                    return false;
                }
            }
        }
        return true;
    }

    private boolean isAntiDiagonalMirrorMatch(char[][] currentPattern) {
        int n = currentPattern.length;
        for (int i = 0; i < n; i++) {
            for (int j = 0; j < n; j++) {
                // reflection over anti-diagonal:
                if (inputPattern[i][j] != currentPattern[n - 1 - j][n - 1 - i]) {
                    return false;
                }
            }
        }
        return true;
    }
}
