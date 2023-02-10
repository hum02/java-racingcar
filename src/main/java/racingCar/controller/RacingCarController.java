package racingCar.controller;

import racingCar.domain.CarGroup;
import racingCar.util.NumberGenerator;
import racingCar.util.RandomNumberGenerator;
import racingCar.view.InputView;
import racingCar.view.OutputView;

import java.util.List;
import java.util.function.Supplier;

public class RacingCarController {

    public void run() {
        CarGroup carGroup = repeat(this::makeCars);
        Integer tryCount = repeat(InputView::readTryCount);
        playRacing(new RandomNumberGenerator(), tryCount, carGroup);
        OutputView.printWinners(carGroup.findWinners());
    }

    private CarGroup makeCars() {
        List<String> carNames = repeat(InputView::readCarNames);
        return new CarGroup(carNames);
    }

    private void playRacing(NumberGenerator numberGenerator, int tryCount, CarGroup carGroup) {
        for (int i = 0; i < tryCount; i++) {
            carGroup.moveCars(numberGenerator);
            OutputView.printCarPosition(carGroup.toCarDtos());
        }
    }

    private static <T> T repeat(Supplier<T> reader) {
        try {
            return reader.get();
        } catch (Exception e) {
            OutputView.printError(e.getMessage());
            return repeat(reader);
        }
    }
}
