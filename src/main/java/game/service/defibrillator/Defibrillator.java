package game.service.defibrillator;

import static game.domain.LifeAndDeath.LIFE;

import game.config.StageDependency;
import game.dto.PlayerDataDto;

public class Defibrillator {
    private boolean isDealersDefibrillatorWorking;
    private boolean isChallengerDefibrillatorWorking;

    public Defibrillator(boolean isDealersDefibrillatorWorking, boolean isChallengerDefibrillatorWorking) {
        this.isDealersDefibrillatorWorking = isDealersDefibrillatorWorking;
        this.isChallengerDefibrillatorWorking = isChallengerDefibrillatorWorking;
    }

    //Todo: PlayerDataDto 에 Role 을 넣어서 메서드를 하나로 만들 수 있음

    public PlayerDataDto tryUsingDealersDefibrillator(PlayerDataDto dealer) {
        if(isDealersDefibrillatorWorking) {
            return new PlayerDataDto(dealer.healthPoint(), dealer.items(), LIFE);
        }
        return dealer;
    }

    public PlayerDataDto tryUsingChallengerDefibrillator(PlayerDataDto challenger) {
        if(isChallengerDefibrillatorWorking) {
            return new PlayerDataDto(challenger.healthPoint(), challenger.items(), LIFE);
        }
        return challenger;
    }

    public void initializeDefibrillator() {
        isDealersDefibrillatorWorking = true;
        isChallengerDefibrillatorWorking = true;
    }

    public void tryToBreakDefibrillators(PlayerDataDto challenger, PlayerDataDto dealer, StageDependency stageDependency) {
        if(stageDependency.isBreakingDefibrillatorsCondition(challenger)) {
            isChallengerDefibrillatorWorking = false;
        }
        if(stageDependency.isBreakingDefibrillatorsCondition(dealer)) {
            isDealersDefibrillatorWorking = false;
        }
    }
}
