package game.domain.item;

import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public enum ItemType {
    BEAR(new Bear(), "맥주"),
    CIGARETTE_PACK(new CigarettePack(), "담배"),
    EXPIRED_MEDICINE(new ExpiredMedicine(), "상한약"),
    HAND_CUFFS(new Handcuffs(), "수갑"),
    INVERTER(new Inverter(), "인버터"),
    MAGNIFYING_GLASS(new MagnifyingGlass(), "돋보기"),
    SHOT_GUN(new ShotGun(), "총");

    private static final Map<String, ItemType> NAME_TO_ENUM =
            Stream.of(values()).collect(Collectors.toMap(ItemType::getName, e -> e));

    private final Item instance;
    private final String name;

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

