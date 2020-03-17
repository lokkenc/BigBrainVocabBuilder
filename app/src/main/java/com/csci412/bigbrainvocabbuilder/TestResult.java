package com.csci412.bigbrainvocabbuilder;

public class TestResult {
    private char grade;
    private int testsTaken;
    private int numCorrect;

    public char getGrade() {
        return grade;
    }

    public int getTestsTaken() {
        return testsTaken;
    }

    public int getNumCorrect() {
        return numCorrect;
    }
    public void setTestsTaken(int testsTaken) {
        this.testsTaken = testsTaken;
    }
    public void testIsCorrect() {
        numCorrect++;
    }
    public void setGrade() {
        double percent = ((double)numCorrect / (double)testsTaken) * 100;
        if (percent >=90) {
            grade = 'A';
        } else if (percent >= 80) {
            grade = 'B';
        } else if (percent >= 70) {
            grade = 'C';
        } else if (percent >= 60) {
            grade = 'D';
        } else {
            grade = 'F';
        }
    }
}
