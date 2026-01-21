package com.example.projectY.response;

import java.util.List;

import com.example.projectY.utils.constants.LevelEnum;

import lombok.Data;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Data
public class ResJobEmailDTO {
    private Long id;
    private String name;
    private ResCompanyEmailDTO company;
    private LevelEnum level;
    private String location;
    private List<ResSkillEmailDTO> skills;

    public ResJobEmailDTO() {

    }

    @Getter
    @Setter
    @Data
    public static class ResCompanyEmailDTO {
        private String name;
        public ResCompanyEmailDTO() {

        }
    }

    @Getter
    @Setter
    @Data
    public static class ResSkillEmailDTO {
        private String name;

        public ResSkillEmailDTO() {

        }
    }

    // public static class ResLevel
}
