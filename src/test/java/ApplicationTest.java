import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.fail;
import static org.mockito.Mockito.*;

import game.Application;
import game.service.Stage.GameState;
import game.view.input.InputView;
import game.view.output.OutputView;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import org.mockito.ArgumentCaptor;

class ApplicationTest {

    @Test
    void shouldPrintErrorMessageWhenInputIs3() throws IOException {
        // Arrange
        OutputView mockOutputView = mock(OutputView.class); // OutputView를 Mock 객체로 생성
        InputView mockInputView = mock(InputView.class);   // InputView를 Mock 객체로 생성

        Application app = new Application();
        app.inputView = mockInputView; // Mock InputView를 애플리케이션에 주입
        app.outputView = mockOutputView; // Mock OutputView를 애플리케이션에 주입

        // 게임 상태 입력 설정
        when(mockInputView.askPersonToSelect())
                .thenReturn("3"); // 1을 입력해 게임 시작 선택

        ArgumentCaptor<String> captor = ArgumentCaptor.forClass(String.class); // 출력 메시지 캡처 생성

        // Act
        GameState result = app.selectMenu(3);

        // Assert
        verify(mockOutputView, atLeastOnce()).println(captor.capture()); // 출력된 메시지를 캡처
    }
}
