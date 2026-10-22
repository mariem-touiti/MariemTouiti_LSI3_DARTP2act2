
import java.io.Serializable;

public class Operation implements Serializable {

    private double op1;
    private String operateur;
    private double op2;

    public Operation(double op1, String operateur, double op2) {
        this.op1 = op1;
        this.operateur = operateur;
        this.op2 = op2;
    }

    public double getOp1() {
        return op1;
    }

    public String getOperateur() {
        return operateur;
    }

    public double getOp2() {
        return op2;
    }
}

