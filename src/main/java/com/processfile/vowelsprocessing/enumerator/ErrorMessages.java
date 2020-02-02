package com.processfile.vowelsprocessing.enumerator;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum ErrorMessages {

    UNEXPECTED_ERROR("Unexpected error happened, please call application support for more details"),

    ERROR_BUILD_WORD_INFO_DTO("Error to build word info dto"),
    ERROR_CALCULATE_AVERAGE("Error to calculate average"),
    ERROR_CREATE_OUTPUT_FILE("Error to create output file"),
    ERROR_CREATE_WORD_GROUPED("Error to create word grouped"),
    ERROR_DELETE_FILE("Error to delete file"),
    ERROR_FILE_IS_EMPTY("Source file is empty"),
    ERROR_MONITOR_SERVICE("Error to start monitor service"),
    ERROR_READ_FILE("Error to read file"),
    ERROR_UPDATE_WORD_GROUPED("Error to update word grouped");








    private String message;

}
