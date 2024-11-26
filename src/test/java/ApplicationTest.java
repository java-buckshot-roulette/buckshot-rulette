import static org.mockito.Mockito.*;

import game.Application;
import game.view.input.InputView;
import game.view.output.OutputView;
import org.junit.jupiter.api.Test;

import java.io.IOException;

class ApplicationTest {

    @Test
    void shouldPrintErrorMessageWhenInputIs3() throws IOException {
        // Arrange
        OutputView mockOutputView = mock(OutputView.class); // OutputView를 Mock 객체로 생성
        InputView mockInputView = mock(InputView.class);   // InputView를 Mock 객체로 생성

        // Mock InputView의 행동 설정: "3" 입력 후 "2"를 입력해 종료
        when(mockInputView.askPersonToSelect())
                .thenReturn("3") // 첫 번째 입력: 잘못된 입력
                .thenReturn("1"); // 두 번째 입력: 종료

        Application app = new Application();
        app.inputView = mockInputView; // Mock InputView를 애플리케이션에 주입
        app.outputView = mockOutputView; // Mock OutputView를 애플리케이션에 주입

        // Act
        app.run();

        // Assert
        // "잘못된 입력입니다. 다시 입력해주세요." 메시지가 출력되었는지 검증
        verify(mockOutputView, atLeastOnce()).println("잘못된 입력입다. 다시 입력해주세요.\n");
    }
}
