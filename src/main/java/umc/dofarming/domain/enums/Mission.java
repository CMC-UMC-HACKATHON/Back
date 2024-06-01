package umc.dofarming.domain.enums;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

import static umc.dofarming.domain.enums.Category.*;

@Getter
@RequiredArgsConstructor
public enum Mission {

    COMMON1("스크린샷 인증하기", "스크린타임을 인증해주세요.", COMMON),
    COMMON2("경제신문 스크랩 1개", "경제 신문 기사 하나를 요약하고, 의견을 덧붙여서 캡처해주세요. ", COMMON),
    COMMON3("명상 15분", "명상 음악을 들으면서 명상을 하고 인증샷을 첨부해주세요.", COMMON),
    COMMON4("대청소", "대청소를 하고 인증샷을 첨부해주세요.", COMMON),
    CULTURE1("클래식 음악 앨범 처음부터 끝까지 듣기", "desc3", CULTURE),
    CULTURE2("독서 20분 도전!", "독서를 20분 하고 인증샷을 첨부해주세요.", CULTURE),
    CULTURE3("경제신문 스크랩 1개", "경제 신문 기사 하나를 요약하고, 의견을 덧붙여서 캡처해주세요. ", CULTURE),
    EXERCISE1("팔굽혀펴기 10회", "장소에 상관없이 팔굽혀펴기를 10번 하고 인증샷을 올려주세요", EXERCISE),
    EXERCISE2("런닝 30분", "런닝 30분 달성 후 운동 앱 스크린샷을 첨부해주세요.", EXERCISE),
    EXERCISE3("테니스 1시간", "테니스를 치고 라켓과 함께 인증샷을 첨부해주세요", EXERCISE),
    DAILY1("하루 세끼 잘 챙겨먹기", "먹은 음식 인증샷을 첨부해주세요. ", DAILY),
    DAILY2("주말에도 아침을 알차게!", "9시 이전에 일어난 후 인증샷을 첨부해주세요.", DAILY),
    DAILY3("간단한 요리를 해봅시다. ", "직접 요리를 하고 인증샷을 첨부해주세요.", DAILY);

    private final String title;
    private final String description;
    private final Category category;
}