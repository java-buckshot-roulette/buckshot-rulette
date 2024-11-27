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
    @DisplayName("1, 2가 아닌 다른 숫자를 넣었을 때 예외 처리")
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

        // Act
        GameState result = app.selectMenu();

        verify(mockOutputView, times(1)).println("잘못된 입력입니다. 다시 입력해주세요.\n"); // 출력 메시지 확인
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

        GameState result = app.selectMenu();

        verify(mockOutputView, times(1)).println("숫자를 입력해주세요"); // 출력 메시지 확인
    }
}
