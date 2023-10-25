package graph.extractInfo.utils;
import java.text.DecimalFormat;

public class CouplingCalculator {

    private final int totalBinaryMethodRelations;

    public CouplingCalculator(int totalBinaryMethodRelations) {
        this.totalBinaryMethodRelations = totalBinaryMethodRelations;
    }


    public float couplingBetweenTwoClasses(
            int incomingRelationships,
            int outgoingRelationships
    ) {
        DecimalFormat df = new DecimalFormat("0.0000");
        String formattedResult = df.format((float) (incomingRelationships + outgoingRelationships) / totalBinaryMethodRelations);

        // Remplacer la virgule par un point pour obtenir un format de nombre à virgule flottante valide
        formattedResult = formattedResult.replace(',', '.');

        return Float.parseFloat(formattedResult);
    }


}
