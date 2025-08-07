package stirling.software.SPDF.model.api.security;

import io.swagger.v3.oas.annotations.media.Schema;

import lombok.Data;
import lombok.EqualsAndHashCode;

import stirling.software.common.model.api.PDFFile;

@Data
@EqualsAndHashCode(callSuper = true)
public class RemoveWatermarkRequest extends PDFFile {

    @Schema(description = "The watermark text", defaultValue = "Stirling Software")
    private String watermarkText;
}
