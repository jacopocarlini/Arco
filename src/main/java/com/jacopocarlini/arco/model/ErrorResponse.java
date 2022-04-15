package com.jacopocarlini.arco.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

import javax.validation.constraints.Max;
import javax.validation.constraints.Min;

/**
 * Object returned as response in case of an error.
 * <p> See {@link com.jacopocarlini.arco.exception.ErrorHandler}
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@ToString
@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonIgnoreProperties(ignoreUnknown = true)
public class ErrorResponse {

    @JsonProperty("title")
    private String title;

    @JsonProperty("status")
    @Min(100)
    @Max(600)
    private Integer status;

    @JsonProperty("detail")
    private String detail;

    @JsonProperty("code")
    private Long code;
}
