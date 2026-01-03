package year2023.day25;

class Wire {

    //wire is symmetrical
    int visited = 0;
    Component component1;
    Component component2;

   Wire(Component component1, Component component2) {
        this.component1 = component1;
        this.component2 = component2;
    }
}