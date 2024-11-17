package game.domain.item;

import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public enum ItemType {
    BEAR(new Bear(), "bear"),
    CIGARETTE_PACK(new CigarettePack(), "cigarette pack"),
    EXPIRED_MEDICINE(new ExpiredMedicine(), "expired medicine"),
    HAND_CUFFS(new Handcuffs(), "handcuffs"),
    INVERTER(new Inverter(), "inverter"),
    MAGNIFYING_GLASS(new MagnifyingGlass(), "magnifying glass"),
    SHOT_GUN(new ShotGun(), "shot gun");

    private static final Map<String, ItemType> NAME_TO_ENUM =
            Stream.of(values()).collect(Collectors.toMap(ItemType::getName, e -> e));

    public final Item instance;
    public final String name;

    private ItemType(Item instance, String name) {
        this.instance = instance;
        this.name = name;
    }

    public Item getInstance() {
        return instance;
    }

    public String getName() {
        return name;
    }

    public static Optional<Item> getInstanceByName(String name) {
        return Optional.ofNullable(NAME_TO_ENUM.get(name.toLowerCase()))
                .map(ItemType::getInstance);
    }
}

