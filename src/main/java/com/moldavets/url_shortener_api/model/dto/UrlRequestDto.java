package com.moldavets.url_shortener_api.model.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Value;
import org.hibernate.validator.constraints.URL;

@Data
@AllArgsConstructor
public class UrlRequestDto {
    @JsonProperty(value = "long_url")
    @NotNull
    @NotEmpty
    @Size(max = 765)
    @Pattern(regexp = "https?:\\/\\/(www\\.)?[-a-zA-Z0-9@:%._\\+~#=]{1,256}\\.[a-zA-Z0-9()]{1,6}\\b([-a-zA-Z0-9()@:%_\\+.~#?&//=]*)")
    private String longUrl;
}
