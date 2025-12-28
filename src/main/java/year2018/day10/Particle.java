package year2018.day10;

public class Particle {
    private final int vx;
    private final int vy;
    private int px;
    private int py;

    Particle(int px, int py, int vx, int vy){
        this.px = px;
        this.py = py;
        this.vx = vx;
        this.vy = vy;
    }

    void incrementParticlePosition(){
        px += vx;
        py += vy;
    }

    void decrementParticlePosition(){
        px -= vx;
        py -= vy;
    }

    int getPy() {
        return py;
    }

    int getPx() {
        return px;
    }
}
