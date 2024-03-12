package final_classes.part16_sealed_class.sealed;

public sealed abstract class SpecialAbstractClass permits FinalKid,
        NonSealedKid, SealedKid, SpecialAbstractClass.Kid {

    final class Kid extends SpecialAbstractClass{

    }
}
