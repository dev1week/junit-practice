package com.junit.practice.ch4.test.domain;

import com.junit.practice.ch4.test.domain.redundant.User;
import org.junit.jupiter.api.*;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;
import org.junit.jupiter.params.provider.NullAndEmptySource;
import org.junit.jupiter.params.provider.ValueSource;

import java.util.ArrayList;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.junit.jupiter.api.Assertions.*;
import static org.junit.jupiter.api.DynamicTest.dynamicTest;

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

    //repeatedTest어노테이션 활용입니다.
    //info객체를 인자로 받으며 내부에서 반복값과 전체 반복 횟수에 접근할 수 있습니다.
    //또한 성공 횟수와 실패 횟수도 활용할 수 있습니다.
    @RepeatedTest(value=10)
    void repeatedTestExample(RepetitionInfo info){
        System.out.println(info.getCurrentRepetition()+"/"+info.getTotalRepetitions());
        System.out.println("실패횟수 = "+info.getFailureCount());
    }

    //@TestFactory 예제입니다.
    //반환 타입이 void가 아닌 Collections 타입입니다.
    //반복하면서 dynamicTest 메서드를 통해서 N개의 테스트 메서드를 생성하고 실행합니다.

    //CsvSource 어노테이션은 컴파일 시점에 값이 정해지지만, 이 어노테이션은 런타임 시점에 변수와 실행 코드 모두 동적으로 주입가능합니다.
        //db에서 가져오는 데이터, 외부 api와 연동하는 경우 (단위 테스트가 아닌 e2e 테스트에서 많이 활용합니다.)
    @TestFactory
    List<DynamicNode> testFactoryEx(){
        int size = 10;
        List<DynamicNode> result = new ArrayList<>();

        for(int i=0; i<size; i++){
            int finalI = i;
            result.add(dynamicTest(i+"Test", ()->System.out.println(finalI)));
        }

        return result;
    }

}