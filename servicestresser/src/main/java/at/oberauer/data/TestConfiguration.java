package at.oberauer.data;

/**
 * Created by michael on 07.01.18.
 */

public class TestConfiguration {

    public enum Multiplicity{
        Single,
        Multiple
    }
    public enum TargetService{
        Gateway,
        Analytics,
        Webcrawler
    }

    private Multiplicity multiplicity;
    private TargetService targetService;

    public Multiplicity getMultiplicity() {
        return multiplicity;
    }

    public void setMultiplicity(Multiplicity multiplicity) {
        this.multiplicity = multiplicity;
    }

    public TargetService getTargetService() {
        return targetService;
    }

    public void setTargetService(TargetService targetService) {
        this.targetService = targetService;
    }
}
