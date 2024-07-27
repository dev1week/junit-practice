package com.junit.practice.ch4.test.domain;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;
import org.junit.jupiter.params.provider.NullAndEmptySource;
import org.junit.jupiter.params.provider.ValueSource;

import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.junit.jupiter.api.Assertions.*;

class UserTest {

    @Test
    @DisplayName("User의 이름이 Null이면 예외를 발생시킨다.")
    void userNameNullThenException(){
        assertThatThrownBy(()-> new User(null))
                .isInstanceOf(IllegalArgumentException.class);
    }

    @Test
    @DisplayName("User의 이름이 공백이면 예외를 발생시킨다.")
    void userNameIsBlankThenException(){
        assertThatThrownBy(()-> new User(""))
                .isInstanceOf(IllegalArgumentException.class);
    }

    //위 두가지 테스트를 ParameterizedTest 어노테이션을 이용해 간소화 할 수 있습니다.
    //코드의 인자값만 다르고 실제 구현부가 같은 테스트 코드들에 적용해볼 수 있습니다.
    @ParameterizedTest
    @ValueSource(strings = {"  ", "    "})
    @NullAndEmptySource
    void parameterizedTestEx(String userName){
        //출력확인용입니다. 실제 테스트 코드에서는 제외해야합니다.
        System.out.println(userName);

        assertThatThrownBy(()-> new User(userName))
                .isInstanceOf(IllegalArgumentException.class);
    }


    //@CsvSource 어노테이션을 활용해 입력과 출력이 주어지는 테스트 또한 만들 수 있습니다.
    //다음 예제는 구구단 중 2단을 실행해보는 예제입니다.
    @ParameterizedTest
    @CsvSource(value = {"1,2", "2,4", "3,6"})
    void csvSourceEx(int input, int expected){
        assertEquals(expected, input*2);
    }
    //구분자를 커스터마이징하는 것 또한 가능합니다.
    @ParameterizedTest
    @CsvSource(value = {"1|2", "2|4", "3|6"}, delimiter = '|')
    void csvSourceEx2(int input, int expected){
        assertEquals(expected, input*2);
    }

    @ParameterizedTest
    @CsvSource(value = {"1||2", "2||4", "3||6"}, delimiterString = "||")
    void csvSourceEx3(int input, int expected){
        assertEquals(expected, input*2);
    }
}