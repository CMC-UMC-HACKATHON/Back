package umc.dofarming.domain.enums;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

import static umc.dofarming.domain.enums.Category.*;

@Getter
@RequiredArgsConstructor
public enum Mission {

    MI1("title1", "desc1", COMMON),
    MI2("title2", "desc2", CULTURE),
    MI3("title3", "desc3", DAILY),
    MI4("title4", "desc4", EXERCISE);

    private final String title;
    private final String description;
    private final Category category;
}