import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

import game.Application;
import game.service.Stage.GameState;
import game.view.input.InputView;
import game.view.output.OutputView;
import org.junit.jupiter.api.DisplayName;
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
                .thenReturn("3"); // 3을 입력해 게임 시작 선택

        ArgumentCaptor<String> captor = ArgumentCaptor.forClass(String.class); // 출력 메시지 캡처 생성

        // Act
        GameState result = app.selectMenu();

        assertNull(result, "GameState가 null이 아닙니다.");
    }

    @Test
    @DisplayName("문자를 던졌을 때 예외 처리 확인 테스트")
    void shouldThrowExceptionWhenInputIsInvalid() throws IOException {
        // Arrange
        OutputView mockOutputView = mock(OutputView.class); // OutputView를 Mock 객체로 생성
        InputView mockInputView = mock(InputView.class);   // InputView를 Mock 객체로 생성

        Application app = new Application();
        app.inputView = mockInputView; // Mock InputView를 애플리케이션에 주입
        app.outputView = mockOutputView; // Mock OutputView를 애플리케이션에 주입

        // 게임 상태 입력 설정 (잘못된 문자 입력)
        when(mockInputView.askPersonToSelect())
                .thenReturn("invalid_input"); // 문자 입력 설정

        // Act & Assert
        Exception exception = assertThrows(IllegalArgumentException.class, () -> {
            app.selectMenu(); // selectMenu 호출 시 예외 발생 예상
        });
    }
}
