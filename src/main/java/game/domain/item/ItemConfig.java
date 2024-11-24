package game.domain.item;

public class ItemConfig {
    public final static Item BEAR = new Bear();
    public final static Item CIGARETTE_PACK = new CigarettePack();
    public final static Item EXPIRED_MEDICINE = new ExpiredMedicine();
    public final static Item HAND_CUFFS = new Handcuffs();
    public final static Item HAND_SAW = new HandSaw();
    public final static Item INVERTER = new Inverter();
    public final static Item MAGNIFYING_GLASS = new MagnifyingGlass();
    //Todo: ItemConfig 역할을 ItemType 에서 사용하도록 리펙토링
}